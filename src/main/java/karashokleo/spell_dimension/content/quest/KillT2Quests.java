package karashokleo.spell_dimension.content.quest;

import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;

public class KillT2Quests
{
    public static SimpleLootItemQuest KILL_GRAVEYARD_LICH;
    public static SimpleLootItemQuest KILL_BOMD_LICH;
    public static SimpleLootItemQuest KILL_VOID_BLOSSOM;
    public static SimpleLootItemQuest KILL_GAUNTLET;

    public static void register()
    {
        KILL_GRAVEYARD_LICH = QuestBuilder.of(
                        "kill_graveyard_lich",
                        new SimpleLootItemQuest(
                                "graveyard:lich",
                                "endrem:undead_soul",
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .toEntry("boss/graveyard_lich")
                .addDependencies(KillMutantQuests.KILL_MUTANT_ENDERMAN)
                .register();
        KILL_BOMD_LICH = QuestBuilder.of(
                        "kill_bomd_lich",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:lich",
                                "bosses_of_mass_destruction:ancient_anima",
                                SDBags.EPIC_BOOK::getStack
                        )
                )
                .toEntry("boss/bomd_lich")
                .addDependencies(KillMutantQuests.KILL_MUTANT_ENDERMAN)
                .register();
        KILL_VOID_BLOSSOM = QuestBuilder.of(
                        "kill_void_blossom",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:void_blossom",
                                "bosses_of_mass_destruction:void_thorn",
                                TrinketItems.LOOT_3::getDefaultStack
                        )
                )
                .toEntry("boss/void_blossom")
                .addDependencies(KillMutantQuests.KILL_MUTANT_ENDERMAN)
                .register();
        KILL_GAUNTLET = QuestBuilder.of(
                        "kill_gauntlet",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:gauntlet",
                                "bosses_of_mass_destruction:blazing_eye",
                                SDBags.EPIC_MATERIAL::getStack
                        )
                )
                .toEntry("boss/gauntlet")
                .addDependencies(KillMutantQuests.KILL_MUTANT_ENDERMAN)
                .register();
    }
}
