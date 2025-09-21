package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import karashokleo.spell_dimension.content.object.SoulInput;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulControllerComponent implements Component
{
    private static final String OWNER_KEY = "Owner";
    private static final String INPUT_KEY = "Input";
    @Nullable
    private PlayerEntity owner;
    @Nullable
    private UUID ownerUuid;
    private final SoulInput input = new SoulInput();

    @Nullable
    public PlayerEntity getOwner(ServerWorld world)
    {
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
}
