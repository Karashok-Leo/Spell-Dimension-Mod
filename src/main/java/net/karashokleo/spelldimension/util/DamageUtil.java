package net.karashokleo.spelldimension.util;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.entity.ConfigurableKnockback;
import net.spell_power.api.MagicSchool;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPower;
import org.jetbrains.annotations.Nullable;

public class DamageUtil
{
    public static double calculateDamage(LivingEntity player, MagicSchool school, double multiplier, double base, double exponent)
    {
        return multiplier * SpellPower.getSpellPower(school, player).randomValue() * Math.pow(base, exponent);
    }

    public static void spellDamage(LivingEntity entity, MagicSchool school, @Nullable LivingEntity attacker, float amount, boolean impact)
    {
        if (attacker == null) entity.damage(entity.getDamageSources().magic(), amount);
        else if (impact) entity.damage(SpellDamageSource.create(school, attacker), amount);
        else {
            ((ConfigurableKnockback)entity).pushKnockbackMultiplier_SpellEngine(0);
            entity.damage(SpellDamageSource.create(school, attacker), amount);
            ((ConfigurableKnockback)entity).popKnockbackMultiplier_SpellEngine();
        }
    }
}
