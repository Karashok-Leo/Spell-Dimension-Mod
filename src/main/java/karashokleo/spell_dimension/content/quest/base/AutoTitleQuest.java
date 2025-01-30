package karashokleo.spell_dimension.content.quest.base;

import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestRegistry;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface AutoTitleQuest extends Quest
{
    default boolean isBeginning()
    {
        return false;
    }

    default boolean isEnd()
    {
        return false;
    }

    default boolean isChallenge()
    {
        return false;
    }

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
        MutableText title = Text.translatable(titleKey);
        if (this.isBeginning())
            return SDTexts.TEXT$QUEST$BEGINNING.get()
                    .append(Text.literal(" "))
                    .append(title)
                    .formatted(Formatting.AQUA);
        else if (this.isEnd())
            return SDTexts.TEXT$QUEST$END.get()
                    .append(Text.literal(" "))
                    .append(title)
                    .formatted(Formatting.YELLOW);
        else if (this.isChallenge())
            return SDTexts.TEXT$QUEST$CHALLENGE.get()
                    .append(Text.literal(" "))
                    .append(title)
                    .formatted(Formatting.RED);
        else
            return title;
    }
}
