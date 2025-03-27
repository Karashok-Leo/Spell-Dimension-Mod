package karashokleo.spell_dimension.api.quest;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class QuestTag
{
    private static final HashMap<Identifier, Integer> PRIORITY_MAP = new HashMap<>();
    private static final HashMap<Identifier, Formatting> FORMATTING_MAP = new HashMap<>();

    public static TagKey<Quest> of(Identifier id)
    {
        return TagKey.of(QuestRegistry.QUEST_REGISTRY_KEY, id);
    }

    public static void configure(TagKey<Quest> tag, int priority, Formatting formatting)
    {
        PRIORITY_MAP.put(tag.id(), priority);
        FORMATTING_MAP.put(tag.id(), formatting);
    }

    public static int getTagPriority(TagKey<Quest> tag)
    {
        return PRIORITY_MAP.getOrDefault(tag.id(), 100);
    }

    public static Formatting getTagFormatting(TagKey<Quest> tag)
    {
        return FORMATTING_MAP.getOrDefault(tag.id(), Formatting.WHITE);
    }

    public static String getTagTranslationKey(TagKey<Quest> tag)
    {
        return tag.id().toTranslationKey("tag.quest");
    }

    public static MutableText getTagText(TagKey<Quest> tag)
    {
        return SDTexts.TOOLTIP$BRACKETS.get(
                Text.translatable(getTagTranslationKey(tag))
        ).formatted(
                getTagFormatting(tag)
        );
    }
}
