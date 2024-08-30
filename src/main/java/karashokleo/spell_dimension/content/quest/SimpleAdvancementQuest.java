package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.AdvancementQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public record SimpleAdvancementQuest(
        Identifier advancementId,
        List<ItemStack> rewards
) implements AdvancementQuest, ItemRewardQuest
{
    @Override
    public Identifier getAdvancementId()
    {
        return advancementId;
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return rewards;
    }
}
