package net.karashokleo.spelldimension.spell;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.spell_engine.api.spell.CustomSpellHandler;

public class AllCustomSpellHandles
{
    public static void register()
    {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(BlazingMark.LISTENER);
        CustomSpellHandler.register(MageMajor.NUCLEUS.spellId().withSuffixedPath("1"), new NucleusSpell());
        CustomSpellHandler.register(MageMajor.NUCLEUS.spellId().withSuffixedPath("2"), new NucleusSpell());
        CustomSpellHandler.register(MageMajor.NUCLEUS.spellId().withSuffixedPath("3"), new NucleusSpell());
    }
}