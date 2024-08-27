package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public interface Quest
{
    boolean completeTasks(ServerPlayerEntity player);

    void reward(ServerPlayerEntity player);

    default void appendTaskDesc(List<Text> desc)
    {
        desc.add(Text.empty());
    }

    default void appendRewardDesc(List<Text> desc)
    {
        desc.add(Text.empty());
    }

    default List<Text> getDesc()
    {
        List<Text> desc = new ArrayList<>();
        desc.add(SDTexts.TOOLTIP$QUEST$TASK.get().formatted(Formatting.BOLD));
        this.appendTaskDesc(desc);
        desc.add(SDTexts.TOOLTIP$QUEST$REWARD.get().formatted(Formatting.BOLD));
        this.appendRewardDesc(desc);
        desc.add(SDTexts.TOOLTIP$QUEST$COMPLETE.get().formatted(Formatting.GOLD));
        return desc;
    }
}
