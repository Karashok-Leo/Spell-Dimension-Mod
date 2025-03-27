package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.client.quest.QuestItemTooltipData;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public interface Quest
{
    boolean completeTasks(ServerPlayerEntity player);

    void reward(ServerPlayerEntity player);

    default void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(Text.empty());
    }

    default void appendRewardDesc(World world, List<Text> desc)
    {
        desc.add(Text.empty());
    }

    @Nullable
    default Text getTagsText(World world)
    {
        return streamTags()
                .sorted(Comparator.comparingInt(QuestTag::getTagPriority))
                .map(QuestTag::getTagText)
                .reduce((a, b) -> a.append(" ").append(b))
                .orElse(null);
    }

    @Nullable
    default Text getTitle(World world)
    {
        return null;
    }

    default List<Text> getDesc(World world)
    {
        List<Text> desc = new ArrayList<>();
        desc.add(SDTexts.TOOLTIP$QUEST$TASK.get().formatted(Formatting.BOLD));
        this.appendTaskDesc(world, desc);
        desc.add(SDTexts.TOOLTIP$QUEST$REWARD.get().formatted(Formatting.BOLD));
        this.appendRewardDesc(world, desc);
        return desc;
    }

    @Nullable
    default TooltipData getTooltipData()
    {
        return null;
    }

    @Nullable
    default ItemStack getIcon()
    {
        if (this.getTooltipData() instanceof QuestItemTooltipData data)
            return data.stacks().get(0);
        return null;
    }

    default void appendTooltip(@Nullable World world, List<Text> tooltip)
    {
        Text tagsText = this.getTagsText(world);
        if (tagsText != null) tooltip.add(tagsText);
        Text title = this.getTitle(world);
        if (title != null) tooltip.add(title);
        tooltip.addAll(this.getDesc(world));
    }

    default boolean isIn(TagKey<Quest> tag)
    {
        return QuestRegistry.QUEST_REGISTRY.getEntry(this).isIn(tag);
    }

    default Stream<TagKey<Quest>> streamTags()
    {
        return QuestRegistry.QUEST_REGISTRY.getEntry(this).streamTags();
    }
}
