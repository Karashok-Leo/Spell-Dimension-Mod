package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.QuestTag;
import karashokleo.spell_dimension.content.effect.NirvanaEffect;
import karashokleo.spell_dimension.content.item.upgrade.IllusionUpgradeTab;
import karashokleo.spell_dimension.content.object.EventAward;
import karashokleo.spell_dimension.data.generic.recipe.SDLocateRecipes;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.data.loot_bag.SDContents;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.Locale;

public enum SDTexts
{
    /**
     * Text
     */
    TEXT$MAGE("Mage", "法师"),
    TEXT$SPELL_BOOK("%s %s Spell Book", "%s%s法术书"),
    TEXT$SPELL_SCROLL("Spell Scroll", "法术卷轴"),
    TEXT$ESSENCE("%s %s Essence", "%s%s精华"),
    TEXT$ESSENCE$BASE("Base Essence", "基础精华"),
    TEXT$ESSENCE$ENCHANTED("Enchanted Essence", "束魔精华"),
    TEXT$ESSENCE$ENLIGHTENING("Enlightening Essence", "源启精华"),
    TEXT$ESSENCE$SUCCESS("Successful use of essence!", "精华使用成功!"),
    TEXT$ESSENCE$FAIL("Failed to use essence!", "精华使用失败!"),
    TEXT$INCOMPATIBLE_SCHOOL("Incompatible spell school!", "不兼容的法术学派!"),
    TEXT$HIGHER_BOOK_REQUIRED("A higher level Spell Book is required to hold the spell!", "需要更高级的法术书才能收纳该法术!"),
    TEXT$SKILLED_SCHOOL("This is not the spell school you are skilled in!", "这不是你擅长的法术学派!"),
    TEXT$BLANK_BOOK("Blank spell book!", "空白法术书!"),
    TEXT$SPELL_POWER_INFO("Spell Power Information", "魔法信息"),
    TEXT$ABYSS_GUARD("You need to have Abyss Guard in your inventory to sound the shell horn!", "你需要在物品栏中持有深渊守护才能吹响号角！"),
    TEXT$END_STAGE("End Stage Unlocked!", "终末阶段解锁！"),
    TEXT$END_STAGE$LOCK("You need to use %s to unlock the End Stage to enter the End!", "你需要使用%s解锁终末阶段才能进入末地！"),
    TEXT$END_STAGE$BOOK_EVERYTHING("You need to use %s to unlock the End Stage to use Book of Omniscience!", "你需要使用%s解锁终末阶段才能使用全知之书！"),
    TEXT$PROGRESS_ROLLBACK("Progress has been rolled back!", "进度已回退！"),
    TEXT$SPELL_TRAIT$POWER("Monsters gain %s spell power of the corresponding spell school.", "怪物获得%s点对应学派的法术强度"),
    TEXT$SPELL_TRAIT$INTERVAL("Every %s seconds, cast spell on target.", "每隔%s秒，对目标施放法术"),
    TEXT$SPELL_TRAIT$DESCRIPTION("Spell Description: %s", "法术描述：%s"),
    TEXT$SPELL_TRAIT$ACTION("%s is casting [%s]", "%s 正在施法 [%s]"),

    /**
     * Quest
     */
    TEXT$QUEST$COMPLETE("Quest completed!", "任务完成！"),
    TEXT$QUEST$SKIP("Quest skipped!", "已跳过该任务！"),
    TEXT$QUEST$COMPLETED("The quest has been completed", "该任务已完成"),
    TEXT$QUEST$ALL_COMPLETED("All quests have been completed", "所有任务已完成"),
    TEXT$QUEST$REQUIREMENT("Quest requirements not met!", "任务要求未达成！"),
    TEXT$QUEST$DEPENDENCIES("Prerequisite quests not completed!", "前置任务未完成！"),
    TEXT$QUEST$SPELL_POWER("Increase any Spell Power to %s", "将任意法术强度提升至%s"),

    TEXT$BANNED_SPELL("You cannot cast this spell in your current dimension!", "你不能在当前维度施放该法术！"),
    TEXT$INVALID_KEY_ITEM("Invalid Key Item!", "无效的索引物品！"),
    TEXT$SPELL_INFUSION("Spell Infusion", "魔力灌注"),

