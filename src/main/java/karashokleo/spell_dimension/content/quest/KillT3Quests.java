package karashokleo.spell_dimension.content.quest;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllTags;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;

public class KillT3Quests
{
    public static SimpleLootItemQuest KILL_MOON_KNIGHT;
    public static SimpleLootItemQuest KILL_BLACKSTONE_GOLEM;
    public static SimpleLootItemQuest KILL_CAPTAIN_CORNELIA;
    public static SimpleLootItemQuest KILL_CHAOS_MONARCH;
    public static SimpleLootItemQuest KILL_RETURNING_KNIGHT;
    public static SimpleLootItemQuest KILL_STALKER;

    public static void register()
    {
        KILL_MOON_KNIGHT = QuestBuilder.of(
                        "kill_moon_knight",
                        new SimpleLootItemQuest(
                                "soulsweapons:moonknight",
                                "soulsweapons:essence_of_luminescence",
                                SDBags.RARE_BOOK::getStack
                        )
                )
                .toEntry("boss/moon_knight")
                .addTag(AllTags.MAIN)
                .addDependencies(KillT4Quests.KILL_OLD_CHAMPION)
                .register();
        KILL_BLACKSTONE_GOLEM = QuestBuilder.of(
                        "kill_blackstone_golem",
                        new SimpleLootItemQuest(
                                () -> EntityInit.BLACKSTONE_GOLEM,
                                () -> ItemInit.BLACKSTONE_GOLEM_HEART,
                                SDBags.RARE_BOOK::getStack
                        )
                )
                .toEntry("boss/blackstone_golem")
                .addTag(AllTags.MAIN)
                .addDependencies(KillT4Quests.KILL_DECAYING_KING)
                .register();
        KILL_CAPTAIN_CORNELIA = QuestBuilder.of(
                        "kill_captain_cornelia",
                        new SimpleLootItemQuest(
                                () -> AquamiraeEntities.CAPTAIN_CORNELIA,
                                () -> AquamiraeItems.FROZEN_KEY,
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .toEntry("boss/captain")
                .addTag(AllTags.MAIN)
                .addDependencies(KillT4Quests.KILL_ELDER_GUARDIAN)
                .register();
        KILL_CHAOS_MONARCH = QuestBuilder.of(
                        "kill_chaos_monarch",
                        new SimpleLootItemQuest(
                                "soulsweapons:chaos_monarch",
                                "soulsweapons:chaos_crown",
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .toEntry("boss/chaos_monarch")
                .addTag(AllTags.MAIN)
                .addDependencies(KillT4Quests.KILL_WITHER)
                .register();
        KILL_RETURNING_KNIGHT = QuestBuilder.of(
                        "kill_returning_knight",
                        new SimpleLootItemQuest(
                                "soulsweapons:returning_knight",
                                "soulsweapons:arkenstone",
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .toEntry("boss/returning_knight")
                .addTag(AllTags.MAIN)
                .addDependencies(KillT4Quests.KILL_INVOKER)
                .register();
        KILL_STALKER = QuestBuilder.of(
                        "kill_stalker",
                        new SimpleLootItemQuest(
                                "deeperdarker:stalker",
                                "deeperdarker:soul_crystal",
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .toEntry("boss/stalker")
                .addTag(AllTags.MAIN)
                .addDependencies(KillT4Quests.KILL_WARDEN)
                .register();
    }
}
