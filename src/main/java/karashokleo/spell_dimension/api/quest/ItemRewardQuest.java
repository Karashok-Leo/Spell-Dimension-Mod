package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

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
    default void appendRewardDesc(World world, List<Text> desc)
    {
        for (ItemStack reward : this.getRewards())
            desc.add(
                    SDTexts.TOOLTIP$QUEST$MUL.get(
                            Text.empty()
                                    .append(reward.getName())
                                    .formatted(reward.getRarity().formatting),
                            reward.getCount()
                    )
            );
    }
}
