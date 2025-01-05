package karashokleo.spell_dimension.config;

import com.klikli_dev.modonomicon.client.gui.BookGuiManager;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.data.book.MagicGuidanceProvider;
import karashokleo.spell_dimension.init.AllQuests;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class QuestToEntryConfig
{
    private static final Map<Quest, Identifier> QUEST_TO_ENTRY = new HashMap<>();

    public static void openEntry(Quest quest)
    {
        Identifier entryId = QUEST_TO_ENTRY.get(quest);
        if (entryId == null) return;
        BookGuiManager.get().openEntry(MagicGuidanceProvider.BOOK_ID, entryId, 0);
    }

    public static void init()
    {
        QUEST_TO_ENTRY.put(AllQuests.HEALTH, BookGenUtil.id("power/health"));
        QUEST_TO_ENTRY.put(AllQuests.CHOOSE_PATH, BookGenUtil.id("mage/bind"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_TRAIT, BookGenUtil.id("power/hostility"));
        QUEST_TO_ENTRY.put(AllQuests.BASE_ESSENCE, BookGenUtil.id("mage/essence"));
        QUEST_TO_ENTRY.put(AllQuests.MORE_ESSENCE, BookGenUtil.id("mage/enchant"));
        QUEST_TO_ENTRY.put(AllQuests.SPELL_BOOK_0, BookGenUtil.id("mage/book"));
        QUEST_TO_ENTRY.put(AllQuests.SPELL_BOOK_1, BookGenUtil.id("mage/book"));
        QUEST_TO_ENTRY.put(AllQuests.SPELL_BOOK_2, BookGenUtil.id("mage/book"));
//        QUEST_TO_ENTRY.put(AllQuests.SPELL_POWER_0, BookGenUtil.id("mage/book"));
//        QUEST_TO_ENTRY.put(AllQuests.SPELL_POWER_1, BookGenUtil.id("mage/book"));
//        QUEST_TO_ENTRY.put(AllQuests.SPELL_POWER_2, BookGenUtil.id("mage/book"));

        QUEST_TO_ENTRY.put(AllQuests.KILL_OLD_CHAMPION, BookGenUtil.id("boss/old_champion"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_DECAYING_KING, BookGenUtil.id("boss/decaying_king"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_ELDER_GUARDIAN, BookGenUtil.id("boss/guardian"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_WITHER, BookGenUtil.id("boss/wither"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_INVOKER, BookGenUtil.id("boss/invoker"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_WARDEN, BookGenUtil.id("boss/warden"));

        QUEST_TO_ENTRY.put(AllQuests.KILL_MOON_KNIGHT, BookGenUtil.id("boss/moon_knight"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_BLACKSTONE_GOLEM, BookGenUtil.id("boss/blackstone_golem"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_CAPTAIN_CORNELIA, BookGenUtil.id("boss/captain"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_CHAOS_MONARCH, BookGenUtil.id("boss/chaos_monarch"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_RETURNING_KNIGHT, BookGenUtil.id("boss/returning_knight"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_STALKER, BookGenUtil.id("boss/stalker"));

        QUEST_TO_ENTRY.put(AllQuests.KILL_GRAVEYARD_LICH, BookGenUtil.id("boss/graveyard_lich"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_BOMD_LICH, BookGenUtil.id("boss/bomd_lich"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_VOID_BLOSSOM, BookGenUtil.id("boss/void_blossom"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_GAUNTLET, BookGenUtil.id("boss/gauntlet"));

        QUEST_TO_ENTRY.put(AllQuests.KILL_DAY_NIGHT, BookGenUtil.id("boss/day_night"));

        QUEST_TO_ENTRY.put(AllQuests.KILL_ENDER_DRAGON, BookGenUtil.id("boss/dragon"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_OBSIDLITH, BookGenUtil.id("boss/obsidilith"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_THE_EYE, BookGenUtil.id("boss/the_eye"));
        QUEST_TO_ENTRY.put(AllQuests.KILL_VOID_SHADOW, BookGenUtil.id("boss/void_shadow"));
    }
}
