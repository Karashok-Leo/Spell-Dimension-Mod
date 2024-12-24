package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimensionClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomModelStatusEffect;
import net.spell_engine.api.render.CustomLayers;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.render.LightEmission;

public class FrostedEffectRenderer implements CustomModelStatusEffect.Renderer
{
    private static final RenderLayer RENDER_LAYER = CustomLayers.spellEffect(LightEmission.RADIATE, false);

    @Override
    public void renderEffect(int amplifier, LivingEntity entity, float delta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {
        matrices.push();
        matrices.scale(entity.getWidth() * 0.8F, entity.getHeight(), entity.getWidth() * 0.8F);
        matrices.translate(0, 0.5, 0);
        CustomModels.render(RENDER_LAYER, MinecraftClient.getInstance().getItemRenderer(), SpellDimensionClient.FROSTED_MODEL,
                matrices, vertexConsumers, light, entity.getId());
        matrices.pop();
    }
}
