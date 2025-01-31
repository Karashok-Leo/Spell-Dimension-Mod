package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity>
{
    public static final Identifier CORE = SpellDimension.modLoc("textures/entity/black_hole.png");
    private final Random random = Random.create();

    public BlackHoleRenderer(EntityRendererFactory.Context ctx)
    {
        super(ctx);
    }

    @Override
    public Identifier getTexture(BlackHoleEntity entity)
    {
        return CORE;
    }

    @Override
    public void render(BlackHoleEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {
        matrices.push();

        matrices.translate(0, entity.getBoundingBox().getYLength() / 2, 0);

        float animationProgress = (entity.age + tickDelta) / BlackHoleEntity.LIFESPAN;
        float m = Math.min(animationProgress > 0.8F ? (animationProgress - 0.8F) / 0.2F : 0.0F, 1.0F);
        int alpha = (int) (255.0F * (1.0F - m));

        matrices.push();
        {
            float scale = entity.getWidth() * 0.0125f;

            matrices.scale(scale, scale, scale);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));

            //        VertexConsumer centerBuffer = vertexConsumers.getBuffer(RenderLayer.getEndGateway());
            VertexConsumer centerBuffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(CORE));
            renderCore(centerBuffer, matrices, alpha);
        }
        matrices.pop();

        random.setSeed(entity.getId() * 6666L + entity.getId() * entity.getId() * 77777L);
        float blackHoleRadius = entity.getRadius();
        VertexConsumer beamBuffer = vertexConsumers.getBuffer(RenderLayer.getLightning());

        matrices.push();
        {
            float segments = Math.min(animationProgress, 0.8f);
            float total = (segments + segments * segments) * (12 + blackHoleRadius / 2);
            for (int n = 0; n < total; ++n)
            {
                float radius = random.nextFloat() + blackHoleRadius + m * blackHoleRadius * 0.4F;
                float width = random.nextFloat() * 2.0F + 1.0F + m * 2.0F;
                renderBeam(beamBuffer, matrices, animationProgress, alpha, radius, width);
            }
        }
        matrices.pop();

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private static void renderCore(VertexConsumer centerBuffer, MatrixStack matrices, int alpha)
    {
        MatrixStack.Entry peek = matrices.peek();
        Matrix4f positionMatrix = peek.getPositionMatrix();
        Matrix3f normalMatrix = peek.getNormalMatrix();
        centerBuffer.vertex(positionMatrix, 0, -8, -8)
                .color(255, 255, 255, alpha)
                .texture(0, 1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        centerBuffer.vertex(positionMatrix, 0, 8, -8)
                .color(255, 255, 255, alpha)
                .texture(0, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        centerBuffer.vertex(positionMatrix, 0, 8, 8)
                .color(255, 255, 255, alpha)
                .texture(1, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        centerBuffer.vertex(positionMatrix, 0, -8, 8)
                .color(255, 255, 255, alpha)
                .texture(1, 1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
    }

    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);

    private static void putDeathLightSourceVertex(VertexConsumer buffer, Matrix4f matrix, int alpha)
    {
        buffer.vertex(matrix, 0.0F, 0.0F, 0.0F).color(255, 255, 255, alpha).next();
    }

    private static void putDeathLightNegativeXTerminalVertex(VertexConsumer buffer, Matrix4f matrix, float radius, float width)
    {
        buffer.vertex(matrix, -HALF_SQRT_3 * width, radius, -0.5F * width).color(255, 0, 255, 0).next();
    }

    private static void putDeathLightPositiveXTerminalVertex(VertexConsumer buffer, Matrix4f matrix, float radius, float width)
    {
        buffer.vertex(matrix, HALF_SQRT_3 * width, radius, -0.5F * width).color(255, 0, 255, 0).next();
    }

    private static void putDeathLightPositiveZTerminalVertex(VertexConsumer buffer, Matrix4f matrix, float radius, float width)
    {
        buffer.vertex(matrix, 0.0F, radius, width).color(255, 0, 255, 0).next();
    }

    private void renderBeam(VertexConsumer buffer, MatrixStack matrices, float animationProgress, int alpha, float radius, float width)
    {
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360.0F));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360.0F + animationProgress * 90.0F));
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        putDeathLightSourceVertex(buffer, matrix4f, alpha);
        putDeathLightNegativeXTerminalVertex(buffer, matrix4f, radius, width);
        putDeathLightPositiveXTerminalVertex(buffer, matrix4f, radius, width);
        putDeathLightSourceVertex(buffer, matrix4f, alpha);
        putDeathLightPositiveXTerminalVertex(buffer, matrix4f, radius, width);
        putDeathLightPositiveZTerminalVertex(buffer, matrix4f, radius, width);
        putDeathLightSourceVertex(buffer, matrix4f, alpha);
        putDeathLightPositiveZTerminalVertex(buffer, matrix4f, radius, width);
        putDeathLightNegativeXTerminalVertex(buffer, matrix4f, radius, width);
    }
}
