package karashokleo.spell_dimension.content.recipe.spell;

import karashokleo.enchantment_infusion.api.recipe.InfusionRecipe;
import karashokleo.enchantment_infusion.init.EIRecipes;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public record SpellInfusionRecipe(
        Identifier id,
        Ingredient input,
        DefaultedList<Ingredient> ingredients,
        Identifier spellId
) implements InfusionRecipe
{
    @Override
    public Ingredient getTableIngredient()
    {
        return input;
    }

    @Override
    public DefaultedList<Ingredient> getPedestalIngredient()
    {
        return ingredients;
    }

    @Override
    public ItemStack infuse(ItemStack tableStack)
    {
        return AllItems.SPELL_SCROLL.getStack(spellId);
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager)
    {
        return AllItems.SPELL_SCROLL.getStack(spellId);
    }

    @Override
    public Identifier getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SpellInfusionRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType()
    {
        return EIRecipes.INFUSION_RECIPE_TYPE;
    }
}
