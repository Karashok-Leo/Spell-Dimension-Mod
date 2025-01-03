package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.AdvancementQuest;
import karashokleo.spell_dimension.api.quest.ItemRewardQuest;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public record EnderDragonAdvancementQuest(
        List<ItemStack> rewards
) implements AdvancementQuest, ItemRewardQuest, AutoDescQuest
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
        return rewards;
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        AutoDescQuest.super.appendTaskDesc(world, desc);
        desc.add(Text.translatable("advancements.end.kill_dragon.description"));
    }
}
