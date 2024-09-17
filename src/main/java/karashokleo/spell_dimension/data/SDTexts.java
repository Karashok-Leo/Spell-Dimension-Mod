package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.effect.FrostAuraEffect;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.data.loot_bag.SDContents;
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

    /**
     * Spells
     */
    SPELL$LOCATE$NAME("Locate", "定位"),
    SPELL$LOCATE$DESCRIPTION(
            "Launch a spell, if it touches a lodestone, it will use the item in your off-hand as a key item to find the corresponding structure or biome. If found, it will shatter the lodestone and the key item to summon a portal to the corresponding structure or biome.",
            "发射一道咒语, 若其触碰到磁石, 则会以你副手的物品为索引物, 寻找相应的结构或群系. 若找到，则击碎磁石和索引物, 召唤出一个通往对应结构或群系的传送门."
    ),
    SPELL$SUMMON$NAME("Summon", "引唤"),
    SPELL$SUMMON$DESCRIPTION(
            "Launch a spell, if it touches a spawner, it will use the item in your off-hand as a key item to replace the mob in the spawner.",
            "发射一道咒语, 若其触碰到刷怪笼, 则会以你副手的物品为索引物, 替换刷怪笼中的生物."
    ),
    SPELL$PLACE$NAME("Remote Manipulation", "远端操控"),
    SPELL$PLACE$DESCRIPTION(
            "Launch a spell, if it touches a block, it will use the item in your off-hand on the block.",
            "发射一道咒语, 若其触碰到方块, 则会对其使用你副手的物品."
    ),
    SPELL$CONVERGE$NAME("Converge", "汇聚"),
    SPELL$CONVERGE$DESCRIPTION(
            "Launching a spell to explode at the landing point and make enemies converged.",
            "发射一道咒语, 在落点处爆炸并汇聚敌人."
    ),
    SPELL$PHASE$NAME("Phase", "相位"),
    SPELL$PHASE$DESCRIPTION(
            "Apply phase {effect_amplifier} effect to oneself for {effect_duration} seconds. Phase: You are free as a bird, even more than a bird.",
            "施法者获得相位{effect_amplifier}效果, 持续{effect_duration}秒. 相位: 你自由了."
    ),
    SPELL$BLAST$NAME("Blast", "热爆"),
    SPELL$BLAST$DESCRIPTION(
            "Causing a powerful flame explosion, repelling surrounding creatures and causing {damage} fire spell damage.",
            "造成一次威力巨大的烈焰爆炸, 击退周围生物并造成{damage}点伤害."
    ),
    SPELL$IGNITE$NAME("Ignite", "引火"),
    SPELL$IGNITE$DESCRIPTION(
            "Apply ignite effect to oneself. Ignite: " + BlazingMark.DESC_EN,
            "施法者获得引火效果. 引火: " + BlazingMark.DESC_ZH
    ),
    SPELL$AURA$NAME("Aura", "霜环"),
    SPELL$AURA$DESCRIPTION(
            "Apply aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Aura: " + FrostAuraEffect.DESC_EN,
            "施法者获得霜环{effect_amplifier}效果, 持续{effect_duration}秒. 霜环: " + FrostAuraEffect.DESC_ZH
    ),
    SPELL$NUCLEUS$NAME("Nucleus", "冰核"),
    SPELL$NUCLEUS$DESCRIPTION(
            "Freeze the target's heart into a ice nucleus, explode in " + Nucleus.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.",
            "将敌人的心脏化作一个冰核, " + Nucleus.TOTAL_DURATION / 20F + "秒后爆炸, 并向周围射出冰刺."
    ),
    SPELL$ICICLE$NAME("Icicle", "冰刺"),
    SPELL$ICICLE$DESCRIPTION(
            "Shooting an icicle, which deals {damage} frost spell damage to the hit enemy and can cause a chain reaction.",
            "发射一道冰刺, 对命中的敌人造成{damage}点伤害, 冰刺可以引发链式反应."
    ),
    SPELL$POWER$NAME("Power", "力量"),
    SPELL$POWER$DESCRIPTION(
            "Apply strength {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得力量{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    SPELL$REGEN$NAME("Regen", "再生"),
    SPELL$REGEN$DESCRIPTION(
            "Apply regen {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得生命再生{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    SPELL$RESIST$NAME("Resist", "抗性"),
    SPELL$RESIST$DESCRIPTION(
            "Apply resist {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得抗性提升{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    SPELL$LIGHTMOON$NAME("Light Moon", "浅月"),
    SPELL$LIGHTMOON$DESCRIPTION(
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云, 造成{damage}点伤害."
    ),
    SPELL$GREATMOON$NAME("Great Moon", "漫月"),
    SPELL$GREATMOON$DESCRIPTION(
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云, 造成{damage}点伤害."
    ),
    SPELL$FORLORN$NAME("Scythe Dance", "镰舞"),
    SPELL$FORLORN$DESCRIPTION(
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    SPELL$KAYN$NAME("Scythe Dance", "镰舞"),
    SPELL$KAYN$DESCRIPTION(
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    SPELL$RHAAST$NAME("Scythe Dance", "镰舞"),
    SPELL$RHAAST$DESCRIPTION(
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    SPELL$BLOODTHIRSTER$NAME("Blood Thirster", "渴血"),
    SPELL$BLOODTHIRSTER$DESCRIPTION(
            "Build up your strength and swing your sword forward to deal {damage} melee damage and heal {heal} health.",
            "蓄力后向前挥剑造成{damage}点近战伤害并回复{heal}点生命值."
    ),
    SPELL$FEATHERLIGHT$NAME("Featherlight", "羽轻"),
    SPELL$FEATHERLIGHT$DESCRIPTION(
            "Gain a short-lived Speed and Slow-Falling effect",
            "获得短暂的迅捷和缓降效果."
    ),
    SPELL$CRUCIBLE$NAME("Empyrean", "擎天"),
    SPELL$CRUCIBLE$DESCRIPTION(
            "Two-Week Turn of the Greatsword, dealing {damage} fire spell damage  and slowing and catching the target on fire.",
            "横转巨剑两周, 造成{damage}点火焰法术伤害并使目标着火和缓速."
    ),
    SPELL$LICHBANE$NAME("Lich Bane", "妖斩"),
    SPELL$LICHBANE$DESCRIPTION(
            "Splash multiple times, inflicting {damage} damage, and additional damage when the target's maximum blood level is below 40.",
            "连续劈出多道剑斩, 造成{damage}点伤害, 当目标最大血量低于40时造成额外伤害."
    ),
    SPELL$DAWNBREAKER$NAME("Dawnbreaker", "破晓"),
    SPELL$DAWNBREAKER$DESCRIPTION(
            "Meteor Dawn, Strikes the target, dealing {damage} damage and stunning the target.",
            "流星破晓, 冲击目标, 造成{damage}点伤害并使目标晕眩."
    ),
    ;


    private final String key;
    private final String en;
    private final String zh;

    SDTexts(String key, String en, String zh)
    {
        this.key = key;
        this.en = en;
        this.zh = zh;
    }

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