    /**
     * Consciousness Core
     */
    TEXT$CONSCIOUSNESS_CORE$LEVEL("Active Level: %s", "激活等级：%s"),
    TEXT$CONSCIOUSNESS_CORE$LEVEL_FACTOR("Difficulty Factor: %s", "难度系数：%s"),
    TEXT$CONSCIOUSNESS_CORE$AWARD("Award: %s", "奖励：%s"),
    TEXT$CONSCIOUSNESS_CORE$TRIGGERING("Invasion Event on going...", "入侵事件进行中..."),
    TEXT$CONSCIOUSNESS_CORE$NOT_TRIGGERED("Invasion Event not triggered", "入侵事件未触发"),
    TEXT$EVENT$PREPARE("The invasion is about to begin - Countdown: %s seconds", "入侵即将开始 - 倒计时：%s秒"),
    TEXT$EVENT$RUNNING("Wave %s/%s - Enemies Remaining: %s", "第%s/%s波 - 剩余敌人：%s"),
    TEXT$EVENT$WAITING("Wave %s/%s is about to start - Countdown: %s seconds", "第%s/%s波即将开始 - 倒计时：%s秒"),
    TEXT$EVENT$FINISH$SUCCESS("Invasion End - Successful", "入侵结束 - 成功"),
    TEXT$EVENT$FINISH$FAIL("Invasion End - Failed", "入侵结束 - 失败"),

    TEXT$DIFFICULTY$BAN("This item/action is only allowed on the %s difficulty tier", "该物品/操作仅限%s难度层级下允许使用"),
    TEXT$RANDOM_LOOT_NAME("? %s ?", "? %s ?"),
    TEXT$LOCATING("Locating: %s", "正在定位：%s"),
    TEXT$SUMMON$DISALLOW("%s can only be summoned with spell at its spawn location!", "只有在%s的生成地点才能使用魔力召唤它！"),

    /**
     * Game Over
     */
    TEXT$GAME_OVER$HEAD("To players:", "致玩家:"),
    TEXT$GAME_OVER$THANKS("Congrats on the pass, and thank you very much for traveling this far. Spell Dimension was my first modpack and by far my most heartfelt offering to the MC community.", "恭喜你通关啦！非常感谢你游玩至此。《咒次元》是我的第一个整合包，也是目前为止我献给MC社区的最用心的作品。"),
    TEXT$GAME_OVER$MAKING("I have put a lot of time and effort into this modpack since I started working on it. From developing exclusive mods for this pack, customizing loot tables, and optimizing performance, to fixing bugs and balancing difficulty, I've encountered countless challenges. Luckily and finally, I've finished it.", "自着手制作这个整合包以来，我投入了无数的时间和精力。从开发专属模组、定制战利品表、优化游戏性能，到修复各种Bug、平衡游戏难度，我遇到了一个又一个挑战。幸运的是，最终我完成了它。"),
    TEXT$GAME_OVER$PASSION("During the making process, I gradually found my old passion for Minecraft. I think I've found something - that I truly love, which seems to be a luxury for most people. I hope you too can find what you love and stick with it.", "在制作过程中，我逐渐找回了自己从前对Minecraft的热情。我想我找到了某些东西——某些我真正热爱的东西，这似乎对大多数人来说是一种奢侈。希望你也能找到你的热爱并为之坚持下去。"),
    TEXT$GAME_OVER$FEEDBACK("If you have any suggestions, please give me feedback, I would appreciate it.", "如果你有任何建议，请反馈给我，我将感激不尽。"),
    TEXT$GAME_OVER$WISH("Lastly, I wish you a happy life. Have fun playing!", "最后，祝你生活愉快。玩的开心！"),

    /**
     * Guide book
     */
    TEXT$MAGE_BOOK$1("An Introductory Grimoire Even a Three-Year-Old Can Understand", "三岁小孩也能看懂的入门魔法书"),
    TEXT$MAGE_BOOK$2("When you're in a quandary, turn to it~", "当你遇到窘境时，不妨翻开看看吧~"),

    TEXT$SOUL_BIND("You feel the connection between your soul and your body becoming stronger...", "你感觉灵魂与身体的联系变得紧密..."),
    TEXT$SOUL_UNBIND("You feel the connection between your soul and your body becoming tenuous...", "你感到灵魂与身体的联系变得脆弱..."),

    // ATI Structures translations
    TEXT$TRADER$OLDEN("Mysterious Olden", "神秘老登"),

    /**
     * Advancements
     */
    ADVANCEMENT$MEDAL$TITLE("I knocked my tooth...", "磕到牙了..."),
    ADVANCEMENT$MEDAL$DESCRIPTION("Try nibbling on a Medal", "尝试啃一口勋章"),

    /**
     * Spell School
     */
    SCHOOL$ARCANE("Arcane", "奥秘"),
    SCHOOL$FIRE("Fire", "火焰"),
    SCHOOL$FROST("Frost", "寒冰"),
    SCHOOL$HEALING("Healing", "治愈"),
    SCHOOL$LIGHTNING("Lightning", "雷电"),
    SCHOOL$SOUL("Soul", "灵魂"),

