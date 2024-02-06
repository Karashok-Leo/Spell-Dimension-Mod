package net.karashokleo.spelldimension.mixin.phase;

import net.karashokleo.spelldimension.misc.INoClip;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin
{
    /*
    * 取消阴影渲染
    * Learned from
    * https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/client/EntityRenderDispatcherMixin.java
    * */

    /**
     * Cancels shadow rendering if clipping.
     */
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void onRenderShadow(MatrixStack matrices, VertexConsumerProvider vertices, Entity entity, float opacity, float tickDelta, WorldView world, float radius, CallbackInfo ci)
    {
        if (INoClip.noClip(entity)) ci.cancel();
    }
}
