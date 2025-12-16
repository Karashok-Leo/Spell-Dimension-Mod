package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.BlackHoleEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity>
{
    public static final Identifier CORE = SpellDimension.modLoc("textures/entity/black_hole.png");
    public static final RenderLayer RENDER_LAYER = createRenderLayer();
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

    public static RenderLayer createRenderLayer()
    {
        return RenderLayer.of(
            "black_hole",
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
            VertexFormat.DrawMode.TRIANGLE_FAN, 256, true, true,
            RenderLayer.MultiPhaseParameters.builder()
                .program(RenderPhase.ENTITY_TRANSLUCENT_PROGRAM)
                .texture(new RenderPhase.Texture(CORE, false, false))
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                .build(false)
        );
    }

    @Override
    public void render(BlackHoleEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {
        matrices.push();

        float blackHoleRadius = entity.getRadius();

        float animationProgress = (entity.age + tickDelta) / BlackHoleEntity.LIFESPAN;
        float m = Math.min(animationProgress > 0.8F ? (animationProgress - 0.8F) / 0.2F : 0.0F, 1.0F);
        int alpha = (int) (200.0F * (1.0F - m));

        matrices.push();
        {
            float scale = blackHoleRadius * 0.08f;

            matrices.scale(scale, scale, scale);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));

            VertexConsumer centerBuffer = vertexConsumers.getBuffer(RENDER_LAYER);
            renderCore(centerBuffer, matrices, alpha);
        }
        matrices.pop();

        random.setSeed(entity.getId() * 6666L + entity.getId() * entity.getId() * 77777L);
        VertexConsumer beamBuffer = vertexConsumers.getBuffer(RenderLayer.getLightning());

        matrices.push();
        {
            float segments = Math.min(animationProgress, 0.8f);
            float total = (segments + segments * segments) * (12 + blackHoleRadius / 2);
            for (int n = 0; n < total; ++n)
            {
                float radius = random.nextFloat() + blackHoleRadius + m * blackHoleRadius * 0.5F;
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
        int segments = 32;
        float radius = 4F;

        MatrixStack.Entry peek = matrices.peek();
        Matrix4f positionMatrix = peek.getPositionMatrix();
        Matrix3f normalMatrix = peek.getNormalMatrix();

        centerBuffer.vertex(positionMatrix, 0, 0, 0)
            .color(255, 255, 255, alpha)
            .texture(0.5f, 0.5f)
            .overlay(OverlayTexture.DEFAULT_UV)
            .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
            .normal(normalMatrix, 0, 1, 0)
            .next();
        for (int i = 0; i <= segments; i++)
        {
            float theta = 2.0f * MathHelper.PI * i / segments;
            float cos = MathHelper.cos(theta);
            float sin = MathHelper.sin(theta);
            centerBuffer.vertex(positionMatrix, 0, radius * cos, radius * sin)
                .color(255, 255, 255, alpha)
                .texture(0.5f + 0.5f * cos, 0.5f + 0.5f * sin)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        }
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
