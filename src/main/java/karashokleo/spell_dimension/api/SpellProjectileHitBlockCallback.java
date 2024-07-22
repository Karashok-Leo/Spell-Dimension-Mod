package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.spell_engine.entity.SpellProjectile;

public interface SpellProjectileHitBlockCallback
{
    Event<SpellProjectileHitBlockCallback> EVENT = EventFactory.createArrayBacked(SpellProjectileHitBlockCallback.class, (listeners) -> ((projectile, spellId, hitResult) ->
    {
        for (SpellProjectileHitBlockCallback listener : listeners)
            listener.onHitBlock(projectile, spellId, hitResult);
    }));

    void onHitBlock(SpellProjectile projectile, Identifier spellId, BlockHitResult hitResult);
}
