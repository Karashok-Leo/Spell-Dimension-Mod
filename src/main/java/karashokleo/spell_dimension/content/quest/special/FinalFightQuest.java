package karashokleo.spell_dimension.content.quest.special;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.api.quest.ItemTaskQuest;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public record FinalFightQuest(
    Supplier<EntityType<?>> entity,
    Supplier<ItemConvertible> task,
    Supplier<ItemStack> reward
) implements ItemTaskQuest, ItemRewardQuest
{
    @Override
    public List<ItemConvertible> getTaskItems()
    {
        return List.of(task.get());
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(reward.get());
    }

    @Override
    public void appendTaskDescription(World world, List<Text> desc)
    {
        desc.add(SDTexts.TOOLTIP$QUEST$LOOT_ITEM.get(entity.get().getName(), task.get().asItem().getName()));
    }
}