    /**
     * Grade
     */
    GRADE$0("Primary", "初级"),
    GRADE$1("Intermediate", "中级"),
    GRADE$2("Advanced", "高级"),

    /**
     * Equipment Slot
     */
    SLOT$HEAD("Slot: Head", "槽位：头盔"),
    SLOT$CHEST("Slot: Chest", "槽位：胸甲"),
    SLOT$LEGS("Slot: Legs", "槽位：护腿"),
    SLOT$FEET("Slot: Feet", "槽位：靴子"),
    SLOT$MAINHAND("Slot: Main Hand", "槽位：主手"),
    SLOT$OFFHAND("Slot: Off Hand", "槽位：副手"),

    /**
     * Difficulty Tier
     */
    DIFFICULTY_TIER$0("Normal", "普通"),
    DIFFICULTY_TIER$1("Hardcore", "硬核"),
    DIFFICULTY_TIER$2("Nightmare", "梦魇"),

    /**
     * Tooltip
     */
    TOOLTIP$INVALID("Invalid Nbt Data!", "无效的Nbt数据!"),
    TOOLTIP$DYNAMIC_BOOK$USAGE_1("Can be used as a container for spell scrolls", "可作为法术卷轴的容器"),
    TOOLTIP$DYNAMIC_BOOK$USAGE_2("Usage: In the inventory, right-click on the book with a spell scroll to put it in, or right-click on an empty slot with the book to take the scroll out.", "用法：物品栏中用法术卷轴右键该物品将其放入，或使用该物品右键空槽位将卷轴取出."),
    TOOLTIP$USE$CLICK("Usage: Right-click on other item with this item in the inventory.", "用法：物品栏中用该物品右键其他物品."),
    TOOLTIP$USE$PRESS("Usage: Main hand holding, press the right button.", "用法：主手持有时，长按右键."),
    TOOLTIP$BOOK_REQUIREMENT("Must have at least %s %s to equip", "需要拥有至少%s的%s才能装备"),
    TOOLTIP$EFFECT("Effect:", "效果:"),
    TOOLTIP$LEVEL("Enchanted Level: %s", "束魔等级：%s"),
    TOOLTIP$THRESHOLD("Threshold: %s", "阈值：%s"),
    TOOLTIP$MODIFIER("Modifier:", "属性修饰语:"),
    TOOLTIP$DISENCHANT("Remove attribute modifiers from the item.", "从物品上移除所有通过束魔精华获得的属性修饰符."),
    TOOLTIP$MENDING("Completely repair the item and eliminate the repair cost of the item.", "完全修复物品，并且清除物品的修复惩罚."),
    TOOLTIP$END_STAGE("Use to unlock the End stage.", "使用后解锁终末阶段."),
    TOOLTIP$BRACKETS("[%s]", "[%s]"),
    TOOLTIP$QUEST$TASK("Task:", "任务目标:"),
    TOOLTIP$QUEST$REWARD("Reward:", "任务奖励:"),
    TOOLTIP$QUEST$SUBMIT("Right-click to submit", "右键提交任务"),
    TOOLTIP$QUEST$SUBMIT_OR_SKIP("Right-click to submit or skip", "右键提交或跳过任务"),
    TOOLTIP$QUEST$OPEN_ENTRY("Shift + Right-click to open the related guide page", "Shift + 右键打开相关指引页面"),
    TOOLTIP$QUEST$OBTAIN_CURRENT("Right-click to obtain all current quests", "右键获取当前所有任务"),
    TOOLTIP$QUEST$VIEW_CURRENT("Right-click to view all current quests", "右键浏览当前所有任务"),
    TOOLTIP$QUEST$RESELECT("Left-click empty space to reselect the quest", "左键空白处重选任务"),
    TOOLTIP$QUEST$MUL("%s × %s", "%s × %s"),
    TOOLTIP$QUEST$AND(" and ", "和"),
    TOOLTIP$QUEST$TASK_ITEM("Obtain the following items:", "获取以下物品:"),
    TOOLTIP$QUEST$LOOT_ITEM("Defeat %s to obtain %s", "击杀%s并获取%s"),
    TOOLTIP$QUEST$TAG_ITEM("Search %s for the required item", "搜索 %s 以查询对应物品"),
    TOOLTIP$NOT_CONSUMED("Will not be consumed", "不消耗"),
    TOOLTIP$TOOK_SECONDS("Took %s second(s)", "耗时%s秒"),
    TOOLTIP$SUMMON_ENTITY("Entity Type: %s", "实体类型：%s"),
    TOOLTIP$SUMMON_REMAIN("Remain: %s", "剩余：%s"),

