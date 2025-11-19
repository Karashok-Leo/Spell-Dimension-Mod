package karashokleo.spell_dimension.mixin.client.phase;

import karashokleo.spell_dimension.content.misc.NoClip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 取消所有叠加渲染
 * Learned from <a href="https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/client/InGameOverlayRendererMixin.java">...</a>
 */
@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin
{
    /**
     * Add Phase overlay.
     * Cancels other overlays when clipping.
     */
    @Inject(method = "renderOverlays", at = @At("HEAD"), cancellable = true)
    private static void onRenderOverlays(MinecraftClient client, MatrixStack matrices, CallbackInfo ci)
    {
        if (NoClip.noClip(client.player))
            ci.cancel();
    }
}
