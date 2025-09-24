package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulControllerComponent implements AutoSyncedComponent
{
    private static final String MINION_KEY = "Minion";

    private final PlayerEntity player;
    @Nullable
    private MobEntity minion;
    @Nullable
    private UUID minionUuid;
    /**
     * used in client-side only
     */
    private boolean controlling = false;

    public SoulControllerComponent(PlayerEntity player)
    {
        this.player = player;
    }

    public boolean isControlling()
    {
        return controlling;
    }

    @Nullable
    public MobEntity getMinion()
    {
        if (!(player.getWorld() instanceof ServerWorld world))
        {
            return null;
        }
        if (minion == null &&
            world.getEntity(minionUuid) instanceof MobEntity mob)
        {
            minion = mob;
        }
        if (minion == null ||
            minion.isDead() ||
            minion.isRemoved())
        {
            return null;
        }
        return minion;
    }

    public void setMinion(@Nullable MobEntity minion)
    {
        if (minion == null ||
            minion.isDead() ||
            minion.isRemoved())
        {
            this.minion = null;
            this.minionUuid = null;
            this.controlling = false;
        } else
        {
            this.minion = minion;
            this.minionUuid = minion.getUuid();
            this.controlling = true;
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        if (tag.get(MINION_KEY) != null)
        {
            minionUuid = tag.getUuid(MINION_KEY);
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (minionUuid != null)
        {
            tag.putUuid(MINION_KEY, minionUuid);
        }
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf)
    {
        controlling = buf.readBoolean();
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient)
    {
        buf.writeBoolean(controlling);
    }
}
