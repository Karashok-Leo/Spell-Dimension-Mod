package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllTags;

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
                .addEnFeedback("Undead lich possessed the secret art of rising from the dead with only the help of a specific medium and at a minimal cost. The bone staff that broke into three pieces was one of them, and you used your blood as a guide to bring him back to the present and give him an eternal end yourself.")
                .addZhFeedback("亡灵巫妖掌握着从死亡中复生的秘术，仅需借助特定的媒介，其代价微乎其微。那柄断裂为三截的骨杖便是其中之一，你以鲜血为引，将他召回现世，并亲自赋予他永恒的终结。")
                .toEntry("boss/graveyard_lich")
                .addTag(AllTags.MAIN)
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
                .addEnFeedback("The skeleton of Night Lich collapsed with a hiss as the canopy of eternal night cracked its first slit. The popping sound of roots awakening comes from beneath the ice. As light pierces the remnants of the tower, you are inconsolable for a long time.")
                .addZhFeedback("巫妖的骸骨在嘶鸣中坍缩，永夜天幕裂开第一道缝隙。冰层下传来根系苏醒的爆裂声。当光刺透高塔残垣，你久久无法平静。")
                .toEntry("boss/bomd_lich")
                .addTag(AllTags.MAIN)
                .addDependencies(KillMutantQuests.KILL_MUTANT_ENDERMAN)
                .register();
        KILL_VOID_BLOSSOM = QuestBuilder.of(
                        "kill_void_blossom",
                        new SimpleLootItemQuest(
                                "bosses_of_mass_destruction:void_blossom",
                                "bosses_of_mass_destruction:void_thorn",
                                SDBags.EPIC_MATERIAL::getStack
                        )
                )
                .addEnFeedback("A giant flower of thorns planted deep in the earth, it draws its power from the void below the bedrock and grows. Do these thorns from the void symbolize life, or destruction?")
                .addZhFeedback("深植于地底的刺棘巨花，它从基岩下方的虚空中汲取力量日益成长。这些源自虚空的荆棘之力，究竟象征着生机，抑或是毁灭？")
                .toEntry("boss/void_blossom")
                .addTag(AllTags.MAIN)
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
                .addEnFeedback("In the ruins of the Blackstone Arena, the dust settled, and the gauntlet that had once been sealed in the black stone was transformed into a cluster of netherracks. It was once so powerful that the death rays from the palm's eyeballs deterred countless heroes. Now it has been completely extinguished, leaving only an eye that still burns brightly.")
                .addZhFeedback("黑石竞技场的废墟之中，尘埃落定，曾经被封存在黑石之中的铁掌化成一簇下界岩。它曾经强大无比，源自掌心眼球的死亡射线让无数英雄望而却步。而如今它已经彻底烬灭，只剩下一个依然灼灼燃烧的眼睛。")
                .toEntry("boss/gauntlet")
                .addTag(AllTags.MAIN)
                .addDependencies(KillMutantQuests.KILL_MUTANT_ENDERMAN)
                .register();
    }
}
