package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.IngredientTaskQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public record SimpleTagIngredientQuest(
        List<TagKey<Item>> tags,
        List<ItemStack> rewards
) implements IngredientTaskQuest, ItemRewardQuest, AutoDescQuest
{
    public SimpleTagIngredientQuest(TagKey<Item> tag, ItemStack reward)
    {
        this(List.of(tag), List.of(reward));
    }

    @Override
    public List<Ingredient> getTasks()
    {
        return tags.stream().map(Ingredient::fromTag).toList();
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return rewards;
    }
}
