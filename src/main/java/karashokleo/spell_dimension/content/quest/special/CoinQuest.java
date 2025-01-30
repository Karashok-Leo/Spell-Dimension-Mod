package karashokleo.spell_dimension.content.quest.special;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

@Deprecated
public class CoinQuest implements ItemRewardQuest
{
    @Override
    public List<ItemStack> getRewards()
    {
        return List.of();
    }

    @Override
    public boolean completeTasks(ServerPlayerEntity player)
    {
        return false;
    }
}
