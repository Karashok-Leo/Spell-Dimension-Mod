package karashokleo.spell_dimension.content.effect;

import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.util.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_power.api.SpellSchools;

public class FrostedEffect extends StatusEffect
{
    public static String getDesc(boolean en)
    {
        return (en ?
                "Frozen and continuously receiving frost spell damage, based on %.1fx spell power." :
                "冻结并持续收到寒冰法术伤害，基于%.1f倍法术强度。")
                .formatted(SpellConfig.FROSTED_FACTOR);
    }

    public static final String UUID = "D6FABA0B-44F5-FC1A-6F2D-5F3C26119D51";
    public static final double MULTIPLIER = -0.2D;

    public FrostedEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x00FFFF);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID, MULTIPLIER, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        LivingEntity attacker = entity.getLastAttacker();
        float damage = attacker == null ? 2F : (float) DamageUtil.calculateDamage(attacker, SpellSchools.FROST, SpellConfig.FROSTED_FACTOR);
        DamageUtil.spellDamage(entity, SpellSchools.FROST, attacker, damage, false);
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
