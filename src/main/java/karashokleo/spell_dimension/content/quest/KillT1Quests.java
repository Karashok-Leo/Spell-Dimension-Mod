package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.content.quest.special.EnderDragonAdvancementQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class KillT1Quests
{
    public static SimpleLootItemQuest KILL_DAY_NIGHT;
    public static EnderDragonAdvancementQuest KILL_ENDER_DRAGON;
    public static SimpleLootItemQuest KILL_THE_EYE;

    public static void register()
    {
        KILL_DAY_NIGHT = QuestBuilder.of(
                        "kill_day_night",
                        new SimpleLootItemQuest(
                                List.of(
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:day_stalker")),
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:night_prowler"))
                                ),
                                List.of(() -> AllItems.CELESTIAL_DEBRIS),
                                SDBags.LEGENDARY_GEAR::getStack
                        )
                )
                .addEnFeedback("Day and night shift, and the Gatekeepers are reduced to dust and ash. The invisible barrier blasts open and the embers scatter into the void, while the end is still far away.")
                .addZhFeedback("昼夜变换，守门人化作尘灰。隐形的屏障轰然洞开，余烬散入虚空，而末路仍在远方。")
                .toEntry("boss/day_night")
                .addTag(AllTags.MAIN)
                .addDependencies(
                        KillT2Quests.KILL_BOMD_LICH,
                        KillT2Quests.KILL_VOID_BLOSSOM,
                        KillT3Quests.KILL_CHAOS_MONARCH,
                        KillT3Quests.KILL_MOON_KNIGHT,
                        KillT3Quests.KILL_RETURNING_KNIGHT,
                        KillT4Quests.KILL_DECAYING_KING,
                        KillT3Quests.KILL_CAPTAIN_CORNELIA,
                        KillT3Quests.KILL_BLACKSTONE_GOLEM
                )
                .register();
        KILL_ENDER_DRAGON = QuestBuilder.of(
                        "kill_ender_dragon",
                        new EnderDragonAdvancementQuest(
                                SDBags.LEGENDARY_MATERIAL::getStack
                        )
                )
                .addEnDesc("Defeat Ender Dragon")
                .addZhDesc("击杀末影龙")
                .addEnFeedback("When the last touch of the dragon's breath dissipated in the void, the End once again fell into silence. The fragments of the stars fell like teardrops, the remnants of the crystal's ringing still echoed among the obsidian pillars, and the dragon that had once coiled in the rift between time and space had now turned into a drifting purple mist. The final chapter of this epic was but a prelude to the next journey.")
                .addZhFeedback("当最后一抹龙息在虚空中消散，终末之地再次陷入寂静。星辰的碎片如泪滴般坠落，水晶的残响仍在黑曜石柱间回荡，而那曾盘踞时空裂隙的巨龙，此刻已化作飘散的紫雾。这场史诗的终章，不过是下一征程的序曲。")
                .toEntry("boss/dragon")
                .addTag(AllTags.MAIN)
                .addDependencies(KILL_DAY_NIGHT)
                .register();
        KILL_THE_EYE = QuestBuilder.of(
                        "kill_the_eye",
                        new SimpleLootItemQuest(
                                () -> EntityInit.THE_EYE,
                                () -> ItemInit.PRIME_EYE,
                                SDBags.LEGENDARY_BOOK::getStack
                        )
                )
                .addEnFeedback("When the giant crystalline lens shatters, the entrance to the deep void opens. You drift through this boundless void as if immersed in a never-ending dream.")
                .addZhFeedback("当巨大的晶状体碎裂，通往深邃虚无的入口随之开启。你在这无边的虚空中漂游，宛如沉浸于一个永无止境的幻梦之中。")
                .toEntry("boss/the_eye")
                .addTag(AllTags.MAIN)
                .addDependencies(KILL_ENDER_DRAGON)
                .register();
    }
}
