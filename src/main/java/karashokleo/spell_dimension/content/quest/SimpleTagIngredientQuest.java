package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.IngredientTaskQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.function.Supplier;

public record SimpleTagIngredientQuest(
        List<TagKey<Item>> tags,
        Supplier<ItemStack> reward
) implements IngredientTaskQuest, ItemRewardQuest, AutoDescQuest
{
    public SimpleTagIngredientQuest(TagKey<Item> tag, Supplier<ItemStack> reward)
    {
        this(List.of(tag), reward);
    }

    @Override
    public List<Ingredient> getTasks()
    {
        return tags.stream().map(Ingredient::fromTag).toList();
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(reward.get());
    }
}
