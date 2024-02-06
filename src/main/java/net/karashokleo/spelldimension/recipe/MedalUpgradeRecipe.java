package net.karashokleo.spelldimension.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.misc.Mage;
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

public class MedalUpgradeRecipe extends ShapedRecipe
{
    public static final String NAME = "medal_upgrade";
    private static final String ORIGINAL_KEY = "original";
    private static final String UPGRADED_KEY = "upgraded";
    final Mage original;
    final Mage upgraded;

    public MedalUpgradeRecipe(Identifier id, DefaultedList<Ingredient> input, Mage original, Mage upgraded)
    {
        super(id, "", CraftingRecipeCategory.MISC, 3, 3, input, AllItems.MAGE_MEDAL.getStack(upgraded));
        this.getIngredients().set(4, Ingredient.ofStacks(AllItems.MAGE_MEDAL.getStack(original)));
        this.original = original;
        this.upgraded = upgraded;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager)
    {
        return AllItems.MAGE_MEDAL.getStack(this.upgraded);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<MedalUpgradeRecipe>
    {
        public static final EnchantedEssenceRecipe.Serializer INSTANCE = new EnchantedEssenceRecipe.Serializer();
        public static final Identifier ID = SpellDimension.modLoc(NAME);
        public static final JsonObject DEFAULT_MAGE = new JsonObject();

        static
        {
            Mage.writeToJson(DEFAULT_MAGE, 0, null, null);
        }

        @Override
        public MedalUpgradeRecipe read(Identifier id, JsonObject json)
        {
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(json, "ingredients"));
            if (defaultedList.isEmpty())
                throw new JsonParseException("No ingredients for medal_upgrade recipe");
            if (defaultedList.size() > 9)
                throw new JsonParseException("Too many ingredients for medal_upgrade recipe");
            Mage original = Mage.readFromJson(JsonHelper.getObject(json, ORIGINAL_KEY, DEFAULT_MAGE));
            Mage upgraded = Mage.readFromJson(JsonHelper.getObject(json, UPGRADED_KEY, DEFAULT_MAGE));
            return new MedalUpgradeRecipe(id, defaultedList, original, upgraded);
        }

        @Override
        public MedalUpgradeRecipe read(Identifier id, PacketByteBuf buf)
        {
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(ignored -> Ingredient.fromPacket(buf));
            Mage original = Mage.readFromPacket(buf);
            Mage upgraded = Mage.readFromPacket(buf);
            return new MedalUpgradeRecipe(id, defaultedList, original, upgraded);
        }

        @Override
        public void write(PacketByteBuf buf, MedalUpgradeRecipe recipe)
        {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients())
                ingredient.write(buf);
            recipe.original.writeToPacket(buf);
            recipe.upgraded.writeToPacket(buf);
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json)
        {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i)
            {
                Ingredient ingredient = Ingredient.fromJson(json.get(i), false);
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }
    }
}
