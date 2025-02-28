package karashokleo.spell_dimension.content.quest.special;

import artifacts.registry.ModItems;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import karashokleo.spell_dimension.content.quest.base.AutoDescQuest;
import karashokleo.spell_dimension.content.quest.base.AutoTitleQuest;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public record HealthQuest(
        float health,
        Supplier<ItemStack> reward,
        boolean challenge
) implements ItemRewardQuest, AutoTitleQuest, AutoDescQuest
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
    public boolean isChallenge()
    {
        return this.challenge;
    }

    @Override
    public Text getDescText()
    {
        return Text.translatable(
                this.getDescKey(),
                Text.translatable(EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey()),
                this.health
        );
    }

    @Override
    public @Nullable ItemStack getIcon()
    {
        return ModItems.CRYSTAL_HEART.get().getDefaultStack();
    }
}
