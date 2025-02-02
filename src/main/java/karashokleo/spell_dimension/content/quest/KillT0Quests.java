package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.content.quest.special.FinalFightQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
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
                .toEntry("boss/obsidilith")
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
                .toEntry("boss/void_shadow")
                .addDependencies(KillT1Quests.KILL_THE_EYE)
                .register();
    }
}
