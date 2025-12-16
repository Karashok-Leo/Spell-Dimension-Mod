package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.AdvancementQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public record SimpleAdvancementQuest(
    Identifier advancementId,
    Supplier<ItemStack> reward,
    String titleKey,
    String descKey,
    String iconKey
) implements AdvancementQuest, ItemRewardQuest
{
    public SimpleAdvancementQuest(Identifier advancementId, Supplier<ItemStack> reward, String translationKey, String iconKey)
    {
        this(
            advancementId,
            reward,
            translationKey + ".title",
            translationKey + ".description",
            iconKey
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
    public void appendTaskDescription(World world, List<Text> desc)
    {
        desc.add(Text.translatable(descKey));
    }

    @Override
    public @Nullable ItemStack getIcon()
    {
        return Registries.ITEM.get(new Identifier(iconKey)).getDefaultStack();
    }
}
