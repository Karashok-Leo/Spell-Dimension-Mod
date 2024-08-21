package karashokleo.spell_dimension.content.buff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.config.SpellConfig;
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
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

public class BlazingMark implements Buff
{
    public static final Codec<BlazingMark> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                    Codecs.NONNEGATIVE_INT.fieldOf("duration").forGetter(BlazingMark::getDuration),
                    Codecs.NONNEGATIVE_INT.fieldOf("amplifier").forGetter(BlazingMark::getAmplifier),
                    Codec.FLOAT.fieldOf("damage").forGetter(BlazingMark::getDamage)
            ).apply(ins, BlazingMark::new)
    );
    public static final BuffType<BlazingMark> TYPE = new BuffType<>(CODEC, false);

    private static final ParticleBatch[] PARTICLES = {
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

    private int duration = 0;
    private int amplifier = 0;
    private float damage = 0;

    private BlazingMark(int duration, int amplifier, float damage)
    {
        this.duration = duration;
        this.amplifier = amplifier;
        this.damage = damage;
    }

    public BlazingMark(int amplifier)
    {
        this(
                SpellConfig.BLAZING_MARK.totalDuration(),
                amplifier,
                0
        );
    }

    @Override
    public void serverTick(LivingEntity entity, @Nullable LivingEntity source)
    {
        if (duration <= 0)
        {
            Buff.remove(entity, TYPE);
            return;
        }
        --duration;
        if (duration == SpellConfig.BLAZING_MARK.triggerDuration())
            trigger(entity, source, damage, amplifier);
        if (duration % 20 == 0)
        {
            if (duration == 0 || entity.isSubmergedInWater())
                Buff.remove(entity, TYPE);
            particle(entity);
        }
    }

    public static void mark(LivingDamageEvent event)
    {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (source.getAttacker() != null &&
                source.getAttacker() instanceof LivingEntity attacker)
        {
            StatusEffectInstance instance = attacker.getStatusEffect(AllStatusEffects.IGNITE);
            if (instance == null) return;
            Buff.get(entity, TYPE).ifPresentOrElse(blazingMark ->
            {
                if (blazingMark.getDuration() > SpellConfig.BLAZING_MARK.triggerDuration())
                    blazingMark.accumulateDamage(event.getAmount());
            }, () -> Buff.apply(entity, TYPE, new BlazingMark(instance.getAmplifier() + 1), attacker));
        }
    }

    public void accumulateDamage(float amount)
    {
        this.damage = Math.min(this.damage + amount, this.amplifier * SpellConfig.BLAZING_MARK.maxDamage());
    }

    public static void trigger(LivingEntity source, LivingEntity caster, float damage, int amplifier)
    {
        ParticleHelper.sendBatches(source, PARTICLES);
        DamageUtil.spellDamage(source, SpellSchools.FIRE, caster, damage * amplifier * SpellConfig.BLAZING_MARK.proportion(), false);
        source.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, amplifier, false, false));
    }

    private void particle(LivingEntity owner)
    {
        float f = Math.min(owner.getWidth(), owner.getHeight()) * 0.5F;
        Vec3d pos = owner.getPos().add(0, owner.getHeight() + f, 0).addRandom(owner.getRandom(), 0.5F);
        int color = duration >= SpellConfig.BLAZING_MARK.triggerDuration() ?
                0xffff00 - 0x100 * (int) (0xff * damage / (amplifier * SpellConfig.BLAZING_MARK.maxDamage())) :
                0x888888;
        sendDustPacket(owner, pos, (int) (f * 100), color, f);
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

    public static float getTriggerTime()
    {
        return (SpellConfig.BLAZING_MARK.totalDuration() - SpellConfig.BLAZING_MARK.triggerDuration()) / 20.0F;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getAmplifier()
    {
        return amplifier;
    }

    public float getDamage()
    {
        return damage;
    }

    @Override
    public BuffType<?> getType()
    {
        return TYPE;
    }
}
