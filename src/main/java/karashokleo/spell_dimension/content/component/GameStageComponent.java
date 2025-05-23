package karashokleo.spell_dimension.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllComponents;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GameStageComponent implements AutoSyncedComponent
{
    public static final int NORMAL = 0;
    public static final int HARDCORE = 1;
    public static final int NIGHTMARE = 2;
    public static final UUID HARDCORE_DIFFICULTY_BONUS = UuidUtil.getUUIDFromString("spell_dimension:hardcore_difficulty");
    public static final UUID NIGHTMARE_DIFFICULTY_BONUS = UuidUtil.getUUIDFromString("spell_dimension:nightmare_difficulty");

    public static int getNextDifficulty(int difficulty)
    {
        return MathHelper.clamp(difficulty + 1, NORMAL, NIGHTMARE);
    }

    public static boolean isNormalMode(PlayerEntity player)
    {
        return getDifficulty(player) == NORMAL;
    }

    public static int getDifficulty(PlayerEntity player)
    {
        return player.getComponent(AllComponents.GAME_STAGE).difficulty;
    }

    public static void addDifficulty(ServerPlayerEntity player)
    {
        GameStageComponent component = player.getComponent(AllComponents.GAME_STAGE);
        component.difficulty = getNextDifficulty(component.difficulty);
        sync(player);
        if (component.difficulty >= HARDCORE)
        {
            AttributeUtil.addModifier(player, LHMiscs.ADD_LEVEL, HARDCORE_DIFFICULTY_BONUS, "Hardcore Difficulty Bonus", 30, EntityAttributeModifier.Operation.ADDITION);
        }
    }

    public static void resetDifficulty(ServerPlayerEntity player)
    {
        GameStageComponent component = player.getComponent(AllComponents.GAME_STAGE);
        component.difficulty = NORMAL;
        sync(player);
        AttributeUtil.removeModifier(player, LHMiscs.ADD_LEVEL, HARDCORE_DIFFICULTY_BONUS);
        AttributeUtil.removeModifier(player, LHMiscs.ADD_LEVEL, NIGHTMARE_DIFFICULTY_BONUS);
        player.sendMessage(SDTexts.TEXT$PROGRESS_ROLLBACK.get(), true);
    }

    public static boolean canEnterEnd(PlayerEntity player)
    {
        return player.getComponent(AllComponents.GAME_STAGE).canEnterEnd;
    }

    public static void setCanEnterEnd(ServerPlayerEntity player, boolean canEnterEnd)
    {
        player.getComponent(AllComponents.GAME_STAGE).canEnterEnd = canEnterEnd;
        sync(player);
    }

    public static boolean keepInventory(PlayerEntity player)
    {
        return player.getComponent(AllComponents.GAME_STAGE).keepInventory;
    }

    public static void setKeepInventory(ServerPlayerEntity player, boolean keepInventory)
    {
        player.getComponent(AllComponents.GAME_STAGE).keepInventory = keepInventory;
        sync(player);
    }

    public static void sync(ServerPlayerEntity player)
    {
        AllComponents.GAME_STAGE.sync(player);
    }

    private static final String DIFFICULTY_KEY = "Difficulty";
    private static final String END_STAGE_KEY = "EndStage";
    private static final String KEEP_INVENTORY_KEY = "KeepInventory";

    private int difficulty = NORMAL;
    private boolean canEnterEnd = false;
    private boolean keepInventory = false;

    public GameStageComponent()
    {
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.difficulty = tag.getInt(DIFFICULTY_KEY);
        this.canEnterEnd = tag.getBoolean(END_STAGE_KEY);
        this.keepInventory = tag.getBoolean(KEEP_INVENTORY_KEY);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        tag.putInt(DIFFICULTY_KEY, this.difficulty);
        tag.putBoolean(END_STAGE_KEY, this.canEnterEnd);
        tag.putBoolean(KEEP_INVENTORY_KEY, this.keepInventory);
    }
}
