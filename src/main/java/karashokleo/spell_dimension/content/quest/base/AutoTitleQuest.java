package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface AutoTitleQuest extends Quest
{
    default String getTitleKey()
    {
        return Objects.requireNonNull(QuestRegistry.QUEST_REGISTRY.getId(this), "Unregistered quest!").toTranslationKey("quest", "title");
    }

    default Text getTitleText()
    {
        return Text.translatable(getTitleKey());
    }

    @Nullable
    @Override
    default Text getTitle(World world)
    {
        String titleKey = this.getTitleKey();
        if (!I18n.hasTranslation(titleKey)) return null;
        return Text.translatable(titleKey);
    }
}
