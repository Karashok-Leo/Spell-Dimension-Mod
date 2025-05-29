package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;

public class BallLightningEntity extends ProjectileEntity
{
    public int lifespan;
    private int bounces = 0;

    public BallLightningEntity(World world, Entity owner)
    {
        this(AllEntities.BALL_LIGHTNING, world);
        this.setOwner(owner);
        this.lifespan = 200;
    }

    public BallLightningEntity(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult)
    {
        super.onBlockHit(blockHitResult);

        Vec3d velocity = this.getVelocity();
        switch (blockHitResult.getSide())
        {
            case UP, DOWN -> this.setVelocity(velocity.multiply(1, -1, 1));
            case EAST, WEST -> this.setVelocity(velocity.multiply(-1, 1, 1));
            case NORTH, SOUTH -> this.setVelocity(velocity.multiply(1, 1, -1));
        }

        bounces++;
        if (bounces >= 20)
        {
            discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)
    {
        super.onEntityHit(entityHitResult);

        if (this.getWorld().isClient())
        {
            return;
        }

        if (entityHitResult.getEntity() instanceof LivingEntity target &&
            getOwner() instanceof LivingEntity owner)
        {
            float damage = (float) DamageUtil.calculateDamage(owner, SpellSchools.LIGHTNING, 0.2f);
            DamageUtil.spellDamage(target, SpellSchools.LIGHTNING, owner, damage, false);
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        setPosition(getPos().add(getVelocity()));
        ProjectileUtil.setRotationFromVelocity(this, 1);

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS)
        {
            this.onCollision(hitResult);
        }

        if (this.getWorld().isClient())
        {
            return;
        }

        // discard if lifespan is over
        if (age > lifespan)
        {
            discard();
            return;
        }
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
    protected boolean canHit(Entity target)
    {
        return target instanceof LivingEntity living &&
               getOwner() instanceof LivingEntity owner &&
               !ImpactUtil.isAlly(living, owner);
    }

    @Override
    protected void initDataTracker()
    {
    }

    @Override
    public boolean isOnFire()
    {
        return false;
    }
}
