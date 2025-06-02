package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class QuantumFieldRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
    private static final Identifier SKIN = SpellDimension.modLoc("textures/entity/quantum_field.png");
    private final M model;

    public QuantumFieldRenderer(FeatureRendererContext<T, M> context)
    {
        super(context);
        model = context.getModel();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch)
    {
        if (!shouldRender(entity))
        {
            return;
        }

        float f = (float) entity.age + tickDelta;
        EntityModel<T> entityModel = this.getEnergySwirlModel();
        entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
        this.getContextModel().copyStateTo(entityModel);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), this.getEnergySwirlX(f) % 1.0F, f * 0.01F % 1.0F));
        entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 1.0F);
    }

    protected boolean shouldRender(T entity)
    {
        return entity.hasStatusEffect(AllStatusEffects.QUANTUM_FIELD);
    }

    protected float getEnergySwirlX(float partialAge)
    {
        return partialAge * 0.01F;
    }

    protected Identifier getEnergySwirlTexture()
    {
        return SKIN;
    }

    protected EntityModel<T> getEnergySwirlModel()
    {
        return model;
    }
}
