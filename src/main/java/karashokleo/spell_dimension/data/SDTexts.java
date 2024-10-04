package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.effect.FrostAuraEffect;
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
    TEXT$PROGRESS_ROLLBACK("Progress has been rolled back!", "进度已回退！"),
    TEXT$QUEST_COMPLETE("Quest completed!", "任务完成！"),
    TEXT$QUEST_COMPLETED("The quest has been completed", "该任务已完成"),
    TEXT$QUEST_ALL_COMPLETED("All quests have been completed", "所有任务已完成"),
    TEXT$QUEST_REQUIREMENT("Quest requirements not met!", "任务要求未达成！"),
    TEXT$QUEST$HEALTH("Gain experience to increase your %s to %s", "获取经验以提升你的%s至%s"),
    TEXT$QUEST$SPELL_POWER("Increase any Spell Power to %s", "将任意法术强度提升至%s"),
    TEXT$INVALID_KEY_ITEM("Invalid Key Item!", "无效的索引物品！"),
    TEXT$SPELL_INFUSION("Spell Infusion", "魔力灌注"),

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
    SLOT$HEAD("Slot: Head", "槽位: 头盔"),
    SLOT$CHEST("Slot: Chest", "槽位: 胸甲"),
    SLOT$LEGS("Slot: Legs", "槽位: 护腿"),
    SLOT$FEET("Slot: Feet", "槽位: 靴子"),
    SLOT$MAINHAND("Slot: Main Hand", "槽位: 主手"),
    SLOT$OFFHAND("Slot: Off Hand", "槽位: 副手"),

    /**
     * Tooltip
     */
    TOOLTIP$INVALID("Invalid Nbt Data!", "无效的Nbt数据!"),
    TOOLTIP$USE$CLICK("Usage: Right click on other item in the inventory.", "用法: 物品栏中用该物品右键其他物品."),
    TOOLTIP$USE$PRESS("Usage: Main hand holding, press the right button.", "用法: 主手持有时，长按右键."),
    TOOLTIP$BOOK_REQUIREMENT("Must have at least %s %s to equip", "需要拥有至少%s的%s才能装备"),
    TOOLTIP$EFFECT("Effect:", "效果:"),
    TOOLTIP$LEVEL("Enchanted Level: %s", "束魔等级: %s"),
    TOOLTIP$THRESHOLD("Threshold: %s", "阈值: %s"),
    TOOLTIP$MODIFIER("Modifier:", "属性修饰语:"),
    TOOLTIP$DISENCHANT("Remove attribute modifiers from the item.", "从物品上移除所有通过束魔精华获得的属性修饰符."),
    TOOLTIP$MENDING("Completely repair the item and eliminate the repair cost of the item.", "完全修复物品, 并且清除物品的修复惩罚."),
    TOOLTIP$END_STAGE("Use to unlock the End stage.", "使用后解锁终末阶段."),
    TOOLTIP$QUEST$TASK("Task:", "任务目标:"),
    TOOLTIP$QUEST$REWARD("Reward:", "任务奖励:"),
    TOOLTIP$QUEST$COMPLETE("Right click to submit", "右键提交任务"),
    TOOLTIP$QUEST$OBTAIN_CURRENT("Right click to obtain all current quests", "右键获取当前所有任务"),
    TOOLTIP$QUEST$MUL("%s × %s", "%s × %s"),
    TOOLTIP$QUEST$AND(" and ", "和"),
    TOOLTIP$QUEST$LOOT_ITEM("Defeat %s to obtain %s", "击杀%s并获取%s"),
    TOOLTIP$NOT_CONSUMED("Will not be consumed", "不消耗"),
    TOOLTIP$TOOK_SECONDS("Took %s second(s)", "耗时%s秒"),
    TOOLTIP$SUMMON_ENTITY("Entity Type: %s", "实体类型: %s"),
    TOOLTIP$SUMMON_REMAIN("Remain: %s", "剩余: %s"),

    /**
     * Scrolls
     */
    SCROLL$OBTAIN("How to obtain: ", "获取方式: "),
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
        LocateSpellConfig.addTranslation();
        SpellDimension.EN_TEXTS.addEntityType(AllEntities.LOCATE_PORTAL, "Locate Portal");
        SpellDimension.ZH_TEXTS.addEntityType(AllEntities.LOCATE_PORTAL, "定位传送门");
    }

    private static void addGroupTranslation()
    {
        SpellDimension.EN_TEXTS.addText(AllGroups.BOOKS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Spell Books");
        SpellDimension.EN_TEXTS.addText(AllGroups.SPELL_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Spell Scrolls");
        SpellDimension.EN_TEXTS.addText(AllGroups.QUEST_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Quest Scrolls");
        SpellDimension.EN_TEXTS.addText(AllGroups.ELES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Enlightening Essences");
        SpellDimension.EN_TEXTS.addText(AllGroups.ECES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Enchanted Essences");
        SpellDimension.EN_TEXTS.addText(AllGroups.MISC_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "Spell Dimension: Misc");

        SpellDimension.ZH_TEXTS.addText(AllGroups.BOOKS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元: 法术书");
        SpellDimension.ZH_TEXTS.addText(AllGroups.SPELL_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元: 法术卷轴");
        SpellDimension.ZH_TEXTS.addText(AllGroups.QUEST_SCROLLS_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元: 任务卷轴");
        SpellDimension.ZH_TEXTS.addText(AllGroups.ELES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元: 束魔精华");
        SpellDimension.ZH_TEXTS.addText(AllGroups.ECES_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元: 附魔精华");
        SpellDimension.ZH_TEXTS.addText(AllGroups.MISC_GROUP_KEY.getValue().toTranslationKey("itemGroup"), "咒次元: 杂项");
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
}
