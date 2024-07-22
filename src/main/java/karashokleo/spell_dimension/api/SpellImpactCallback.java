package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;

public interface SpellImpactCallback
{
    Event<SpellImpactCallback> EVENT = EventFactory.createArrayBacked(SpellImpactCallback.class, (listeners) -> ((world, caster, target, spellInfo, impact, context) ->
    {
        for (SpellImpactCallback listener : listeners)
            listener.onImpact(world, caster, target, spellInfo, impact, context);
    }));

    void onImpact(World world, LivingEntity caster, Entity target, SpellInfo spellInfo, Spell.Impact impact, SpellHelper.ImpactContext context);
}
