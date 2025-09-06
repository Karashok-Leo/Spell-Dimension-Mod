package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class SoulControllerComponent implements AutoSyncedComponent
{
    private static final String OWNER_KEY = "Owner";
    private int owner;

    public LivingEntity getOwner(World world)
    {
        return world.getEntityById(owner) instanceof LivingEntity living ? living : null;
    }

    public void setOwner(LivingEntity owner)
    {
        this.owner = owner.getId();
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        owner = tag.getInt(OWNER_KEY);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(OWNER_KEY, owner);
    }
}
