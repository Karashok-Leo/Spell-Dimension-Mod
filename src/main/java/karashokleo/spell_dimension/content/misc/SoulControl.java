package karashokleo.spell_dimension.content.misc;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.object.SoulMinionMode;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllComponents;
import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.util.ImpactUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import tocraft.walkers.api.PlayerShape;

import java.util.Optional;

public interface SoulControl
{
    static SoulMinionComponent getSoulMinion(MobEntity entity)
    {
        return AllComponents.SOUL_MINION.get(entity);
    }

    static SoulControllerComponent getSoulController(PlayerEntity player)
    {
        return AllComponents.SOUL_CONTROLLER.get(player);
    }

    static void changeSoulOwner(MobEntity mob, @Nullable PlayerEntity newOwner)
    {
        SoulMinionComponent minionComponent = getSoulMinion(mob);
        if (minionComponent.isOwner(newOwner))
        {
            return;
        }
        PlayerEntity oldOwner = minionComponent.getOwner();
        if (oldOwner != null)
        {
            SoulControllerComponent controllerComponent = getSoulController(oldOwner);
            controllerComponent.onMinionRemoved(mob);
        }
        minionComponent.setOwner(newOwner);
        // must check hasOwner here, because setOwner rejects dead owner
        if (newOwner != null && minionComponent.hasOwner())
        {
            SoulControllerComponent controllerComponent = getSoulController(newOwner);
            controllerComponent.onMinionAdded(mob);
        }
    }

    /**
     * @return true if entity2 is a soul minion owned by entity1
     */
    static boolean isSoulMinion(@Nullable Entity entity1, @Nullable Entity entity2)
    {
        if (!(entity1 instanceof PlayerEntity player))
        {
            return false;
        }
        if (!(ImpactUtil.castToLiving(entity2) instanceof MobEntity mob))
        {
            return false;
        }
        return SoulControl.getSoulMinion(mob).isOwner(player);
    }

    /**
     * soul minion cannot and will not attack its owner,
     * but owner can attack its soul minion
     * <p>in default mode, soul minion will not attack allies (other minions) of its owner;
     * <p>in aggressive mode, soul minion can attack anyone except its owner;
     *
     * @return true if the mob is a soul minion and cannot attack the target, otherwise false
     */
    static boolean mobCannotAttack(MobEntity mob, @Nullable Entity target)
    {
        if (target == null)
        {
            return false;
        }
        SoulMinionComponent minionComponent = getSoulMinion(mob);
        if (!minionComponent.hasOwner())
        {
            return false;
        }
        PlayerEntity owner = minionComponent.getOwner();
        // has owner but owner offline, cannot attack anyone
        if (owner == null)
        {
            return true;
        }
        if (owner == target)
        {
            return true;
        }
        if (RelationUtil.isAlly(mob, target))
        {
            return minionComponent.attackMode == SoulMinionMode.Attack.DEFAULT;
        }
        return false;
    }

    static void onSelfBodyDeath(ServerPlayerEntity player)
    {
        player.sendMessage(SDTexts.TEXT$SOUL_CONTROL$BODY_DIED.get().formatted(Formatting.RED), false);
        SoulControl.setControllingMinion(player, null);
        // keep max(2, 1% max_health) health
        player.setHealth(Math.max(2.0f, player.getMaxHealth() * 0.01f));
    }

    static void setControllingMinion(ServerPlayerEntity player, @Nullable MobEntity minion)
    {
        SoulControllerComponent controllerComponent = getSoulController(player);
        ServerWorld world = player.getServerWorld();

        if (minion != null)
        {
            // check ownership
            if (!isSoulMinion(player, minion))
            {
                return;
            }

            if (controllerComponent.isControlling())
            {
                // respawn minion
                NbtCompound minionData = controllerComponent.getControllingMinionData();
                MobEntity loadedMinion = loadMinionFromData(minionData, world);
                updatePosRotHealth(loadedMinion, player);
                transferMinionData(player, loadedMinion);
                world.spawnEntity(loadedMinion);
            } else
            {
                // spawn fake player
                FakePlayerEntity fakePlayer = new FakePlayerEntity(player);
                transferPlayerData(player, fakePlayer);
                updatePosRotHealth(fakePlayer, player);
                world.spawnEntity(fakePlayer);

                controllerComponent.setFakePlayerSelf(fakePlayer);
            }

            // update player shape & position
            PlayerShape.updateShapes(player, minion);
            updatePosRotHealth(player, minion);
            transferMinionData(minion, player);

            // save minion nbt data
            NbtCompound savedMinionData = saveMinionData(minion);
            minion.discard();

            // update controller component data
            controllerComponent.setControllingMinionData(savedMinionData);
            AllComponents.SOUL_CONTROLLER.sync(player);

        } else if (controllerComponent.isControlling())
        {
            // respawn minion
            NbtCompound minionData = controllerComponent.getControllingMinionData();
            MobEntity loadedMinion = loadMinionFromData(minionData, world);

            updatePosRotHealth(loadedMinion, player);
            transferMinionData(player, loadedMinion);
            world.spawnEntity(loadedMinion);

            // handle death if needed
            DamageSource source = loadedMinion.getDamageSources().generic();
            if (loadedMinion.isDead() &&
                ServerLivingEntityEvents.ALLOW_DEATH.invoker().allowDeath(loadedMinion, source, Float.MAX_VALUE))
            {
                loadedMinion.onDeath(source);
            }

            FakePlayerEntity self = controllerComponent.getFakePlayerSelf();
            assert self != null;
            // update player shape & position
            PlayerShape.updateShapes(player, null);
            updatePosRotHealth(player, self);

            // discard fake player
            self.discard();

            // update controller component data
            controllerComponent.setControllingMinionData(null);
            controllerComponent.setFakePlayerSelf(null);
            AllComponents.SOUL_CONTROLLER.sync(player);
        }
    }

