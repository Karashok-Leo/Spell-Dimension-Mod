package net.karashokleo.spelldimension.spell;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.karashokleo.spelldimension.SpellDimensionNetworking;
import net.karashokleo.spelldimension.component.BlazingMarkComponent;
import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.karashokleo.spelldimension.util.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.spell_power.api.MagicSchool;

public class BlazingMark
{
    public static final int TOTAL_DURATION = 200;
    public static final int TRIGGER_TIME = 100;
    public static final int MAX_DAMAGE = 30;
    public static final float PROPORTION = 0.5F;

    public static float getTriggerTime()
    {
        return (TOTAL_DURATION - TRIGGER_TIME) / 20.0F;
    }

    public static final ServerLivingEntityEvents.AllowDamage LISTENER = (entity, source, amount) ->
    {
        if (source.getAttacker() != null &&
                source.getAttacker() instanceof LivingEntity attacker)
        {
            StatusEffectInstance instance = attacker.getStatusEffect(AllStatusEffects.IGNITE_EFFECT);
            if (instance == null) return true;
            BlazingMarkComponent component = BlazingMarkComponent.get(entity);
            if (component.getDuration() > TRIGGER_TIME)
                component.accumulateDamage(amount);
            else if (component.getDuration() <= 0)
                BlazingMarkComponent.applyToLiving(entity, attacker, instance.getAmplifier() + 1);
        }
        return true;
    };

    public static void trigger(LivingEntity source, LivingEntity caster, float damage, int amplifier)
    {
        DamageUtil.spellDamage(source, MagicSchool.FIRE, caster, damage * amplifier * BlazingMark.PROPORTION, false);
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
