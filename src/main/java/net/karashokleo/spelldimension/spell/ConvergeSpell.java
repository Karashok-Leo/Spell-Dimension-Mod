package net.karashokleo.spelldimension.spell;

import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.component.MageComponent;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.karashokleo.spelldimension.util.DamageUtil;
import net.karashokleo.spelldimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.MagicSchool;

public class ConvergeSpell
{
    private static final Identifier[] CONVERGE_SPELL = {SpellDimension.modLoc("converge1"), SpellDimension.modLoc("converge2"), SpellDimension.modLoc("converge3")};
    private static final ParticleBatch[] PARTICLE = {new ParticleBatch(
            "minecraft:explosion_emitter",
            ParticleBatch.Shape.SPHERE,
            ParticleBatch.Origin.CENTER,
            null,
            1,
            0.25F,
            0.5F,
            0
    )};

    public static boolean test(Identifier spellId)
    {
        for (Identifier id : CONVERGE_SPELL)
            if (spellId.equals(id))
                return true;
        return false;
    }

    public static void convergeImpact(SpellProjectile projectile, Identifier spellId)
    {
        if (!ConvergeSpell.test(spellId)) return;
        convergeImpact(projectile);
    }

    public static void convergeImpact(SpellProjectile projectile)
    {
        Entity owner = projectile.getOwner();
        if (owner == null ||
                owner.isRemoved() ||
                (!(owner instanceof PlayerEntity player)) ||
                (!MageComponent.get(player).greaterThan(new Mage(1, MagicSchool.ARCANE, MageMajor.CONVERGE))))
            return;
        int grade = MageComponent.get(player).grade();
        MagicSchool school = MageComponent.get(player).school();
        assert school != null;
        ParticleHelper.sendBatches(projectile, PARTICLE);
        SoundHelper.playSoundEvent(projectile.getWorld(), projectile, SoundEvents.ENTITY_GENERIC_EXPLODE);
        float addition = (float) DamageUtil.calculateDamage(player, MagicSchool.FROST, 0.4, 1.2, grade);
        ImpactUtil.applyAreaImpact(
                projectile,
                3 + grade * 0.8F,
                target -> !ImpactUtil.isAlly(player, target),
                target ->
                {
                    Vec3d movement = projectile.getPos().subtract(target.getPos()).multiply(0.12 + grade * 0.03);
                    target.setVelocity(movement);
                    DamageUtil.spellDamage(target, school, player, 4F + addition, false);
                }
        );
    }
}
