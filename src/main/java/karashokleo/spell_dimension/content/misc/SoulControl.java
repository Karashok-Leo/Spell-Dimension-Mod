package karashokleo.spell_dimension.content.misc;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.init.AllComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import tocraft.walkers.api.PlayerShape;

import java.util.Optional;

/**
 * server-side only
 */
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

    /**
     * soul minion cannot and will not attack its owner,
     * but owner can attack its soul minion
     *
     * @return true if entity2 is a soul minion owned by entity1
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    static boolean isSoulMinion(@Nullable Entity entity1, @Nullable Entity entity2)
    {
        return entity2 instanceof MobEntity mob &&
            SoulControl.getSoulMinion(mob).getOwner() == entity1;
    }

    static void onSelfBodyDeath(ServerPlayerEntity player)
    {
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
                world.spawnEntity(loadedMinion);
            } else
            {
                // spawn fake player
                FakePlayerEntity fakePlayer = new FakePlayerEntity(player);
                copyPlayerProperties(player, fakePlayer);
                updatePosRotHealth(fakePlayer, player);
                world.spawnEntity(fakePlayer);

                controllerComponent.setFakePlayerSelf(fakePlayer);
            }

            // update player shape & position
            PlayerShape.updateShapes(player, minion);
            updatePosRotHealth(player, minion);

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
            world.spawnEntity(loadedMinion);

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

    static MobEntity loadMinionFromData(NbtCompound data, ServerWorld world)
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

    static void updatePosRotHealth(LivingEntity target, LivingEntity entity)
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
        target.setHealth(target.getMaxHealth() * proportion);
    }

    static void copyPlayerProperties(PlayerEntity player, FakePlayerEntity fakePlayer)
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
}
