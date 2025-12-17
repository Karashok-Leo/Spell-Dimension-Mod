package karashokleo.spell_dimension.content.spell;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import org.jetbrains.annotations.Nullable;

public class ShiftSpell
{
    public static void handle(SpellProjectile projectile, SpellInfo spellInfo, @Nullable Entity owner, HitResult hitResult)
    {
        if (owner == null || owner.isRemoved() || owner.getWorld().isClient())
        {
            return;
        }
        if (!(hitResult instanceof EntityHitResult entityHitResult))
        {
            return;
        }
        exchangePosition(owner, entityHitResult.getEntity());
    }

    public static void exchangePosition(Entity thisEntity, Entity thatEntity)
    {
        if (thisEntity.getWorld() != thatEntity.getWorld())
        {
            return;
        }
        double thisEntityX = thisEntity.getX();
        double thisEntityY = thisEntity.getY();
        double thisEntityZ = thisEntity.getZ();
        double thatEntityX = thatEntity.getX();
        double thatEntityY = thatEntity.getY();
        double thatEntityZ = thatEntity.getZ();
        teleportWithEvent(thisEntity, thatEntityX, thatEntityY, thatEntityZ);
        teleportWithEvent(thatEntity, thisEntityX, thisEntityY, thisEntityZ);
    }

    public static void teleportWithEvent(Entity entity, double x, double y, double z)
    {
        var event = new EntityEvents.Teleport.EntityTeleportEvent(entity, x, y, z);
        event.sendEvent();
        if (event.isCanceled())
        {
            return;
        }

        entity.teleport(x, y, z);

        entity.getWorld().emitGameEvent(GameEvent.TELEPORT, entity.getPos(), GameEvent.Emitter.of(entity));

        if (entity.isSilent())
        {
            return;
        }
        entity.getWorld().playSound(null, entity.prevX, entity.prevY, entity.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
        entity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }
}
