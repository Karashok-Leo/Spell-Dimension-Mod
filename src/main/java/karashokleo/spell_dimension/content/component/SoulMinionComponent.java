package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulMinionComponent implements AutoSyncedComponent
{
    private static final String OWNER_KEY = "Owner";

    private final MobEntity mob;
    @Nullable
    private PlayerEntity owner;
    @Nullable
    private UUID ownerUuid;

    public SoulMinionComponent(MobEntity mob)
    {
        this.mob = mob;
    }

    public boolean hasOwner()
    {
        return ownerUuid != null;
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
        }
        mob.setGlowing(hasOwner());
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
}