    /**
     * Magic Mirror
     */
    TOOLTIP$MAGIC_MIRROR$USAGE("Use to teleport to the Ocean of Consciousness", "使用后传送至识之海"),
    TOOLTIP$MAGIC_MIRROR$WARNING("Warning: After entering the Ocean of Consciousness, your game will be much more difficult, please refer to Magic Guidance for more details.", "警告：进入识之海后，你的游戏难度将大幅提升，详情请查阅《魔力接引》"),
    TOOLTIP$MAGIC_MIRROR$BROKEN("This item is a consumable", "该物品为消耗品"),

    /**
     * Spell Container & Illusion Container
     */
    TOOLTIP$CONTAINER$SPELL("Attempt to void spell traits, converting them to damage reduction amount for the corresponding spell school.", "尝试销毁法术词条，转化为对应法术学派的减伤数额"),
    TOOLTIP$CONTAINER$DAMAGE_REDUCTION("[%s] damage reduction amount: %s", "[%s] 减伤数额：%s"),
    TOOLTIP$CONTAINER$CONVERTED("Converted:", "已转化："),
    TOOLTIP$CONTAINER$CONVERTED$VALUE("- %s (+%s/%s) * %s", "- %s (+%s/%s) * %s"),
    TOOLTIP$CONTAINER$RETRIEVE("Main hand holding, right-click to retrieve all finished products", "主手持有右键取出所有成品"),
    TOOLTIP$CONTAINER$ILLUSION("Attempt to void items and their enchantments, converting them into materials of the same rarity and the Book of Omniscience.", "尝试销毁物品及其附魔，转化为同等级材料和全知之书"),
    TOOLTIP$CONTAINER$ILLUSION_UPGRADE("Attempt to pick up and void convertible items and their enchantments, converting them into materials of the same rarity and the Book of Omniscience.", "尝试拾取并销毁可转化的物品及其附魔，转化为同等级材料和全知之书"),

    /**
     * Trinkets
     */
    TOOLTIP$ARMOR_OF_CONVERGENCE("- Casting [Converge] spell in place while rolling.", "- 翻滚时在原地施放 [汇聚] 法术"),
    TOOLTIP$ARCANE_THRONE$1("- Provides permanent Phase effect.", "- 提供永久相位"),
    TOOLTIP$ARCANE_THRONE$2("Use the mouse cursor to right-click on the item to switch the Phase.", "使用鼠标指针右键该物品以开关"),
    TOOLTIP$ARCANE_THRONE$ON("Enabled", "已开启"),
    TOOLTIP$ARCANE_THRONE$OFF("Disabled", "已关闭"),
    TOOLTIP$NIRVANA_STARFALL$DEFRAUDING_REAPER("- [Defrauding Reaper] If you are near death and your fire spell power is above %s, you are spared from death once at the cost of gaining a %s second Nirvana effect and removing all harmful effects.", "- [欺诈死神] 濒临死亡且自身火焰法术强度高于%s时，以获得%s秒涅槃效果为代价免死一次，并清除身上所有负面效果"),
    TOOLTIP$NIRVANA_STARFALL$NIRVANA_REBIRTH("- [Nirvana Rebirth] %s".formatted(NirvanaEffect.getDesc(true)), "- [涅槃重生] %s".formatted(NirvanaEffect.getDesc(false))),
    TOOLTIP$SOULFIRE_MASK$1("- Every %ss, apply Soul Burning effect to enemies within %s blocks of your sight.", "- 每隔%s秒，向目视的%s格以内的敌人施加魂火效果"),
    TOOLTIP$SOULFIRE_MASK$2("- For every %s fire spell power, Soul Burning effect level increases by 1.", "- 每有%s点火焰法术强度，魂火效果等级增加1级"),
    TOOLTIP$GLACIAL_NUCLEAR_ERA$1("- Your cast [Icy Nucleus] explosion has a %s%% chance to cast [Icicle] on the surrounding area.", "- 你施放的 [冰核] 爆炸时有%s%%的概率向周围施放 [冰刺]"),
    TOOLTIP$GLACIAL_NUCLEAR_ERA$2("- Your cast [Icicle] has a %s%% chance to cast [Icy Nucleus] on the target upon hitting.", "- 你施放的 [冰刺] 命中时有%s%%的概率向目标施放 [冰核]"),
    TOOLTIP$FROSTBITE_DOME$1("- Apply a Incarceration effect for %s seconds when dealing frost spell damage.", "- 造成寒冰法术伤害时施加禁锢效果，持续%s秒"),
    TOOLTIP$FROSTBITE_DOME$2("- If the target already has a Incarceration effect, remove Incarceration and deals more damage, depending on the duration of Incarceration.", "- 如果目标已有禁锢效果，则移除禁锢，并造成更多伤害，额外伤害取决于禁锢的时长"),
    TOOLTIP$HEART_SPELL_STEEL$USAGE("- When killing a mob with a level higher than %s using healing spell damage within %s blocks, gain a max health boost equal to %s of target's max health, with a cooldown of %s seconds.", "- 在%s格范围内使用治愈魔法伤害击杀等级高于%s的生物时，可获得相当于目标最大生命值%s的生命上限提升，冷却%s秒"),
    TOOLTIP$HEART_SPELL_STEEL$ACCUMULATED("Max health obtained: %s", "已获得的最大生命值：%s"),
    TOOLTIP$REJUVENATING_BLOSSOM$USAGE_2("- When injured, gain a regeneration effect that restores health equivalent to the amount of damage received.", "- 受伤时获得生命恢复效果，该效果将恢复的生命值等同于受到的伤害值"),
    TOOLTIP$REJUVENATING_BLOSSOM$USAGE_1("- When injured, if the amount of health that your existing regeneration effect will recover is less than the damage dealt, then remove all harmful effects from you and halves this damage.", "- 受伤时，如果你还未恢复的生命值低于受到伤害，则移除你身上所有负面效果并减半此次伤害"),
    TOOLTIP$SUPERCONDUCTOR("Apply a stun when dealing lightning spell damage, increasing the duration by 0.1 seconds per 100 spell power.", "造成雷电法术伤害时施加眩晕，每100点法术强度增加0.1秒持续时间"),
    TOOLTIP$MACRO_ELECTRON("The ball lightning you cast will randomly select and destroy the target, without dropping any loot.", "你施放的球状闪电将随机选取并摧毁目标，但不会掉落战利品。"),

