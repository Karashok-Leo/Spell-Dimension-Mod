package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import karashokleo.spell_dimension.content.object.SoulInput;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ChunkPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulMinionComponent implements ServerTickingComponent
{
    private static final String OWNER_KEY = "Owner";
//    private static final String INPUT_KEY = "Input";

    private final MobEntity mob;
    @Nullable
    private PlayerEntity owner;
    @Nullable
    private UUID ownerUuid;
    private final SoulInput input = new SoulInput();

    private static final int CHUNK_LOAD_RADIUS = 3;
    private static final int CHUNK_LOAD_TICKS = 80;
    private static final ChunkTicketType<Unit> TICKET_TYPE = ChunkTicketType.create("soul_minion", (a, b) -> 0, CHUNK_LOAD_TICKS);

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
            this.setControlling(false);
        } else
        {
            this.owner = owner;
            this.ownerUuid = owner.getUuid();
            this.setControlling(true);
        }
    }

    public SoulInput getInput()
    {
        return input;
    }

    public void setInput(SoulInput input)
    {
        this.input.copyFrom(input);
    }

    public void setControlling(boolean controlling)
    {
        this.input.controlling = controlling;
        if (!controlling)
        {
            this.input.clear();
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        if (tag.get(OWNER_KEY) != null)
        {
            ownerUuid = tag.getUuid(OWNER_KEY);
        }
//        input = SoulInput.fromNbt(tag.getCompound(INPUT_KEY));
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (ownerUuid != null)
        {
            tag.putUuid(OWNER_KEY, ownerUuid);
        }
//        tag.put(INPUT_KEY, input.toNbt());
    }

    @Override
    public void serverTick()
    {
        if (!(mob.getWorld() instanceof ServerWorld world))
        {
            return;
        }

        if (mob.age % CHUNK_LOAD_TICKS != 0)
        {
            return;
        }

        ChunkPos centerChunkPos = mob.getChunkPos();
        ServerChunkManager chunkManager = world.getChunkManager();
        for (int i = -CHUNK_LOAD_RADIUS; i <= CHUNK_LOAD_RADIUS; i++)
        {
            for (int j = -CHUNK_LOAD_RADIUS; j <= CHUNK_LOAD_RADIUS; j++)
            {
                var chunkPos = new ChunkPos(centerChunkPos.x + i, centerChunkPos.z + j);
                chunkManager.addTicket(TICKET_TYPE, chunkPos, 2, Unit.INSTANCE);
            }
        }
    }
}
