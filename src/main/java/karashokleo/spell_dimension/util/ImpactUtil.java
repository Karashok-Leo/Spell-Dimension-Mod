package karashokleo.spell_dimension.util;

import it.unimi.dsi.fastutil.Function;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.mixin.modded.SpellHelperInvoker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.event.CombatEvents;
import net.spell_engine.api.spell.CustomSpellHandler;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.SpellEvents;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.WorldScheduler;
import net.spell_engine.internals.arrow.ArrowHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;
import net.spell_power.api.SpellPower;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ImpactUtil
{
    public static SpellHelper.ImpactContext createContext(LivingEntity caster, Spell spell)
    {
        return new SpellHelper.ImpactContext(1.0F, 1.0F, null, SpellPower.getSpellPower(spell.school, caster), SpellHelper.impactTargetingMode(spell));
    }

    /// copied from SpellHelper
    public static void performSpell(World world, LivingEntity caster, Identifier spellId, List<Entity> targets, SpellCast.Action action, float progress)
    {
        Spell spell = SpellRegistry.getSpell(spellId);
        ItemStack itemStack = caster.getMainHandStack();
        if (spell == null) return;
        SpellInfo spellInfo = new SpellInfo(spell, spellId);
        Spell.Release.Target targeting = spell.release.target;
        boolean released = action == SpellCast.Action.RELEASE;

        SpellImpactEvents.PRE.invoker().invoke(world, caster, targets, spellInfo);
        SpellImpactEvents.POST.invoker().invoke(world, caster, targets, spellInfo);

        SpellHelper.ImpactContext context = createContext(caster, spell);
        if (spell.release.custom_impact)
        {
            Function<CustomSpellHandler.Data, Boolean> handler = CustomSpellHandler.handlers.get(spellId);
            released = false;
            if (handler != null && caster instanceof PlayerEntity player)
            {
                released = handler.apply(new CustomSpellHandler.Data(player, targets, itemStack, action, progress, context));
            }
        } else
        {
            Optional<Entity> optionalTarget = targets.stream().findFirst();
            switch (targeting.type)
            {
                case AREA:
                    Vec3d center = caster.getPos().add(0.0, (double) (caster.getHeight() / 2.0F), 0.0);
                    Spell.Release.Target.Area area = spell.release.target.area;
                    SpellHelperInvoker.applyAreaImpact(world, caster, targets, spell.range, area, spellInfo, context.position(center), true);
                    break;
                case BEAM:
                    SpellHelperInvoker.beamImpact(world, caster, targets, spellInfo, context);
                    break;
                case CLOUD:
                    SpellHelper.placeCloud(world, caster, spellInfo, context);
                    released = true;
                    break;
                case CURSOR:
                    if (optionalTarget.isPresent())
                    {
                        SpellHelperInvoker.directImpact(world, caster, (Entity) optionalTarget.get(), spellInfo, context);
                    } else
                    {
                        released = false;
                    }
                    break;
                case PROJECTILE:
                    optionalTarget.ifPresent(entity -> shootProjectile(world, caster, entity, spellInfo, context));
                    break;
                case METEOR:
                    if (optionalTarget.isPresent())
                    {
                        SpellHelper.fallProjectile(world, caster, optionalTarget.get(), spellInfo, context);
                    } else
                    {
                        released = false;
                    }
                    break;
                case SELF:
                    SpellHelperInvoker.directImpact(world, caster, caster, spellInfo, context);
                    released = true;
                    break;
                case SHOOT_ARROW:
                    ArrowHelper.shootArrow(world, caster, spellInfo, context);
                    released = true;
            }
        }

        if (released)
        {
            ParticleHelper.sendBatches(caster, spell.release.particles);
            SoundHelper.playSound(world, caster, spell.release.sound);

            if (CombatEvents.SPELL_CAST.isListened() && caster instanceof PlayerEntity player)
            {
                CombatEvents.SpellCast.Args args = new CombatEvents.SpellCast.Args(player, spellInfo, targets, action, progress);
                CombatEvents.SPELL_CAST.invoke((listener) -> listener.onSpellCast(args));
            }
        }
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

    public static void shootProjectile(World world, LivingEntity caster, Entity target, SpellInfo spellInfo, SpellHelper.ImpactContext context)
    {
        shootProjectile(world, caster, SpellHelper.launchPoint(caster), target.getPos().subtract(caster.getPos()), spellInfo.spell().range, spellInfo, context);
    }

    public static void shootProjectile(World world, LivingEntity caster, Vec3d position, Vec3d direction, float range, SpellInfo spellInfo, SpellHelper.ImpactContext context)
    {
        shootProjectile(world, caster, position, direction, range, spellInfo, context, 0);
    }

    private static void shootProjectile(World world, LivingEntity caster, Vec3d position, Vec3d direction, float range, SpellInfo spellInfo, SpellHelper.ImpactContext context, int sequenceIndex)
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
