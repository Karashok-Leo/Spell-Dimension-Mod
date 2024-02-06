package net.karashokleo.spelldimension.effect;

import net.karashokleo.spelldimension.util.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_power.api.MagicSchool;

public class FrostedEffect extends StatusEffect
{
    public static final String UUID = "D6FABA0B-44F5-FC1A-6F2D-5F3C26119D51";
    public static final double MULTIPLIER = -0.2D;

    protected FrostedEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x00FFFF);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID, MULTIPLIER, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        LivingEntity attacker = entity.getLastAttacker();
        float addition = attacker == null ? 0 : (float) DamageUtil.calculateDamage(attacker, MagicSchool.FROST, 0.4, 1.2, amplifier);
        DamageUtil.spellDamage(entity, MagicSchool.FROST, attacker, 2.0F + addition, false);
        if (entity.canFreeze())
        {
            entity.setInPowderSnow(true);
            entity.setFrozenTicks(entity.getMinFreezeDamageTicks());
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 25 == 0;
    }
}
