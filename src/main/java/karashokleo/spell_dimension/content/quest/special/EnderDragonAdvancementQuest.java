package karashokleo.spell_dimension.content.quest.special;

import karashokleo.spell_dimension.api.quest.AdvancementQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public record EnderDragonAdvancementQuest(
        Supplier<ItemStack> reward
) implements AdvancementQuest, ItemRewardQuest
{
    private static final Identifier ADVANCEMENT_ID = new Identifier("minecraft:end/kill_dragon");

    @Override
    public Identifier getAdvancementId()
    {
        return ADVANCEMENT_ID;
    }

    @Override
    public List<ItemStack> getRewards()
    {
        return List.of(reward.get());
    }

    @Override
    public Text getTitle(World world)
    {
        return Text.translatable("advancements.end.kill_dragon.title");
    }

    @Override
    public void appendTaskDescription(World world, List<Text> desc)
    {
        AdvancementQuest.super.appendTaskDescription(world, desc);
        desc.add(Text.translatable("advancements.end.kill_dragon.description"));
    }

    @Override
    public @Nullable ItemStack getIcon()
    {
        return Items.DRAGON_HEAD.getDefaultStack();
    }
}
