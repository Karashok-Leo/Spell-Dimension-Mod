package net.karashokleo.spelldimension.spell;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.karashokleo.spelldimension.SpellDimensionNetworking;
import net.karashokleo.spelldimension.component.BlazingMarkComponent;
import net.karashokleo.spelldimension.config.AllConfig;
import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.karashokleo.spelldimension.util.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.MagicSchool;

public class BlazingMark
{
    public static final ParticleBatch[] PARTICLES = {
            new ParticleBatch(
                    "flame",
                    ParticleBatch.Shape.SPHERE,
                    ParticleBatch.Origin.CENTER,
                    null,
                    72,
                    0.3F,
                    0.6F,
                    0
            ),
            new ParticleBatch(
                    "smoke",
                    ParticleBatch.Shape.SPHERE,
                    ParticleBatch.Origin.CENTER,
                    null,
                    48,
                    0.3F,
                    0.6F,
                    0
            )
    };

    public static int getTotalDuration()
    {
        return AllConfig.INSTANCE.blazing_mark.total_duration;
    }

    public static int getTriggerDuration()
    {
        return AllConfig.INSTANCE.blazing_mark.trigger_duration;
    }

    public static int getMaxDamage()
    {
        return AllConfig.INSTANCE.blazing_mark.max_damage;
    }

    public static float getProportion()
    {
        return AllConfig.INSTANCE.blazing_mark.proportion;
    }

    public static float getTriggerTime()
    {
        return (getTotalDuration() - getTriggerDuration()) / 20.0F;
    }

    public static final ServerLivingEntityEvents.AllowDamage LISTENER = (entity, source, amount) ->
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
    };

    public static void trigger(LivingEntity source, LivingEntity caster, float damage, int amplifier)
    {
        ParticleHelper.sendBatches(source, PARTICLES);
        DamageUtil.spellDamage(source, MagicSchool.FIRE, caster, damage * amplifier * BlazingMark.getProportion(), false);
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
        SpellDimensionNetworking.sendToTrackers(trackedEntity, SpellDimensionNetworking.DUST_PACKET, buf);
    }
}
