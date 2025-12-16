package karashokleo.spell_dimension.content.quest.special;

import artifacts.registry.ModItems;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Supplier;

public record HealthQuest(
    float health,
    Supplier<ItemStack> reward
) implements ItemRewardQuest
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
    public Text getDescriptionText()
    {
        return Text.translatable(
            this.getTranslationKey("description"),
            Text.translatable(EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey()),
            this.health
        );
    }

    @Override
    public ItemStack getIcon()
    {
        return ModItems.CRYSTAL_HEART.get().getDefaultStack();
    }
}
