package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.content.particle.ZapParticleOption;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Learned from <a href="https://github.com/iron431/irons-spells-n-spellbooks/blob/1.20.1/src/main/java/io/redspace/ironsspellbooks/entity/spells/ChainLightning.java">...</a>
 */
public class ChainLightningEntity extends ProjectileEntity
{
    public static final ParticleBatch[] HIT_PARTICLES = {
            new ParticleBatch(
                    "spell_engine:electric_arc_a",
                    ParticleBatch.Shape.PIPE,
                    ParticleBatch.Origin.FEET,
                    null,
                    0,
                    0,
                    1,
                    0.06F,
                    0.12F,
                    0,
                    0,
                    0,
                    false
            ),
            new ParticleBatch(
                    "spell_engine:electric_arc_b",
                    ParticleBatch.Shape.PIPE,
                    ParticleBatch.Origin.FEET,
                    null,
                    0,
                    0,
                    1,
                    0.06F,
                    0.12F,
                    0,
                    0,
                    0,
                    false
            ),
    };

    public int power;
    public int lifespan;
    public int chainStep;
    public float range;
    public boolean canPenetrate;
    private LivingEntity initialTarget;
    private final ArrayList<LivingEntity> alreadyChained = new ArrayList<>();
    private final ArrayList<LivingEntity> lastChained = new ArrayList<>();

    public ChainLightningEntity(World world, Entity owner, LivingEntity initialTarget)
    {
        this(AllEntities.CHAIN_LIGHTNING, world);
        this.setOwner(owner);
        this.setPosition(initialTarget.getPos());
        this.initialTarget = initialTarget;
    }

    public ChainLightningEntity(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        this.setNoGravity(true);
        this.noClip = true;
        // default settings
        this.power = 1;
        this.lifespan = SpellConfig.CHAIN_LIGHTNING_CONFIG.lifespan();
        this.chainStep = SpellConfig.CHAIN_LIGHTNING_CONFIG.chainStep();
        this.range = SpellConfig.CHAIN_LIGHTNING_CONFIG.range();
        this.canPenetrate = false;
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Override
    public void tick()
    {
        super.tick();
        if (this.getWorld().isClient()) return;

        // discard if lifespan is over
        if (age > lifespan)
        {
            discard();
            return;
        }

        // update every 6 ticks
        if ((age - 1) % 6 == 0 && this.getWorld() instanceof ServerWorld world)
        {
            Entity owner = getOwner();
            if (owner == null) return;

            // if initial target not zapped yet
            if (notZappedYet(initialTarget))
            {
                chainTarget(world, initialTarget, owner);
            } else
            {
                int chainStep = 0;
                // cannot be enhanced for, because lastChained may be modified in this loop
                int size = lastChained.size();
                for (int i = 0; i < size; i++)
                {
                    LivingEntity chained = lastChained.get(i);
                    // get valid targets in range sorting by distance
                    var targets = world.getOtherEntities(chained, chained.getBoundingBox().expand(range), this::canHit);
                    targets.sort(Comparator.comparingDouble(o -> o.squaredDistanceTo(chained)));

                    for (Entity target : targets)
                    {
                        // break loop if chains this time has reached maxChainStep
                        if (chainStep >= this.chainStep) break;
                        // skip non living
                        if (!(target instanceof LivingEntity living)) continue;
                        // skip if out of range of last chained
                        if (target.squaredDistanceTo(chained) > range * range) continue;
                        // skip if blocked and cannot penetrate
                        if (!canPenetrate && isBlocked(world, chained.getEyePos(), target.getEyePos())) continue;

                        chainStep++;
                        chainTarget(world, living, chained);
                    }
                }
                // remove duplicated ones from lastChained
                lastChained.removeAll(alreadyChained);
            }
            // add chained ones to alreadyChained
            alreadyChained.addAll(lastChained);
        }
    }

    protected void chainTarget(ServerWorld world, LivingEntity target, Entity from)
    {
        lastChained.add(target);

        world.spawnParticles(new ZapParticleOption(from.getId(), target.getId(), power), from.getX(), from.getY(), from.getZ(), 1, 0, 0, 0, 0);

        ParticleHelper.sendBatches(target, HIT_PARTICLES);

        // TODO: play sounds

        if (getOwner() instanceof LivingEntity caster)
        {
            SpellInfo spellInfo = new SpellInfo(SpellRegistry.getSpell(AllSpells.CHAIN_LIGHTNING), AllSpells.CHAIN_LIGHTNING);
            SpellImpactEvents.POST.invoker().invoke(world, caster, List.of(target), spellInfo);

            float damage = (float) DamageUtil.calculateDamage(caster, SpellSchools.LIGHTNING, power * SpellConfig.CHAIN_LIGHTNING_CONFIG.damageFactor());
            DamageUtil.spellDamage(target, SpellSchools.LIGHTNING, caster, damage, false);
        }
    }

    protected boolean isBlocked(World level, Vec3d start, Vec3d end)
    {
        return level.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this)).getType() != HitResult.Type.MISS;
    }

    protected boolean notZappedYet(LivingEntity entity)
    {
        return !alreadyChained.contains(entity) && !lastChained.contains(entity);
    }

    @Override
    protected boolean canHit(Entity target)
    {
        LivingEntity living = ImpactUtil.castToLiving(target);
        return living != null &&
               getOwner() instanceof LivingEntity owner &&
               !ImpactUtil.isAlly(living, owner) &&
               notZappedYet(living);
    }

    @Override
    public void checkDespawn()
    {
        if (this.getWorld() instanceof ServerWorld world &&
            !world.getChunkManager().threadedAnvilChunkStorage.getTicketManager().shouldTickEntities(this.getChunkPos().toLong()))
        {
            this.discard();
        }
    }

    @Override
    protected void initDataTracker()
    {
    }

    @Override
    public boolean shouldSave()
    {
        return false;
    }

    @Override
    public boolean isOnFire()
    {
        return false;
    }
}
