package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public record HealthQuest(float health, Supplier<ItemStack> reward) implements ItemRewardQuest
{
    @Override
    public boolean completeTasks(ServerPlayerEntity player)
    {
        return player.getMaxHealth() >= health;
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(reward.get());
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(SDTexts.TEXT$QUEST$HEALTH.get(
                Text.translatable(EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey()),
                this.health
        ));
    }
}
