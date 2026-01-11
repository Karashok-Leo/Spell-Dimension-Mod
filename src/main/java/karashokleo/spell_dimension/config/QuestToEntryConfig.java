package karashokleo.spell_dimension.config;

import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.PatchouliLookupCallback;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.data.book.MagicGuideProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QuestToEntryConfig
{
    private static final Map<Quest, Pair<Identifier, Integer>> QUEST_TO_ENTRY = new HashMap<>();
    public static final Pair<Identifier, Integer> QUEST_SYSTEM_ENTRY = new Pair<>(SpellDimension.modLoc("welcome/quest"), 0);

    public static void init()
    {
        PatchouliLookupCallback.EVENT.register(stack ->
        {
            if (!stack.isOf(AllItems.QUEST_SCROLL))
            {
                return null;
            }
            Optional<Quest> optional = AllItems.QUEST_SCROLL.getQuest(stack);
            if (optional.isEmpty())
            {
                return QUEST_SYSTEM_ENTRY;
            }
            return QUEST_TO_ENTRY.get(optional.get());
        });
    }

    @Deprecated
    public static void openEntry(Quest quest)
    {
        Pair<Identifier, Integer> pair = QUEST_TO_ENTRY.get(quest);
        if (pair == null)
        {
            return;
        }
        Identifier entryId = pair.getFirst();
        if (entryId == null)
        {
            return;
        }
        BookGuiManager.get().openEntry(MagicGuideProvider.BOOK_ID, entryId, 0);
    }

    public static void register(Quest quest, String entryId)
    {
        register(quest, SpellDimension.modLoc(entryId), 0);
    }

    public static void register(Quest quest, String entryId, int page)
    {
        register(quest, SpellDimension.modLoc(entryId), page);
    }

    public static void register(Quest quest, Identifier entryId, int page)
    {
        QUEST_TO_ENTRY.put(quest, Pair.of(entryId, page));
    }
}
