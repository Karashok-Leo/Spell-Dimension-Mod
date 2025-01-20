package karashokleo.spell_dimension.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.Tameable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellEvents;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.WorldScheduler;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ImpactUtil
{
    public static void applyAreaImpact(Entity origin, double range, Predicate<LivingEntity> predicate, Consumer<LivingEntity> consumer)
    {
        List<LivingEntity> targets = getLivingsInRange(origin, range, predicate);
        if (targets.isEmpty()) return;
        targets.forEach(consumer);
    }

    public static boolean isAlly(LivingEntity origin, LivingEntity target)
    {
        return origin == target ||
               origin.isTeammate(target) ||
               (target instanceof Tameable tameable && tameable.getOwner() == origin) ||
               (target instanceof Ownable ownable && ownable.getOwner() == origin) ||
               PartyUtil.isPartner(origin, target);
    }

    public static Vec3d fromEulerAngle(float pitch, float yaw, float roll)
    {
        float x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float y = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        return new Vec3d(x, y, z);
    }

    public static void shootProjectile(World world, LivingEntity caster, Vec3d position, Vec3d direction, float range, SpellInfo spellInfo, SpellHelper.ImpactContext context)
    {
        shootProjectile(world, caster, position, direction, range, spellInfo, context, 0);
    }

    public static void shootProjectile(World world, LivingEntity caster, Vec3d position, Vec3d direction, float range, SpellInfo spellInfo, SpellHelper.ImpactContext context, int sequenceIndex)
    {
        if (world.isClient()) return;

        var spell = spellInfo.spell();
        var data = spell.release.target.projectile;
        var projectileData = data.projectile;
        var mutablePerks = projectileData.perks.copy();
        var mutableLaunchProperties = data.launch_properties.copy();
        var projectile = new SpellProjectile(world, caster,
                position.getX(), position.getY(), position.getZ(),
                SpellProjectile.Behaviour.FLY, spellInfo.id(), null, context, mutablePerks);

        if (SpellEvents.PROJECTILE_SHOOT.isListened())
            SpellEvents.PROJECTILE_SHOOT.invoke((listener) -> listener.onProjectileLaunch(
                    new SpellEvents.ProjectileLaunchEvent(projectile, mutableLaunchProperties, caster, null, spellInfo, context, sequenceIndex)));

        projectile.setVelocity(direction.x, direction.y, direction.z, mutableLaunchProperties.velocity, projectileData.divergence);
        projectile.range = range > 0 ? range : spell.range;

        world.spawnEntity(projectile);

        if (sequenceIndex == 0 && mutableLaunchProperties.extra_launch_count > 0)
            for (int i = 0; i < mutableLaunchProperties.extra_launch_count; i++)
            {
                var ticks = (i + 1) * mutableLaunchProperties.extra_launch_delay;
                int nextSequenceIndex = i + 1;
                ((WorldScheduler) world).schedule(ticks, () ->
                {
                    if (caster == null || !caster.isAlive())
                        return;
                    shootProjectile(world, caster, position, direction, range, spellInfo, context, nextSequenceIndex);
                });
            }
    }

    public static List<LivingEntity> getLivingsInRange(Entity origin, double range, Predicate<LivingEntity> predicate)
    {
        return origin.getWorld().getEntitiesByClass(LivingEntity.class, origin.getBoundingBox().expand(range), predicate);
    }
}
