package karashokleo.spell_dimension.content.quest;

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
        return List.of(reward.get());
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(Text.translatable(taskDesc));
    }
}
