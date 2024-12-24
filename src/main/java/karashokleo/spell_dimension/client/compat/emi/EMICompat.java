package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import karashokleo.l2hostility.compat.emi.EMICategory;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.recipe.InfusionRecipes;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.SpellTexts;
import net.minecraft.util.Identifier;

public class EMICompat implements EmiPlugin
{
    public static final Identifier LOCATE = SpellDimension.modLoc("locate");
    public static final Identifier SUMMON = SpellDimension.modLoc("summon");
    public static final Identifier SPELL_INFUSION = SpellDimension.modLoc("spell_infusion");

    public static final EmiRecipeCategory LOCATE_CATEGORY = new EMICategory(LOCATE, EMILocateRecipe.CATALYSTS, SpellTexts.LOCATE::getNameText);

    public static final EmiRecipeCategory SUMMON_CATEGORY = new EMICategory(SUMMON, EMISummonRecipe.CATALYSTS, SpellTexts.SUMMON::getNameText);

    public static final EmiRecipeCategory SPELL_INFUSION_CATEGORY = new EMICategory(SPELL_INFUSION, EMIInfusionRecipe.CATALYSTS, SDTexts.TEXT$SPELL_INFUSION::get);

    @Override
    public void register(EmiRegistry registry)
    {
        registry.addCategory(LOCATE_CATEGORY);
        registry.addCategory(SUMMON_CATEGORY);
        registry.addCategory(SPELL_INFUSION_CATEGORY);

        LocateSpellConfig.STRUCTURE_CONFIG.cellSet().forEach(cell -> registry.addRecipe(new EMILocateRecipe(cell.getRowKey(), cell.getValue())));
        LocateSpellConfig.BIOME_CONFIG.cellSet().forEach(cell -> registry.addRecipe(new EMILocateRecipe(cell.getRowKey(), cell.getValue())));
        SummonSpellConfig.forEach((item, entry) -> registry.addRecipe(new EMISummonRecipe(item, entry)));
        InfusionRecipes.getAll().forEach(entry -> registry.addRecipe(new EMIInfusionRecipe(entry.getRowKey(), entry.getColumnKey(), entry.getValue())));

        registry.addWorkstation(LOCATE_CATEGORY, EMILocateRecipe.CATALYSTS);
        registry.addWorkstation(SUMMON_CATEGORY, EMISummonRecipe.CATALYSTS);
        registry.addWorkstation(SPELL_INFUSION_CATEGORY, EMIInfusionRecipe.CATALYSTS);
    }
}
