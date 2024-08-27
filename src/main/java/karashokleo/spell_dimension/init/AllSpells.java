package karashokleo.spell_dimension.init;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import net.spell_engine.api.spell.CustomSpellHandler;

public class AllSpells
{
    public static void register()
    {
        LivingDamageEvent.DAMAGE.register(BlazingMark::mark);
        CustomSpellHandler.register(Nucleus.NUCLEUS, data -> Nucleus.handle((CustomSpellHandler.Data) data));
        SpellProjectileHitEntityCallback.EVENT.register((projectile, spellId, hitResult) -> ConvergeSpell.convergeImpact(projectile, spellId));
        SpellProjectileHitBlockCallback.EVENT.register((projectile, spellId, hitResult) -> ConvergeSpell.convergeImpact(projectile, spellId));
    }
}