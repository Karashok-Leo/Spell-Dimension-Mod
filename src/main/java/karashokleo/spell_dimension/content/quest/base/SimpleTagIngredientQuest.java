package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.IngredientTaskQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public record SimpleTagIngredientQuest(
        List<TagKey<Item>> tags,
        Supplier<ItemStack> reward
) implements IngredientTaskQuest, ItemRewardQuest
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

    @Override
    public void appendTaskDescription(World world, List<Text> desc)
    {
        IngredientTaskQuest.super.appendTaskDescription(world, desc);
        if (tags.isEmpty()) return;
        MutableText tagText = Text.literal("#" + tags.get(0).id().toString()).formatted(Formatting.AQUA);
        for (int i = 1; i < tags.size(); i++)
        {
            tagText.append(Text.literal(" / "));
            MutableText text = Text.literal("#" + tags.get(i).id().toString()).formatted(Formatting.AQUA);
            tagText.append(text);
        }
        desc.add(SDTexts.TOOLTIP$QUEST$TAG_ITEM.get(tagText));
    }
}
