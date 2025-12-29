package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.runes.api.RuneItems;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class RailgunEntity extends ProjectileEntity
{
    private static final int LENGTH = 32;
    private static final int RADIUS = 2;

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
        if (itemEntity.isOnGround())
        {
            return;
        }
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

        this.endPos = this.getPos().add(this.getVelocity().normalize().multiply(LENGTH));

        World world = getWorld();
        if (world.isClient())
        {
            return;
        }

        // play sound
        world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.PLAYERS, 2.0F, 1.0F);

        // break blocks
        // spawnExplosionParticles();
        {
            BlockPos minPos = BlockPos.ofFloored(this.getPos());
            BlockPos maxPos = BlockPos.ofFloored(this.endPos);
            BlockBox blockBox = BlockBox.create(minPos, maxPos).expand(RADIUS);
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
            List<LivingEntity> hit = ImpactUtil.getLivingsNearLineSegment(world, this.getPos(), this.endPos, RADIUS / 2F);
            for (LivingEntity target : hit)
            {
                if (RelationUtil.isAlly(caster, target))
                {
                    continue;
                }
                double power = SpellPower.getSpellPower(SpellSchools.LIGHTNING, caster).randomValue();
                DamageUtil.spellDamage(target, SpellSchools.LIGHTNING, caster, (float) (power * power), true);
            }
        }
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
