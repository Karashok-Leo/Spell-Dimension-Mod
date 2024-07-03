package karashokleo.spell_dimension.content.recipe;

import com.google.gson.JsonObject;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.misc.AttrModifier;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.content.misc.EnchantedModifier;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.Map;

public class EnchantedEssenceRecipe extends ShapedRecipe
{
    public static final String NAME = "enchanted_essence";
    public static final String GRADE_KEY = "grade";
    public static final String THRESHOLD_KEY = "threshold";
    public static final String SLOT_KEY = "slot";
    public static final String SCHOOL_KEY = "school";
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
    final int threshold;
    final EquipmentSlot slot;
    final SpellSchool school;

    public EnchantedEssenceRecipe(Identifier id, int grade, int threshold, EquipmentSlot slot, SpellSchool school)
    {
        super(id, "", CraftingRecipeCategory.MISC, getWidth(slot), getHeight(slot), getIngredients(slot, school, grade), AllItems.ENCHANTED_ESSENCE.getStack(
                new EnchantedModifier(
                        threshold,
                        slot,
                        new AttrModifier(
                                school.attribute,
                                UuidUtil.getEquipUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                1.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                )
        ));
        this.grade = grade;
        this.threshold = threshold;
        this.slot = slot;
        this.school = school;
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

    public static DefaultedList<Ingredient> getIngredients(EquipmentSlot slot, SpellSchool school, int tier)
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
        return AllItems.ENCHANTED_ESSENCE.getStack(
                new EnchantedModifier(
                        threshold,
                        slot,
                        new AttrModifier(
                                school.attribute,
                                UuidUtil.getEquipUuid(slot, EntityAttributeModifier.Operation.ADDITION),
                                1.0,
                                EntityAttributeModifier.Operation.ADDITION
                        )
                )
        );
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
            int grade = JsonHelper.getInt(json, GRADE_KEY);
            int threshold = JsonHelper.getInt(json, THRESHOLD_KEY);
            EquipmentSlot slot = EquipmentSlot.valueOf(JsonHelper.getString(json, SLOT_KEY));
            SpellSchool school = SpellSchools.getSchool(JsonHelper.getString(json, SCHOOL_KEY));
            return new EnchantedEssenceRecipe(id, grade, threshold, slot, school);
        }

        @Override
        public EnchantedEssenceRecipe read(Identifier id, PacketByteBuf buf)
        {
            int grade = buf.readVarInt();
            int threshold = buf.readVarInt();
            EquipmentSlot slot = buf.readEnumConstant(EquipmentSlot.class);
            SpellSchool school = SpellSchools.getSchool(buf.readString());
            return new EnchantedEssenceRecipe(id, grade, threshold, slot, school);
        }

        @Override
        public void write(PacketByteBuf buf, EnchantedEssenceRecipe recipe)
        {
            buf.writeVarInt(recipe.grade);
            buf.writeVarInt(recipe.threshold);
            buf.writeEnumConstant(recipe.slot);
            buf.writeString(recipe.school.id.toString());
        }
    }
}
