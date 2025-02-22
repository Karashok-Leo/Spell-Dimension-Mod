package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.content.event.conscious.EventAward;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.data.loot_bag.SDContents;
import karashokleo.spell_dimension.init.AllEntities;
import karashokleo.spell_dimension.init.AllGroups;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.spell_power.api.SpellSchool;

import java.util.Locale;

public enum SDTexts
{
    /**
     * Text
     */
    TEXT$MAGE("Mage", "法师"),
    TEXT$SPELL_BOOK("Spell Book", "法术书"),
    TEXT$SPELL_SCROLL("Spell Scroll", "法术卷轴"),
    TEXT$ESSENCE("Essence", "精华"),
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
    TEXT$INTERVAL_SPELL_TRAIT("Every %s seconds, cast spell [%s] on target.", "每隔%s秒，对目标施放法术 [%s]"),
    TEXT$SPELL_TRAIT$ACTION("%s is casting [%s]", "%s 正在施法 [%s]"),
    TEXT$SPELL_TRAIT$POWER("Monsters gain spell power equal to %s%% Hostility level", "怪物获得相当于%s%%恶意等级的法术强度"),
    TEXT$QUEST_COMPLETE("Quest completed!", "任务完成！"),
    TEXT$QUEST_COMPLETED("The quest has been completed", "该任务已完成"),
    TEXT$QUEST_ALL_COMPLETED("All quests have been completed", "所有任务已完成"),
    TEXT$QUEST_REQUIREMENT("Quest requirements not met!", "任务要求未达成！"),
    TEXT$QUEST_DEPENDENCIES("Prerequisite quests not completed!", "前置任务未完成！"),
    TEXT$QUEST$SPELL_POWER("Increase any Spell Power to %s", "将任意法术强度提升至%s"),
    TEXT$BANNED_SPELL("You cannot cast this spell in your current dimension!", "你不能在当前维度施放该法术！"),
    TEXT$INVALID_KEY_ITEM("Invalid Key Item!", "无效的索引物品！"),
    TEXT$SPELL_INFUSION("Spell Infusion", "魔力灌注"),
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
    TEXT$DIFFICULTY$BAN_ITEM("You're in Hardcore mode and can no longer use this item.", "你处于硬核模式，不再能够使用该物品"),
    TEXT$RANDOM_LOOT_NAME("? %s ?", "? %s ?"),
    TEXT$LOCATING("Locating: %s", "正在定位：%s"),
    TEXT$GAME_OVER$HEAD("To players:", "致玩家:"),
    TEXT$GAME_OVER$THANKS("Congrats on the pass, and thank you very much for traveling this far. My hands are still shaking as I write this - just as they were three years ago when I first successfully ran a mod I wrote.", "恭喜你通关啦！非常感谢你游玩至此。写这段话的时候，我的手还在抖——就像三年前第一次成功运行自己写的模组时那样。"),
    TEXT$GAME_OVER$MAKING("I have put a lot of time and effort into this modpack since I started working on it. From developing exclusive mods for this pack, customizing loot tables, and optimizing performance, to fixing bugs and balancing difficulty, I've encountered countless challenges. Luckily and finally, I've finished it.", "自着手制作这个整合包以来，我投入了无数的时间和精力。从开发专属模组、定制战利品表、优化游戏性能，到修复各种Bug、平衡游戏难度，我遇到了一个又一个挑战。幸运的是，最终我完成了它。"),
    TEXT$GAME_OVER$PASSION("During the making process, I gradually found my old passion for Minecraft. I think I've found something - that I truly love, which seems to be a luxury for most people. I hope you too can find what you love and stick with it.", "在制作过程中，我逐渐找回了自己从前对Minecraft的热情。我想我找到了某些东西——某些我真正热爱的东西，这似乎对大多数人来说是一种奢侈。希望你也能找到你的热爱并为之坚持下去。"),
    TEXT$GAME_OVER$FEEDBACK("If you have any suggestions, please give me feedback, I would appreciate it.", "如果你有任何建议，请反馈给我，我将感激不尽。"),
    TEXT$GAME_OVER$WISH("Lastly, I wish you a happy life. Have fun playing!", "最后，祝你生活愉快。玩的开心！"),
    TEXT$GAME_OVER$TRUE_MAGIC("True magic is the love that can make time stand still", "真正的魔法，是能让时光驻留的热爱"),
    TEXT$QUEST$BEGINNING("[BEGINNING]", "[起点]"),
    TEXT$QUEST$END("[END]", "[终点]"),
    TEXT$QUEST$CHALLENGE("[CHALLENGE]", "[挑战]"),
    TEXT$MAGE_BOOK$1("An Introductory Grimoire Even a Three-Year-Old Can Understand", "三岁小孩也能看懂的入门魔法书"),
    TEXT$MAGE_BOOK$2("When you're in a quandary, turn to it~", "当你遇到窘境时，不妨翻开看看吧~"),
    TEXT$SOUL_BIND("You feel the connection between your soul and your body becoming stronger...", "你感觉灵魂与身体的联系变得紧密..."),
    TEXT$SOUL_UNBIND("You feel the connection between your soul and your body becoming tenuous...", "你感到灵魂与身体的联系变得脆弱..."),
    TEXT$TRADER$OLDEN("Mysterious Olden", "神秘老登"),

