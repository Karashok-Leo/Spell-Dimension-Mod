package karashokleo.spell_dimension.content.quest.special;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.content.quest.base.AutoDescQuest;
import karashokleo.spell_dimension.content.quest.base.AutoTitleQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

import java.util.List;
import java.util.function.Supplier;

public record FirstDayQuest(Supplier<ItemStack> reward) implements ItemRewardQuest, AutoTitleQuest, AutoDescQuest
{
    // 20 minutes
    private static final int ONE_DAY = 20 * 60 * 20;

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(reward.get());
    }

    @Override
    public boolean completeTasks(ServerPlayerEntity player)
    {
        if (player.getAbilities().creativeMode) return true;
        int sinceDeath = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
        return sinceDeath >= ONE_DAY;
    }

    @Override
    public boolean isBeginning()
    {
        return true;
    }
}
