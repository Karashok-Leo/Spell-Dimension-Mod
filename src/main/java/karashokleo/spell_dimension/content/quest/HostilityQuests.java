package karashokleo.spell_dimension.content.quest;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.content.quest.base.SimpleItemQuest;
import karashokleo.spell_dimension.content.quest.base.SimpleTagIngredientQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllTags;

public class HostilityQuests
{
    public static SimpleItemQuest CHAOS_INGOT;
    public static SimpleItemQuest ODDEYES_GLASSES;
    public static SimpleItemQuest TRIPLE_STRIP_CAPE;
    public static SimpleItemQuest CURSE_ENVY;
    public static SimpleTagIngredientQuest TRAIT_ITEM;
    public static SimpleTagIngredientQuest HOSTILITY_CURSE;
    public static SimpleTagIngredientQuest HOSTILITY_RING;
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
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(BaseQuests.HOSTILITY_ORB)
                .register();
        ODDEYES_GLASSES = QuestBuilder.of(
                        "oddeyes_glasses",
                        new SimpleItemQuest(
                                () -> TrinketItems.ODDEYES_GLASSES,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        TRIPLE_STRIP_CAPE = QuestBuilder.of(
                        "triple_strip_cape",
                        new SimpleItemQuest(
                                () -> TrinketItems.TRIPLE_STRIP_CAPE,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        CURSE_ENVY = QuestBuilder.of(
                        "curse_envy",
                        new SimpleItemQuest(
                                () -> TrinketItems.CURSE_ENVY,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        TRAIT_ITEM = QuestBuilder.of(
                        "trait_item",
                        new SimpleTagIngredientQuest(
                                LHTags.TRAIT_ITEM,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addEnDesc("Obtain any Hostility Trait")
                .addZhDesc("获得任意恶意词条")
                .toEntry("power/trait")
                .addDependencies(CURSE_ENVY)
                .register();
        HOSTILITY_CURSE = QuestBuilder.of(
                        "hostility_curse",
                        new SimpleTagIngredientQuest(
                                AllTags.HOSTILITY_CURSE,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addEnDesc("Obtain any Hostility Curse")
                .addZhDesc("获得任意恶意诅咒")
                .toEntry("resource/curse")
                .addDependencies(TRAIT_ITEM)
                .register();
        HOSTILITY_RING = QuestBuilder.of(
                        "hostility_ring",
                        new SimpleTagIngredientQuest(
                                AllTags.HOSTILITY_RING,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addEnDesc("Obtain any Hostility Ring")
                .addZhDesc("获得任意恶意戒指")
                .addDependencies(TRAIT_ITEM)
                .register();
        INFINITY_GLOVE = QuestBuilder.of(
                        "infinity_glove",
                        new SimpleItemQuest(
                                () -> TrinketItems.INFINITY_GLOVE,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(TRAIT_ITEM)
                .register();
        MIRACLE_INGOT = QuestBuilder.of(
                        "miracle_ingot",
                        new SimpleItemQuest(
                                () -> MiscItems.MIRACLE.ingot(),
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(CHAOS_INGOT)
                .register();
        DIVINITY_CROSS = QuestBuilder.of(
                        "divinity_cross",
                        new SimpleItemQuest(
                                () -> TrinketItems.DIVINITY_CROSS,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(MIRACLE_INGOT, TRAIT_ITEM)
                .register();
        DIVINITY_LIGHT = QuestBuilder.of(
                        "divinity_light",
                        new SimpleItemQuest(
                                () -> TrinketItems.DIVINITY_LIGHT,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(MIRACLE_INGOT, TRAIT_ITEM)
                .register();
        ETERNIUM_INGOT = QuestBuilder.of(
                        "eternium_ingot",
                        new SimpleItemQuest(
                                () -> ComplementItems.ETERNIUM.ingot(),
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(MIRACLE_INGOT)
                .register();
        RESTORATION = QuestBuilder.of(
                        "restoration",
                        new SimpleItemQuest(
                                () -> TrinketItems.RESTORATION,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT, TRAIT_ITEM)
                .register();
        ABYSSAL_THORN = QuestBuilder.of(
                        "abyssal_thorn",
                        new SimpleItemQuest(
                                () -> TrinketItems.ABYSSAL_THORN,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT)
                .register();
        ABRAHADABRA = QuestBuilder.of(
                        "abrahadabra",
                        new SimpleItemQuest(
                                () -> TrinketItems.ABRAHADABRA,
                                SDBags.JEWELRY_RINGS::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT, TRAIT_ITEM)
                .register();
        NIDHOGGUR = QuestBuilder.of(
                        "nidhoggur",
                        new SimpleItemQuest(
                                () -> TrinketItems.NIDHOGGUR,
                                SDBags.JEWELRY_NECKLACES::getStack
                        )
                )
                .addDependencies(ETERNIUM_INGOT, TRAIT_ITEM)
                .register();
    }
}
