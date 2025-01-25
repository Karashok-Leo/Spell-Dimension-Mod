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
    public static final Identifier CORE = SpellDimension.modLoc("textures/entity/black_hole/core.png");
    public static final Identifier BEAM = SpellDimension.modLoc("textures/entity/black_hole/beam.png");
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

        matrices.push();

        float scale = entity.getWidth() * 0.025f;

        matrices.scale(0.5f * scale, 0.5f * scale, 0.5f * scale);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));

        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(CORE));

        MatrixStack.Entry peek = matrices.peek();
        Matrix4f positionMatrix = peek.getPositionMatrix();
        Matrix3f normalMatrix = peek.getNormalMatrix();

        buffer.vertex(positionMatrix, 0, -8, -8)
                .color(255, 255, 255, 255)
                .texture(0, 1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        buffer.vertex(positionMatrix, 0, 8, -8)
                .color(255, 255, 255, 255)
                .texture(0, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        buffer.vertex(positionMatrix, 0, 8, 8)
                .color(255, 255, 255, 255)
                .texture(1, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        buffer.vertex(positionMatrix, 0, -8, 8)
                .color(255, 255, 255, 255)
                .texture(1, 1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();

        matrices.pop();

        matrices.push();

        random.setSeed(entity.getId() * 6666L + entity.getId() * entity.getId() * 77777L);
        // 200f refer to lifecycle of the animation
        float animationProgress = (entity.age + tickDelta) / 200f;
        float fadeProgress = 0.5f;

        VertexConsumer beamBuffer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(BEAM, 0, 0));

        float segments = Math.min(animationProgress, 0.8f);
        float total = (segments + segments * segments) / 2 * 60f;

        for (int i = 0; i < total; i++)
        {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360f));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360f));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360f));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(random.nextFloat() * 360f));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(random.nextFloat() * 360f));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(random.nextFloat() * 360f + animationProgress * 90f));

            float size = (random.nextFloat() * 10f + 5f + fadeProgress * 5f) * scale * 0.4f;
            renderBeam(beamBuffer, matrices, size);
        }

        matrices.pop();

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private static void renderBeam(VertexConsumer buffer, MatrixStack matrices, float size)
    {
        MatrixStack.Entry peek = matrices.peek();
        Matrix4f positionMatrix = peek.getPositionMatrix();
        Matrix3f normalMatrix = peek.getNormalMatrix();

        buffer.vertex(positionMatrix, 0, 0, 0)
                .color(255, 0, 255, 255)
                .texture(0, 1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        buffer.vertex(positionMatrix, 0, 3 * size, -size)
                .color(0, 0, 0, 0)
                .texture(0, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        buffer.vertex(positionMatrix, 0, size, size)
                .color(0, 0, 0, 0)
                .texture(1, 0)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
        buffer.vertex(positionMatrix, 0, 0, 0)
                .color(255, 0, 255, 255)
                .texture(1, 1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(normalMatrix, 0, 1, 0)
                .next();
    }
}
