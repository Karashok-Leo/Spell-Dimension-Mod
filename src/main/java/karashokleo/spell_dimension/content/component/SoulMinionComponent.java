package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulMinionComponent implements ServerTickingComponent
{
    private static final String OWNER_KEY = "Owner";

    private final MobEntity mob;
    @Nullable
    private PlayerEntity owner;
    @Nullable
    private UUID ownerUuid;
    private boolean controlling = false;

    public SoulMinionComponent(MobEntity mob)
    {
        this.mob = mob;
    }

    @Nullable
    public PlayerEntity getOwner()
    {
        if (!(mob.getWorld() instanceof ServerWorld world))
        {
            return null;
        }
        if (owner == null &&
            world.getEntity(ownerUuid) instanceof PlayerEntity player)
        {
            owner = player;
        }
        if (owner == null ||
            owner.isDead() ||
            owner.isRemoved())
        {
            return null;
        }
        return owner;
    }

    public void setOwner(PlayerEntity owner)
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
        }
    }

    public void setControlling(boolean controlling)
    {
        this.controlling = controlling;
    }

    public boolean isControlling()
    {
        return controlling;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        if (tag.get(OWNER_KEY) != null)
        {
            ownerUuid = tag.getUuid(OWNER_KEY);
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (ownerUuid != null)
        {
            tag.putUuid(OWNER_KEY, ownerUuid);
        }
    }

    @Override
    public void serverTick()
    {
        if (!(getOwner() instanceof ServerPlayerEntity player))
        {
            return;
        }
    }
}
