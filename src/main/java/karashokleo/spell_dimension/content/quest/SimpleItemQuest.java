package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.api.quest.ItemTaskQuest;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public record SimpleItemQuest(
        List<Supplier<ItemConvertible>> tasks,
        Supplier<ItemStack> reward
) implements ItemTaskQuest, ItemRewardQuest
{
    public SimpleItemQuest(Supplier<ItemConvertible> task, Supplier<ItemStack> reward)
    {
        this(List.of(task), reward);
    }

    @Override
    public List<ItemConvertible> getTaskItems()
    {
        return this.tasks.stream().map(Supplier::get).toList();
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(this.reward.get());
    }
}
