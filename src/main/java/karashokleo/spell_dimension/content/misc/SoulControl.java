package karashokleo.spell_dimension.content.misc;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import tocraft.walkers.api.PlayerShape;

/**
 * server-side only
 */
public interface SoulControl
{
    @Nullable
    static SoulMinionComponent getSoulMinion(MobEntity entity)
    {
        if (entity.getWorld().isClient())
        {
            return null;
        }
        return AllComponents.SOUL_MINION.get(entity);
    }

    static SoulControllerComponent getSoulController(PlayerEntity player)
    {
        return AllComponents.SOUL_CONTROLLER.get(player);
    }

    static void setControllingMinion(ServerPlayerEntity player, @Nullable MobEntity minion)
    {
        SoulControllerComponent controllerComponent = getSoulController(player);

        // release control
        if (minion == null)
        {
            MobEntity controllingMinion = controllerComponent.getMinion();
            if (controllingMinion != null)
            {
                SoulMinionComponent component = getSoulMinion(controllingMinion);
                if (component != null)
                {
                    component.setControlling(false);
                }
            }
            controllerComponent.setMinion(null);
            AllComponents.SOUL_CONTROLLER.sync(player);

            PlayerShape.updateShapes(player, null);
            return;
        }

        // take control
        SoulMinionComponent minionComponent = getSoulMinion(minion);
        if (minionComponent == null)
        {
            return;
        }
        LivingEntity soulOwner = minionComponent.getOwner();
        if (soulOwner != player)
        {
            return;
        }
        // cancel attacking state to avoid some issues: zombie's hands' angle
        minion.setAttacking(false);
        minionComponent.setControlling(true);
        controllerComponent.setMinion(minion);
        AllComponents.SOUL_CONTROLLER.sync(player);

        player.teleport(((ServerWorld) minion.getWorld()), minion.getX(), minion.getY(), minion.getZ(), minion.getYaw(), minion.getPitch());
        PlayerShape.updateShapes(player, minion);
        minion.discard();
    }
}
