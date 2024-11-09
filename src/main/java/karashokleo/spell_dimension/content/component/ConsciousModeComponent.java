package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ConsciousModeComponent implements AutoSyncedComponent
{
    public static ConsciousModeComponent get(PlayerEntity player)
    {
        return player.getComponent(AllComponents.CONSCIOUS_MODE);
    }

    public static final String KEY = "ConsciousMode";

    private boolean consciousMode = false;
    private boolean cost = true;

    public ConsciousModeComponent()
    {
    }

    public boolean isConsciousMode()
    {
        return consciousMode;
    }

    public void setConsciousMode(boolean consciousMode)
    {
        this.consciousMode = consciousMode;
        AllComponents.CONSCIOUS_MODE.sync(this);
    }

    public boolean isCost()
    {
        return cost;
    }

    public void setCost(boolean cost)
    {
        this.cost = cost;
        AllComponents.CONSCIOUS_MODE.sync(this);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.consciousMode = tag.getBoolean(KEY);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putBoolean(KEY, this.consciousMode);
    }
}