package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.RailgunEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class RailgunRenderer<T extends RailgunEntity> extends EntityRenderer<T>
{
    public static final Identifier TEXTURE = SpellDimension.modLoc("textures/entity/railgun.png");
    private static final float TEXTURE_WIDTH = 256;
    private static final float TEXTURE_HEIGHT = 64;

    public RailgunRenderer(EntityRendererFactory.Context ctx)
    {
        super(ctx);
    }

    @Override
    public Identifier getTexture(T entity)
    {
        return TEXTURE;
    }

    @Override
    public void render(T entity, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {
        if (!entity.fired)
        {
            return;
        }

        int frame = (entity.age - entity.firedAge) % 20;

        VertexConsumer buffer = vertexConsumers.getBuffer(getGlowingEffect(getTexture(entity)));

        renderStart(entity, frame, matrices, buffer, light);
        renderBeam(entity, frame, matrices, buffer, light);
        renderEnd(entity, frame, matrices, buffer, light);
    }

    public static RenderLayer getGlowingEffect(Identifier textureId)
    {
        RenderPhase.Texture shard = new RenderPhase.Texture(textureId, false, false);
        RenderLayer.MultiPhaseParameters parameters = RenderLayer.MultiPhaseParameters
                .builder()
                .texture(shard)
                .program(RenderLayer.BEACON_BEAM_PROGRAM)
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .cull(RenderPhase.DISABLE_CULLING)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .writeMaskState(RenderPhase.COLOR_MASK)
                .build(false);
        return RenderLayer.of("glow_effect", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, parameters);
    }

    //渲染四边形平面
    protected void renderFlatQuad(int frame, MatrixStack matrices, VertexConsumer vertexConsumer, int light, float size)
    {
        float minU = 0 + 16F / TEXTURE_WIDTH * frame;
        float minV = 0;
        float maxU = minU + 16F / TEXTURE_WIDTH;
        float maxV = minV + 16F / TEXTURE_HEIGHT;
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f positionMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, -size, -size, minU, minV, light);
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, -size, size, minU, maxV, light);
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, size, size, maxU, maxV, light);
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, size, -size, maxU, minV, light);
    }

    //渲染激光起始位置
    protected void renderStart(T entity, int frame, MatrixStack matrices, VertexConsumer vertexConsumer, int light)
    {
        matrices.push();
        Quaternionf quaternionf = this.dispatcher.getRotation();
        matrices.multiply(quaternionf);
        renderFlatQuad(frame, matrices, vertexConsumer, light, 2f);
        matrices.pop();
    }

    //渲染激光后半部分
    protected void renderEnd(T entity, int frame, MatrixStack matrices, VertexConsumer vertexConsumer, int light)
    {
        matrices.push();
        Vec3d subtract = entity.endPos.subtract(entity.getPos());
        matrices.translate(subtract.getX(), subtract.getY(), subtract.getZ());
        Quaternionf quaternionf = this.dispatcher.getRotation();
        matrices.multiply(quaternionf);
        renderFlatQuad(frame, matrices, vertexConsumer, light, 2f);
        matrices.pop();
    }

    //渲染光束
    protected void renderBeam(T entity, int frame, MatrixStack matrices, VertexConsumer vertexConsumer, int light)
    {
        Vec3d velocity = entity.getVelocity();
        float yaw = (float) (MathHelper.atan2(velocity.z, velocity.x) * (180F / (float) Math.PI));
        //noinspection SuspiciousNameCombination
        float pitch = (float) (MathHelper.atan2(velocity.horizontalLength(), velocity.y) * (double) (180F / (float) Math.PI));
        float length = (float) entity.endPos.distanceTo(entity.getPos());

        matrices.push();
        //旋转角度
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(yaw));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90 - pitch));

        //绘制一根射线
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MinecraftClient.getInstance().gameRenderer.getCamera().getPitch() + 90F));
        drawBeam(length, frame, matrices, vertexConsumer, light);
        matrices.pop();

        //绘制一根射线
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-MinecraftClient.getInstance().gameRenderer.getCamera().getPitch() - 90F));
        drawBeam(length, frame, matrices, vertexConsumer, light);
        matrices.pop();

        //绘制一根射线
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-MinecraftClient.getInstance().gameRenderer.getCamera().getPitch() + 180F));
        drawBeam(length, frame, matrices, vertexConsumer, light);
        matrices.pop();

        //绘制一根射线
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-MinecraftClient.getInstance().gameRenderer.getCamera().getPitch() - 180F));
        drawBeam(length, frame, matrices, vertexConsumer, light);
        matrices.pop();

        matrices.pop();
    }

    //绘制光束
    protected void drawBeam(float length, int frame, MatrixStack matrices, VertexConsumer vertexConsumer, int light)
    {
        float minU = 0;
        //通过控制帧数,来渲染二维UV的最高位置
        float minV = 16 / TEXTURE_HEIGHT + 1 / TEXTURE_HEIGHT * frame;
        float maxU = minU + 20 / TEXTURE_WIDTH;
        float maxV = minV + 1 / TEXTURE_HEIGHT;
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f positionMatrix = entry.getPositionMatrix();
        Matrix3f normalMatrix = entry.getNormalMatrix();
        float offset = 0;
        // beam radius
        float SIZE = 0.9f;
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, -SIZE, offset, minU, minV, light);
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, -SIZE, length, minU, maxV, light);
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, SIZE, length, maxU, maxV, light);
        drawVertex(positionMatrix, normalMatrix, vertexConsumer, SIZE, offset, maxU, minV, light);
    }

    protected void drawVertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float u, float v, int light)
    {
        vertexConsumer.vertex(positionMatrix, x, y, 0F)
                .color(1F, 1F, 1F, 1F)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(normalMatrix, 0F, 1F, 0F)
                .next();
    }
}
