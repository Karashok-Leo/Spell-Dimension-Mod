package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import karashokleo.spell_dimension.content.misc.ThreadedAnvilChunkStorageExtension;
import karashokleo.spell_dimension.content.object.SoulInput;
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
//    private static final String INPUT_KEY = "Input";

    private final MobEntity mob;
    @Nullable
    private PlayerEntity owner;
    @Nullable
    private UUID ownerUuid;
    private final SoulInput input = new SoulInput();

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
        if (!input.controlling)
        {
            return;
        }

        if (!(getOwner() instanceof ServerPlayerEntity player))
        {
            return;
        }

        var extension = (ThreadedAnvilChunkStorageExtension) (player.getServerWorld().getChunkManager().threadedAnvilChunkStorage);
        extension.updatePositionOfControlledEntity(player, mob);
    }
}
