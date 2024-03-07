package net.karashokleo.spelldimension;

import net.fabricmc.api.ModInitializer;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.item.AllGroups;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.karashokleo.spelldimension.loot.AllLoots;
import net.karashokleo.spelldimension.misc.DebugStaffCommand;
import net.karashokleo.spelldimension.recipe.AllRecipeSerializers;
import net.karashokleo.spelldimension.spell.AllCustomSpellHandles;
import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.minecraft.util.Identifier;

public class SpellDimension implements ModInitializer
{
    public static final String MOD_ID = "spell-dimension";

    @Override
    public void onInitialize()
    {
        AllConfigs.refresh();
        AllItems.register();
        AllGroups.register();
        AllLoots.register();
        AllStatusEffects.register();
        AllRecipeSerializers.register();
        AllCustomSpellHandles.register();
        EnchantedModifier.init();
        DebugStaffCommand.init();
    }

    public static Identifier modLoc(String id)
    {
        return new Identifier(MOD_ID, id);
    }
}