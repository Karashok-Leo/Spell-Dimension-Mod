package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.quest.base.SimpleItemQuest;
import karashokleo.spell_dimension.content.quest.base.SimpleTagIngredientQuest;
import karashokleo.spell_dimension.content.quest.special.SpellPowerQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;

import java.util.List;

public class MageQuests
{
    public static SimpleTagIngredientQuest BASE_ESSENCE;
    public static SimpleTagIngredientQuest RUNE;
    public static SimpleItemQuest MORE_ESSENCE;
    public static SimpleTagIngredientQuest SPELL_BOOK_0;
    public static SimpleTagIngredientQuest SPELL_BOOK_1;
    public static SimpleTagIngredientQuest SPELL_BOOK_2;
    public static SimpleTagIngredientQuest ENDGAME_TRINKETS;
    public static SpellPowerQuest SPELL_POWER_0;
    public static SpellPowerQuest SPELL_POWER_1;
    public static SpellPowerQuest SPELL_POWER_2;
    public static SpellPowerQuest SPELL_POWER_3;
    public static SpellPowerQuest SPELL_POWER_4;

    public static void register()
    {
        BASE_ESSENCE = QuestBuilder.of(
                "base_essence",
                new SimpleTagIngredientQuest(
                    AllTags.ESSENCE_ALL,
                    SDBags.COMMON_MATERIAL::getStack
                )
            )
            .addEnDesc("Obtain any basic magic essence")
            .addZhDesc("获取任意基础魔法精华")
            .addEnFeedback("Strange substances that appear after magic acts on a target. Regardless of the principle, these essences are crystals of magic that correspond to spells, and you can use them to strengthen yourself.")
            .addZhFeedback("魔法作用于目标后出现的奇特物质。不管原理如何，这些精华都是对应法术的魔力结晶，你可以用它们来强化自己。")
            .toEntry("mage/essence")
            .addTag(AllTags.MAIN)
            .addDependencies(BaseQuests.CHOOSE_PATH)
            .register();
        RUNE = QuestBuilder.of(
                "rune",
                new SimpleTagIngredientQuest(
                    AllTags.RUNE,
                    SDBags.UNCOMMON_MATERIAL::getStack
                )
            )
            .addEnDesc("Use basic magic essence to craft runes at the rune altar")
            .addZhDesc("使用基础魔法精华在符文祭坛上合成符文")
            .addEnFeedback("Trying to cast spells through implements often requires the consumption of additional energy. Crafting runes directly however creates a lot of waste, so perhaps resorting to an Rune Altar is a good option.")
            .addZhFeedback("想要通过器具施展法术往往需要消耗额外的能源。然而直接制作符文会产生许多浪费，也许借助符文制作祭坛是一个不错的选择。")
            .toEntry("mage/rune")
            .addTag(AllTags.MAIN)
            .addDependencies(BASE_ESSENCE)
            .register();
        MORE_ESSENCE = QuestBuilder.of(
                "more_essence",
                new SimpleItemQuest(
                    List.of(
                        () -> AllItems.ENLIGHTENING_ESSENCE,
                        () -> AllItems.ENCHANTED_ESSENCE,
                        () -> AllItems.MENDING_ESSENCE
                    ),
                    SDBags.RARE_MATERIAL::getStack
                )
            )
            .addEnFeedback("All three of the essences you gain in your adventures are undoubtedly very powerful. The Enlightening Essence directly enhances your spell power, the Enchanted Essence brings magic together in your equipment, and the Mending Essence repairs your equipment until it is as good as new.")
            .addZhFeedback("你在冒险中获得的这三种精华无疑都十分强大。源启精华可以直接增强魔力，束魔精华可以将魔力汇聚在装备上，而修复精华则可以将装备修复至完好如初。")
            .toEntry("mage/enchant")
            .addTag(AllTags.MAIN)
            .addDependencies(BASE_ESSENCE)
            .register();
        SPELL_BOOK_0 = QuestBuilder.of(
                "spell_book_0",
                new SimpleTagIngredientQuest(
                    AllTags.BOOK.get(0),
                    SDBags.RARE_GEAR::getStack
                )
            )
            .addEnDesc("Craft your Apprentice Spell Book which was crafted through the Spellbinding Table into a Primary Spell Book")
            .addZhDesc("将通过法术绑定台制作的学徒法术书合成为初级法术书")
            .addEnFeedback("Spell Book obtained by simple upgrades using primary materials have the ability to hold spell scrolls and provide you with basic enhancements.")
            .addZhFeedback("使用初级材料简单升级得到的法术书，具备容纳法术卷轴的功能，并能为你提供基本的增强效果。")
            .toEntry("mage/book")
            .addTag(AllTags.MAIN)
            .addDependencies(BaseQuests.CHOOSE_PATH)
            .register();
        SPELL_BOOK_1 = QuestBuilder.of(
                "spell_book_1",
                new SimpleTagIngredientQuest(
                    AllTags.BOOK.get(1),
                    SDBags.EPIC_GEAR::getStack
                )
            )
            .addEnDesc("Craft your Primary Spell Book into a Intermediate Spell Book")
            .addZhDesc("将初级法术书合成为中级法术书")
            .addEnFeedback("Spell Book obtained by upgrading with rare materials can hold more spell scrolls and have more significant gain effects.")
            .addZhFeedback("使用稀有材料升级得到的法术书，可以容纳更多的法术卷轴，增益效果也更加显著。")
            .toEntry("mage/book")
            .addTag(AllTags.MAIN)
            .addDependencies(SPELL_BOOK_0)
            .register();
        SPELL_BOOK_2 = QuestBuilder.of(
                "spell_book_2",
                new SimpleTagIngredientQuest(
                    AllTags.BOOK.get(2),
                    SDBags.LEGENDARY_GEAR::getStack
                )
            )
            .addEnDesc("Craft your Intermediate Spell Book into a Advanced Spell Book")
            .addZhDesc("将中级法术书合成为高级法术书")
            .addEnFeedback("Crafted using top quality materials and skillful craftsmanship, this Spell Book provides you with a powerful increase in spells. Unfortunately, this is the final form of spell books.")
            .addZhFeedback("使用顶级材料与精湛技艺打造的法术书，为你提供强大的法术增幅。可惜这就是法术书的最终形态了。")
            .toEntry("mage/book")
            .addTag(AllTags.MAIN)
            .addDependencies(SPELL_BOOK_1)
            .register();
        ENDGAME_TRINKETS = QuestBuilder.of(
                "endgame_trinkets",
                new SimpleTagIngredientQuest(
                    AllTags.ENDGAME_TRINKETS,
                    SDBags.JEWELRY_NECKLACES::getStack
                )
            )
            .addEnDesc("Craft trinkets that carry special spell effects")
            .addZhDesc("合成携带法术特效的饰品")
            .addEnFeedback("The spell chanted softly, shimmering and misty, revealing deep and unknowable magical potential.")
            .addZhFeedback("咒语轻吟，微光闪烁，迷雾环绕，透露出深邃而不可知的魔力潜能。")
            .toEntry("power/endgame_trinket")
            .addTag(AllTags.MAIN)
            .addDependencies(SPELL_BOOK_2)
            .register();
        SPELL_POWER_0 = QuestBuilder.of(
                "spell_power_0",
                new SpellPowerQuest(
                    DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE[0],
                    SDBags.UNCOMMON_MATERIAL::getStack
                )
            )
            .addEnFeedback("You have enhanced the magic you can manipulate through various methods. At the same time, your understanding of the world has grown.")
            .addZhFeedback("你已经通过各种方法增强了自己所能操控的魔力。与此同时，你对这个世界的了解也日益加深。")
            .toEntry("mage/power")
            .addTag(AllTags.MAIN)
            .addDependencies(BaseQuests.CHOOSE_PATH)
            .register();
        SPELL_POWER_1 = QuestBuilder.of(
                "spell_power_1",
                new SpellPowerQuest(
                    DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE[1],
                    SDBags.RARE_MATERIAL::getStack
                )
            )
            .addEnFeedback("You've reached a new level of proficiency in magic, and you're going to start going your own way next. Be careful though, they are learning your magic.")
            .addZhFeedback("你对魔法的精通已经到达了一个新境界，接下来你要开始走自己的路了。不过小心，它们正在学习你的魔法。")
            .toEntry("mage/power")
            .addTag(AllTags.MAIN)
            .addDependencies(SPELL_POWER_0)
            .register();
        SPELL_POWER_2 = QuestBuilder.of(
                "spell_power_2",
                new SpellPowerQuest(
                    DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE[2],
                    SDBags.EPIC_MATERIAL::getStack
                )
            )
            .addEnFeedback("You are already powerful enough to fight against the top powerhouses of this world. But you are not slacking off, because you know that stagnation is self-destruction.")
            .addZhFeedback("你的力量已然足以抗衡这个世界的顶级强者。但你毫无懈怠，因为你深知，停滞不前即是自取灭亡。")
            .toEntry("mage/power")
            .addTag(AllTags.MAIN)
            .addDependencies(SPELL_POWER_1)
            .register();
        SPELL_POWER_3 = QuestBuilder.of(
                "spell_power_3",
                new SpellPowerQuest(
                    2048,
                    SDBags.LEGENDARY_MATERIAL::getStack
                )
            )
            .addEnTitle("Ancient Ones")
            .addZhTitle("旧日支配者")
            .addEnFeedback("The total amount of your magical power has broken through a certain boundary that belonged to the extremes of the olden times. You realize that your knowledge of the world has jumped to a new realm, and the world reveals to you its mysterious name: Spell Dimension.")
            .addZhFeedback("你的魔力总量已经突破了某个界限，那是属于旧时代的极致。你意识到自己对世界的认知跃升到了一个新境界，这个世界向你揭示了它的神秘之名——咒次元。")
            .toEntry("mage/power")
            .addTag(AllTags.BRANCH, AllTags.CHALLENGE)
            .addDependencies(SPELL_POWER_2)
            .register();
        SPELL_POWER_4 = QuestBuilder.of(
                "spell_power_4",
                new SpellPowerQuest(
                    65536,
                    SDBags.LEGENDARY_MATERIAL::getStack
                )
            )
            .addEnTitle("Ultimate Limit")
            .addZhTitle("最终极限")
            .addEnFeedback("You don't know how long you have stayed. Since defeating Him you have been obsessed with improving yourself. Until today when you finally mastered the power that tells you this is the limit of the Creator's preset limits. You realize it's time for you to go home. You are once again in a funk, just as you were when you first came here.")
            .addZhFeedback("你也不知道自己待了多久。自从战胜了祂后你就沉迷于提升自己。直到今天你终于掌握了这份力量，它告诉你这是缔造者预设的极限。你明白自己是时候回家了。你又一次陷入了迷茫，正如你第一次来到这里一样。")
            .toEntry("mage/power")
            .addTag(AllTags.BRANCH, AllTags.CHALLENGE)
            .addDependencies(SPELL_POWER_3)
            .register();
    }
}
