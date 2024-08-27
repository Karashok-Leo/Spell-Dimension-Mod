package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class EndStageComponent implements Component
{
    public static boolean canEnterEnd(PlayerEntity player)
    {
        return player.getComponent(AllComponents.ENTER_END).canEnterEnd;
    }

    public static void setCanEnterEnd(PlayerEntity player, boolean canEnterEnd)
    {
        player.getComponent(AllComponents.ENTER_END).canEnterEnd = canEnterEnd;
    }

    private static final String KEY = "EndStage";

    private boolean canEnterEnd = false;

    public EndStageComponent()
    {
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.canEnterEnd = tag.getBoolean(KEY);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putBoolean(KEY, this.canEnterEnd);
    }
}
