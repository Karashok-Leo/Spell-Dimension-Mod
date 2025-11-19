package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net/minecraft/server/world/ThreadedAnvilChunkStorage$EntityTracker")
public abstract class EntityTrackerMixin
{
    @Redirect(
        method = "updateTrackedStatus(Lnet/minecraft/server/network/ServerPlayerEntity;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerPlayerEntity;getPos()Lnet/minecraft/util/math/Vec3d;"
        )
    )
    private Vec3d redirectGetPos(ServerPlayerEntity player)
    {
        SoulControllerComponent component = SoulControl.getSoulController(player);
        if (component.isControlling())
        {
            MobEntity minion = component.getMinion();
            if (minion != null)
            {
                return minion.getPos();
            }
        }
        return player.getPos();
    }
}
