package karashokleo.spell_dimension.mixin.client.phase;

import karashokleo.spell_dimension.content.misc.NoClip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tocraft.walkers.api.PlayerShape;
import tocraft.walkers.traits.TraitRegistry;
import tocraft.walkers.traits.impl.FlyingTrait;
import tocraft.walkers.traits.impl.NoPhysicsTrait;

/**
 * 强制飞行
 * Learned from <a href="https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/client/ClientPlayerInteractionManagerMixin.java">...</a>
 */
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin
{
    @Final
    @Shadow
    private MinecraftClient client;

    @Inject(method = "isFlyingLocked", at = @At("HEAD"), cancellable = true)
    private void lockedFlying(CallbackInfoReturnable<Boolean> cir)
    {
        if (client.player == null)
        {
            return;
        }
        if (NoClip.noClip(client.player))
        {
            cir.setReturnValue(true);
            return;
        }
        LivingEntity shape = PlayerShape.getCurrentShape(client.player);
        if (TraitRegistry.has(shape, FlyingTrait.ID) &&
            TraitRegistry.has(shape, NoPhysicsTrait.ID))
        {
            cir.setReturnValue(true);
        }
    }
}
