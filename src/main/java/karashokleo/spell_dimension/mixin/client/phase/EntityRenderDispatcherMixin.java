package karashokleo.spell_dimension.mixin.client.phase;

import karashokleo.spell_dimension.content.misc.NoClip;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 取消阴影渲染
 * Learned from <a href="https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/client/EntityRenderDispatcherMixin.java">...</a>
 */
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin
{
    /**
     * Cancels shadow rendering if clipping.
     */
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void onRenderShadow(MatrixStack matrices, VertexConsumerProvider vertices, Entity entity, float opacity, float tickDelta, WorldView world, float radius, CallbackInfo ci)
    {
        if (NoClip.noClip(entity))
        {
            ci.cancel();
        }
    }
}
