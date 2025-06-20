package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public interface SpellImpactEvents
{
    Event<Callback> PRE = EventFactory.createArrayBacked(Callback.class, (listeners) -> ((world, caster, targets, spellInfo) ->
    {
        for (Callback listener : listeners)
            listener.invoke(world, caster, targets, spellInfo);
    }));

    Event<Callback> POST = EventFactory.createArrayBacked(Callback.class, (listeners) -> ((world, caster, targets, spellInfo) ->
    {
        for (Callback listener : listeners)
            listener.invoke(world, caster, targets, spellInfo);
    }));

    interface Callback
    {
        void invoke(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo);
    }
}
