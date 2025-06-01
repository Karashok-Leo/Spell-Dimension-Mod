package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

public class ConvergeSpell
{
    private static final ParticleBatch[] PARTICLE = {new ParticleBatch(
            "minecraft:explosion_emitter",
            ParticleBatch.Shape.SPHERE,
            ParticleBatch.Origin.CENTER,
            null,
            0,
            0,
            1,
            0.25F,
            0.5F,
            0,
            0,
            0,
            false
    )};

    public static void handle(SpellProjectile projectile, Identifier spellId, HitResult hitResult)
    {
        if (spellId.equals(AllSpells.CONVERGE))
            convergeImpact(projectile);
    }

    public static void convergeImpact(SpellProjectile projectile)
    {
        Entity owner = projectile.getOwner();
        if (owner == null ||
            owner.isRemoved() ||
            (!(owner instanceof LivingEntity caster)))
            return;
        convergeImpact(caster, projectile, projectile.getPos());
    }

    public static void convergeImpact(LivingEntity caster, Entity tracked, Vec3d pos)
    {
        SpellPower.Result power = SpellPower.getSpellPower(SpellSchools.ARCANE, caster);
        int amplifier = Math.min((int) (power.baseValue()) / 24 + 1, 3);
        ParticleHelper.sendBatches(tracked, PARTICLE);
        SoundHelper.playSoundEvent(tracked.getWorld(), tracked, SoundEvents.ENTITY_GENERIC_EXPLODE);
        float damage = (float) DamageUtil.calculateDamage(caster, SpellSchools.ARCANE, SpellConfig.CONVERGE_FACTOR);
        List<LivingEntity> targets = ImpactUtil.getLivingsInRange(
                tracked,
                3 + amplifier * 0.8F,
                target -> !ImpactUtil.isAlly(caster, target)
        );
        if (targets.isEmpty()) return;

        SpellInfo spellInfo = new SpellInfo(SpellRegistry.getSpell(AllSpells.CONVERGE), AllSpells.CONVERGE);
        SpellImpactEvents.BEFORE.invoker().beforeImpact(caster.getWorld(), caster, new ArrayList<>(targets), spellInfo);

        for (LivingEntity target : targets)
        {
            Vec3d movement = pos.subtract(target.getPos()).multiply(0.12 + amplifier * 0.03);
            target.setVelocity(movement);
            DamageUtil.spellDamage(target, SpellSchools.ARCANE, caster, damage, false);
        }
    }
}
