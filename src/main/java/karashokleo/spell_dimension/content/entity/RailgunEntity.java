package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.util.DamageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.runes.api.RuneItems;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

public class RailgunEntity extends ProjectileEntity
{
    public boolean fired = false;
    public int firedAge = 0;
    public Vec3d endPos;

    public RailgunEntity(World world, Entity owner)
    {
        this(AllEntities.RAILGUN, world);
        this.setOwner(owner);
    }

    public RailgunEntity(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        ignoreCameraFrustum = true;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult)
    {
        super.onBlockHit(blockHitResult);
        discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)
    {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!(entity instanceof ItemEntity itemEntity))
        {
            return;
        }
//        if (itemEntity.isOnGround())
//        {
//            return;
//        }
        ItemStack itemStack = itemEntity.getStack();
        if (itemStack.isEmpty())
        {
            return;
        }
        if (!itemStack.isOf(RuneItems.get(RuneItems.RuneType.LIGHTNING)))
        {
            return;
        }

        itemStack.decrement(1);
        itemEntity.setStack(itemStack);

        onFire();
    }

    // server-side
    protected void onFire()
    {
        this.fired = true;
        this.firedAge = this.age;

        this.endPos = this.getPos().add(this.getVelocity().normalize().multiply(32));

        World world = getWorld();
        if (world.isClient())
        {
            return;
        }

        // break blocks
        // spawnExplosionParticles();
        {
            BlockPos minPos = BlockPos.ofFloored(this.getPos());
            BlockPos maxPos = BlockPos.ofFloored(this.endPos);
            BlockBox blockBox = BlockBox.create(minPos, maxPos).expand(2);
            BlockPos.stream(blockBox).forEach(pos ->
            {
                Vec3d line = this.endPos.subtract(this.getPos());
                Vec3d point = pos.toCenterPos();
                Vec3d eline = point.subtract(this.getPos());
                Vec3d crossProduct = eline.crossProduct(line);
                double distance = crossProduct.length() / line.length();
                if (distance > 2)
                {
                    return;
                }
                world.breakBlock(pos, false);
            });
        }

        // apply damage
        if (this.getOwner() instanceof LivingEntity caster)
        {
            List<LivingEntity> hit = getHitEntities(world, this.getPos(), this.endPos);
            for (LivingEntity target : hit)
            {
                double damage = DamageUtil.calculateDamage(caster, SpellSchools.LIGHTNING, 10);
                DamageUtil.spellDamage(target, SpellSchools.LIGHTNING, caster, (float) damage, true);
            }
        }
    }

    public List<LivingEntity> getHitEntities(World world, Vec3d from, Vec3d to)
    {
        List<LivingEntity> hitEntities = new ArrayList<>();
        List<LivingEntity> entities = world.getNonSpectatingEntities(
                LivingEntity.class,
                new Box(from, to).expand(1, 1, 1)
        );
        for (LivingEntity entity : entities)
        {
            if (entity == this.getOwner())
            {
                continue;
            }
            float pad = entity.getTargetingMargin() + 0.5F;
            Box aabb = entity.getBoundingBox().expand(pad, pad, pad);
            if (aabb.contains(from) ||
                aabb.raycast(from, to).isPresent())
            {
                hitEntities.add(entity);
            }
        }
        return hitEntities;
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!fired)
        {
            // update position
            setPosition(getPos().add(getVelocity()));

            // handle collision
            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            if (hitResult.getType() != HitResult.Type.MISS)
            {
                this.onCollision(hitResult);
            }

            // discard if lifespan is over
            if (age > 100)
            {
                discard();
            }
        } else if (age - firedAge > 20)
        {
            discard();
        }
    }

    @Override
    protected boolean canHit(Entity entity)
    {
        return entity instanceof ItemEntity;
    }

    @Override
    public boolean isOnFire()
    {
        return false;
    }

    @Override
    protected void initDataTracker()
    {
    }
}
