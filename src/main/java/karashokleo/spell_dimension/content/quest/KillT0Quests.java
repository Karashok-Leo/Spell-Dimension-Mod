package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.content.quest.special.FinalFightQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;

public class KillT0Quests
{
    public static SimpleLootItemQuest KILL_OBSIDLITH;
    public static FinalFightQuest KILL_VOID_SHADOW;

    public static void register()
    {
        KILL_OBSIDLITH = QuestBuilder.of(
                "kill_obsidilith",
                new SimpleLootItemQuest(
                    "bosses_of_mass_destruction:obsidilith",
                    "bosses_of_mass_destruction:obsidian_heart",
                    SDBags.LEGENDARY_MATERIAL::getStack
                )
            )
            .addEnFeedback("The dark purple runes were gradually extinguished from the base upwards, and the Obsidilith had condensed into a pitch-black mirror of absolute stillness, reflecting the gasping figure of the Conqueror like a gigantic tombstone.")
            .addZhFeedback("暗紫色符文自基座向上渐次熄灭，巨石柱已凝成绝对静止的漆黑镜面，倒映着征服者喘息的身影，宛如一个巨大的墓碑。")
            .toEntry("boss/obsidilith")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT1Quests.KILL_ENDER_DRAGON)
            .register();
        KILL_VOID_SHADOW = QuestBuilder.of(
                "kill_void_shadow",
                new FinalFightQuest(
                    () -> EntityInit.VOID_SHADOW,
                    () -> ItemInit.SOURCE_STONE,
                    AllItems.MEDAL::getDefaultStack
                )
            )
            .addEnTitle("The Final Fight")
            .addZhTitle("最终之战")
            .addEnFeedback("As the shadows dissipate and the squire is disillusioned, you find yourself no longer able to take a step deeper into the void. The island-like platform is suspended in the endless void. Now you can leave at any time, so what is your choice?")
            .addZhFeedback("当阴影消散，侍从幻灭，你发现自己再也无法深入虚空一步。宛如孤岛般的平台悬浮在无尽的虚空中。现在你随时可以离开，那么你的选择是什么？")
            .toEntry("boss/void_shadow")
            .addTag(AllTags.MAIN, AllTags.END)
            .addDependencies(KillT1Quests.KILL_THE_EYE)
            .register();
    }
}
