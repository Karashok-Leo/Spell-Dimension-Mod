package karashokleo.spell_dimension.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.LocatePortalEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class LocatePortalRenderer extends EntityRenderer<LocatePortalEntity>
{
    public static final int FRAME_COUNT = 7;
    public static final Identifier TEXTURE = SpellDimension.modLoc("textures/entity/locate_portal.png");

    private static final int EMERGE_DURATION = 20;

    public LocatePortalRenderer(EntityRendererFactory.Context ctx)
    {
        super(ctx);
    }

    @Override
    public Identifier getTexture(LocatePortalEntity entity)
    {
        return TEXTURE;
    }

    @Override
    public void render(LocatePortalEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {
        int activeTime = entity.getActiveTime();
        if (activeTime < 0) return;

        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return;

        matrices.push();
        Vec3d playerV = player.getCameraPosVec(tickDelta);
        Vec3d portal = entity.getPos();

        // Rotate to face the player
        matrices.translate(0, entity.getHeight() / 2, 0);
        matrices.multiply(new Quaternionf().rotationAxis(MathHelper.RADIANS_PER_DEGREE * 90, 0, 1, 0));
        matrices.multiply(new Quaternionf().rotationAxis(MathHelper.RADIANS_PER_DEGREE * (180F - (float) angleOf(portal, playerV)), 0, 1, 0));

        double emergeHeight = 3.6 * entity.getHeight();
        double yOffset = activeTime > EMERGE_DURATION ? 0 : emergeHeight * (1.0 * activeTime / EMERGE_DURATION - 1.0);
        matrices.translate(0, -yOffset, 0);

        float scale = activeTime > EMERGE_DURATION ? 2 : 2F * activeTime / EMERGE_DURATION;
        matrices.scale(scale, scale, 1);

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.getTexture(entity));
        VertexConsumer builder = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(this.getTexture(entity)));
//        int color = entity.getGateway().color().getValue();
//        int color = 0x00FF00;
//        int r = color >> 16 & 255, g = color >> 8 & 255, b = color & 255;
        float frameHeight = 1F / FRAME_COUNT;
        int frame = activeTime % FRAME_COUNT;

        float v1 = 1 - frame * frameHeight;
        float v2 = v1 - frameHeight;

        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        builder.vertex(matrix4f, -1, -1, 0)
//                .color(r, g, b, 255)
                .color(255, 255, 255, 255)
                .texture(1, v1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrices.peek().getNormalMatrix(), 0, 1, 0)
                .next();
        builder.vertex(matrix4f, -1, 1, 0)
//                .color(r, g, b, 255)
                .color(255, 255, 255, 255)
                .texture(1, v2)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrices.peek().getNormalMatrix(), 0, 1, 0)
                .next();
        builder.vertex(matrix4f, 1, 1, 0)
//                .color(r, g, b, 255)
                .color(255, 255, 255, 255)
                .texture(0, v2)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrices.peek().getNormalMatrix(), 0, 1, 0)
                .next();
        builder.vertex(matrix4f, 1, -1, 0)
//                .color(r, g, b, 255)
                .color(255, 255, 255, 255)
                .texture(0, v1)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(matrices.peek().getNormalMatrix(), 0, 1, 0)
                .next();

        matrices.pop();
    }

    public static double angleOf(Vec3d p1, Vec3d p2)
    {
        final double deltaY = p2.z - p1.z;
        final double deltaX = p2.x - p1.x;
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return result < 0 ? 360d + result : result;
    }
}
