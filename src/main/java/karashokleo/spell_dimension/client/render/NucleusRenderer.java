package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Nucleus;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

/**
 * 冰核渲染
 * Learned from <a href="https://github.com/TeamTwilight/twilightforest-fabric/blob/1.20.x/src/main/java/twilightforest/client/renderer/entity/IceLayer.java">...</a>
 *
 * @param <T>
 * @param <M>
 */
public class NucleusRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
    private final Random random = Random.create();

    public NucleusRenderer(FeatureRendererContext<T, M> context)
    {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch)
    {
        Optional<Nucleus> optional = Buff.get(entity, Nucleus.TYPE);
        if (optional.isEmpty() || optional.get().getDuration() <= 0) return;
        this.random.setSeed(entity.getId() * entity.getId() * 3121L + entity.getId() * 45238971L);

        float width = entity.getWidth();
        float height = entity.getHeight();
        float scale = optional.get().getScale();

        for (int i = 0; i < scale * 10; i++)
        {
            matrices.push();
            float dw = (this.random.nextFloat() - 0.5F) * 2 * width * 0.1F;
            float dh = Math.max(1.5F - this.random.nextFloat() * height, -0.1F);
            matrices.translate(dw, dh, dw);
            matrices.scale(0.5F, 0.5F, 0.5F);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.random.nextFloat() * 360F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.random.nextFloat() * 360F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.random.nextFloat() * 360F));
            matrices.translate(-0.5F, -0.5F, -0.5F);
            matrices.scale(scale, scale, scale);
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.ICE.getDefaultState(), matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        }
    }
}
