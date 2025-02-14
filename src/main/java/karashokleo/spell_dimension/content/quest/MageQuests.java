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
                .toEntry("mage/essence")
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
                .toEntry("mage/rune")
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
                .toEntry("mage/enchant")
                .addDependencies(BASE_ESSENCE)
                .register();
        SPELL_BOOK_0 = QuestBuilder.of(
                        "spell_book_0",
                        new SimpleTagIngredientQuest(
                                AllTags.BOOK.get(0),
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .addEnDesc("Craft your Apprentice Spell Book into a Primary Spell Book")
                .addZhDesc("将学徒法术书合成为初级法术书")
                .toEntry("mage/book")
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
                .toEntry("mage/book")
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
                .toEntry("mage/book")
                .addDependencies(SPELL_BOOK_1)
                .register();
        SPELL_POWER_0 = QuestBuilder.of(
                        "spell_power_0",
                        new SpellPowerQuest(
                                DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE[0],
                                SDBags.UNCOMMON_MATERIAL::getStack,
                                false
                        )
                )
                .toEntry("mage/power")
                .addDependencies(BaseQuests.CHOOSE_PATH)
                .register();
        SPELL_POWER_1 = QuestBuilder.of(
                        "spell_power_1",
                        new SpellPowerQuest(
                                DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE[1],
                                SDBags.RARE_MATERIAL::getStack,
                                false
                        )
                )
                .toEntry("mage/power")
                .addDependencies(SPELL_POWER_0)
                .register();
        SPELL_POWER_2 = QuestBuilder.of(
                        "spell_power_2",
                        new SpellPowerQuest(
                                DynamicSpellBookItem.REQUIREMENT_SPELL_POWER_PER_GRADE[2],
                                SDBags.EPIC_MATERIAL::getStack,
                                false
                        )
                )
                .toEntry("mage/power")
                .addDependencies(SPELL_POWER_1)
                .register();
        SPELL_POWER_3 = QuestBuilder.of(
                        "spell_power_3",
                        new SpellPowerQuest(
                                2048,
                                SDBags.LEGENDARY_MATERIAL::getStack,
                                true
                        )
                )
                .addEnTitle("Ancient Ones")
                .addZhTitle("旧日支配者")
                .toEntry("mage/power")
                .addDependencies(SPELL_POWER_2)
                .register();
        SPELL_POWER_4 = QuestBuilder.of(
                        "spell_power_4",
                        new SpellPowerQuest(
                                65536,
                                SDBags.LEGENDARY_MATERIAL::getStack,
                                true
                        )
                )
                .addEnTitle("Ultimate Limit")
                .addZhTitle("最终极限")
                .toEntry("mage/power")
                .addDependencies(SPELL_POWER_3)
                .register();
    }
}
