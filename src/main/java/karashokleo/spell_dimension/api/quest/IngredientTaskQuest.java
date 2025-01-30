package karashokleo.spell_dimension.api.quest;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.compat.trinket.slot.EntitySlotAccess;
import karashokleo.spell_dimension.client.quest.QuestItemTooltipData;
import karashokleo.spell_dimension.mixin.vanilla.PlayerInventoryAccessor;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IngredientTaskQuest extends Quest
{
    List<Ingredient> getTasks();

    @Override
    default boolean completeTasks(ServerPlayerEntity player)
    {
        List<Ingredient> tasks = new ArrayList<>(this.getTasks());
        for (var list : ((PlayerInventoryAccessor) player.getInventory()).getCombinedInventory())
            for (ItemStack stack : list)
                tasks.removeIf(goal -> goal.test(stack));
        for (EntitySlotAccess access : TrinketCompat.getItemAccess(player))
            tasks.removeIf(goal -> goal.test(access.get()));
        return tasks.isEmpty();
    }

    @Override
    default TooltipData getTooltipData()
    {
        DefaultedList<ItemStack> stacks = DefaultedList.of();
        for (Ingredient goal : this.getTasks())
            stacks.addAll(Arrays.asList(goal.getMatchingStacks()));
        return new QuestItemTooltipData(stacks);
    }
}
