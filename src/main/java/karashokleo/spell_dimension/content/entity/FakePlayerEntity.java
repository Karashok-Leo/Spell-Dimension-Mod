package karashokleo.spell_dimension.content.entity;

import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class FakePlayerEntity extends LivingEntity
{
    protected static final TrackedData<Optional<UUID>> PLAYER_UUID = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    private final DefaultedList<ItemStack> heldItems;
    private final DefaultedList<ItemStack> armorItems;

    public FakePlayerEntity(EntityType<? extends LivingEntity> entityType, World world)
    {
        super(entityType, world);
        this.heldItems = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.armorItems = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    public FakePlayerEntity(World world, PlayerEntity player)
    {
        this(AllEntities.FAKE_PLAYER, world);
        setPlayerUUID(player.getUuid());
        setCustomName(player.getName());
    }

    public FakePlayerEntity(PlayerEntity player)
    {
        this(player.getWorld(), player);
    }

    @Override
    protected void initDataTracker()
    {
        super.initDataTracker();
        this.dataTracker.startTracking(PLAYER_UUID, Optional.empty());
    }

    @Nullable
    public UUID getPlayerUUID()
    {
        return this.dataTracker.get(PLAYER_UUID).orElse(null);
    }

    public void setPlayerUUID(@Nullable UUID uuid)
    {
        this.dataTracker.set(PLAYER_UUID, Optional.ofNullable(uuid));
    }

    @Nullable
    public PlayerEntity getPlayer()
    {
        UUID playerUUID = getPlayerUUID();
        return playerUUID == null ? null : getWorld().getPlayerByUuid(playerUUID);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource)
    {
        return super.isInvulnerableTo(damageSource) &&
            damageSource.getAttacker() == getPlayer();
    }

    @Override
    public void onDeath(DamageSource damageSource)
    {
        super.onDeath(damageSource);
        if (!(getPlayer() instanceof ServerPlayerEntity player))
        {
            return;
        }

        SoulControl.onSelfBodyDeath(player);
    }

    @Override
    protected void drop(DamageSource source)
    {
    }

    @Override
    public Iterable<ItemStack> getArmorItems()
    {
        return armorItems;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot)
    {
        switch (slot.getType())
        {
            case HAND ->
            {
                return this.heldItems.get(slot.getEntitySlotId());
            }
            case ARMOR ->
            {
                return this.armorItems.get(slot.getEntitySlotId());
            }
            default ->
            {
                return ItemStack.EMPTY;
            }
        }
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack)
    {
        this.processEquippedStack(stack);
        switch (slot.getType())
        {
            case HAND -> this.onEquipStack(slot, this.heldItems.set(slot.getEntitySlotId(), stack), stack);
            case ARMOR -> this.onEquipStack(slot, this.armorItems.set(slot.getEntitySlotId(), stack), stack);
        }
    }

    @Override
    public Arm getMainArm()
    {
        return Arm.RIGHT;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt)
    {
        super.readCustomDataFromNbt(nbt);
        setPlayerUUID(nbt.getUuid("PlayerUUID"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt)
    {
        super.writeCustomDataToNbt(nbt);
        nbt.putUuid("PlayerUUID", this.getPlayerUUID());
    }
}
