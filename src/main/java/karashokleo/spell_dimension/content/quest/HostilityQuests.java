package karashokleo.spell_dimension.content.quest;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.content.quest.base.SimpleItemQuest;
import karashokleo.spell_dimension.content.quest.base.SimpleTagIngredientQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
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
    public static SimpleItemQuest ABYSSAL_THRONE;
    public static SimpleItemQuest ABRAHADABRA;
    public static SimpleItemQuest NIDHOGGUR;
    public static SimpleItemQuest MIRAGE_REFLECTOR;

    public static void register()
    {
        CHAOS_INGOT = QuestBuilder.of(
                "chaos_ingot",
                new SimpleItemQuest(
                    () -> MiscItems.CHAOS.ingot(),
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnFeedback("Special materials filled with chaotic power seem to be substances created due to otherworldly conflicts.")
            .addZhFeedback("充斥着混沌力量的特殊材料，似乎是由于异界冲突而产生的物质。")
            .addTag(AllTags.MAIN)
            .addDependencies(BaseQuests.HOSTILITY_ORB)
            .register();
        ODDEYES_GLASSES = QuestBuilder.of(
                "oddeyes_glasses",
                new SimpleItemQuest(
                    () -> TrinketItems.ODDEYES_GLASSES,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("Odd glasses made using Chaos Ingots. Doesn't seem to be able to wear two at once though.")
            .addZhFeedback("使用混沌锭制作的奇特眼镜。不过似乎无法同时佩戴两个。")
            .addTag(AllTags.BRANCH)
            .addDependencies(CHAOS_INGOT)
            .register();
        TRIPLE_STRIP_CAPE = QuestBuilder.of(
                "triple_strip_cape",
                new SimpleItemQuest(
                    () -> TrinketItems.TRIPLE_STRIP_CAPE,
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnFeedback("An tricolor cape made with Chaos Ingots. Doesn't seem to be able to wear two at once though.")
            .addZhFeedback("使用混沌锭制作的三色披风。不过似乎无法同时佩戴两个。")
            .addTag(AllTags.BRANCH)
            .addDependencies(CHAOS_INGOT)
            .register();
        CURSE_ENVY = QuestBuilder.of(
                "curse_envy",
                new SimpleItemQuest(
                    () -> TrinketItems.CURSE_ENVY,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("The otherworldly conflict has made the monsters stronger and stronger, and with the Chaos Ingot at its core you crafted this curse, which allows the powers the monsters possess to drop off in a special form. Are you envious of the power those monsters possess, or is the world envious of you?")
            .addZhFeedback("异界的冲突已经让怪物变得越来越强了，以混沌锭为核心你制作了这个诅咒，它可以让怪物拥有的力量以特殊形式掉落。究竟是你在嫉妒那些怪物所拥有的力量，还是这个世界在嫉妒你？")
            .addTag(AllTags.MAIN)
            .addDependencies(CHAOS_INGOT)
            .register();
        TRAIT_ITEM = QuestBuilder.of(
                "trait_item",
                new SimpleTagIngredientQuest(
                    LHTags.TRAIT_ITEM,
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnDesc("Obtain any Hostility Trait")
            .addZhDesc("获得任意恶意词条")
            .addEnFeedback("The good news is that you made it, the bad news is that you can't use them directly. It looks like you'll need to do something with them.")
            .addZhFeedback("好消息是，你成功了，坏消息是你无法直接使用它们。看来你需要对它们进行一些处理。")
            .toEntry("mechanic/hostility")
            .addTag(AllTags.MAIN)
            .addDependencies(CURSE_ENVY)
            .register();
        HOSTILITY_CURSE = QuestBuilder.of(
                "hostility_curse",
                new SimpleTagIngredientQuest(
                    AllTags.HOSTILITY_CURSE,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnDesc("Obtain any Hostility Curse")
            .addZhDesc("获得任意恶意诅咒")
            .addEnFeedback("You summoned the courage to craft more curses, and they granted you greater power, but at what cost?")
            .addZhFeedback("你鼓起勇气制作了更多的诅咒，它们赋予了你更强大的力量，但代价是什么呢？")
            .toEntry("mechanic/curse")
            .addTag(AllTags.BRANCH)
            .addDependencies(TRAIT_ITEM)
            .register();
        HOSTILITY_RING = QuestBuilder.of(
                "hostility_ring",
                new SimpleTagIngredientQuest(
                    AllTags.HOSTILITY_RING,
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnDesc("Obtain any Hostility Ring")
            .addZhDesc("获得任意恶意戒指")
            .addEnFeedback("Using the Chaos Ingot as a centerpiece with those hostility traits, you've managed to craft rings with varying effects. But you don't seem to have room for more rings... There really isn't?")
            .addZhFeedback("以混沌锭为核心搭配那些恶意词条，你成功制作出了效果各异的戒指。但你似乎没有余力容纳更多的戒指了...真的没有吗？")
            .addTag(AllTags.BRANCH)
            .addDependencies(TRAIT_ITEM)
            .register();
        INFINITY_GLOVE = QuestBuilder.of(
                "infinity_glove",
                new SimpleItemQuest(
                    () -> TrinketItems.INFINITY_GLOVE,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("A fanciful glove crafted using Chaos Ingots, which allows you to wear more rings on one hand at a time. Doesn't seem to be able to wear two at once though.")
            .addZhFeedback("使用混沌锭制作的奇特手套，它可以让你的一只手同时容纳更多戒指。不过似乎无法同时佩戴两个。")
            .addTag(AllTags.BRANCH)
            .addDependencies(TRAIT_ITEM)
            .register();
        MIRACLE_INGOT = QuestBuilder.of(
                "miracle_ingot",
                new SimpleItemQuest(
                    () -> MiscItems.MIRACLE.ingot(),
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnFeedback("A powerful material crafted using Chaos Ingots, Hostility Essence, and Miracle Powder, it is the product of an otherworldly fusion stabilized with seemingly unparalleled potential.")
            .addZhFeedback("使用混沌锭、恶意精华和奇迹粉末制作的强大材料，它是异界融合后稳定的产物，似乎有着无与伦比的潜力。")
            .addTag(AllTags.MAIN)
            .addDependencies(CHAOS_INGOT)
            .register();
        DIVINITY_CROSS = QuestBuilder.of(
                "divinity_cross",
                new SimpleItemQuest(
                    () -> TrinketItems.DIVINITY_CROSS,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("A peculiar trinket crafted using a Miracle Ingot as its core, it attenuates the weakening of yourself by Cleanse effects, though it only works on lower level beneficial effects.")
            .addZhFeedback("使用奇迹锭为核心制作的奇特饰品，它可以减弱净化效果对你自己的削弱，不过只能对较低级的增益效果起作用。")
            .addTag(AllTags.BRANCH)
            .addDependencies(MIRACLE_INGOT, TRAIT_ITEM)
            .register();
        DIVINITY_LIGHT = QuestBuilder.of(
                "divinity_light",
                new SimpleItemQuest(
                    () -> TrinketItems.DIVINITY_LIGHT,
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnFeedback("A peculiar trinket crafted using a large number of miracle ingots. It anchors you steadily to this world, the fusion of the otherworlds is suspended as a result, but the negative effects from the curse can never be counteracted.")
            .addZhFeedback("使用大量奇迹锭制作的奇特饰品。它可以将你稳定地锚定在这个世界，异界的融合因此而暂停，但诅咒产生的负面影响始终无法被抵消。")
            .addTag(AllTags.BRANCH)
            .addDependencies(MIRACLE_INGOT, TRAIT_ITEM)
            .register();
        ETERNIUM_INGOT = QuestBuilder.of(
                "eternium_ingot",
                new SimpleItemQuest(
                    () -> ComplementItems.ETERNIUM.ingot(),
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("A top-notch material born from the collision of extreme powers, you would definitely try to build a full set of armor with it if you weren't lacking in skill. It was created to make up for the lack of physical properties of Miracle Ingot, allowing you to craft some of the top items in the world.")
            .addZhFeedback("极致力量碰撞下诞生的顶级材料，如果不是技艺不足，你绝对会尝试使用它打造一整套的盔甲。它的诞生可以弥补奇迹锭物理性质上的不足，让你能够制作一些在这个世界上顶尖的物品。")
            .addTag(AllTags.MAIN)
            .addDependencies(MIRACLE_INGOT)
            .register();
        RESTORATION = QuestBuilder.of(
                "restoration",
                new SimpleItemQuest(
                    () -> TrinketItems.RESTORATION,
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnFeedback("The enemy's indefensible sealing ability gives you a headache, and sometimes you are so busy that you don't even notice if there are seals on you. So you crafted this pocket, which automatically absorbs those sealed items and automatically unseals them. But does a better method exist?")
            .addZhFeedback("敌人防不胜防的封印能力让你感到头疼，有时忙碌的你甚至没有注意到自己身上是否存在封印。于是你制作了这个口袋，它可以自动吸纳那些被封印的物品，并自动解除封印。但是否存在更好的方法呢？")
            .toEntry("mechanic/hostility", 3)
            .addTag(AllTags.BRANCH)
            .addDependencies(ETERNIUM_INGOT, TRAIT_ITEM)
            .register();
        ABYSSAL_THRONE = QuestBuilder.of(
                "abyssal_throne",
                new SimpleItemQuest(
                    () -> TrinketItems.ABYSSAL_THRONE,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("You think you must have been crazy to create it, that summoning monsters around you would become real monsters just by wearing it, which would be tantamount to suicide. But the desire for power compels you to do it.")
            .addZhFeedback("你觉得自己一定是疯了才会将它创造出来，只要佩戴着它，在你周围生成怪物将变成真正的怪物，这无异于自杀。但是对力量的渴望让你不得不这么做。")
            .addTag(AllTags.BRANCH)
            .addDependencies(ETERNIUM_INGOT)
            .register();
        ABRAHADABRA = QuestBuilder.of(
                "abrahadabra",
                new SimpleItemQuest(
                    () -> TrinketItems.ABRAHADABRA,
                    SDBags.JEWELRY$RINGS::getStack
                )
            )
            .addEnFeedback("The pinnacle of present-day craftsmanship, it is enough to reverse the laws of the world. The warp and woof of space and time are mirrored, and all the hostility will inflicted upon you will fall like a swirling starburst into the chamber of the gun that fired it... But remember, it is not invincible.")
            .addZhFeedback("堪称现世工艺的巅峰杰作，足以令万物法则倒转逆行。时空经纬皆成镜面，所有施加于你的恶意都将如同回旋的星火般，坠向发射它们的枪膛...但切记，它并非无法战胜。")
            .addTag(AllTags.MAIN)
            .addDependencies(ETERNIUM_INGOT, TRAIT_ITEM)
            .register();
        NIDHOGGUR = QuestBuilder.of(
                "nidhoggur",
                new SimpleItemQuest(
                    () -> TrinketItems.NIDHOGGUR,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("Named after the Black Dragon, the dark gold pupil hides a secret that has driven countless demon hunters mad. But beware, excessive greed will surely lead to disaster.")
            .addZhFeedback("以黑龙为名，暗金竖瞳中掩映着令无数猎魔者癫狂的秘辛。但要小心，过度的贪婪必然招致灾难。")
            .addTag(AllTags.BRANCH)
            .addDependencies(ETERNIUM_INGOT, TRAIT_ITEM)
            .register();
        MIRAGE_REFLECTOR = QuestBuilder.of(
                "mirage_reflector",
                new SimpleItemQuest(
                    () -> AllItems.MIRAGE_REFLECTOR,
                    SDBags.JEWELRY$NECKLACES::getStack
                )
            )
            .addEnFeedback("The past and the future mingle in the folds, and the border between reality and illusion blurs like melted wax. All the forces that seek to harm you will eventually become their own gravediggers in a reversal of karma. You are invincible.")
            .addZhFeedback("过去与未来在褶皱中交融，现实与幻象的边界如融化的蜡般模糊。所有企图伤害你的力量，终将在逆转的因果中成为自己的掘墓人。你无敌了。")
            .addTag(AllTags.MAIN, AllTags.CHALLENGE)
            .addDependencies(ABRAHADABRA)
            .register();
    }
}
