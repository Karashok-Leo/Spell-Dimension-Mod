package net.karashokleo.spelldimension.spell;

import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.component.NucleusComponent;
import net.karashokleo.spelldimension.config.AllConfigs;
import net.karashokleo.spelldimension.util.DamageUtil;
import net.karashokleo.spelldimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.CustomSpellHandler;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.MagicSchool;
import net.spell_power.api.SpellPower;

import java.util.Optional;

public class NucleusSpell implements ISpellHandler
{
    public static final Identifier ICICLE = SpellDimension.modLoc("nuclear_icicle");
    public static final double MULTIPLIER = -0.75D;
    public static final int TOTAL_DURATION = 80;

    @Override
    public Boolean handle(CustomSpellHandler.Data data)
    {
        Optional<Entity> target = data.targets().stream().findFirst();
        if (target.isPresent() && (target.get() instanceof LivingEntity livingEntity) && livingEntity.isAttackable())
        {
            NucleusComponent.applyToLiving(livingEntity, data.caster());
            return true;
        } else
            return false;
    }

    public static void nucleusBoom(LivingEntity source, LivingEntity caster)
    {
        if (caster == null) return;
        int amplifier = 0;
        if (caster instanceof PlayerEntity player)
            amplifier = MageComponent.get(player).grade();
        Spell spell = SpellRegistry.getSpell(ICICLE);
        if (spell == null) return;

        //Damage
        float damage = (float) DamageUtil.calculateDamage(caster, MagicSchool.FROST, AllConfigs.nucleus.value.damage, amplifier);
        DamageUtil.spellDamage(source, MagicSchool.FROST, caster, damage, false);

        //Adjust amplifier
        float height = source.getHeight();
        spell.range = Math.max(3 + amplifier, height * (1.6F + amplifier * 0.4F));
        for (Spell.Impact impact : spell.impact)
            if (impact.action.status_effect != null)
                impact.action.status_effect.amplifier = amplifier;

        SpellInfo spellInfo = new SpellInfo(spell, ICICLE);
        SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(1.0F, 1.0F, null, SpellPower.getSpellPower(spell.school, caster), SpellHelper.impactTargetingMode(spell));
        int step = 72 / (amplifier + 1);
        for (int i = 0; i < 360; i += step)
            for (int j = 0; j < 360; j += step)
            {
                Vec3d direction = ImpactUtil.fromEulerAngle(i, j, 0);
                ImpactUtil.shootProjectile(source.getWorld(), caster, source.getPos().add(0, height / 2, 0), direction, spellInfo, context);
            }
    }
}
