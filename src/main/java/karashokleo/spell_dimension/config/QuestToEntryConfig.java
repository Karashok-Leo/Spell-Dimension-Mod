package karashokleo.spell_dimension.config;

import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class QuestToEntryConfig
{
    private static final Map<Quest, Identifier> QUEST_TO_ENTRY = new HashMap<>();

    public static boolean hasEntry(Quest quest)
    {
        return QUEST_TO_ENTRY.containsKey(quest);
    }

    public static void openEntry(Quest quest)
    {
        Identifier entryId = QUEST_TO_ENTRY.get(quest);
        if (entryId == null) return;
        BookGuiManager.get().openEntry(MagicGuidanceProvider.BOOK_ID, entryId, 0);
    }

    public static void register(Quest quest, String entryId)
    {
        register(quest, BookGenUtil.id(entryId));
    }

    public static void register(Quest quest, Identifier entryId)
    {
        QUEST_TO_ENTRY.put(quest, entryId);
    }
}
