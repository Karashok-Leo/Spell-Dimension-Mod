package karashokleo.spell_dimension.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 从受控实体视角渲染玩家，原版逻辑会跳过该渲染
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin
{
    @Definition(id = "ClientPlayerEntity", type = ClientPlayerEntity.class)
    @Expression("? instanceof ClientPlayerEntity")
    @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean modifyCameraCheck(boolean original)
    {
        return false;
    }
}
