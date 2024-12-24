package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.AdvancementQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public record SimpleAdvancementQuest(
        Identifier advancementId,
        List<ItemStack> rewards,
        String taskDesc
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

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(Text.translatable(taskDesc));
    }
}
