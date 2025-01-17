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
    TEXT$SKILLED_SCHOOL("This is not the magic school you are skilled in!", "这不是你擅长的法术学派!"),
    TEXT$BLANK_BOOK("Blank spell book!", "空白法术书!"),
    TEXT$SPELL_POWER_INFO("Spell Power Information", "魔法信息"),
    TEXT$ABYSS_GUARD("You need to have Abyss Guard in your inventory to sound the shell horn!", "你需要在物品栏中持有深渊守护才能吹响号角！"),
    TEXT$END_STAGE("End Stage Unlocked!", "终末阶段解锁！"),
    TEXT$END_STAGE$LOCK("You need to use %s to unlock the End Stage to enter the End!", "你需要使用%s解锁终末阶段才能进入末地！"),
    TEXT$PROGRESS_ROLLBACK("Progress has been rolled back!", "进度已回退！"),
    TEXT$QUEST_COMPLETE("Quest completed!", "任务完成！"),
    TEXT$QUEST_COMPLETED("The quest has been completed", "该任务已完成"),
    TEXT$QUEST_ALL_COMPLETED("All quests have been completed", "所有任务已完成"),
    TEXT$QUEST_REQUIREMENT("Quest requirements not met!", "任务要求未达成！"),
    TEXT$QUEST$HEALTH("Kill monsters to increase your %s to %s", "击杀怪物以提升你的%s至%s"),
    TEXT$QUEST$SPELL_POWER("Increase any Spell Power to %s", "将任意法术强度提升至%s"),
    TEXT$INVALID_KEY_ITEM("Invalid Key Item!", "无效的索引物品！"),
    TEXT$SPELL_INFUSION("Spell Infusion", "魔力灌注"),
    TEXT$SPAWN_POINT_RESTRICTION("You can only set your spawn point in the sea of consciousness!", "你只能将重生点设置在识之海！"),
    TEXT$CONSCIOUSNESS_CORE$LEVEL("Active Level: %s", "激活等级：%s"),
    TEXT$CONSCIOUSNESS_CORE$LEVEL_FACTOR("Difficulty Factor: %s", "难度系数：%s"),
    TEXT$CONSCIOUSNESS_CORE$AWARD("Award: %s", "奖励：%s"),
    TEXT$CONSCIOUSNESS_CORE$TRIGGERING("Invasion Event on going...", "入侵事件进行中..."),
    TEXT$CONSCIOUSNESS_CORE$TRIGGERED("Invasion Event has been triggered", "入侵事件已触发"),
    TEXT$CONSCIOUSNESS_CORE$NOT_TRIGGERED("Invasion Event not triggered", "入侵事件未触发"),
    TEXT$EVENT$PREPARE("The invasion is about to begin - Countdown: %s seconds", "入侵即将开始 - 倒计时：%s秒"),
    TEXT$EVENT$RUNNING("Wave %s/%s - Enemies Remaining: %s", "第%s/%s波 - 剩余敌人：%s"),
    TEXT$EVENT$WAITING("Wave %s/%s is about to start - Countdown: %s seconds", "第%s/%s波即将开始 - 倒计时：%s秒"),
    TEXT$EVENT$FINISH$SUCCESS("Invasion End - Successful", "入侵结束 - 成功"),
    TEXT$EVENT$FINISH$FAIL("Invasion End - Failed", "入侵结束 - 失败"),
    TEXT$CONSCIOUS$BAN_ITEM("You have entered the Ocean of Consciousness and can no longer use this item.", "你已经进入过识之海，不再能够使用该物品"),
    TEXT$RANDOM_LOOT_NAME("? %s ?", "? %s ?"),
    TEXT$LOCATING("Locating: %s", "正在定位：%s"),
    TEXT$GAME_OVER$HEAD("To players:", "致玩家:"),
    TEXT$GAME_OVER$THANKS("Congrats on the pass, and thank you very much for traveling this far.", "恭喜你通关了！非常感谢你游玩至此。"),
    TEXT$GAME_OVER$MAKING("I have put a lot of time and effort into this integration pack since I started working on it. From developing exclusive modules for this pack, customizing the loot table, and optimizing performance, to fixing bugs and balancing the game's difficulty, I've encountered countless challenges, and luckily I've finally finished it.", "自从开始制作这个整合包以来，我投入了大量的时间和精力。从开发这个整合包的专属模组、定制战利品表、优化性能，到修复Bug、平衡游戏难度，我遇到了无数挑战，幸运的是我最后完成了它。"),
    TEXT$GAME_OVER$FEEDBACK("If you have any suggestions, please give me feedback, I would appreciate it.", "如果你有任何建议，请反馈给我，我将感激不尽。"),
    TEXT$GAME_OVER$WISH("Lastly, I wish you a happy life. Have fun playing!", "最后，祝你生活愉快。玩的开心！"),

    /**
     * Spell School
     */
    SCHOOL$ARCANE("Arcane ", "奥秘"),
    SCHOOL$FIRE("Fire ", "烈焰"),
    SCHOOL$FROST("Frost ", "冰霜"),
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
    TOOLTIP$QUEST$OPEN_ENTRY("Shift + Right-click to open the related guide page", "Shift+右键打开相关指引页面"),
    TOOLTIP$QUEST$OBTAIN_CURRENT("Right-click to obtain all current quests", "右键获取当前所有任务"),
    TOOLTIP$QUEST$MUL("%s × %s", "%s × %s"),
    TOOLTIP$QUEST$AND(" and ", "和"),
    TOOLTIP$QUEST$LOOT_ITEM("Defeat %s to obtain %s", "击杀%s并获取%s"),
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

    /**
     * Scrolls
     */
    SCROLL$OBTAIN("How to obtain: %s", "获取方式：%s"),
    SCROLL$UNAVAILABLE("Not yet available", "暂无"),
    SCROLL$PRIMARY("Primary Wand (unable to acquire scrolls)", "初始法杖（无法获取卷轴）"),
    SCROLL$BINDING("Bound via Spellbinding Table", "通过法术绑定台绑定"),
    SCROLL$EXPLORING("Obtained through exploring %s", "探索%s获得"),
    SCROLL$KILLING("Dropped by killing %s", "击杀%s掉落"),
    SCROLL$CRAFT("Obtained through crafting using %s", "使用%s合成获得"),
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
