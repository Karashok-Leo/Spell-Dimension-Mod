package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.content.entity.BallLightningEntity;
import karashokleo.spell_dimension.init.AllEntities;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class MacroElectronRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer
{
    private BallLightningEntity entity;
    private BallLightningRenderer renderer;

    public MacroElectronRenderer()
    {
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        setUp(mc);
        if (entity == null || renderer == null)
        {
            return;
        }
        matrices.push();
        translate(mode, matrices);
        renderer.render(entity, 0, mc.getTickDelta(), matrices, vertexConsumers, light);
        matrices.pop();
    }

    private void setUp(MinecraftClient client)
    {
        World world = client.world;
        if (world == null)
        {
            entity = null;
            return;
        }
        if (entity != null &&
            entity.getWorld() != world)
        {
            entity = null;
        }
        if (entity == null)
        {
            entity = AllEntities.BALL_LIGHTNING.create(world);
        }
        if (entity == null)
        {
            return;
        }
        assert client.player != null;
        entity.age = client.player.age;

        EntityRenderDispatcher dispatcher = client.getEntityRenderDispatcher();
        EntityRendererFactory.Context context = new EntityRendererFactory.Context(
                dispatcher,
                client.getItemRenderer(),
                client.getBlockRenderManager(),
                dispatcher.getHeldItemRenderer(),
                client.getResourceManager(),
                client.getEntityModelLoader(),
                client.textRenderer
        );
        renderer = new BallLightningRenderer(context);
    }

    private void translate(ModelTransformationMode mode, MatrixStack matrices)
    {
        switch (mode)
        {
            case GUI:
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            {
                matrices.translate(0.25, 0.4, 0.5);
                float size = 0.625f;
                matrices.scale(size, size, size);
                break;
            }
            case GROUND:
            {
                matrices.translate(0.25, 0, 0.5);
                float size = 0.625f;
                matrices.scale(size, size, size);
                break;
            }
            case NONE:
            case HEAD:
            case FIXED:
            {
                matrices.translate(0.5, 0.5, 0.5);
                float size = 0.6f;
                matrices.scale(size, -size, size);
                matrices.translate(0, -0.45, 0);
                return;
            }
        }
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(135));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-155));
        float size = 0.6f;
        matrices.scale(size, size, size);
        matrices.translate(0, -1.6, 0);
    }
}
