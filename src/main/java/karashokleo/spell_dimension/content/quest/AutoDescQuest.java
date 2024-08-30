package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Objects;

public interface AutoDescQuest extends Quest
{
    default String getTranslationKey()
    {
        return Objects.requireNonNull(QuestRegistry.QUEST_REGISTRY.getId(this), "Unregistered quest!").toTranslationKey("quest", "description");
    }

    default Text getDescText()
    {
        return Text.translatable(this.getTranslationKey());
    }

    @Override
    default void appendTaskDesc(List<Text> desc)
    {
        desc.add(getDescText());
    }
}
