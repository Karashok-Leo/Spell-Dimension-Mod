package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import org.jetbrains.annotations.Nullable;

public interface SpellProjectileHitCallback
{
    Event<SpellProjectileHitCallback> EVENT = EventFactory.createArrayBacked(SpellProjectileHitCallback.class, (listeners) -> ((projectile, spellInfo, owner, hitResult) ->
    {
        for (SpellProjectileHitCallback listener : listeners)
        {
            listener.onHit(projectile, spellInfo, owner, hitResult);
        }
    }));

    void onHit(SpellProjectile projectile, SpellInfo spellInfo, @Nullable Entity owner, HitResult hitResult);
}