    /**
     * Breastplates
     */
    TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS(" - Current progress: %s", "当前进度：%s"),
    TOOLTIP$ATOMIC_BREASTPLATE_UPGRADEABLE(" - Upgradeable: Right click on this item with %s to upgrade it to the corresponding enhanced breastplate", " - 可升级：使用%s右键该物品即可升级为对应强化护心镜"),
    TOOLTIP$ATOMIC_TO_ENCHANTED("After dealing a total of %s magic damage, upgrade to Enchanted Breastplate", "总共造成%s点魔法伤害后升级为束魔护心镜"),
    TOOLTIP$ATOMIC_TO_FLEX("After suffering a total of %s damage, upgrade to Flex Breastplate", "总共承受%s点伤害后升级为曲御护心镜"),
    TOOLTIP$ATOMIC_TO_FLICKER("After rolling a total of %s times, upgrade to Flicker Breastplate", "总共翻滚%s次后升级为闪曳护心镜"),
    TOOLTIP$ATOMIC_TO_OBLIVION("After restoring with a total of %s health, upgrade to Oblivion Breastplate", "总共恢复%s点生命值后升级为湮灭护心镜"),
    TOOLTIP$ENCHANTED_BREASTPLATE("For every 1 spell power, your armor increases by %s, and armor toughness increases by %s", "每拥有1点法术强度，护甲增加%s，护甲韧性增加%s"),
    TOOLTIP$FLEX_BREASTPLATE("Gain a certain damage reduction ratio, which depends on the %s of your spell power, %s of armor, and %s of armor toughness, up to %s", "获得一定免伤比例，该比例取决于你的法术强度的%s，护甲值的%s和护甲韧性的%s，最高%s"),
    TOOLTIP$FLEX_BREASTPLATE$DAMAGE_FACTOR("Current damage reduction ratio: %s", "当前免伤比例：%s"),
    TOOLTIP$FLICKER_BREASTPLATE("Initially gains %s%% dodge chance, reduced by %s%% for every 1 level of enchantment on your equipments, down to %s%% minimum", "初始获得%s%%闪避率，装备每有1级附魔降低%s%%，最低降至%s%%"),
    TOOLTIP$FLICKER_BREASTPLATE$DODGE_CHANCE("Current dodge chance: %s", "当前闪避率：%s"),
    TOOLTIP$OBLIVION_BREASTPLATE_1("When your health is higher than %s of your max health, the Oblivion Breastplate will absorb %s of your health every second and convert it into Oblivion Amount.", "当你的生命值高于你的最大生命值的%s时，湮灭护心镜将每秒汲取你%s的生命值，并将其转化为湮灭值"),
    TOOLTIP$OBLIVION_BREASTPLATE_2("The max Oblivion Amount is %s of your spell power. When you are injured, it will try to consume Oblivion Amount to resist this damage", "湮灭值最大为你的法术强度的%s，当你受伤时将优先消耗湮灭值抵挡此次伤害"),
    TOOLTIP$OBLIVION_BREASTPLATE_3("Current Oblivion Amount: %s", "当前湮灭值：%s"),

