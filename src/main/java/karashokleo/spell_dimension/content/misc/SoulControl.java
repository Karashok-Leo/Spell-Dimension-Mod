package karashokleo.spell_dimension.content.misc;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.SetCameraEntityS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

/**
 * server-side only
 */
public interface SoulControl
{
    @Nullable
    static SoulControllerComponent getSoulController(MobEntity entity)
    {
        if (entity.getWorld().isClient())
        {
            return null;
        }
        return AllComponents.SOUL_CONTROLLER.get(entity);
    }

    static SoulMinionComponent getSoulMinion(PlayerEntity player)
    {
        return AllComponents.SOUL_MINION.get(player);
    }

    static void setControllingMinion(ServerPlayerEntity player, @Nullable MobEntity minion)
    {
        ServerWorld world = player.getServerWorld();
        SoulMinionComponent minionComponent = getSoulMinion(player);
        if (minion == null)
        {
            MobEntity controllingMinion = minionComponent.getMinion(world);
            if (controllingMinion != null)
            {
                SoulControllerComponent component = getSoulController(controllingMinion);
                if (component != null)
                {
                    component.setControlling(false);
                }
            }
            minionComponent.setMinion(null);
            player.networkHandler.sendPacket(new SetCameraEntityS2CPacket(player));
            AllComponents.SOUL_MINION.sync(player);
            return;
        }
        SoulControllerComponent controllerComponent = getSoulController(minion);
        if (controllerComponent == null)
        {
            return;
        }
        LivingEntity soulOwner = controllerComponent.getOwner(world);
        if (soulOwner != player)
        {
            return;
        }
        // cancel attacking state to avoid some issues: zombie's hands' angle
        minion.setAttacking(false);
        controllerComponent.setControlling(true);
        minionComponent.setMinion(minion);
        player.networkHandler.sendPacket(new SetCameraEntityS2CPacket(minion));
        AllComponents.SOUL_MINION.sync(player);
    }
}
