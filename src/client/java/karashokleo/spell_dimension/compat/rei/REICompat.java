package karashokleo.spell_dimension.compat.rei;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.recipe.InfusionRecipes;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

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

        registry.addWorkstations(LOCATE, REILocateCategory.LODESTONE);
        registry.addWorkstations(SUMMON, REISummonCategory.SPAWNER);
        registry.addWorkstations(SPELL_INFUSION, REIInfusionCategory.PEDESTAL);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        LocateSpellConfig.forEach((item, key) -> registry.add(new REILocateDisplay(item, key)));
        SummonSpellConfig.forEach((item, entry) -> registry.add(new REISummonDisplay(item, entry)));
        InfusionRecipes.getAll().forEach(entry -> registry.add(new REIInfusionDisplay(entry)));
    }
}
