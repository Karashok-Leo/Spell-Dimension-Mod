package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.spell_power.api.SpellPower;

import java.util.List;

public record SpellPowerQuest(
        double min,
        List<ItemStack> rewards
) implements ItemRewardQuest
{
    public SpellPowerQuest(double min, ItemStack reward)
    {
        this(min, List.of(reward));
    }

    @Override
    public boolean completeTasks(ServerPlayerEntity player)
    {
        return SchoolUtil.SCHOOLS
                .stream()
                .anyMatch(school ->
                        SpellPower
                                .getSpellPower(school, player)
                                .baseValue() >= min
                );
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return rewards;
    }

    @Override
    public void appendTaskDesc(List<Text> desc)
    {
        desc.add(SDTexts.TEXT$QUEST$SPELL_POWER.get(min));
    }
}
