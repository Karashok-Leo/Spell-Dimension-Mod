package karashokleo.spell_dimension.content.buff;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.item.trinket.GlacialNuclearEraItem;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Nucleus implements Buff
{
    public static final Codec<Nucleus> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                    Codecs.NONNEGATIVE_INT.fieldOf("duration").forGetter(Nucleus::getDuration)
            ).apply(ins, Nucleus::new)
    );
    public static final BuffType<Nucleus> TYPE = new BuffType<>(CODEC, true);

    public static final UUID uuid = UUID.fromString("977AE476-9F10-5499-4B5D-09B296214773");
    private static final String MODIFIER_KEY = "IceNucleus";
    private static SpellInfo MINI_ICICLE;
    private static SpellInfo ICICLE;
    public static final double MULTIPLIER = -0.75D;
    public static final int TOTAL_DURATION = 80;

    private int duration = 0;

    public Nucleus(int duration)
    {
        this.duration = duration;
    }

    public Nucleus()
    {
        this(TOTAL_DURATION);
    }

    private static SpellInfo getOrCreateSpellInfo(LivingEntity caster)
    {
        boolean era = TrinketCompat.hasItemInTrinket(caster, AllItems.GLACIAL_NUCLEAR_ERA);
        if (era && caster.getRandom().nextFloat() < GlacialNuclearEraItem.CHANCE)
        {
            if (ICICLE == null)
            {
                Identifier spellId = SpellDimension.modLoc("icicle");
                Spell spell = SpellRegistry.getSpell(spellId);
                assert spell != null;
                ICICLE = new SpellInfo(spell, spellId);
            }
            return ICICLE;
        } else
        {
            if (MINI_ICICLE == null)
            {
                Identifier spellId = SpellDimension.modLoc("mini_icicle");
                Spell spell = SpellRegistry.getSpell(spellId);
                assert spell != null;
                MINI_ICICLE = new SpellInfo(spell, spellId);
            }
            return MINI_ICICLE;
        }
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
        if (duration % 20 == 0)
        {
            if (duration == 0)
            {
                nucleusBoom(entity, source);
                Buff.remove(entity, TYPE);
            }
            if (entity.isOnFire())
                Buff.remove(entity, TYPE);
        }
    }

    @Override
    public void onApplied(LivingEntity entity, @Nullable LivingEntity source)
    {
        AttributeUtil.addModifier(entity, EntityAttributes.GENERIC_MOVEMENT_SPEED, uuid, MODIFIER_KEY, MULTIPLIER, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void onRemoved(LivingEntity entity, @Nullable LivingEntity source)
    {
        AttributeUtil.removeModifier(entity, EntityAttributes.GENERIC_MOVEMENT_SPEED, uuid);
    }

    public static void nucleusBoom(LivingEntity source, LivingEntity caster)
    {
        if (caster == null) return;
        SpellPower.Result power = SpellPower.getSpellPower(SpellSchools.FROST, caster);
        int amplifier = Math.min((int) (power.baseValue()) / 24 + 1, 3);
        SpellInfo icicleInfo = getOrCreateSpellInfo(caster);

        //Damage
        float damage = (float) DamageUtil.calculateDamage(caster, SpellSchools.FROST, SpellConfig.NUCLEUS_FACTOR, amplifier);
        DamageUtil.spellDamage(source, SpellSchools.FROST, caster, damage, false);

        //Adjust amplifier
        float height = source.getHeight();
        float range = Math.max(3 + amplifier, height * (1.6F + amplifier * 0.4F));

        SpellHelper.ImpactContext context = ImpactUtil.createContext(caster, icicleInfo.spell());

        int step = 72 / (amplifier + 1);
        for (int i = 0; i < 360; i += step)
            for (int j = 0; j < 360; j += step)
                ImpactUtil.shootProjectile(source.getWorld(), caster, source.getPos().add(0, height / 2, 0), ImpactUtil.fromEulerAngle(i, j, 0), range, icicleInfo, context);

        source.getWorld().playSound(null, source.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, source.getSoundCategory(), 1.0F, 1.0F);
    }

    public int getDuration()
    {
        return duration;
    }

    public float getScale()
    {
        return 2 - (float) this.duration / TOTAL_DURATION;
    }

    public boolean isActive()
    {
        return this.duration > 0;
    }

    @Override
    public BuffType<?> getType()
    {
        return TYPE;
    }
}
