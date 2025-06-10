package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.Comparison;
import karashokleo.l2hostility.compat.emi.EMICategory;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.data.SpellTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class EMICompat implements EmiPlugin
{
    public static final Identifier LOCATE = SpellDimension.modLoc("locate");
    public static final Identifier SUMMON = SpellDimension.modLoc("summon");

    public static final EmiRecipeCategory LOCATE_CATEGORY = new EMICategory(LOCATE, EMILocateRecipe.WORKSTATION, SpellTexts.LOCATE::getNameText);

    public static final EmiRecipeCategory SUMMON_CATEGORY = new EMICategory(SUMMON, EMISummonRecipe.WORKSTATION, SpellTexts.SUMMON::getNameText);

    @Override
    public void register(EmiRegistry registry)
    {
        // see VanillaPlugin
        registry.setDefaultComparison(AllItems.SPELL_SCROLL, Comparison.compareNbt());
        registry.setDefaultComparison(AllItems.ENCHANTED_ESSENCE, Comparison.compareNbt());

        registry.addCategory(LOCATE_CATEGORY);
        registry.addCategory(SUMMON_CATEGORY);

        RecipeManager recipeManager = registry.getRecipeManager();
        for (LocateRecipe locateRecipe : recipeManager.listAllOfType(LocateRecipe.TYPE))
        {
            EMILocateRecipe recipe = new EMILocateRecipe(locateRecipe);
            registry.addRecipe(recipe);
            registry.addEmiStack(recipe.location());
        }
        for (SummonRecipe summonRecipe : recipeManager.listAllOfType(SummonRecipe.TYPE))
        {
            registry.addRecipe(new EMISummonRecipe(summonRecipe));
        }

        registry.addWorkstation(LOCATE_CATEGORY, EMILocateRecipe.WORKSTATION);
        registry.addWorkstation(LOCATE_CATEGORY, EMILocateRecipe.SPELL_SCROLL);
        registry.addWorkstation(SUMMON_CATEGORY, EMISummonRecipe.WORKSTATION);
        registry.addWorkstation(SUMMON_CATEGORY, EMISummonRecipe.SPELL_SCROLL);
    }
}
