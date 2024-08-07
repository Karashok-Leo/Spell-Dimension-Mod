package karashokleo.spell_dimension.api.quest;

import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;

import java.util.List;

public interface ItemTaskQuest extends IngredientTaskQuest
{
    List<ItemConvertible> getTaskItems();

    @Override
    default List<Ingredient> getTasks()
    {
        return this.getTaskItems().stream().map(Ingredient::ofItems).toList();
    }

    @Override
    default void appendTaskDesc(List<Text> desc)
    {
        for (ItemConvertible item : this.getTaskItems())
            desc.add(item.asItem().getName());
    }
}
