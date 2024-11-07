package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ConsciousModeComponent implements Component
{
    public static ConsciousModeComponent get(PlayerEntity player)
    {
        return player.getComponent(AllComponents.CONSCIOUS_MODE);
    }

    public static final String KEY = "ConsciousMode";

    public boolean consciousMode = false;
    public boolean cost = true;

    public ConsciousModeComponent()
    {
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
