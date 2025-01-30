package karashokleo.spell_dimension.content.quest.base;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class SimpleItemChallengeQuest extends SimpleItemQuest implements AutoTitleQuest, AutoDescQuest
{
    public SimpleItemChallengeQuest(List<Supplier<ItemConvertible>> tasks, Supplier<ItemStack> reward)
    {
        super(tasks, reward);
    }

    public SimpleItemChallengeQuest(Supplier<ItemConvertible> task, Supplier<ItemStack> reward)
    {
        super(List.of(task), reward);
    }

    @Override
    public boolean isChallenge()
    {
        return true;
    }

    @Override
    public void appendTaskDesc(World world, List<Text> desc)
    {
        super.appendTaskDesc(world, desc);
        String descKey = this.getDescKey();
        if (I18n.hasTranslation(descKey))
            desc.add(Text.translatable(descKey));
    }
}
