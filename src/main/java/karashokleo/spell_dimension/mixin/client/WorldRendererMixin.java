package karashokleo.spell_dimension.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * 从受控实体视角渲染玩家，原版逻辑会跳过该渲染
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin
{
    @Shadow
    @Final
    private MinecraftClient client;

    @Definition(id = "ClientPlayerEntity", type = ClientPlayerEntity.class)
    @Expression("? instanceof ClientPlayerEntity")
    @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean modifyCameraCheck(boolean original)
    {
        return false;
    }

    @ModifyVariable(
        method = "setupTerrain",
        at = @At("STORE"),
        ordinal = 0
    )
    private double modifyXSetupTerrain(double original, @Share("cameraEntity") LocalRef<Entity> cameraEntity)
    {
        cameraEntity.set(client.getCameraEntity());
        if (cameraEntity.get() == null)
        {
            return original;
        }
        return cameraEntity.get().getX();
    }

    @ModifyVariable(
        method = "setupTerrain",
        at = @At("STORE"),
        ordinal = 1
    )
    private double modifyYSetupTerrain(double original, @Share("cameraEntity") LocalRef<Entity> cameraEntity)
    {
        if (cameraEntity.get() == null)
        {
            return original;
        }
        return cameraEntity.get().getY();
    }

    @ModifyVariable(
        method = "setupTerrain",
        at = @At("STORE"),
        ordinal = 2
    )
    private double modifyZSetupTerrain(double original, @Share("cameraEntity") LocalRef<Entity> cameraEntity)
    {
        if (cameraEntity.get() == null)
        {
            return original;
        }
        return cameraEntity.get().getZ();
    }
}
