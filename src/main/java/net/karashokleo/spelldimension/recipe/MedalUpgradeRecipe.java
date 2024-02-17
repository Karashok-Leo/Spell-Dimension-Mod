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
import net.minecraft.world.World;

public class MedalUpgradeRecipe extends ShapedRecipe
{
    public static final String NAME = "medal_upgrade";
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String ORIGINAL_KEY = "original";
    public static final String UPGRADED_KEY = "upgraded";
    final Mage original;
    final Mage upgraded;

    public MedalUpgradeRecipe(Identifier id, DefaultedList<Ingredient> input, Mage original, Mage upgraded)
    {
        super(id, "", CraftingRecipeCategory.MISC, 3, 3, input, AllItems.MAGE_MEDAL.getStack(upgraded));
        this.original = original;
        this.upgraded = upgraded;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager dynamicRegistryManager)
    {
        return AllItems.MAGE_MEDAL.getStack(this.upgraded);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world)
    {
        return testMedal(inventory) && super.matches(inventory, world);
    }

    private boolean testMedal(RecipeInputInventory inventory)
    {
        for (ItemStack stack : this.getIngredients().get(4).getMatchingStacks())
        {
            ItemStack invStack = inventory.getStack(4);
            if (invStack.isOf(stack.getItem()) && invStack.getOrCreateNbt().equals(stack.getOrCreateNbt()))
                return true;
        }
        return false;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<MedalUpgradeRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = SpellDimension.modLoc(NAME);
        public static final JsonObject DEFAULT_MAGE = new JsonObject();

        static
        {
            Mage.writeToJson(DEFAULT_MAGE, 0, null, null);
        }

        @Override
        public MedalUpgradeRecipe read(Identifier id, JsonObject json)
        {
            Mage original = Mage.readFromJson(JsonHelper.getObject(json, ORIGINAL_KEY, DEFAULT_MAGE));
            Mage upgraded = Mage.readFromJson(JsonHelper.getObject(json, UPGRADED_KEY, DEFAULT_MAGE));
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(json, INGREDIENTS_KEY));
            defaultedList.set(4, Ingredient.ofStacks(AllItems.MAGE_MEDAL.getStack(original)));
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
            if (json.isEmpty())
                throw new JsonParseException("No ingredients for medal_upgrade recipe");
            if (json.size() > 8)
                throw new JsonParseException("Too many ingredients for medal_upgrade recipe");
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(9, Ingredient.EMPTY);
            for (int i = 0; i < json.size(); ++i)
                defaultedList.set((i < 4 ? i : (i + 1)), Ingredient.fromJson(json.get(i)));
            return defaultedList;
        }
    }
}
