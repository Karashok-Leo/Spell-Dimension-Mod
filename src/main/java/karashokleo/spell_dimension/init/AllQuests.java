package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.api.quest.QuestTag;
import karashokleo.spell_dimension.content.quest.*;
import net.minecraft.util.Formatting;

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
    }
}
