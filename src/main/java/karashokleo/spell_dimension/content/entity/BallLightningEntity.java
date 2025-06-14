package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.particle.ParticleHelper;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

public class BallLightningEntity extends ProjectileEntity
{
    public static final ParticleBatch[] AMBIENT = {
            new ParticleBatch(
                    "spell_engine:electric_arc_a",
                    ParticleBatch.Shape.SPHERE,
                    ParticleBatch.Origin.CENTER,
                    null,
                    0,
                    0,
                    0.2f,
                    0.06F,
                    0.12F,
                    0,
                    0,
                    0,
                    false
            ),
            new ParticleBatch(
                    "spell_engine:electric_arc_b",
                    ParticleBatch.Shape.SPHERE,
                    ParticleBatch.Origin.CENTER,
                    null,
                    0,
                    0,
                    0.2f,
                    0.06F,
                    0.12F,
                    0,
                    0,
                    0,
                    false
            ),
    };

    public boolean macro = false;
    private int lifespan;

    public BallLightningEntity(World world, Entity owner)
    {
        this(AllEntities.BALL_LIGHTNING, world);
        this.setOwner(owner);
    }

    public BallLightningEntity(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        this.setNoGravity(true);
        this.lifespan = SpellConfig.BALL_LIGHTNING_CONFIG.lifespan();
    }

    private void applyPassives(List<String> spells)
    {
        if (spells.contains(AllSpells.CLOSED_LOOP.toString()))
        {
            this.lifespan += SpellConfig.BALL_LIGHTNING_CONFIG.lifespanIncrement();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult)
    {
        super.onBlockHit(blockHitResult);

        if (macro)
        {
            World world = this.getWorld();
            BlockPos blockPos = blockHitResult.getBlockPos();

            ArrayList<Runnable> possibles = new ArrayList<>();
            possibles.add(() -> world.breakBlock(blockPos, false));
            if (world.getBlockEntity(blockPos) instanceof Inventory inventory)
            {
                int size = inventory.size();
                for (int i = 0; i < size; i++)
                {
                    ItemStack stack = inventory.getStack(i);
                    if (stack.isEmpty())
                    {
                        continue;
                    }
                    int finalI = i;
                    possibles.add(() ->
                    {
                        inventory.removeStack(finalI);
                        inventory.markDirty();
                    });
                }
            }
            Runnable runnable = RandomUtil.randomFromList(this.random, possibles);
            runnable.run();
            this.discard();
            return;
        }

        Vec3d velocity = this.getVelocity();
        switch (blockHitResult.getSide())
        {
            case UP, DOWN -> this.setVelocity(velocity.multiply(1, -1, 1));
            case EAST, WEST -> this.setVelocity(velocity.multiply(-1, 1, 1));
            case NORTH, SOUTH -> this.setVelocity(velocity.multiply(1, 1, -1));
        }

        if (this.getOwner() instanceof PlayerEntity player)
        {
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(player.getMainHandStack(), player);
            if (spellContainer != null)
            {
                applyPassives(spellContainer.spell_ids);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)
    {
        super.onEntityHit(entityHitResult);

        World world = this.getWorld();
        if (world.isClient())
        {
            return;
        }

        Entity entity = entityHitResult.getEntity();

        if (macro && !(entity instanceof PlayerEntity))
        {
            ArrayList<Runnable> possibles = new ArrayList<>();
            possibles.add(entity::discard);
            for (ItemStack stack : entity.getItemsEquipped())
            {
                if (stack.isEmpty())
                {
                    continue;
                }
                possibles.add(() -> stack.setCount(0));
            }
            Runnable runnable = RandomUtil.randomFromList(this.random, possibles);
            runnable.run();
            this.discard();
            return;
        }

        if (entity instanceof LivingEntity target &&
            getOwner() instanceof LivingEntity caster)
        {
            SpellInfo spellInfo = new SpellInfo(SpellRegistry.getSpell(AllSpells.BALL_LIGHTNING), AllSpells.BALL_LIGHTNING);
            SpellImpactEvents.POST.invoker().invoke(world, caster, List.of(target), spellInfo);

            float damage = (float) DamageUtil.calculateDamage(caster, SpellSchools.LIGHTNING, SpellConfig.BALL_LIGHTNING_CONFIG.damageFactor());
            DamageUtil.spellDamage(target, SpellSchools.LIGHTNING, caster, damage, false);

            ParticleHelper.sendBatches(target, ChainLightningEntity.HIT_PARTICLES);
            // TODO: play sounds
        }
    }

    @Override
    public void tick()
    {
        super.tick();

        lifespan--;

        setPosition(getPos().add(getVelocity()));
        ProjectileUtil.setRotationFromVelocity(this, 1);

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS)
        {
            this.onCollision(hitResult);
        }

        if (this.getWorld().isClient())
        {
            ParticleHelper.play(this.getWorld(), this, AMBIENT);
            return;
        }

        // discard if lifespan is over
        if (lifespan < 0)
        {
            discard();
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

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt)
    {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Lifespan", lifespan);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {
        super.readCustomDataFromNbt(nbt);
        lifespan = nbt.getInt("Lifespan");
    }
}
