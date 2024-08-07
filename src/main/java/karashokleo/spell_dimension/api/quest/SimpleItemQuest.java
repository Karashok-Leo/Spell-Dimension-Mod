package karashokleo.spell_dimension.api.quest;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.List;

public record SimpleItemQuest(
        List<ItemConvertible> tasks,
        List<ItemStack> rewards
) implements ItemTaskQuest, ItemRewardQuest
{
    public SimpleItemQuest(ItemConvertible task, ItemStack reward)
    {
        this(List.of(task), List.of(reward));
    }

    public SimpleItemQuest(ItemConvertible task, List<ItemStack> rewards)
    {
        this(List.of(task), rewards);
    }

    public SimpleItemQuest(List<ItemConvertible> tasks, ItemStack reward)
    {
        this(tasks, List.of(reward));
    }

    @Override
    public List<ItemConvertible> getTaskItems()
    {
        return this.tasks;
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return this.rewards;
    }
}
