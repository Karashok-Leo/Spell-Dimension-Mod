package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.component.BlazingMarkComponent;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.NetworkUtil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.SpellSchools;

public class BlazingMark
{
    public static final ParticleBatch[] PARTICLES = {
            new ParticleBatch(
                    "minecraft:flame",
                    ParticleBatch.Shape.SPHERE,
                    ParticleBatch.Origin.CENTER,
                    null,
                    0,
                    0,
                    72,
                    0.3F,
                    0.6F,
                    0,
                    0,
                    0,
                    false
            ),
            new ParticleBatch(
                    "minecraft:smoke",
                    ParticleBatch.Shape.SPHERE,
                    ParticleBatch.Origin.CENTER,
                    null,
                    0,
                    0,
                    48,
                    0.3F,
                    0.6F,
                    0,
                    0,
                    0,
                    false
            )
    };

    public static int getTotalDuration()
    {
        return SpellConfig.BLAZING_MARK.totalDuration();
    }

    public static int getTriggerDuration()
    {
        return SpellConfig.BLAZING_MARK.triggerDuration();
    }

    public static int getMaxDamage()
    {
        return SpellConfig.BLAZING_MARK.maxDamage();
    }

    public static float getProportion()
    {
        return SpellConfig.BLAZING_MARK.proportion();
    }

    public static float getTriggerTime()
    {
        return (getTotalDuration() - getTriggerDuration()) / 20.0F;
    }

    public static boolean mark(LivingEntity entity, DamageSource source, float amount)
    {
        if (source.getAttacker() != null &&
                source.getAttacker() instanceof LivingEntity attacker)
        {
            StatusEffectInstance instance = attacker.getStatusEffect(AllStatusEffects.IGNITE_EFFECT);
            if (instance == null) return true;
            BlazingMarkComponent component = BlazingMarkComponent.get(entity);
            if (component.getDuration() > getTriggerDuration())
                component.accumulateDamage(amount);
            else if (component.getDuration() <= 0)
                BlazingMarkComponent.applyToLiving(entity, attacker, instance.getAmplifier() + 1);
        }
        return true;
    }

    public static void trigger(LivingEntity source, LivingEntity caster, float damage, int amplifier)
    {
        ParticleHelper.sendBatches(source, PARTICLES);
        DamageUtil.spellDamage(source, SpellSchools.FIRE, caster, damage * amplifier * BlazingMark.getProportion(), false);
        source.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, amplifier, false, false));
    }

    public static void sendDustPacket(Entity trackedEntity, Vec3d pos, int count, int color, float range)
    {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
        buf.writeInt(count);
        buf.writeInt(color);
        buf.writeFloat(range);
        NetworkUtil.sendToTrackers(trackedEntity, NetworkUtil.DUST_PACKET, buf);
    }
}
