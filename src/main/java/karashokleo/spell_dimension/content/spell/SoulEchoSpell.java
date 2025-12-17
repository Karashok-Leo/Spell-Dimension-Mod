package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class SoulEchoSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        if (targets.isEmpty())
        {
            return;
        }
        if (!(targets.get(0) instanceof LivingEntity target))
        {
            return;
        }

        float range = spellInfo.spell().range;

        SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
        List<MobEntity> activeMinions = controllerComponent.getActiveMinions();

        launchEcho(caster, caster, target, range);
        for (MobEntity minion : activeMinions)
        {
            launchEcho(caster, minion, target, range);
        }
    }

    private static void launchEcho(LivingEntity caster, LivingEntity launcher, LivingEntity target, float range)
    {
        if (!(caster.getWorld() instanceof ServerWorld world))
        {
            return;
        }

        Vec3d from = launcher.getEyePos();
        Vec3d to = target.getEyePos();
        Vec3d direction = to.subtract(from).normalize();
        to = from.add(direction.multiply(range));

        // particles
        for (int i = 1; i < range; i++)
        {
            Vec3d pos = from.add(direction.multiply(i));
            world.spawnParticles(ParticleTypes.SONIC_BOOM, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }

        // sound
        launcher.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 1.0F);

        // damage
        float amount = (float) DamageUtil.calculateDamage(caster, SpellSchools.SOUL, 0.5f);
        List<LivingEntity> hit = ImpactUtil.getLivingsNearLineSegment(world, from, to, 2);
        for (LivingEntity living : hit)
        {
            if (RelationUtil.isAlly(caster, living))
            {
                continue;
            }
            DamageSource damageSource = SpellDamageSource.create(SpellSchools.SOUL, caster);
            damageSource.setBypassCooldown();
            living.damage(damageSource, amount);
        }
    }
}
