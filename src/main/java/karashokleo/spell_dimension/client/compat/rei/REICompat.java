package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.recipe.RecipeManager;

public class REICompat implements REIClientPlugin
{
    public static final CategoryIdentifier<REILocateDisplay> LOCATE = CategoryIdentifier.of(SpellDimension.MOD_ID, "locate");
    public static final CategoryIdentifier<REISummonDisplay> SUMMON = CategoryIdentifier.of(SpellDimension.MOD_ID, "summon");
    public static final CategoryIdentifier<REIInfusionDisplay> SPELL_INFUSION = CategoryIdentifier.of(SpellDimension.MOD_ID, "spell_infusion");

    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new REILocateCategory());
        registry.add(new REISummonCategory());
        registry.add(new REIInfusionCategory());

        registry.addWorkstations(LOCATE, REILocateCategory.LOCATE_TARGET);
        registry.addWorkstations(SUMMON, REISummonCategory.SPAWNER);
        registry.addWorkstations(SPELL_INFUSION, REIInfusionCategory.PEDESTAL);
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
}
