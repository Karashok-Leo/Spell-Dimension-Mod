package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.ParticleUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.ConfigurableKnockback;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

public class SoulBurstSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        Entity target = targets.isEmpty() ? player : targets.get(0);

        if (target instanceof MobEntity mob)
        {
            if (SoulControl.isSoulMinion(player, mob))
            {
                burst(player, mob, spellInfo);
            }
        } else if (target == player)
        {
            SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
            if (controllerComponent.isControlling())
            {
                burst(player, player, spellInfo);
                SoulControl.setControllingMinion(player, null);
            }
        }
    }

    private static void burst(LivingEntity caster, LivingEntity minion, SpellInfo spellInfo)
    {
        // particle and sound
        ParticleHelper.sendBatches(minion, ParticleUtil.EXPLOSION);
        SoundHelper.playSoundEvent(minion.getWorld(), minion, SoundEvents.ENTITY_GENERIC_EXPLODE);

        float amount = minion.getHealth();
        // kill minion after getting its health
        // should not use damage() or kill() here because minion can be a player
        minion.setHealth(0);

        List<LivingEntity> targets = ImpactUtil.getLivingsInRange(
            minion,
            5,
            target -> !RelationUtil.isAlly(caster, target)
        );
        if (targets.isEmpty())
        {
            return;
        }

        SpellImpactEvents.POST.invoker().invoke(caster.getWorld(), caster, new ArrayList<>(targets), spellInfo);

        Vec3d pos = minion.getPos();
        double knockback = 0.2 + Math.max(0f, Math.log10(amount) / 10d);
        for (LivingEntity target : targets)
        {
            Vec3d movement = target.getPos()
                .subtract(pos)
                .multiply(knockback)
                .add(0, 0.2, 0);
            target.setVelocity(movement);

            DamageSource damageSource = SpellDamageSource.create(SpellSchools.SOUL, caster);
            damageSource.setBypassMagic();
            // apply damage
            ((ConfigurableKnockback) target).pushKnockbackMultiplier_SpellEngine(0);
            target.damage(damageSource, amount);
            ((ConfigurableKnockback) target).popKnockbackMultiplier_SpellEngine();
        }
    }
}
