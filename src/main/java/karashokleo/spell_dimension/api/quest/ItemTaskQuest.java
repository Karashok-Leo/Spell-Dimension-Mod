package karashokleo.spell_dimension.api.quest;

import net.minecraft.item.Item;
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
        for (ItemConvertible c : this.getTaskItems())
        {
            Item item = c.asItem();
            desc.add(
                    Text.empty()
                            .append(item.getName())
                            .formatted(item.getRarity(item.getDefaultStack()).formatting)
            );
        }
    }
}
