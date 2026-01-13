package karashokleo.spell_dimension.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class OptionalEntityRef<T extends Entity>
{
    private static final ChunkTicketType<Unit> TICKET_TYPE = ChunkTicketType.create("entity_ref", (a, b) -> 0, 40);
    private static final String WORLD_KEY = "World";
    private static final String POS_KEY = "Pos";
    private static final String UUID_KEY = "Uuid";

    private final Class<T> entityClass;
    private final Predicate<T> validator;
    @Nullable
    private RegistryKey<World> worldKey;
    @Nullable
    private ChunkPos chunkPos;
    @Nullable
    private UUID uuid;

    public OptionalEntityRef(Class<T> entityClass, Predicate<T> validator)
    {
        this.entityClass = entityClass;
        this.validator = validator;
    }

    @Nullable
    private T validate(Entity entity)
    {
        if (entity == null)
        {
            return null;
        }
        if (entityClass.isInstance(entity))
        {
            T casted = entityClass.cast(entity);
            if (validator.test(casted))
            {
                return casted;
            }
        }
        return null;
    }

    public void keepLoaded(MinecraftServer server)
    {
        if (worldKey == null || chunkPos == null || uuid == null)
        {
            set(null);
            return;
        }

        ServerWorld world = server.getWorld(worldKey);
        if (world == null)
        {
            return;
        }
        world.getChunkManager().addTicket(
            TICKET_TYPE,
            chunkPos,
            2,
            Unit.INSTANCE
        );
    }

    public T get(MinecraftServer server)
    {
        if (worldKey == null || chunkPos == null || uuid == null)
        {
            set(null);
            return null;
        }

        T result = null;

        ServerWorld world = server.getWorld(worldKey);
        // first, search in the stored world and chunk
        if (world != null)
        {
            world.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL, true);
            result = validate(world.getEntity(uuid));
        }

        // not found, search in all worlds
        if (result == null)
        {
            for (ServerWorld serverWorld : server.getWorlds())
            {
                result = validate(serverWorld.getEntity(uuid));
                if (result != null)
                {
                    break;
                }
            }
        }

        if (result != null)
        {
            set(result);
        }
        return result;
    }

    public void set(@Nullable T entity)
    {
        T validate = validate(entity);
        if (validate == null)
        {
            this.worldKey = null;
            this.chunkPos = null;
            this.uuid = null;
        } else
        {
            this.worldKey = validate.getWorld().getRegistryKey();
            this.chunkPos = validate.getChunkPos();
            this.uuid = validate.getUuid();
        }
    }

    public boolean isPresent()
    {
        return worldKey != null &&
            chunkPos != null &&
            uuid != null;
    }

    public void readFromNbt(NbtCompound tag)
    {
        if (tag.contains(WORLD_KEY, NbtCompound.STRING_TYPE))
        {
            worldKey = RegistryKey.of(
                RegistryKeys.WORLD,
                new Identifier(tag.getString(WORLD_KEY))
            );
        }
        if (tag.contains(POS_KEY, NbtCompound.LONG_TYPE))
        {
            chunkPos = new ChunkPos(tag.getLong(POS_KEY));
        }
        if (tag.containsUuid(UUID_KEY))
        {
            uuid = tag.getUuid(UUID_KEY);
        }
        if (worldKey == null || chunkPos == null || uuid == null)
        {
            set(null);
        }
    }

    public void writeToNbt(NbtCompound tag)
    {
        if (worldKey == null || chunkPos == null || uuid == null)
        {
            set(null);
            return;
        }
        tag.putString(WORLD_KEY, worldKey.getValue().toString());
        tag.putUuid(UUID_KEY, uuid);
        tag.putLong(POS_KEY, chunkPos.toLong());
    }
}
