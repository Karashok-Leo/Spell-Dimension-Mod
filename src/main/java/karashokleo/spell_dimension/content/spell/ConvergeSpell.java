package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

public class ConvergeSpell
{
    private static final Identifier CONVERGE_SPELL = SpellDimension.modLoc("converge");
    private static final ParticleBatch[] PARTICLE = {new ParticleBatch(
            "minecraft:explosion_emitter",
            ParticleBatch.Shape.SPHERE,
            ParticleBatch.Origin.CENTER,
            null,
            0,
            0,
            0,
            1,
            0.25F,
            0.5F,
            0,
            0,
            false
    )};

    public static void convergeImpact(SpellProjectile projectile, Identifier spellId)
    {
        if (spellId.equals(CONVERGE_SPELL))
            convergeImpact(projectile);
    }

    public static void convergeImpact(SpellProjectile projectile)
    {
        Entity owner = projectile.getOwner();
        if (owner == null ||
                owner.isRemoved() ||
                (!(owner instanceof PlayerEntity player)))
            return;
        SpellPower.Result power = SpellPower.getSpellPower(SpellSchools.ARCANE, player);
        int amplifier = Math.min((int) (power.baseValue()) / 24 + 1, 3);
        ParticleHelper.sendBatches(projectile, PARTICLE);
        SoundHelper.playSoundEvent(projectile.getWorld(), projectile, SoundEvents.ENTITY_GENERIC_EXPLODE);
        float damage = (float) DamageUtil.calculateDamage(player, SpellSchools.ARCANE, SpellConfig.CONVERGE, amplifier);
        ImpactUtil.applyAreaImpact(
                projectile,
                3 + amplifier * 0.8F,
                target -> !ImpactUtil.isAlly(player, target),
                target ->
                {
                    Vec3d movement = projectile.getPos().subtract(target.getPos()).multiply(0.12 + amplifier * 0.03);
                    target.setVelocity(movement);
                    DamageUtil.spellDamage(target, SpellSchools.ARCANE, player, damage, false);
                }
        );
    }
}
