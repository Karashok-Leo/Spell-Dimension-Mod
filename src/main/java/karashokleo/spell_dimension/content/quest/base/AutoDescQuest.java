package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public interface AutoDescQuest extends Quest
{
    default String getDescKey()
    {
        return Objects.requireNonNull(QuestRegistry.QUEST_REGISTRY.getId(this), "Unregistered quest!").toTranslationKey("quest", "description");
    }

    default Text getDescText()
    {
        return Text.translatable(this.getDescKey());
    }

    @Override
    default void appendTaskDesc(World world, List<Text> desc)
    {
        desc.add(getDescText());
    }
}
