package net.karashokleo.spelldimension;

import net.fabricmc.api.ModInitializer;
import net.karashokleo.spelldimension.item.AllGroups;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.item.ExtraModifier;
import net.karashokleo.spelldimension.loot.AllLoot;
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
        AllItems.register();
        AllGroups.register();
        AllLoot.register();
        ExtraModifier.register();
        AllStatusEffects.register();
        AllRecipeSerializers.register();
        AllCustomSpellHandles.register();
    }

    public static Identifier modLoc(String id)
    {
        return new Identifier(MOD_ID, id);
    }
}