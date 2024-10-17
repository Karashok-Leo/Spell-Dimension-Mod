package karashokleo.spell_dimension.render;

import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

public class ConsciousnessCoreRenderer implements BlockEntityRenderer<ConsciousnessCoreTile>
{
    public ConsciousnessCoreRenderer(BlockEntityRendererFactory.Context ctx)
    {
    }

    private static void renderBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, float tickDelta, long worldTime, int yOffset, int maxY, float[] color)
    {
        renderBeam(matrices, vertexConsumers, BeaconBlockEntityRenderer.BEAM_TEXTURE, tickDelta, 1.0F, worldTime, yOffset, maxY, color, 0.2F, 0.25F);
    }

    public static void renderBeam(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Identifier textureId, float tickDelta, float heightScale, long worldTime, int yOffset, int maxY, float[] color, float innerRadius, float outerRadius)
    {
        int i = yOffset + maxY;
        matrices.push();
        matrices.translate(0.5, 0.0, 0.5);
        float f = (float) Math.floorMod(worldTime, 40) + tickDelta;
        float g = maxY < 0 ? f : -f;
        float h = MathHelper.fractionalPart(g * 0.2F - (float) MathHelper.floor(g * 0.1F));
        float j = color[0];
        float k = color[1];
        float l = color[2];
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f * 2.25F - 45.0F));
        float m = 0.0F;
        float n = innerRadius;
        float o = innerRadius;
        float p = 0.0F;
        float q = -innerRadius;
        float r = 0.0F;
        float s = 0.0F;
        float t = -innerRadius;
        float w = -1.0F + h;
        float x = (float) maxY * heightScale * (0.5F / innerRadius) + w;
        renderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, false)), j, k, l, 1.0F, yOffset, i, 0.0F, n, o, 0.0F, q, 0.0F, 0.0F, t, 0.0F, 1.0F, x, w);
        matrices.pop();
        m = -outerRadius;
        n = -outerRadius;
        o = outerRadius;
        p = -outerRadius;
        q = -outerRadius;
        r = outerRadius;
        s = outerRadius;
        t = outerRadius;
        w = -1.0F + h;
        x = (float) maxY * heightScale + w;
        renderBeamLayer(matrices, vertexConsumers.getBuffer(RenderLayer.getBeaconBeam(textureId, true)), j, k, l, 0.125F, yOffset, i, m, n, o, p, q, r, s, t, 0.0F, 1.0F, x, w);
        matrices.pop();
    }

    private static void renderBeamLayer(MatrixStack matrices, VertexConsumer vertices, float red, float green, float blue, float alpha, int yOffset, int height, float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4, float u1, float u2, float v1, float v2)
    {
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x1, z1, x2, z2, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x4, z4, x3, z3, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x2, z2, x4, z4, u1, u2, v1, v2);
        renderBeamFace(matrix4f, matrix3f, vertices, red, green, blue, alpha, yOffset, height, x3, z3, x1, z1, u1, u2, v1, v2);
    }

    private static void renderBeamFace(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertices, float red, float green, float blue, float alpha, int yOffset, int height, float x1, float z1, float x2, float z2, float u1, float u2, float v1, float v2)
    {
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, height, x1, z1, u2, v1);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, yOffset, x1, z1, u2, v2);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, yOffset, x2, z2, u1, v2);
        renderBeamVertex(positionMatrix, normalMatrix, vertices, red, green, blue, alpha, height, x2, z2, u1, v1);
    }

    private static void renderBeamVertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertices, float red, float green, float blue, float alpha, int y, float x, float z, float u, float v)
    {
        vertices.vertex(positionMatrix, x, (float) y, z).color(red, green, blue, alpha).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    public boolean rendersOutsideBoundingBox(ConsciousnessCoreTile ConsciousnessCoreTile)
    {
        return true;
    }

    public int getRenderDistance()
    {
        return 256;
    }

    public boolean isInRenderDistance(ConsciousnessCoreTile ConsciousnessCoreTile, Vec3d vec3d)
    {
        return Vec3d.ofCenter(ConsciousnessCoreTile.getPos())
                .multiply(1.0, 0.0, 1.0)
                .isInRange(vec3d.multiply(1.0, 0.0, 1.0), this.getRenderDistance());
    }

    @Override
    public void render(ConsciousnessCoreTile entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        World world = entity.getWorld();
        if (world == null || !entity.isActivated()) return;

        List<ConsciousnessCoreTile.BeamSegment> beamSegments = entity.getBeamSegments();

        int startHeight = 0;
        int endHeight;
        for (int m = 0; m < beamSegments.size(); ++m)
        {
            ConsciousnessCoreTile.BeamSegment beamSegment = beamSegments.get(m);
            endHeight = m == beamSegments.size() - 1 ? BeaconBlockEntityRenderer.MAX_BEAM_HEIGHT : beamSegment.getHeight();
            renderBeam(matrices, vertexConsumers, tickDelta, world.getTime(), startHeight, endHeight, beamSegment.getColor());
            startHeight += beamSegment.getHeight();
        }
    }
}
