package karashokleo.spell_dimension.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import karashokleo.l2hostility.compat.emi.EMICategory;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.config.recipe.ScrollLootConfig;
import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class EMICompat implements EmiPlugin
{
    public static final Identifier LOCATE = SpellDimension.modLoc("locate");
    public static final Identifier SUMMON = SpellDimension.modLoc("summon");
    public static final Identifier SPELL_INFUSION = SpellDimension.modLoc("spell_infusion");

    public static final EmiRecipeCategory LOCATE_CATEGORY = new EMICategory(LOCATE, EmiStack.of(Items.LODESTONE), SDTexts.SPELL$LOCATE$NAME::get);

    public static final EmiRecipeCategory SUMMON_CATEGORY = new EMICategory(SUMMON, EmiStack.of(Items.SPAWNER), SDTexts.SPELL$SUMMON$NAME::get);

    public static final EmiRecipeCategory SPELL_INFUSION_CATEGORY = new EMICategory(SPELL_INFUSION, EmiStack.of(AllBlocks.SPELL_INFUSION_PEDESTAL.item()), SDTexts.TEXT$SPELL_INFUSION::get);

    @Override
    public void register(EmiRegistry registry)
    {
        registry.addCategory(LOCATE_CATEGORY);
        registry.addCategory(SUMMON_CATEGORY);
        registry.addCategory(SPELL_INFUSION_CATEGORY);

        LocateSpellConfig.forEach((item, key) -> registry.addRecipe(new EMILocateRecipe(item, key)));
        SummonSpellConfig.forEach((item, entry) -> registry.addRecipe(new EMISummonRecipe(item, entry)));
        ScrollLootConfig.forEach((item, id) -> registry.addRecipe(new EMIInfusionRecipe(Items.PAPER, item, id)));
    }
}
