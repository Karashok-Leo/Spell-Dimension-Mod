package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.config.SpellConfig;
import net.minecraft.entity.LivingEntity;
import net.spell_engine.entity.ConfigurableKnockback;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

public class DamageUtil
{
    public static double calculateDamage(LivingEntity caster, SpellSchool school, SpellConfig.Damage damage, double exponent)
    {
        return damage.addition() + damage.multiplier() * SpellPower.getSpellPower(school, caster).randomValue() * Math.pow(damage.base(), exponent);
    }

    public static void spellDamage(LivingEntity entity, SpellSchool school, @Nullable LivingEntity attacker, float amount, boolean impact)
    {
        if (attacker == null) entity.damage(entity.getDamageSources().magic(), amount);
        else if (impact) entity.damage(SpellDamageSource.create(school, attacker), amount);
        else
        {
            ((ConfigurableKnockback) entity).pushKnockbackMultiplier_SpellEngine(0);
            entity.damage(SpellDamageSource.create(school, attacker), amount);
            ((ConfigurableKnockback) entity).popKnockbackMultiplier_SpellEngine();
        }
    }
}
