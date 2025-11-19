package karashokleo.spell_dimension.mixin.client.phase;

import karashokleo.spell_dimension.content.misc.NoClip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * 世界渲染参数修改
 * Learned from <a href="https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/client/WorldRendererMixin.java">...</a>
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin
{
    /**
     * Modifies an argument of setupTerrain to make the world render as if the player is in spectator when clipping.
     */
    @ModifyArg(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;setupTerrain(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;ZZ)V"
            ),
            index = 3
    )
    private boolean onRenderReplaceSpectator(boolean isSpectator)
    {
        return isSpectator || NoClip.noClip(MinecraftClient.getInstance().player);
    }
}
