package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.api.quest.ItemTaskQuest;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class SimpleItemQuest implements ItemTaskQuest, ItemRewardQuest
{
    private final List<Supplier<ItemConvertible>> tasks;
    private final Supplier<ItemStack> reward;

    public SimpleItemQuest(List<Supplier<ItemConvertible>> tasks, Supplier<ItemStack> reward)
    {
        this.tasks = tasks;
        this.reward = reward;
    }

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
