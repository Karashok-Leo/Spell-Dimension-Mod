package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public record HealthQuest(float health, List<ItemStack> rewards) implements ItemRewardQuest
{
    @Override
    public boolean completeTasks(ServerPlayerEntity player)
    {
        return player.getMaxHealth() >= health;
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return this.rewards;
    }

    @Override
    public void appendTaskDesc(List<Text> desc)
    {
        desc.add(SDTexts.TOOLTIP_QUEST_HEALTH.get(Text.translatable(EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey()), this.health));
    }
}
