package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

import java.util.List;

public record FirstDayQuest(List<ItemStack> rewards) implements ItemRewardQuest, AutoDescQuest
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
}
