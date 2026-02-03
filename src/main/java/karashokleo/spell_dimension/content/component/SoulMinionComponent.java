package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.content.object.SoulMinionMode;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulMinionComponent implements AutoSyncedComponent, CopyableComponent<SoulMinionComponent>
{
    private static final String OWNER_KEY = "Owner";
    private static final String MOVE_MODE_KEY = "MoveMode";
    private static final String ATTACK_MODE_KEY = "AttackMode";

    private final MobEntity mob;
    @Nullable
    private PlayerEntity owner;
    @Nullable
    private UUID ownerUuid;

    public SoulMinionMode.Move moveMode;
    public SoulMinionMode.Attack attackMode;
    public byte soulNetFlag;

    public SoulMinionComponent(MobEntity mob)
    {
        this.mob = mob;
        this.moveMode = SoulMinionMode.Move.FOLLOW;
        this.attackMode = SoulMinionMode.Attack.DEFAULT;
        this.soulNetFlag = 0;
    }

    @Nullable
    public UUID getOwnerUuid()
    {
        return ownerUuid;
    }

    public boolean hasOwner()
    {
        return ownerUuid != null;
    }

    public boolean isOwner(@Nullable PlayerEntity player)
    {
        return player != null &&
            ownerUuid != null &&
            ownerUuid.equals(player.getUuid());
    }

    @Nullable
    public PlayerEntity getOwner()
    {
        if (ownerUuid == null)
        {
            return null;
        }
        if (owner != null)
        {
            if (owner.isDead() ||
                owner.isRemoved())
            {
                owner = null;
            }
        }
        if (owner == null)
        {
            owner = mob.getWorld().getPlayerByUuid(ownerUuid);
        }
        return owner;
    }

    /**
     * After the change in ownership, SoulControllerComponent#onMinionAdded must be called on the new owner's SoulControllerComponent. If mob is already spawned, call SoulControl#changeSoulOwner instead.
     *
     * @param owner owner player entity, or null to clear owner
     * @see SoulControllerComponent#onMinionAdded(MobEntity)
     * @see SoulControllerComponent#onMinionRemoved(MobEntity)
     * @see SoulControllerComponent#getActiveMinions()
     * @see karashokleo.spell_dimension.content.misc.SoulControl#changeSoulOwner(MobEntity, PlayerEntity)
     */
    public void setOwner(@Nullable PlayerEntity owner)
    {
        if (owner == null ||
            owner.isDead() ||
            owner.isRemoved())
        {
            this.owner = null;
            this.ownerUuid = null;
        } else
        {
            this.owner = owner;
            this.ownerUuid = owner.getUuid();
            if (this.owner instanceof ServerPlayerEntity player)
            {
                SpiritTomeComponent.onSpiritTomeRule(player, mob, true);
            }
        }
        mob.setGlowing(hasOwner());
        if (mob.getWorld().isClient())
        {
            return;
        }
        AllComponents.SOUL_MINION.sync(mob);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        if (tag.get(OWNER_KEY) != null)
        {
            ownerUuid = tag.getUuid(OWNER_KEY);
        }
        this.moveMode = SoulMinionMode.Move.values()[tag.getInt(MOVE_MODE_KEY)];
        this.attackMode = SoulMinionMode.Attack.values()[tag.getInt(ATTACK_MODE_KEY)];
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (ownerUuid != null)
        {
            tag.putUuid(OWNER_KEY, ownerUuid);
        }
        tag.putInt(MOVE_MODE_KEY, moveMode.ordinal());
        tag.putInt(ATTACK_MODE_KEY, attackMode.ordinal());
    }

    @Override
    public void copyFrom(@NotNull SoulMinionComponent other)
    {
        this.ownerUuid = other.ownerUuid;
        this.moveMode = other.moveMode;
        this.attackMode = other.attackMode;
        mob.setGlowing(hasOwner());
    }
}
