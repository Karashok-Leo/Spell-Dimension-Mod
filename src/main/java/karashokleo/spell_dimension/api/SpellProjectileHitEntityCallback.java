package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.spell_engine.entity.SpellProjectile;

public interface SpellProjectileHitEntityCallback
{
    Event<SpellProjectileHitEntityCallback> EVENT = EventFactory.createArrayBacked(SpellProjectileHitEntityCallback.class, (listeners) -> ((projectile, spellId, hitResult) ->
    {
        for (SpellProjectileHitEntityCallback listener : listeners)
        {
            listener.onHitEntity(projectile, spellId, hitResult);
        }
    }));

    void onHitEntity(SpellProjectile projectile, Identifier spellId, EntityHitResult hitResult);
}