    /**
     * Advancements
     */
    ADVANCEMENT$MEDAL$TITLE("I knocked my tooth...", "磕到牙了..."),
    ADVANCEMENT$MEDAL$DESCRIPTION("Try nibbling on a Medal", "尝试啃一口勋章"),

    /**
     * Spell School
     */
    SCHOOL$ARCANE("Arcane ", "奥秘"),
    SCHOOL$FIRE("Fire ", "火焰"),
    SCHOOL$FROST("Frost ", "寒冰"),
    SCHOOL$HEALING("Healing ", "治愈"),
    SCHOOL$LIGHTNING("Lightning ", "闪电"),
    SCHOOL$SOUL("Soul ", "灵魂"),

    /**
     * Grade
     */
    GRADE$0("Primary ", "初级"),
    GRADE$1("Intermediate ", "中级"),
    GRADE$2("Advanced ", "高级"),

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
    TOOLTIP$QUEST$TASK("Task:", "任务目标:"),
    TOOLTIP$QUEST$REWARD("Reward:", "任务奖励:"),
    TOOLTIP$QUEST$COMPLETE("Right-click to submit", "右键提交任务"),
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
    TOOLTIP$HEART_SPELL_STEEL$USAGE("Dealing magic damage from beyond %s blocks away to instantly kill a full-health mob that is higher than %s levelFactor, will grant a max health increase equivalent to %s of the damage dealt.", "在%s格以外使用魔法伤害秒杀满血且等级高于%s的生物，可获得相当于此次伤害%s的生命上限提升"),
    TOOLTIP$HEART_SPELL_STEEL$ACCUMULATED("Max health obtained: %s", "已获得的最大生命值：%s"),
    TOOLTIP$REJUVENATING_BLOSSOM$USAGE_1("- When injured, if the amount of health that your existing regeneration effect will recover is less than the damage dealt * the count of this item equipped, then remove all negative effects from you.", "- 受伤时，如果你还未恢复的生命值低于受到伤害 * 装备的该物品数量，则移除你身上所有负面效果"),
    TOOLTIP$REJUVENATING_BLOSSOM$USAGE_2("- When injured, gain a regeneration effect that restores health equivalent to the amount of damage received * the count of this item equipped.", "- 受伤时获得生命恢复效果，该效果将恢复的生命值等同于受到的伤害值 * 装备的该物品数量"),
    TOOLTIP$MAGIC_MIRROR$USAGE("Use to teleport to the Ocean of Consciousness", "使用后传送至识之海"),
    TOOLTIP$MAGIC_MIRROR$WARNING("Warning: After entering the Ocean of Consciousness, your game will be much more difficult, please refer to Magic Guidance for more details.", "警告：进入识之海后，你的游戏难度将大幅提升，详情请查阅《魔力接引》"),
    TOOLTIP$MAGIC_MIRROR$BROKEN("This item is a consumable", "该物品为消耗品"),
    TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS(" - Current progress: %s", "当前进度：%s"),
    TOOLTIP$ATOMIC_BREASTPLATE_UPGRADEABLE(" - Upgradeable: Right click on this item with %s to upgrade it to the corresponding enhanced breastplate", " - 可升级：使用%s右键该物品即可升级为对应强化护心镜"),
    TOOLTIP$ATOMIC_TO_ENCHANTED("After dealing a total of %s magic damage, upgrade to Enchanted Breastplate", "总共造成%s点魔法伤害后升级为束魔护心镜"),
    TOOLTIP$ATOMIC_TO_FLEX("After suffering a total of %s damage, upgrade to Flex Breastplate", "总共承受%s点伤害后升级为曲御护心镜"),
    TOOLTIP$ATOMIC_TO_FLICKER("After rolling a total of %s times, upgrade to Flicker Breastplate", "总共翻滚%s次后升级为闪曳护心镜"),
    TOOLTIP$ATOMIC_TO_OBLIVION("After restoring with a total of %s health, upgrade to Oblivion Breastplate", "总共恢复%s点生命值后升级为湮灭护心镜"),
    TOOLTIP$ENCHANTED_BREASTPLATE("For every 1 spell power, your max health increases by %s, armor increases by %s, and armor toughness increases by %s", "每拥有1点法术强度，最大生命值增加%s，护甲增加%s，护甲韧性增加%s"),
    TOOLTIP$FLEX_BREASTPLATE("Gain a certain damage reduction ratio, which depends on the %s of your spell power, %s of armor, and %s of armor toughness, up to 50%%", "获得一定免伤比例，该比例取决于你的法术强度的%s，护甲值的%s和护甲韧性的%s，最高50%%"),
    TOOLTIP$FLEX_BREASTPLATE$DAMAGE_FACTOR("Current damage reduction ratio: %s", "当前免伤比例：%s"),
    TOOLTIP$FLICKER_BREASTPLATE("Gain a certain probability of dodging enemy attacks, which depends on the ratio of your speed to the enemy's speed and doubles when in the air", "获得一定闪避敌人攻击的概率，该概率取决于你与敌人的速度之比，且在空中时翻倍"),
    TOOLTIP$OBLIVION_BREASTPLATE_1("When your health is higher than %s of your max health, the Oblivion Breastplate will absorb %s of your health every second and convert it into Oblivion Amount.", "当你的生命值高于你的最大生命值的%s时，湮灭护心镜将每秒汲取你%s的生命值，并将其转化为湮灭值"),
    TOOLTIP$OBLIVION_BREASTPLATE_2("The max Oblivion Amount is %s of your max health value. When you are injured, it will try to consume Oblivion Amount to resist this damage", "湮灭值最大为你的最大生命值的%s，当你受伤时将优先消耗湮灭值抵挡此次伤害"),
    TOOLTIP$OBLIVION_BREASTPLATE_3("Current Oblivion Amount: %s", "当前湮灭值：%s"),
    TOOLTIP$CURSE_PRIDE_1("Both damage dealt and damage taken are increased by %s%% per difficulty level", "根据玩家难度，造成伤害与受到伤害每级提升%s%%"),
    TOOLTIP$CURSE_PRIDE_2("Current damage increment: +%s%%", "当前增伤：+%s%%"),
    TOOLTIP$DIFFICULTY_TIER$CURRENT("Current Difficulty Tier: %s", "当前难度层级：%s"),
    TOOLTIP$DIFFICULTY_TIER$TITLE("[%s]:", "[%s]:"),
    TOOLTIP$DIFFICULTY_TIER$DESC("- %s", "- %s"),
    TOOLTIP$DIFFICULTY_TIER$ENTER("Difficulty Tier - %s", "难度层级 - %s"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$1("Player initial extra difficulty increases by 30", "玩家初始额外难度增加30"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$2("Doubles the player's difficulty increment for inflicting kills", "玩家造成击杀提升的难度翻倍"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$3("Doubles the hostility damage bonus", "恶意伤害加成翻倍"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$4("High tier spell scrolls cannot be put into low tier spell books", "高级法术卷轴不能放入低级法术书"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$5("Enchanted Essence threshold from loot gets lowered", "从战利品中获取的束魔精华阈值降低"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$6("Enchanted Essences cannot be merged", "束魔精华无法合并"),
    TOOLTIP$DIFFICULTY_TIER$HARDCORE$7("Unable to use Bottle of Sanity and Hostility Orb", "无法使用恶意净化药水和恶意吸收宝珠"),
    TOOLTIP$BOTTLE_NIGHTMARE("Enter [%s] mode after use, cannot be undone", "使用后进入 [%s] 模式，不可撤销"),
    TOOLTIP$BOTTLE_SOUL_BINDING("After using it, you will no longer lose the item when you die", "使用后，死亡不再丢失物品"),
    TOOLTIP$BOTTLE_SOUL_BINDING$WARNING("By default, items other than trinkets dropped by a player upon death are retained in the Gravestone. However, for some unknown reason, it is still possible, albeit highly unlikely, for items in the gravestone to disappear. If you are completely intolerant of the possibility of losing everything you own, drink this potion.", "默认情况下，玩家死亡时掉落的除饰品外的其他物品将被保留在墓碑中。但由于某些不明原因，墓碑中的物品仍有可能不翼而飞，尽管这种可能性微乎其微。如果你完全无法容忍失去所有身家的可能性，请喝下这瓶药水。"),

    /**
     * Scrolls
     */
    SCROLL$BOOK_REQUIREMENT("Tier %s Spell: Can only be put into [%s] or more advanced Spell Books", "%s级法术：只能放入 [%s] 或更高级的法术书中"),
    SCROLL$OBTAIN("How to obtain: %s", "获取方式：%s"),
    SCROLL$UNAVAILABLE("Not yet available", "暂无"),
    SCROLL$PRIMARY("Primary Wand (unable to acquire scrolls)", "初始法杖（无法获取卷轴）"),
    SCROLL$BINDING("Bound via Spellbinding Table", "通过法术绑定台绑定"),
    SCROLL$EXPLORING("Obtained through exploring %s", "探索%s获得"),
    SCROLL$KILLING("Dropped by killing %s", "击杀%s掉落"),
    SCROLL$CRAFT("Obtained through crafting using %s", "使用%s合成获得"),
    SCROLL$EVENT("Can only be obtained through invasion event loot in the Ocean of Consciousness.", "只能通过识之海入侵事件战利品获得"),
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
        addGroupTranslation();
        addLootBagTranslation();
        addEventAwardTranslation();
        LocateSpellConfig.register();
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.LOCATE_PORTAL, "Locate Portal");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.LOCATE_PORTAL, "定位传送门");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.CONSCIOUSNESS_EVENT, "Consciousness Invasion Event");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.CONSCIOUSNESS_EVENT, "意识入侵事件");
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.BLACK_HOLE, "Black Hole");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.BLACK_HOLE, "黑洞");
        SpellDimension.EN_TEXTS.addText("trinkets.slot.chest.breastplate", "Breastplate");
        SpellDimension.ZH_TEXTS.addText("trinkets.slot.chest.breastplate", "护心镜");
        SpellDimension.EN_TEXTS.addText("trinkets.slot.chest.breastplate", "Breastplate");
        SpellDimension.ZH_TEXTS.addText("trinkets.slot.chest.breastplate", "护心镜");
    }

    private static void addGroupTranslation()
    {
        SpellDimension.EN_TEXTS.addText(AllGroups.BOOKS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Spell Books");
        SpellDimension.EN_TEXTS.addText(AllGroups.SPELL_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Spell Scrolls");
        SpellDimension.EN_TEXTS.addText(AllGroups.QUEST_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Quest Scrolls");
        SpellDimension.EN_TEXTS.addText(AllGroups.ELES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Enlightening Essences");
        SpellDimension.EN_TEXTS.addText(AllGroups.ECES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Enchanted Essences");
        SpellDimension.EN_TEXTS.addText(AllGroups.MISC_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Misc");

        SpellDimension.ZH_TEXTS.addText(AllGroups.BOOKS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元：法术书");
        SpellDimension.ZH_TEXTS.addText(AllGroups.SPELL_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元：法术卷轴");
        SpellDimension.ZH_TEXTS.addText(AllGroups.QUEST_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元：任务卷轴");
        SpellDimension.ZH_TEXTS.addText(AllGroups.ELES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元：束魔精华");
        SpellDimension.ZH_TEXTS.addText(AllGroups.ECES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元：附魔精华");
        SpellDimension.ZH_TEXTS.addText(AllGroups.MISC_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元：杂项");
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
}
