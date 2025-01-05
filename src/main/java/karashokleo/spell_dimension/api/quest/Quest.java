package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.config.QuestToEntryConfig;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public interface Quest
{
    boolean completeTasks(ServerPlayerEntity player);

    void reward(ServerPlayerEntity player);

    default void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(Text.empty());
    }

    default void appendRewardDesc(World world, List<Text> desc)
    {
        desc.add(Text.empty());
    }

    default List<Text> getDesc(World world)
    {
        List<Text> desc = new ArrayList<>();
        desc.add(SDTexts.TOOLTIP$QUEST$TASK.get().formatted(Formatting.BOLD));
        this.appendTaskDesc(world, desc);
        desc.add(SDTexts.TOOLTIP$QUEST$REWARD.get().formatted(Formatting.BOLD));
        this.appendRewardDesc(world, desc);
        desc.add(SDTexts.TOOLTIP$QUEST$COMPLETE.get().formatted(Formatting.GOLD));
        if (QuestToEntryConfig.hasEntry(this))
            desc.add(SDTexts.TOOLTIP$QUEST$OPEN_ENTRY.get().formatted(Formatting.DARK_AQUA));
        return desc;
    }
}
