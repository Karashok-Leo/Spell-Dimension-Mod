package karashokleo.spell_dimension.util;

import net.minecraft.entity.Entity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.particle.Particles;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ParticleUtil
{
    public static final ParticleEffect CRIT = ParticleTypes.CRIT;

    private static final Map<SpellSchool, ParticleEffect> map = Map.of(
        SpellSchools.ARCANE, Particles.arcane_spell.particleType,
        SpellSchools.FIRE, ParticleTypes.FLAME,
        SpellSchools.FROST, Particles.snowflake.particleType,
        SpellSchools.HEALING, Particles.healing_ascend.particleType,
        SpellSchools.LIGHTNING, ParticleTypes.SMOKE,
        SpellSchools.SOUL, ParticleTypes.SOUL_FIRE_FLAME
    );

    public static ParticleEffect getDustParticle(int color)
    {
        return new DustParticleEffect(Vec3d.unpackRgb(color).toVector3f(), 3.0f);
    }

    public static ParticleEffect getParticle(@Nullable SpellSchool school)
    {
        return school == null ? CRIT : map.getOrDefault(school, CRIT);
    }

    public static void ringParticleEmit(Entity entity, int amount, double divisor, ParticleEffect effect)
    {
        for (int i = 0; i < amount; i++)
        {
            double radian = Math.toRadians(i * 360.0 / amount);
            double x = Math.cos(radian) / divisor;
            double z = Math.sin(radian) / divisor;
            entity.getWorld().addParticle(effect, entity.getX(), entity.getY() + entity.getHeight() * 0.5F, entity.getZ(), x, 0.05, z);
        }
    }

    public static void ringParticle(Entity entity, int amount, double divisor, ParticleEffect effect)
    {
        for (int i = 0; i < amount; i++)
        {
            double radian = Math.toRadians(i * 360.0 / amount);
            double x = entity.getX() + Math.cos(radian) * amount / divisor;
            double z = entity.getZ() + Math.sin(radian) * amount / divisor;
            entity.getWorld().addParticle(effect, x, entity.getY() + entity.getHeight() * 0.5F, z, 0.0, 0.0, 0.0);
        }
    }

    public static void sparkParticleEmit(ServerWorld world, Vec3d pos, int count)
    {
        world.spawnParticles(
            ParticleTypes.END_ROD,
            pos.getX(),
            pos.getY(),
            pos.getZ(),
            count,
            0.01,
            0.01,
            0.01,
            0.06
        );
    }
}
