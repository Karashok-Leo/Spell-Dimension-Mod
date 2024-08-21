package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;

import java.util.List;

public record FirstDayQuest(List<ItemStack> rewards) implements ItemRewardQuest
{
    private static final int ONE_DAY = 24000;

    @Override
    public List<ItemStack> getRewards()
    {
        return this.rewards;
    }

    @Override
    public boolean completeTasks(ServerPlayerEntity player)
    {
        int sinceDeath = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
        return sinceDeath >= ONE_DAY;
    }

    @Override
    public void appendTaskDesc(List<Text> desc)
    {
        desc.add(SDTexts.TOOLTIP_QUEST_FIRSTDAY.get());
    }
}
