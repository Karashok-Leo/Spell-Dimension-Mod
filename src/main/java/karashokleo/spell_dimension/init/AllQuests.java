package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.content.quest.*;

public class AllQuests
{
    public static void register()
    {
        BaseQuests.register();
        HostilityQuests.register();
        CraftQuests.register();
//        CoinQuests.register();
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
    }
}