    static NbtCompound saveMinionData(MobEntity minion)
    {
        String typeId = minion.getSavedEntityId();
        if (typeId == null)
        {
            throw new IllegalArgumentException("Cannot save minion data: unknown entity type ID");
        }

        // do something before saving
        minion.setPersistent();
        minion.setVelocity(Vec3d.ZERO);
        // do saving
        var entityNbt = new NbtCompound();
        entityNbt.putString("id", typeId);
        minion.writeNbt(entityNbt);
        return entityNbt;
    }

    static MobEntity loadMinionFromData(NbtCompound data, World world)
    {
        Optional<EntityType<?>> entityType = EntityType.fromNbt(data);
        if (entityType.isEmpty())
        {
            throw new IllegalArgumentException("Cannot spawn minion: unknown entity type");
        }
        Entity spawned = entityType.get().create(world);
        if (!(spawned instanceof MobEntity mob))
        {
            throw new IllegalArgumentException("Cannot spawn minion: entity is not a MobEntity");
        }
        mob.readNbt(data);
        return mob;
    }

    private static void updatePosRotHealth(LivingEntity target, LivingEntity entity)
    {
        if (entity.getWorld().isClient())
        {
            return;
        }

        // TODO: head yaw?
        target.teleport(
            ((ServerWorld) entity.getWorld()),
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            PositionFlag.VALUES,
            entity.getYaw(),
            entity.getPitch()
        );

        // update health proportionally
        float proportion = entity.getHealth() / entity.getMaxHealth();
        float health = target.getMaxHealth() * proportion;
        target.setHealth(health);
    }

    private static void transferMinionData(LivingEntity source, LivingEntity target)
    {
        StatusEffectInstance effect = source.getStatusEffect(AllStatusEffects.REBIRTH);
        if (effect == null)
        {
            return;
        }
        target.addStatusEffect(effect);
        source.removeStatusEffect(AllStatusEffects.REBIRTH);
    }

    private static void transferPlayerData(PlayerEntity player, FakePlayerEntity fakePlayer)
    {
        var attributes = new EntityAttribute[]{
            EntityAttributes.GENERIC_MAX_HEALTH,
            EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
            EntityAttributes.GENERIC_MOVEMENT_SPEED,
            EntityAttributes.GENERIC_ARMOR,
            EntityAttributes.GENERIC_ARMOR_TOUGHNESS
        };

        for (EntityAttribute attribute : attributes)
        {
            EntityAttributeInstance origin = player.getAttributeInstance(attribute);
            EntityAttributeInstance fake = fakePlayer.getAttributeInstance(attribute);
            assert origin != null;
            assert fake != null;
            fake.setFrom(origin);
        }

//        AttributeContainer attributes = player.getAttributes();
//        fakePlayer.getAttributes().readNbt(attributes.toNbt());

//        for (EquipmentSlot slot : EquipmentSlot.values())
//        {
//            fakePlayer.equipStack(slot, player.getEquippedStack(slot).copy());
//        }
    }

    @Environment(EnvType.CLIENT)
    static int getOutlineColor(SoulMinionComponent minionComponent)
    {
        // SpellSchools.SOUL.color
        return minionComponent.getOwner() == MinecraftClient.getInstance().player ?
            0x2dd4da :
            0x078b8f;
    }

    static void teleportNearSomeone(LivingEntity source, Entity destination, boolean forced)
    {
        if (!(destination.getWorld() instanceof ServerWorld world))
        {
            return;
        }

        BlockPos destPos = destination.getBlockPos();
        Vec3d targetPos = null;
        Random random = source.getRandom();
        for (int i = 0; i < 10; i++)
        {
            // HORIZONTAL_VARIATION = 3
            // VERTICAL_VARIATION = 1
            int j = random.nextInt(7) - 3;
            int k = random.nextInt(3) - 1;
            int l = random.nextInt(7) - 3;

            BlockPos pos = destPos.add(j, k, l);
            PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(world, pos.mutableCopy());
            if (pathNodeType != PathNodeType.WALKABLE)
            {
                continue;
            }

            BlockPos vec = pos.subtract(source.getBlockPos());
            if (!world.isSpaceEmpty(source, source.getBoundingBox().offset(vec)))
            {
                continue;
            }

            targetPos = new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            break;
        }

        if (targetPos == null)
        {
            if (!forced)
            {
                return;
            }

            targetPos = destination.getPos()
                .add(
                    destination.getRotationVector()
                        .multiply(1, 0, 1)
                        .normalize().multiply(2)
                );
        }

        source.teleport(
            world,
            targetPos.getX(),
            targetPos.getY(),
            targetPos.getZ(),
            PositionFlag.VALUES,
            source.getYaw(),
            source.getPitch()
        );
    }

    static void setAttackTarget(MobEntity mob, LivingEntity target)
    {
        mob.setTarget(target);
        if (mob instanceof WardenEntity warden)
        {
            warden.increaseAngerAt(target, Angriness.ANGRY.getThreshold() + 20, false);
            warden.updateAttackTarget(target);
            return;
        }

        Brain<?> brain = mob.getBrain();
        if (mob instanceof PiglinBruteEntity || mob instanceof PiglinEntity)
        {
            brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            brain.remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);
            brain.remember(MemoryModuleType.ATTACK_TARGET, target);
            return;
        }

        if (mob instanceof HoglinEntity)
        {
            brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            brain.forget(MemoryModuleType.BREED_TARGET);
            brain.remember(MemoryModuleType.ATTACK_TARGET, target, 200L);
            return;
        }

        brain.remember(MemoryModuleType.ATTACK_TARGET, target);
    }
}
