package karashokleo.spell_dimension.init;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import karashokleo.spell_dimension.content.misc.MageMajor;
import karashokleo.spell_dimension.content.spell.BlazingMark;
import karashokleo.spell_dimension.content.spell.NucleusSpell;
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