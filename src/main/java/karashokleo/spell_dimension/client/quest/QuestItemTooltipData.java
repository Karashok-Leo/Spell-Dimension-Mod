package karashokleo.spell_dimension.client.quest;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public record QuestItemTooltipData(DefaultedList<ItemStack> stacks) implements TooltipData
{
}
