package karashokleo.spell_dimension.content.quest;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.spell_dimension.content.quest.base.SimpleItemQuest;
import karashokleo.spell_dimension.content.quest.base.SimpleTagIngredientQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllTags;

public class HostilityQuests
{
    public static SimpleItemQuest CHAOS_INGOT;
    public static SimpleTagIngredientQuest HOSTILITY_CURSE;
    public static SimpleTagIngredientQuest HOSTILITY_RING;
    public static SimpleItemQuest ODDEYES_GLASSES;
    public static SimpleItemQuest TRIPLE_STRIP_CAPE;
    public static SimpleItemQuest INFINITY_GLOVE;
    public static SimpleItemQuest MIRACLE_INGOT;
    public static SimpleItemQuest DIVINITY_CROSS;
    public static SimpleItemQuest DIVINITY_LIGHT;
    public static SimpleItemQuest ETERNIUM_INGOT;
    public static SimpleItemQuest RESTORATION;
    public static SimpleItemQuest ABYSSAL_THORN;
    public static SimpleItemQuest ABRAHADABRA;
    public static SimpleItemQuest NIDHOGGUR;

    public static void register()
    {
        CHAOS_INGOT = QuestBuilder.of(
                        "chaos_ingot",
                        new SimpleItemQuest(
                                () -> MiscItems.CHAOS.ingot(),
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(BaseQuests.HOSTILITY_ORB)
                .register();
        HOSTILITY_CURSE = QuestBuilder.of(
                        "hostility_curse",
                        new SimpleTagIngredientQuest(
                                AllTags.HOSTILITY_CURSE,
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addEnDesc("Obtain any Hostility Curse")
                .addZhDesc("获得任意恶意诅咒")
                .toEntry("resource/curse")
                .addDependencies(CHAOS_INGOT)
                .register();
        HOSTILITY_RING = QuestBuilder.of(
                        "hostility_ring",
                        new SimpleTagIngredientQuest(
                                AllTags.HOSTILITY_RING,
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addEnDesc("Obtain any Hostility Ring")
                .addZhDesc("获得任意恶意戒指")
                .addDependencies(CHAOS_INGOT)
                .register();
        ODDEYES_GLASSES = QuestBuilder.of(
                        "oddeyes_glasses",
                        new SimpleItemQuest(
                                () -> TrinketItems.ODDEYES_GLASSES,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        TRIPLE_STRIP_CAPE = QuestBuilder.of(
                        "triple_strip_cape",
                        new SimpleItemQuest(
                                () -> TrinketItems.TRIPLE_STRIP_CAPE,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        INFINITY_GLOVE = QuestBuilder.of(
                        "infinity_glove",
                        new SimpleItemQuest(
                                () -> TrinketItems.INFINITY_GLOVE,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        MIRACLE_INGOT = QuestBuilder.of(
                        "miracle_ingot",
                        new SimpleItemQuest(
                                () -> MiscItems.MIRACLE.ingot(),
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        DIVINITY_CROSS = QuestBuilder.of(
                        "divinity_cross",
                        new SimpleItemQuest(
                                () -> TrinketItems.DIVINITY_CROSS,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(MIRACLE_INGOT)
                .register();
        DIVINITY_LIGHT = QuestBuilder.of(
                        "divinity_light",
                        new SimpleItemQuest(
                                () -> TrinketItems.DIVINITY_LIGHT,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(MIRACLE_INGOT)
                .register();
        ETERNIUM_INGOT = QuestBuilder.of(
                        "eternium_ingot",
                        new SimpleItemQuest(
                                () -> ComplementItems.ETERNIUM.ingot(),
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addDependencies(MIRACLE_INGOT)
                .register();
        RESTORATION = QuestBuilder.of(
                        "restoration",
                        new SimpleItemQuest(
                                () -> TrinketItems.RESTORATION,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT)
                .register();
        ABYSSAL_THORN = QuestBuilder.of(
                        "abyssal_thorn",
                        new SimpleItemQuest(
                                () -> TrinketItems.ABYSSAL_THORN,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT)
                .register();
        ABRAHADABRA = QuestBuilder.of(
                        "abrahadabra",
                        new SimpleItemQuest(
                                () -> TrinketItems.ABRAHADABRA,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT)
                .register();
        NIDHOGGUR = QuestBuilder.of(
                        "nidhoggur",
                        new SimpleItemQuest(
                                () -> TrinketItems.NIDHOGGUR,
                                SDBags.EPIC_GEAR::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT)
                .register();
    }
}
