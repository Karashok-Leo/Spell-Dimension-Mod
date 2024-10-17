package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

public class ConvergeSpell
{
    public static final Identifier SPELL_ID = SpellDimension.modLoc("converge");
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
        if (spellId.equals(SPELL_ID))
            convergeImpact(projectile);
    }

    public static void convergeImpact(SpellProjectile projectile)
    {
        Entity owner = projectile.getOwner();
        if (owner == null ||
                owner.isRemoved() ||
                (!(owner instanceof PlayerEntity player)))
            return;
        convergeImpact(player, projectile, projectile.getPos());
    }

    public static void convergeImpact(PlayerEntity player, Entity tracked, Vec3d pos)
    {
        SpellPower.Result power = SpellPower.getSpellPower(SpellSchools.ARCANE, player);
        int amplifier = Math.min((int) (power.baseValue()) / 24 + 1, 3);
        ParticleHelper.sendBatches(tracked, PARTICLE);
        SoundHelper.playSoundEvent(tracked.getWorld(), tracked, SoundEvents.ENTITY_GENERIC_EXPLODE);
        float damage = (float) DamageUtil.calculateDamage(player, SpellSchools.ARCANE, SpellConfig.CONVERGE, amplifier);
        ImpactUtil.applyAreaImpact(
                tracked,
                3 + amplifier * 0.8F,
                target -> !ImpactUtil.isAlly(player, target),
                target ->
                {
                    Vec3d movement = pos.subtract(target.getPos()).multiply(0.12 + amplifier * 0.03);
                    target.setVelocity(movement);
                    DamageUtil.spellDamage(target, SpellSchools.ARCANE, player, damage, false);
                }
        );
    }
}
