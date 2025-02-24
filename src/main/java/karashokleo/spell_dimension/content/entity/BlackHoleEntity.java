package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BlackHoleEntity extends Entity implements Ownable
{
    public static final float MAX_RADIUS = 32f;
    public static final float MIN_RADIUS = 2f;
    public static final int LIFESPAN = 20 * 16;
    public static final int IDLE_SOUND_INTERVAL = 60;

    private static final TrackedData<Float> RADIUS = DataTracker.registerData(BlackHoleEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Nullable
    private SpellInfo spellInfo;
    @Nullable
    private UUID ownerUuid;
    @Nullable
    private Entity owner;

    public BlackHoleEntity(EntityType<?> type, World world)
    {
        super(type, world);
        this.setOwner(null);
    }

    public BlackHoleEntity(World world, Entity owner, float radius)
    {
        super(AllEntities.BLACK_HOLE, world);
        this.setOwner(owner);
        this.setRadius(radius);
    }

    public SpellInfo getSpellInfo()
    {
        if (this.spellInfo == null)
        {
            this.spellInfo = new SpellInfo(SpellRegistry.getSpell(AllSpells.BLACK_HOLE), AllSpells.BLACK_HOLE);
        }
        return spellInfo;
    }

    public void setOwner(@Nullable Entity owner)
    {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUuid();
    }

    @Override
    @Nullable
    public Entity getOwner()
    {
        if (this.owner != null && !this.owner.isRemoved())
        {
            return this.owner;
        } else if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld serverWorld)
        {
            this.owner = serverWorld.getEntity(this.ownerUuid);
            return this.owner;
        } else
        {
            return null;
        }
    }

    public float getRadius()
    {
        return this.getDataTracker().get(RADIUS);
    }

    public void setRadius(float radius)
    {
        if (this.getWorld().isClient()) return;
        this.getDataTracker().set(RADIUS, MathHelper.clamp(radius, MIN_RADIUS, MAX_RADIUS));
    }

    @Override
    protected void initDataTracker()
    {
        this.getDataTracker().startTracking(RADIUS, 3.0f);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data)
    {
        if (RADIUS.equals(data))
        {
            this.calculateDimensions();
            if (this.getRadius() < MIN_RADIUS ||
                this.getRadius() > MAX_RADIUS)
                this.discard();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {
        this.age = nbt.getInt("Age");
        this.setRadius(nbt.getFloat("Radius"));
        if (nbt.containsUuid("Owner"))
        {
            this.ownerUuid = nbt.getUuid("Owner");
            this.owner = null;
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {
        nbt.putInt("Age", this.age);
        nbt.putFloat("Radius", this.getRadius());
        if (this.ownerUuid != null)
        {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    public static Box getAffectedBox(Vec3d center, float radius)
    {
        return new Box(
                center.x - radius,
                center.y - radius,
                center.z - radius,
                center.x + radius,
                center.y + radius,
                center.z + radius
        );
    }

    private Map<Entity, Float> getEntityDistanceMap(@Nullable LivingEntity caster)
    {
        Vec3d center = this.getPos();
        float radius = this.getRadius();
        Box boundingBox = getAffectedBox(center, radius);
        var trackingEntities = this.getWorld().getOtherEntities(this, boundingBox.expand(1.0));
        trackingEntities.removeIf(
                entity -> entity == caster ||
                          (entity instanceof LivingEntity living &&
                           caster != null &&
                           ImpactUtil.isAlly(caster, living))
        );
        Map<Entity, Float> entityDistanceMap = new HashMap<>();
        for (Entity entity : trackingEntities)
        {
            Vec3d pos = entity.getPos();
            float distance = (float) center.distanceTo(pos);
            if (distance > radius) continue;
            entityDistanceMap.put(entity, distance);
        }
        return entityDistanceMap;
    }

    @Override
    public void tick()
    {
        super.tick();

        if (this.getWorld().isClient()) return;

        float radius = this.getRadius();
        int effectInterval = Math.max(2, (int) this.getRadius() / 2);
        if (this.age % effectInterval != 0) return;

        if (radius < MIN_RADIUS ||
            radius > MAX_RADIUS)
        {
            this.discard();
            return;
        }

        Vec3d center = this.getPos();
        LivingEntity caster = this.getOwner() instanceof LivingEntity living ? living : null;

        float damage = caster == null ? 0 : (float) DamageUtil.calculateDamage(caster, SpellSchools.ARCANE, SpellConfig.BLACK_HOLE_FACTOR, radius);

        var entityDistanceMap = this.getEntityDistanceMap(caster);
        List<Entity> targets = entityDistanceMap.keySet().stream().toList();
        if (caster != null)
        {
            SpellImpactEvents.BEFORE.invoker().beforeImpact(this.getWorld(), caster, targets, this.getSpellInfo());
        }
        for (var entry : entityDistanceMap.entrySet())
        {
            Entity entity = entry.getKey();
            float f = 1.0f - entry.getValue() / radius / 2;
            float scale = f * f * f * f * 0.25f;

            Vec3d d = center.subtract(entity.getPos()).multiply(scale);
            entity.addVelocity(d.x, d.y, d.z);
            entity.fallDistance = 0.0f;

            if (entity instanceof LivingEntity target)
                DamageUtil.spellDamage(target, SpellSchools.ARCANE, caster, damage, false);
        }

        if (this.age > LIFESPAN)
        {
            this.discard();
            // remove sound
        } else if (this.age % IDLE_SOUND_INTERVAL == 0)
        {
            // idle sound
        }
    }
}
