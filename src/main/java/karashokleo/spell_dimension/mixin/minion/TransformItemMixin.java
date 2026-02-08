package karashokleo.spell_dimension.mixin.minion;

import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.l2hostility.content.item.complements.TransformItem;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TransformItem.class)
public abstract class TransformItemMixin
{
    @Inject(
        method = "convertMob",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
        )
    )
    private void inject_convertMob(ServerWorld world, LivingEntity entity, CallbackInfo ci, @Local(name = "result") MobEntity mob)
    {
        if (entity instanceof MobEntity oldMob)
        {
            SoulMinionComponent oldComponent = SoulControl.getSoulMinion(oldMob);
            SoulMinionComponent newComponent = SoulControl.getSoulMinion(mob);
            newComponent.copyFrom(oldComponent);
        }
    }
}
