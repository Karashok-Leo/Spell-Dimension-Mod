package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.client.quest.QuestItemTooltipData;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.resource.language.I18n;
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
import java.util.Objects;
import java.util.stream.Stream;

public interface Quest
{
    boolean completeTasks(ServerPlayerEntity player);

    void reward(ServerPlayerEntity player);

    default String getTranslationKey(String suffix)
    {
        return Objects.requireNonNull(
                QuestRegistry.QUEST_REGISTRY.getId(this),
                "Unregistered quest!"
        ).toTranslationKey("quest", suffix);
    }

    default String getTitleKey()
    {
        return getTranslationKey("title");
    }

    default String getDescriptionKey()
    {
        return getTranslationKey("description");
    }

    default String getFeedbackKey()
    {
        return getTranslationKey("feedback");
    }

    default Text getTitleText()
    {
        return Text.translatable(getTitleKey());
    }

    default Text getDescriptionText()
    {
        return Text.translatable(getDescriptionKey());
    }

    default Text getFeedbackText()
    {
        return Text.translatable(getFeedbackKey());
    }

    default void appendTaskDescription(World world, List<Text> desc)
    {
        desc.add(getDescriptionText());
    }

    default void appendRewardDescription(World world, List<Text> desc)
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
        String titleKey = this.getTitleKey();
        if (!I18n.hasTranslation(titleKey)) return null;
        return Text.translatable(titleKey);
    }

    default List<Text> getDescriptions(World world)
    {
        List<Text> desc = new ArrayList<>();
        desc.add(SDTexts.TOOLTIP$QUEST$TASK.get().formatted(Formatting.BOLD));
        this.appendTaskDescription(world, desc);
        desc.add(SDTexts.TOOLTIP$QUEST$REWARD.get().formatted(Formatting.BOLD));
        this.appendRewardDescription(world, desc);
        return desc;
    }

    @Nullable
    default Text getFeedback(World world)
    {
        String feedbackKey = this.getFeedbackKey();
        if (!I18n.hasTranslation(feedbackKey)) return null;
        return Text.translatable(feedbackKey).formatted(Formatting.LIGHT_PURPLE);
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
        tooltip.addAll(this.getDescriptions(world));
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
