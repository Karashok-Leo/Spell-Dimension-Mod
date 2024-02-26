package net.karashokleo.spelldimension.util;

import net.karashokleo.spelldimension.item.mod_item.EnchantedEssenceItem;
import net.karashokleo.spelldimension.item.mod_item.IMageItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.particle.Particles;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ParticleUtil
{
    private static final ParticleEffect defaultParticleType = ParticleTypes.END_ROD;
    private static final Map<MagicSchool, ParticleEffect> map = Map.of(
            MagicSchool.ARCANE, Particles.arcane_spell.particleType,
            MagicSchool.FIRE, ParticleTypes.FLAME,
            MagicSchool.FROST, Particles.snowflake.particleType,
            MagicSchool.HEALING, Particles.healing_ascend.particleType,
            MagicSchool.LIGHTNING, ParticleTypes.SMOKE,
            MagicSchool.SOUL, ParticleTypes.SOUL_FIRE_FLAME,
            MagicSchool.PHYSICAL_MELEE, ParticleTypes.CRIT,
            MagicSchool.PHYSICAL_RANGED, ParticleTypes.CRIT
    );

    public static int getSchoolColor(ItemStack stack)
    {
        if (stack.getItem() instanceof IMageItem item)
            return getSchoolColor(item.getMage(stack).school());
        else if (stack.getItem() instanceof EnchantedEssenceItem item)
            return getSchoolColor(item.getSchool(stack));
        else return 0xFFFFFF;
    }

    public static int getSchoolColor(@Nullable MagicSchool school)
    {
        return school == null ? 0xFFFFFF : school.color();
    }

    public static ParticleEffect getDustParticle(@Nullable MagicSchool school)
    {
        return getDustParticle(getSchoolColor(school));
    }

    public static ParticleEffect getDustParticle(int color)
    {
        return new DustParticleEffect(Vec3d.unpackRgb(color).toVector3f(), 1.0f);
    }

    public static ParticleEffect getParticle(@Nullable MagicSchool school)
    {
        return school == null ? defaultParticleType : map.get(school);
    }

    public static void ringParticleEmit(Entity entity, int amount, double divisor, @Nullable MagicSchool school)
    {
        ringParticleEmit(entity, amount, divisor, getParticle(school));
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

    public static void ringParticle(Entity entity, int amount, double divisor, @Nullable MagicSchool school)
    {
        ringParticle(entity, amount, divisor, getParticle(school));
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
}