    TOOLTIP$MIRAGE_REFLECTOR("Damage taken will not exceed %s%% of max health", "- 受到的伤害不会超过最大生命值的%s%%"),
    TOOLTIP$CURSE_PRIDE_1("Both damage dealt and damage taken are increased by %s%% per difficulty level", "根据玩家难度，造成伤害与受到伤害每级提升%s%%"),
    TOOLTIP$CURSE_PRIDE_2("Current damage increment: +%s%%", "当前增伤：+%s%%"),
    TOOLTIP$CURSE_WRATH("Increase damage dealt and damage received by %s%% per difficulty level difference when facing mobs with higher level than you.", "面对等级比自己高的怪物时每级等级差造成伤害与受到伤害均提升%s%%"),
    TOOLTIP$RING_DIVINITY("Gets permanent Cleanse effect. Reduce magic damage taken by %s%%, and not exceed %s%% of your max health", "获得持续的净化效果，受到的魔法伤害减少%s%%，且不超过自身最大血量的%s%%"),
    TOOLTIP$SECONDARY_SCHOOL_ITEM$1("- Gain %s spell power equal to %s%% of your major school's spell power", "- 获得相当于主修学派法术强度%2$s%%的%s法术强度"),
    TOOLTIP$SECONDARY_SCHOOL_ITEM$2("- Spell Scrolls of %s school can be used", "- 可以使用%s法术学派的卷轴"),
    TOOLTIP$SECONDARY_SCHOOL_ITEM$3("- %s%% chance to get %s Spell School's Enchanted Essence and Enlightening Essence", "- 有%s%%的几率获得%s法术学派的束魔精华和源启精华"),

