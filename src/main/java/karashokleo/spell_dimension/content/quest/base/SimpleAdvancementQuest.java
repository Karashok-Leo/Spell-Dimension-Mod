package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.AdvancementQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public record SimpleAdvancementQuest(
        Identifier advancementId,
        Supplier<ItemStack> reward,
        String titleKey,
        String descKey
) implements AdvancementQuest, ItemRewardQuest
{
    public SimpleAdvancementQuest(Identifier advancementId, Supplier<ItemStack> reward, String translationKey)
    {
        this(
                advancementId,
                reward,
                translationKey + ".title",
                translationKey + ".description"
        );
    }

    @Override
    public Identifier getAdvancementId()
    {
        return advancementId;
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(reward.get());
    }

    @Override
    public Text getTitle(World world)
    {
        return Text.translatable(titleKey);
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(Text.translatable(descKey));
    }
}
