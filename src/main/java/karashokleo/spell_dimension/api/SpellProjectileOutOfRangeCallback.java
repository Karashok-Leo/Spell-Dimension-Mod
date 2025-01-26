package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import net.spell_engine.entity.SpellProjectile;

public interface SpellProjectileOutOfRangeCallback
{
    Event<SpellProjectileOutOfRangeCallback> EVENT = EventFactory.createArrayBacked(SpellProjectileOutOfRangeCallback.class, (listeners) -> ((projectile, spellId) ->
    {
        for (SpellProjectileOutOfRangeCallback listener : listeners)
            listener.onOutOfRange(projectile, spellId);
    }));

    void onOutOfRange(SpellProjectile projectile, Identifier spellId);
}
