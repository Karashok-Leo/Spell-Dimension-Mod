package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.content.spell.BlazingMark;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import karashokleo.spell_dimension.content.spell.NucleusSpell;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.spell_engine.api.spell.CustomSpellHandler;

public class AllSpells
{
    public static void register()
    {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register(BlazingMark::mark);
        CustomSpellHandler.register(NucleusSpell.NUCLEUS, new NucleusSpell());
        SpellProjectileHitEntityCallback.EVENT.register((projectile, spellId, hitResult) -> ConvergeSpell.convergeImpact(projectile, spellId));
        SpellProjectileHitBlockCallback.EVENT.register((projectile, spellId, hitResult) -> ConvergeSpell.convergeImpact(projectile, spellId));
    }
}