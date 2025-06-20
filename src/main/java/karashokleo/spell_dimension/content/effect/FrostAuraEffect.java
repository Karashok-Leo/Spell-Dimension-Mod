package karashokleo.spell_dimension.content.effect;

import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.List;

public class FrostAuraEffect extends StatusEffect
{
    public static String getDesc(boolean en)
    {
        return (en ?
                "Targets within the Frost Aura continue to receive the Frosted effect" :
                "霜环内目标持续受到霜冻效果");
    }

    public static final float RADIUS = 3;
    public static final float VELOCITY = 0.12F;
    public static final double[][] GRADES = {{0}, {-0.4, 0.4}, {-0.8, 0, 0.8}};

    public FrostAuraEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0x00FFFF);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        auraParticles(entity, amplifier);
        World world = entity.getWorld();
        if (world.getTime() % 20 != 0)
        {
            return;
        }
        List<Entity> entities = world.getOtherEntities(entity, entity.getBoundingBox().expand(3 + amplifier, 2 + amplifier, 3 + amplifier), EntityPredicates.VALID_LIVING_ENTITY);
        for (Entity e : entities)
        {
            LivingEntity target = ImpactUtil.castToLiving(e);
            if (target == null)
            {
                continue;
            }
            if (ImpactUtil.isAlly(entity, target))
            {
                continue;
            }
            EffectHelper.forceAddEffectWithEvent(target, new StatusEffectInstance(AllStatusEffects.FROSTED, 100, amplifier, false, false), entity);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return true;
    }

    private static void auraParticles(LivingEntity entity, int amplifier)
    {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;
        double radian = Math.toRadians(world.getTime() * 4 % 360);
        int grade = Math.min(amplifier, 2);
        for (int i = 0; i <= amplifier; i++)
        {
            for (double offset : GRADES[grade])
                world.spawnParticles(
                        new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SNOW.getDefaultState()),
                        entity.getX() + RADIUS * Math.cos(radian),
                        entity.getY() + 1 + offset,
                        entity.getZ() + RADIUS * Math.sin(radian),
                        1,
                        VELOCITY * Math.sin(radian),
                        0,
                        -VELOCITY * Math.cos(radian),
                        0.1
                );
            radian += Math.PI * 2 / (amplifier + 1);
        }
    }
}
