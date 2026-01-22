package karashokleo.spell_dimension.content.entity;

import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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
import net.spell_engine.utils.VectorHelper;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BallLightningEntity extends ProjectileEntity
{
    public static final float DAMAGE_FACTOR = 0.8F;
    public static final int LIFESPAN = 200;
    public static final int LIFESPAN_INCREMENT = 100;

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
    public static final float HOMING_ANGLE = 8f;

    private static final TrackedData<Boolean> MACRO = DataTracker.registerData(BallLightningEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public int power;
    private int lifespan;

    @Nullable
    private SpellInfo spellInfo;

    public BallLightningEntity(World world, Entity owner)
    {
        this(AllEntities.BALL_LIGHTNING, world);
        this.setOwner(owner);
    }

    public BallLightningEntity(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
        this.setNoGravity(true);
        this.power = 1;
        this.lifespan = LIFESPAN;
    }

    public SpellInfo getSpellInfo()
    {
        if (this.spellInfo == null)
        {
            this.spellInfo = new SpellInfo(SpellRegistry.getSpell(AllSpells.BALL_LIGHTNING), AllSpells.BALL_LIGHTNING);
        }
        return spellInfo;
    }

    private void applyPassives(List<String> spells)
    {
        if (spells.contains(AllSpells.CLOSED_LOOP.toString()))
        {
            this.lifespan += LIFESPAN_INCREMENT;
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult)
    {
        super.onBlockHit(blockHitResult);

        if (isMacro())
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

        if (isMacro() && !(entity instanceof PlayerEntity))
        {
            ArrayList<Runnable> possibles = new ArrayList<>();
            possibles.add(entity::discard);
            var living = ImpactUtil.castToLiving(entity);
            if (living != null)
            {
                for (EquipmentSlot slot : EquipmentSlot.values())
                {
                    ItemStack stack = living.getEquippedStack(slot);
                    if (stack.isEmpty())
                    {
                        continue;
                    }
                    possibles.add(() -> living.equipStack(slot, ItemStack.EMPTY));
                }
            }
            Runnable runnable = RandomUtil.randomFromList(this.random, possibles);
            runnable.run();
            this.discard();
            return;
        }

        LivingEntity target = ImpactUtil.castToLiving(entity);
        if (target != null &&
            getOwner() instanceof LivingEntity caster)
        {
            SpellImpactEvents.POST.invoker().invoke(world, caster, List.of(target), getSpellInfo());

            float damage = (float) DamageUtil.calculateDamage(caster, SpellSchools.LIGHTNING, this.power * DAMAGE_FACTOR);
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
        followTarget();
        ProjectileUtil.setRotationFromVelocity(this, 0.5f);

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

    private void followTarget()
    {
        if (!isMacro())
        {
            return;
        }
        Entity owner = getOwner();
        if (!(owner instanceof PlayerEntity player))
        {
            return;
        }
        if (this.getWorld().isClient())
        {
            RayTraceUtil.clientUpdateTarget(player, getSpellInfo().spell().range);
            return;
        }

        LivingEntity target = RayTraceUtil.serverGetTarget(player);
        if (target == null)
        {
            return;
        }

        Vec3d targetPos = target.getPos()
            .add(0, target.getHeight() / 2, 0);
        Vec3d selfPos = this.getPos().add(0, this.getHeight() / 2, 0);
        Vec3d distanceVector = targetPos.subtract(selfPos);
        Vec3d newVelocity = VectorHelper.rotateTowards(this.getVelocity(), distanceVector, HOMING_ANGLE);
        if (newVelocity.lengthSquared() > 0)
        {
            this.setVelocity(newVelocity);
            this.velocityDirty = true;
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
        LivingEntity living = ImpactUtil.castToLiving(target);
        return living != null && !RelationUtil.isAlly(this, living);
    }

    public boolean isMacro()
    {
        return this.getDataTracker().get(MACRO);
    }

    public void setMacro(boolean macro)
    {
        this.getDataTracker().set(MACRO, macro);
    }

    @Override
    protected void initDataTracker()
    {
        this.getDataTracker().startTracking(MACRO, false);
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
        nbt.putBoolean("Macro", isMacro());
        nbt.putInt("Power", power);
        nbt.putInt("Lifespan", lifespan);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt)
    {
        super.readCustomDataFromNbt(nbt);
        setMacro(nbt.getBoolean("Macro"));
        power = nbt.getInt("Power");
        lifespan = nbt.getInt("Lifespan");
    }
}
