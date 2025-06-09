package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.init.AllItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import net.minecraft.recipe.RecipeManager;

public class REICompat implements REIClientPlugin
{
    public static final CategoryIdentifier<REILocateDisplay> LOCATE = CategoryIdentifier.of(SpellDimension.MOD_ID, "locate");
    public static final CategoryIdentifier<REISummonDisplay> SUMMON = CategoryIdentifier.of(SpellDimension.MOD_ID, "summon");

    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new REILocateCategory());
        registry.add(new REISummonCategory());

        registry.addWorkstations(LOCATE, REILocateCategory.WORKSTATION, REILocateCategory.SPELL_SCROLL);
        registry.addWorkstations(SUMMON, REISummonCategory.WORKSTATION, REISummonCategory.SPELL_SCROLL);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        RecipeManager recipeManager = registry.getRecipeManager();
        for (LocateRecipe locateRecipe : recipeManager.listAllOfType(LocateRecipe.TYPE))
            registry.add(new REILocateDisplay(locateRecipe));
        for (SummonRecipe summonRecipe : recipeManager.listAllOfType(SummonRecipe.TYPE))
            registry.add(new REISummonDisplay(summonRecipe));
    }

    @Override
    public void registerItemComparators(ItemComparatorRegistry registry)
    {
        // see DefaultPlugin
        registry.registerNbt(AllItems.SPELL_SCROLL);
        registry.registerNbt(AllItems.ENCHANTED_ESSENCE);
    }
}
