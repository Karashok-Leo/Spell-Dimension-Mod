package karashokleo.spell_dimension.util;

import karashokleo.spell_dimension.config.DamageConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.spell_engine.entity.ConfigurableKnockback;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

public class DamageUtil
{
    public static double calculateDamage(LivingEntity player, SpellSchool school, DamageConfig.Damage damage, double exponent)
    {
        return damage.addition + damage.multiplier * SpellPower.getSpellPower(school, player).randomValue() * Math.pow(damage.base, exponent);
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

    @Nullable
    public static SpellSchool getDamageSchool(DamageSource source)
    {
        for (SpellSchool school : SchoolUtil.ALL)
            if (SchoolUtil.isMagic(school) && source.isOf(school.damageType))
                return school;
        return null;
    }
}
