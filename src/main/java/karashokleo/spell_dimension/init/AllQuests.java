package karashokleo.spell_dimension.init;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.api.PatchouliLookupCallback;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestTag;
import karashokleo.spell_dimension.config.QuestToEntryConfig;
import karashokleo.spell_dimension.content.quest.*;
import net.minecraft.util.Formatting;

import java.util.Optional;

public class AllQuests
{
    public static void register()
    {
        BaseQuests.register();
        HostilityQuests.register();
        CraftQuests.register();
        HealthQuests.register();
        MageQuests.register();
        KillMutantQuests.register();
//        KillLevelQuests.register();
        KillT4Quests.register();
        KillT3Quests.register();
        KillT2Quests.register();
        KillT1Quests.register();
        KillT0Quests.register();
        QuestBuilder.buildRelations();

        QuestTag.configure(AllTags.MAIN, 6, Formatting.AQUA);
        QuestTag.configure(AllTags.BRANCH, 6, Formatting.AQUA);
        QuestTag.configure(AllTags.BEGINNING, 66, Formatting.LIGHT_PURPLE);
        QuestTag.configure(AllTags.END, 66, Formatting.YELLOW);
        QuestTag.configure(AllTags.CHALLENGE, 66, Formatting.RED);
        QuestTag.configure(AllTags.SKIPPABLE, 666, Formatting.GREEN);

        PatchouliLookupCallback.EVENT.register(stack ->
        {
            if (!stack.isOf(AllItems.QUEST_SCROLL))
            {
                return null;
            }
            Optional<Quest> optional = AllItems.QUEST_SCROLL.getQuest(stack);
            if (optional.isEmpty())
            {
                return null;
            }
            Quest quest = optional.get();
            if (!QuestToEntryConfig.hasEntry(quest))
            {
                return null;
            }
            var entryId = QuestToEntryConfig.getEntryId(quest);
            return Pair.of(entryId, 0);
        });
    }
}
