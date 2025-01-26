package karashokleo.spell_dimension.content.trait;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.event.GameEvent;

public class ShiftTrait extends CooldownTrait
{
    public ShiftTrait()
    {
        super(() -> 0xfa00ff, lv -> 100 - 20 * lv);
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        this.trigger(level, entity, event.getEntity());
    }

    @Override
    public void onHurt(int level, LivingEntity entity, LivingHurtEvent event)
    {
        if (event.getSource().getAttacker() instanceof LivingEntity target)
            this.trigger(level, entity, target);
    }

    @Override
    public void action(int level, Data data, MobEntity mob, LivingEntity target)
    {
        exchangePosition(mob, target);
    }

    public static void exchangePosition(Entity thisEntity, Entity thatEntity)
    {
        if (thisEntity.getWorld() != thatEntity.getWorld()) return;
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
        if (event.isCanceled()) return;

        entity.teleport(x, y, z);

        entity.getWorld().emitGameEvent(GameEvent.TELEPORT, entity.getPos(), GameEvent.Emitter.of(entity));

        if (entity.isSilent()) return;
        entity.getWorld().playSound(null, entity.prevX, entity.prevY, entity.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
        entity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }
}