    /**
     * Difficulty Tier
     */
    TOOLTIP$DIFFICULTY_TIER$CURRENT("Current Difficulty Tier: %s", "当前难度层级：%s"),
    TOOLTIP$DIFFICULTY_TIER$DESC("- %s", "- %s"),
    TOOLTIP$DIFFICULTY_TIER$ENTER("Difficulty Tier - %s", "难度层级 - %s"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$1("Player initial extra difficulty increases by 30", "玩家初始额外难度增加30"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$2("Doubles the player's difficulty increment for inflicting kills", "玩家造成击杀提升的难度翻倍"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$3("Doubles the hostility damage bonus", "恶意伤害加成翻倍"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$4("High tier spell scrolls cannot be put into low tier spell books", "高级法术卷轴不能放入低级法术书"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$5("Enchanted Essence threshold from loot gets lowered", "从战利品中获取的束魔精华阈值降低"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$6("Unable to use Bottle of Sanity and Hostility Orb", "无法使用恶意净化药水和恶意吸收宝珠"),
    TOOLTIP$DIFFICULTY_TIER$NIGHTMARE$1("Add 0.5 Extra Difficulty for each 1 Spell Power you have", "每拥有1点法术强度，增加0.5额外难度"),
    TOOLTIP$DIFFICULTY_TIER$NIGHTMARE$2("Enchanted Essences can be merged", "束魔精华可以合并"),
    TOOLTIP$DIFFICULTY_TIER$NIGHTMARE$3("Allow the use of Nightmare tier restricted items", "可以使用梦魇层级限定物品"),
    TOOLTIP$BOTTLE_NIGHTMARE("Enter [%s] mode after use, cannot be undone", "使用后进入 [%s] 模式，不可撤销"),
    TOOLTIP$BOTTLE_SOUL_BINDING("After using it, you will no longer lose the item when you die", "使用后，死亡不再丢失物品"),
    TOOLTIP$BOTTLE_SOUL_BINDING$WARNING("By default, items other than trinkets dropped by a player upon death are retained in the Gravestone. However, for some unknown reason, it is still possible, albeit highly unlikely, for items in the gravestone to disappear. If you are completely intolerant of the possibility of losing everything you own, drink this potion.", "默认情况下，玩家死亡时掉落的除饰品外的其他物品将被保留在墓碑中。但由于某些不明原因，墓碑中的物品仍有可能不翼而飞，尽管这种可能性微乎其微。如果你完全无法容忍失去所有身家的可能性，请喝下这瓶药水。"),

    TOOLTIP$ABRAHADABRA("Cannot defend against spell traits.", "无法抵御法术词条"),
    TOOLTIP$BROKEN_ITEM("Use %s Mending Essences to repair it to the original item:", "使用%s个修复精华将其修复为原物品："),
    TOOLTIP$SHIFT_RESET("Shift + Right-click to reset the progress", "Shift + 右键重置进度"),
    TOOLTIP$SPELL_PRISM("Consuming durability to make your spell damage bypass magical protection.", "消耗耐久使你的法术伤害可以穿透魔法防御"),
    TOOLTIP$SPELL_PRISM_ADVANCED("Maybe enhance the effects of certain spells?", "或许可以增强某些法术的效果？"),
    TOOLTIP$CURSED_APPLE$CONSUMED("When consumed:", "食用后:"),
    TOOLTIP$CURSED_APPLE$1("- +7 Curse slots", "- +7 恶意-诅咒饰品槽位"),
    TOOLTIP$CURSED_APPLE$2("- +200 Extra Difficulty", "- +200 额外难度"),
    TOOLTIP$MEDAL$TRUE_MAGIC("True magic is the love that can make time stand still", "真正的魔法，是能让时光驻留的热爱"),

    /**
     * Scrolls
     */
    SCROLL$BOOK_REQUIREMENT("Tier %s Spell: Can only be put into [%s] or more advanced Spell Books", "%s级法术：只能放入 [%s] 或更高级的法术书中"),
    SCROLL$OBTAIN("How to obtain: %s", "获取方式：%s"),
    SCROLL$UNAVAILABLE("Not yet available", "暂无"),
    SCROLL$PRIMARY("Primary Wand (unable to acquire scrolls)", "初始法杖（无法获取卷轴）"),
    SCROLL$BINDING("Bound via Spellbinding Table", "通过法术绑定台绑定"),
    SCROLL$EXPLORING("Explore specific structures to obtain", "探索特定结构获得"),
    SCROLL$KILLING("Dropped by killing certain mobs, see recipes for details", "击杀特定生物掉落，详情查看配方"),
    SCROLL$CRAFTING("Obtained through crafting using specific materials, see recipes for details", "使用特定材料合成获得，详情查看配方"),
    SCROLL$EVENT_AWARD("Can only be obtained through invasion event loot in the Ocean of Consciousness.", "只能通过识之海入侵事件战利品获得"),
    ;


    private final String key;
    private final String en;
    private final String zh;

    SDTexts(String en, String zh)
    {
        String[] split = this.name().split("\\$", 2);
        if (split.length != 2) throw new RuntimeException("SDTexts: Incorrect name!");
        this.key = Util.createTranslationKey(
                split[0].toLowerCase(Locale.ROOT),
                SpellDimension.modLoc(split[1].replace('$', '.').toLowerCase(Locale.ROOT))
        );
        this.en = en;
        this.zh = zh;
    }

    public String getKey()
    {
        return key;
    }

    public String getEn()
    {
        return en;
    }

    public String getZh()
    {
        return zh;
    }

    public MutableText get(Object... objects)
    {
        return Text.translatable(this.key, objects);
    }

    public static MutableText getSchoolText(SpellSchool school)
    {
        return Text.translatable(Util.createTranslationKey("school", SpellDimension.modLoc(school.id.getPath())));
    }

    public static MutableText getGradeText(Integer grade)
    {
        return Text.translatable(Util.createTranslationKey("grade", SpellDimension.modLoc(grade.toString())));
    }

    public static MutableText getSlotText(EquipmentSlot slot)
    {
        return Text.translatable(Util.createTranslationKey("slot", SpellDimension.modLoc(slot.getName())));
    }

    public static MutableText getDifficultyTierText(int tier)
    {
        return Text.translatable(Util.createTranslationKey("difficulty_tier", SpellDimension.modLoc(tier + "")));
    }

    public static void register()
    {
        for (SDTexts value : SDTexts.values())
        {
            SpellDimension.EN_TEXTS.addText(value.getKey(), value.getEn());
            SpellDimension.ZH_TEXTS.addText(value.getKey(), value.getZh());
        }
        addLootBagTranslation();
        addEventAwardTranslation();
        addTagTranslation();
        addDeathMessageTranslation();
        SDLocateRecipes.addTranslations();
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.LOCATE_PORTAL, "Locate Portal");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.LOCATE_PORTAL, "定位传送门");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.CONSCIOUSNESS_EVENT, "Consciousness Invasion Event");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.CONSCIOUSNESS_EVENT, "意识入侵事件");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.BLACK_HOLE, "Black Hole");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.BLACK_HOLE, "黑洞");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.CHAIN_LIGHTNING, "Chain Lightning");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.CHAIN_LIGHTNING, "连锁闪电");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.BALL_LIGHTNING, "Ball Lightning");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.BALL_LIGHTNING, "球状闪电");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.RAILGUN, "Railgun");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.RAILGUN, "超电磁炮");

        SpellDimension.EN_TEXTS.addText("trinkets.slot.chest.breastplate", "Breastplate");
        SpellDimension.ZH_TEXTS.addText("trinkets.slot.chest.breastplate", "护心镜");
        SpellDimension.EN_TEXTS.addText("trinkets.slot.chest.secondary_school", "Secondary School");
        SpellDimension.ZH_TEXTS.addText("trinkets.slot.chest.secondary_school", "副学派");
        SpellDimension.EN_TEXTS.addText("trinkets.slot.legs.spell_container", "Spell Container");
        SpellDimension.ZH_TEXTS.addText("trinkets.slot.legs.spell_container", "法术容器");
        SpellDimension.EN_TEXTS.addText(IllusionUpgradeTab.KEY, "Illusion");
        SpellDimension.ZH_TEXTS.addText(IllusionUpgradeTab.KEY, "幻化");
        SpellDimension.EN_TEXTS.addText(IllusionUpgradeTab.KEY + ".tooltip", "Illusion Settings");
        SpellDimension.ZH_TEXTS.addText(IllusionUpgradeTab.KEY + ".tooltip", "幻化设置");
    }

    private static void addLootBagTranslation()
    {
        for (SDContents ins : SDContents.values())
        {
            SpellDimension.EN_TEXTS.addText(ins.entry.nameKey(), ins.nameEn);
            SpellDimension.EN_TEXTS.addText(ins.entry.descKey(), ins.descEn);
            SpellDimension.ZH_TEXTS.addText(ins.entry.nameKey(), ins.nameZh);
            SpellDimension.ZH_TEXTS.addText(ins.entry.descKey(), ins.descZh);
        }
        for (SDBags ins : SDBags.values())
        {
            SpellDimension.EN_TEXTS.addText(ins.entry.nameKey(), ins.nameEn);
            SpellDimension.ZH_TEXTS.addText(ins.entry.nameKey(), ins.nameZh);
        }
    }

    private static void addEventAwardTranslation()
    {
        EventAward.BAG.addText("Random Loot Bags", "随机战利品袋");
        EventAward.ESSENCE.addText("Random Spell Essences", "随机魔法精华");
        EventAward.SPELL_SCROLL.addText("Random Spell Scrolls", "随机法术卷轴");
        EventAward.BOOK.addText("Random Enchanted Books", "随机附魔书");
        EventAward.GEAR.addText("Random Gears", "随机装备");
        EventAward.MATERIAL.addText("Random Materials", "随机材料");
        EventAward.TRINKET.addText("Random Trinkets", "随机饰品");
    }

    private static void addTagTranslation()
    {
        SpellDimension.EN_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.MAIN), "Main");
        SpellDimension.EN_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.BRANCH), "Branch");
        SpellDimension.EN_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.BEGINNING), "Beginning");
        SpellDimension.EN_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.END), "End");
        SpellDimension.EN_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.CHALLENGE), "Challenge");
        SpellDimension.EN_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.SKIPPABLE), "Skippable");
        SpellDimension.ZH_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.MAIN), "主线");
        SpellDimension.ZH_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.BRANCH), "支线");
        SpellDimension.ZH_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.BEGINNING), "起点");
        SpellDimension.ZH_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.END), "终点");
        SpellDimension.ZH_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.CHALLENGE), "挑战");
        SpellDimension.ZH_TEXTS.addText(QuestTag.getTagTranslationKey(AllTags.SKIPPABLE), "可跳过");
    }

    private static void addDeathMessageTranslation(SpellSchool school, String en, String zh)
    {
        String deathMsg = "death.attack." + school.id.getPath();
        String deathMsgPlayer = "death.attack." + school.id.getPath() + ".player";
        SpellDimension.EN_TEXTS.addText(deathMsg, "%s was killed by " + en);
        SpellDimension.EN_TEXTS.addText(deathMsgPlayer, "%s was killed by %s's " + en);
        SpellDimension.ZH_TEXTS.addText(deathMsg, "%s被" + zh + "杀死了");
        SpellDimension.ZH_TEXTS.addText(deathMsgPlayer, "%s被%s的" + zh + "杀死了");
    }

    private static void addDeathMessageTranslation()
    {
        addDeathMessageTranslation(SpellSchools.ARCANE, "arcane spell", "奥秘法术");
        addDeathMessageTranslation(SpellSchools.FIRE, "fire spell", "火焰法术");
        addDeathMessageTranslation(SpellSchools.FROST, "frost spell", "寒冰法术");
        addDeathMessageTranslation(SpellSchools.HEALING, "healing spell", "治愈法术");
        addDeathMessageTranslation(SpellSchools.LIGHTNING, "lightning spell", "闪电法术");
        addDeathMessageTranslation(SpellSchools.SOUL, "soul spell", "灵魂法术");
    }
}
