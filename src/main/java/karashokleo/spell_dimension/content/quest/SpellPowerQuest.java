package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.spell_power.api.SpellPower;

import java.util.List;
import java.util.function.Supplier;

public record SpellPowerQuest(
        double min,
        Supplier<ItemStack> reward
) implements ItemRewardQuest
{
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
        return List.of(reward.get());
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(SDTexts.TEXT$QUEST$SPELL_POWER.get(min));
    }
}
