package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class GameStageComponent implements Component
{
    public static final int NORMAL = 0;
    public static final int HARDCORE = 1;
    public static final int NIGHTMARE = 2;

    public static int getDifficulty(PlayerEntity player)
    {
        return player.getComponent(AllComponents.GAME_STAGE).difficulty;
    }

    public static void setDifficulty(PlayerEntity player, int difficulty)
    {
        player.getComponent(AllComponents.GAME_STAGE).difficulty = difficulty;
    }

    public static boolean canEnterEnd(PlayerEntity player)
    {
        return player.getComponent(AllComponents.GAME_STAGE).canEnterEnd;
    }

    public static void setCanEnterEnd(PlayerEntity player, boolean canEnterEnd)
    {
        player.getComponent(AllComponents.GAME_STAGE).canEnterEnd = canEnterEnd;
    }

    private static final String DIFFICULTY_KEY = "Difficulty";
    private static final String END_STAGE_KEY = "EndStage";

    private int difficulty = NORMAL;
    private boolean canEnterEnd = false;

    public GameStageComponent()
    {
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.difficulty = tag.getInt(DIFFICULTY_KEY);
        this.canEnterEnd = tag.getBoolean(END_STAGE_KEY);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(DIFFICULTY_KEY, this.difficulty);
        tag.putBoolean(END_STAGE_KEY, this.canEnterEnd);
    }
}
