package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.entity.BallLightningEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BallLightningRenderer extends EntityRenderer<BallLightningEntity>
{
    public static final EntityModelLayer MODEL_LAYER_LOCATION = new EntityModelLayer(SpellDimension.modLoc("ball_lightning"), "main");
    private static final Identifier[] SWIRL_TEXTURES = {
            SpellDimension.modLoc("textures/entity/ball_lightning/ball_lightning_0.png"),
            SpellDimension.modLoc("textures/entity/ball_lightning/ball_lightning_1.png"),
            SpellDimension.modLoc("textures/entity/ball_lightning/ball_lightning_2.png"),
            SpellDimension.modLoc("textures/entity/ball_lightning/ball_lightning_3.png"),
//            SpellDimension.modLoc("textures/entity/ball_lightning/ball_lightning_4.png")
    };

    private final ModelPart orb;

    public BallLightningRenderer(EntityRendererFactory.Context ctx)
    {
        super(ctx);
        ModelPart modelpart = ctx.getPart(MODEL_LAYER_LOCATION);
        this.orb = modelpart.getChild("orb");
    }

    @Override
    public void render(BallLightningEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
    {
        matrices.push();
        matrices.translate(0, entity.getBoundingBox().getYLength() * .5f, 0);

        for (int i = 0; i < 3; i++)
        {
            matrices.push();
            float r = 0.25f;
            float g = 0.8f;
            float b = 1.0f;
            r = MathHelper.clamp(r + r * i, 0, 1f);
            g = MathHelper.clamp(g + g * i, 0, 1f);
            b = MathHelper.clamp(b + b * i, 0, 1f);
            float f = entity.age + tickDelta + i * 777;
            float swirlX = MathHelper.cos(.065f * f) * 180;
            float swirlY = MathHelper.sin(.065f * f) * 180;
            float swirlZ = MathHelper.cos(.065f * f + 5464) * 180;
            float scalePerLayer = 0.2f;
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(swirlX * (int) Math.pow(-1, i)));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(swirlY * (int) Math.pow(-1, i)));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(swirlZ * (int) Math.pow(-1, i)));
            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(getSwirlTextureLocation(entity, i * i), 0, 0));
            float scale = 2f - i * scalePerLayer;
            matrices.scale(scale, scale, scale);
            this.orb.render(matrices, consumer, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, r, g, b, 1f);
            matrices.pop();
        }

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    public static TexturedModelData createBodyLayer()
    {
        ModelData mesh = new ModelData();
        ModelPartData part = mesh.getRoot();
        part.addChild("orb", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.NONE);
        return TexturedModelData.of(mesh, 8, 8);
    }

    @Override
    public Identifier getTexture(BallLightningEntity entity)
    {
        return SWIRL_TEXTURES[0];
    }

    private Identifier getSwirlTextureLocation(BallLightningEntity entity, int offset)
    {
        int frame = (entity.age + offset) % SWIRL_TEXTURES.length;
        return SWIRL_TEXTURES[frame];
    }
}
