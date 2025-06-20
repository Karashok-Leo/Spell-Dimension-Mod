package karashokleo.spell_dimension.content.buff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.ParticleUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

public class BlazingMark implements Buff
{
    public static String getDesc(boolean en)
    {
        return (en ?
                "Your attack will leave a mark on the enemy, and some of the damage you inflict during this period will damage the enemy again in %.1f  seconds." :
                "你的攻击会在敌人身上留下一个印记, 期间你造成的部分伤害将会在%.1f秒后再次打击敌人, 并使其虚弱.")
                .formatted(BlazingMark.getTriggerTime());
    }

    public static final Codec<BlazingMark> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                    Codecs.NONNEGATIVE_INT.fieldOf("duration").forGetter(BlazingMark::getDuration),
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

    private int duration;
    private float damage;

    private BlazingMark(int duration, float damage)
    {
        this.duration = duration;
        this.damage = damage;
    }

    public BlazingMark()
    {
        this(
                SpellConfig.BLAZING_MARK_CONFIG.totalDuration(),
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
        if (duration == SpellConfig.BLAZING_MARK_CONFIG.triggerDuration())
            trigger(entity, source, damage);
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
        LivingEntity attacker = ImpactUtil.castToLiving(source.getAttacker());
        if (attacker == null)
        {
            return;
        }
        StatusEffectInstance instance = attacker.getStatusEffect(AllStatusEffects.IGNITE);
        if (instance == null)
        {
            return;
        }
        Buff.get(entity, TYPE).ifPresentOrElse(blazingMark ->
        {
            if (blazingMark.getDuration() > SpellConfig.BLAZING_MARK_CONFIG.triggerDuration())
                blazingMark.accumulateDamage(attacker, event.getAmount());
        }, () -> Buff.apply(entity, TYPE, new BlazingMark(), attacker));
    }

    public void accumulateDamage(LivingEntity caster, float amount)
    {
        double baseValue = SpellPower.getSpellPower(SpellSchools.FIRE, caster).baseValue();
        this.damage = Math.min(this.damage + amount, (float) baseValue * SpellConfig.BLAZING_MARK_CONFIG.maxDamageRatio());
    }

    public static void trigger(LivingEntity source, LivingEntity caster, float damage)
    {
        ParticleHelper.sendBatches(source, PARTICLES);
        DamageUtil.spellDamage(source, SpellSchools.FIRE, caster, damage * SpellConfig.BLAZING_MARK_CONFIG.reDamageRatio(), false);
        EffectHelper.forceAddEffectWithEvent(source, new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 2, false, false), caster);
    }

    private void particle(LivingEntity owner)
    {
        float f = Math.min(owner.getWidth(), owner.getHeight()) * 0.5F;
        int color = duration >= SpellConfig.BLAZING_MARK_CONFIG.triggerDuration() ?
                0xffff00 - 0x100 * (int) (0xff * damage / SpellConfig.BLAZING_MARK_CONFIG.maxDamageRatio()) :
                0x888888;
        Vec3d pos = owner.getPos()
                .add(0, owner.getHeight() + f, 0).addRandom(owner.getRandom(), 0.5F)
                .addRandom(owner.getRandom(), 0.5F);
        int count = (int) (f * 100);
        if (owner.getWorld() instanceof ServerWorld world)
            world.spawnParticles(
                    ParticleUtil.getDustParticle(color),
                    pos.x,
                    pos.y,
                    pos.z,
                    count,
                    0D,
                    0D,
                    0D,
                    0D
            );
    }

    public static float getTriggerTime()
    {
        return (SpellConfig.BLAZING_MARK_CONFIG.totalDuration() - SpellConfig.BLAZING_MARK_CONFIG.triggerDuration()) / 20.0F;
    }

    public int getDuration()
    {
        return duration;
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
