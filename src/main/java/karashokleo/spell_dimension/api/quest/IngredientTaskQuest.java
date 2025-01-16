package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.mixin.vanilla.PlayerInventoryAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IngredientTaskQuest extends Quest
{
    List<Ingredient> getTasks();

    @Override
    default boolean completeTasks(ServerPlayerEntity player)
    {
        Iterator<DefaultedList<ItemStack>> iterator = ((PlayerInventoryAccessor) player.getInventory()).getCombinedInventory().iterator();
        List<Ingredient> tasks = new ArrayList<>(this.getTasks());
        while (iterator.hasNext())
        {
            List<ItemStack> list = iterator.next();
            for (ItemStack stack : list)
                tasks.removeIf(goal -> goal.test(stack));
        }
        return tasks.isEmpty();
    }
}
