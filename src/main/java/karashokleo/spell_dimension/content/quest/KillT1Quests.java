package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.content.quest.special.EnderDragonAdvancementQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
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
                .toEntry("boss/day_night")
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
                .toEntry("boss/dragon")
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
                .toEntry("boss/the_eye")
                .addDependencies(KILL_ENDER_DRAGON)
                .register();
    }
}
