package net.karashokleo.spelldimension.recipe;

import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.item.ExtraModifier;
import net.karashokleo.spelldimension.misc.Mage;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.spell_power.api.MagicSchool;

import java.util.Map;

public class EnchantedEssenceRecipe extends ShapedRecipe
{
    public static final String NAME = "enchanted_essence";
    public static final Map<EquipmentSlot, String[]> PATTERNS = Map.of(
            EquipmentSlot.MAINHAND, new String[]{" ##",
                    "###",
                    "## "},
            EquipmentSlot.OFFHAND, new String[]{"## ",
                    "###",
                    " ##"},
            EquipmentSlot.HEAD, new String[]{"###",
                    "# #"},
            EquipmentSlot.CHEST, new String[]{"# #",
                    "###",
                    "###"},
            EquipmentSlot.LEGS, new String[]{"###",
                    "# #",
                    "# #"},
            EquipmentSlot.FEET, new String[]{"# #",
                    "# #"}
    );

    final int grade;
    final MagicSchool school;
    final EquipmentSlot slot;
    final int threshold;
    final Identifier attributeId;

    public EnchantedEssenceRecipe(Identifier id, int grade, MagicSchool school, EquipmentSlot slot, int threshold, Identifier attributeId)
    {
        super(id, "", CraftingRecipeCategory.MISC, getWidth(slot), getHeight(slot), getIngredients(slot, school, grade), AllItems.ENCHANTED_ESSENCE.getStack(new Mage(grade, school, null), new ExtraModifier(threshold, slot, attributeId)));
        this.grade = grade;
        this.school = school;
        this.slot = slot;
        this.threshold = threshold;
        this.attributeId = attributeId;
    }

    public static int getHeight(EquipmentSlot slot)
    {
        String[] pattern = PATTERNS.get(slot);
        return pattern.length;
    }

    public static int getWidth(EquipmentSlot slot)
    {
        String[] pattern = PATTERNS.get(slot);
        return pattern[0].length();
    }

    public static DefaultedList<Ingredient> getIngredients(EquipmentSlot slot, MagicSchool school, int tier)
    {
        String[] pattern = PATTERNS.get(slot);
        int height = getHeight(slot);
        int width = getWidth(slot);
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(height * width, Ingredient.EMPTY);
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (pattern[i].charAt(j) == ' ')
                    defaultedList.set(i * width + j, Ingredient.EMPTY);
                else
                    defaultedList.set(i * width + j, Ingredient.ofItems(AllItems.BASE_ESSENCES.get(school).get(tier)));
        return defaultedList;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager)
    {
        return AllItems.ENCHANTED_ESSENCE.getStack(new Mage(grade, school, null), new ExtraModifier(threshold, slot, attributeId));
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<EnchantedEssenceRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = SpellDimension.modLoc(NAME);

        @Override
        public EnchantedEssenceRecipe read(Identifier id, JsonObject json)
        {
            int grade = JsonHelper.getInt(json, "grade");
            MagicSchool school = MagicSchool.valueOf(JsonHelper.getString(json, "school"));
            EquipmentSlot slot = EquipmentSlot.valueOf(JsonHelper.getString(json, "slot"));
            int threshold = JsonHelper.getInt(json, "threshold");
            Identifier attributeId = new Identifier(JsonHelper.getString(json, "attributeId"));
            return new EnchantedEssenceRecipe(id, grade, school, slot, threshold, attributeId);
        }

        @Override
        public EnchantedEssenceRecipe read(Identifier id, PacketByteBuf buf)
        {
            int grade = buf.readVarInt();
            MagicSchool school = buf.readEnumConstant(MagicSchool.class);
            EquipmentSlot slot = buf.readEnumConstant(EquipmentSlot.class);
            int threshold = buf.readVarInt();
            Identifier attributeId = buf.readIdentifier();
            return new EnchantedEssenceRecipe(id, grade, school, slot, threshold, attributeId);
        }

        @Override
        public void write(PacketByteBuf buf, EnchantedEssenceRecipe recipe)
        {
            buf.writeVarInt(recipe.grade);
            buf.writeEnumConstant(recipe.school);
            buf.writeEnumConstant(recipe.slot);
            buf.writeVarInt(recipe.threshold);
            buf.writeIdentifier(recipe.attributeId);
        }
    }
}
