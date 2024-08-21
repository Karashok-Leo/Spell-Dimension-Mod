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
    Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, (listeners) -> ((world, caster, targets, spellInfo) ->
    {
        for (Before listener : listeners)
            listener.beforeImpact(world, caster, targets, spellInfo);
    }));

    interface Before
    {
        void beforeImpact(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo);
    }

    Event<After> AFTER = EventFactory.createArrayBacked(After.class, (listeners) -> ((world, caster, target, spellInfo) ->
    {
        for (After listener : listeners)
            listener.afterImpact(world, caster, target, spellInfo);
    }));

    interface After
    {
        void afterImpact(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo);
    }
}
