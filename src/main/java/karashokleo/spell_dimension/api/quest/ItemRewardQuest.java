package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public interface ItemRewardQuest extends Quest
{
    List<ItemStack> getRewards();

    @Override
    default void reward(ServerPlayerEntity player)
    {
        this.getRewards().forEach(stack -> player.getInventory().offerOrDrop(stack.copy()));
    }

    @Override
    default void appendRewardDesc(List<Text> desc)
    {
        for (ItemStack reward : this.getRewards())
            desc.add(SDTexts.TOOLTIP_QUEST_MUL.get(reward.getName(), reward.getCount()));
    }
}
