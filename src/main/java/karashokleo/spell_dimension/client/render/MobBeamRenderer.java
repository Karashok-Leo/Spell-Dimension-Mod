package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.api.BeamProvider;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.client.beam.BeamEmitterEntity;
import net.spell_engine.client.render.BeamRenderer;
import net.spell_engine.internals.Beam;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.utils.TargetHelper;

public class MobBeamRenderer<T extends MobEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
    public MobBeamRenderer(FeatureRendererContext<T, M> context)
    {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch)
    {
        if (entity.isDead() ||
            !(entity instanceof BeamProvider caster))
        {
            return;
        }
        var beamAppearance = caster.getBeam();
        if (beamAppearance == null)
        {
            return;
        }

        LivingEntity target = caster.getBeamTarget();
        if (target == null)
        {
            return;
        }

        matrices.push();

        var launchHeight = entity.getHeight() * 0.12 * entity.getScaleFactor();

        Vec3d targetPos = new Vec3d(target.prevX, target.prevY, target.prevZ).lerp(target.getPos(), tickDelta);
        Vec3d entityPos = new Vec3d(entity.prevX, entity.prevY, entity.prevZ).lerp(entity.getPos(), tickDelta);

        var lookVector = targetPos.subtract(entityPos);

//        Vec3d lookVector = Vec3d.fromPolar(entity.prevPitch, entity.prevYaw);
//        lookVector = lookVector.lerp(Vec3d.fromPolar(entity.getPitch(), entity.getYaw()), tickDelta);

        lookVector = lookVector.normalize();
        var beamPosition = TargetHelper.castBeam(entity, lookVector, 32);

        var offset = new Vec3d(0.0, launchHeight, SpellHelper.launchPointOffsetDefault);

        float pitchOffset = (float) Math.acos(lookVector.y) * 180F / MathHelper.PI;
        float yawOffset = (float) Math.atan2(lookVector.z, lookVector.x) * 180F / MathHelper.PI;
        float yaw = MathHelper.lerp(tickDelta, entity.prevBodyYaw, entity.getBodyYaw());

        renderBeamFromMob(
            matrices,
            vertexConsumers,
            beamAppearance,
            180 - pitchOffset,
            90 + yawOffset - yaw,
            beamPosition.length(),
            offset,
            entity.getWorld().getTime(),
            tickDelta
        );
        ((BeamEmitterEntity) entity).setLastRenderedBeam(new Beam.Rendered(beamPosition, beamAppearance));

        matrices.pop();
    }


    private static void renderBeamFromMob(
        MatrixStack matrixStack,
        VertexConsumerProvider vertexConsumerProvider,
        Spell.Release.Target.Beam beam,
        float pitchOffset,
        float yawOffset,
        float length,
        Vec3d offset,
        long time,
        float tickDelta
    )
    {
        var absoluteTime = (float) Math.floorMod(time, 40) + tickDelta;

        matrixStack.push();
        matrixStack.translate(0, offset.y, 0);

        // rotate to face the target
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yawOffset));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitchOffset));

        // translate from body center to hands
        matrixStack.translate(0, offset.z, 0); // At this point everything is so rotated, we need to translate along y to move along z

        // beam self rotation
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(absoluteTime * 2.25F - 45.0F));

        var texture = new Identifier(beam.texture_id);
        var color = beam.color_rgba;
        var red = (color >> 24) & 255;
        var green = (color >> 16) & 255;
        var blue = (color >> 8) & 255;
        var alpha = color & 255;

        BeamRenderer.renderBeam(matrixStack, vertexConsumerProvider,
            texture, time, tickDelta, beam.flow, true,
            (int) red, (int) green, (int) blue, (int) alpha,
            0, length, beam.width);

        matrixStack.pop();
    }
}
