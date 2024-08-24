package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.effect.FrostAuraEffect;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.data.loot_bag.SDContents;
import karashokleo.spell_dimension.init.AllGroups;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.spell_power.api.SpellSchool;

import java.util.Locale;

public enum SDTexts
{
    /**
     * Text
     */
    TEXT_MAGE("Mage", "法师"),
    TEXT_SPELL_BOOK("Spell Book", "法术书"),
    TEXT_SPELL_SCROLL("Spell Scroll", "法术卷轴"),
    TEXT_ESSENCE("Essence", "精华"),
    TEXT_ESSENCE_BASE("Base Essence", "基础精华"),
    TEXT_ESSENCE_ENCHANTED("Enchanted Essence", "束魔精华"),
    TEXT_ESSENCE_ENLIGHTENING("Enlightening Essence", "源启精华"),
    TEXT_ESSENCE_SUCCESS("Successful use of essence!", "精华使用成功!"),
    TEXT_ESSENCE_FAIL("Failed to use essence!", "精华使用失败!"),
    TEXT_INCOMPATIBLE_SCHOOL("Incompatible spell school!", "不兼容的法术学派!"),
    TEXT_SKILLED_SCHOOL("This is not the magic school you are skilled in!", "这不是你擅长的法术学派!"),
    TEXT_BLANK_BOOK("Blank spell book!", "空白法术书!"),
    TEXT_SPELL_POWER_INFO("Spell Power Information", "魔法信息"),
    TEXT_QUEST("Quest completed!", "任务完成！"),
    TEXT_ABYSS_GUARD("You need to have Abyss Guard in your inventory to sound the shell horn!", "你需要在物品栏中持有深渊守护才能吹响号角！"),

    /**
     * Spell School
     */
    SCHOOL_ARCANE("Arcane ", "奥秘"),
    SCHOOL_FIRE("Fire ", "烈焰"),
    SCHOOL_FROST("Frost ", "冰霜"),
    SCHOOL_HEALING("Healing ", "治愈"),
    SCHOOL_LIGHTNING("Lightning ", "闪电"),
    SCHOOL_SOUL("Soul ", "灵魂"),

    /**
     * Grade
     */
    GRADE_0("Primary ", "初级"),
    GRADE_1("Intermediate ", "中级"),
    GRADE_2("Advanced ", "高级"),

    /**
     * Equipment Slot
     */
    SLOT_HEAD("Slot: Head", "槽位: 头盔"),
    SLOT_CHEST("Slot: Chest", "槽位: 胸甲"),
    SLOT_LEGS("Slot: Legs", "槽位: 护腿"),
    SLOT_FEET("Slot: Feet", "槽位: 靴子"),
    SLOT_MAINHAND("Slot: Main Hand", "槽位: 主手"),
    SLOT_OFFHAND("Slot: Off Hand", "槽位: 副手"),

    /**
     * Tooltip
     */
    TOOLTIP_INVALID("Invalid Nbt Data!", "无效的Nbt数据!"),
    TOOLTIP_USE_CLICK("Usage: Right click on other item in the inventory.", "用法: 物品栏中用该物品右键其他物品."),
    TOOLTIP_USE_PRESS("Usage: Main hand holding, press the right button.", "用法: 主手持有时，长按右键."),
    TOOLTIP_BOOK_REQUIREMENT("Must have at least %s %s to equip", "需要拥有至少%s的%s才能装备"),
    TOOLTIP_EFFECT("Effect:", "效果:"),
    TOOLTIP_LEVEL("Enchanted Level: %s", "束魔等级: %s"),
    TOOLTIP_THRESHOLD("Threshold: %s", "阈值: %s"),
    TOOLTIP_MODIFIER("Modifier:", "属性修饰语:"),
    TOOLTIP_DISENCHANT("Remove attribute modifiers from the item.", "从物品上移除所有通过束魔精华获得的属性修饰符."),
    TOOLTIP_MENDING("Completely repair the item and eliminate the repair cost of the item.", "完全修复物品, 并且清除物品的修复惩罚."),
    TOOLTIP_OBTAIN("How to obtain: ", "获取方式: "),
    TOOLTIP_UNAVAILABLE("Not yet available", "暂无"),
    TOOLTIP_PRIMARY("Primary Wand (unable to acquire scrolls)", "初始法杖（无法获取卷轴）"),
    TOOLTIP_BINDING("Spell Binding Table", "法术绑定台"),
    TOOLTIP_EXPLORING("Obtained through exploring %s", "探索%s获得"),
    TOOLTIP_KILLING("Drop from killing %s", "击杀%s掉落"),
    TOOLTIP_QUEST_TASK("Task:", "任务目标:"),
    TOOLTIP_QUEST_REWARD("Reward:", "任务奖励:"),
    TOOLTIP_QUEST_COMPLETE("Right click to submit", "右键提交任务"),
    TOOLTIP_QUEST_MUL("%s × %s", "%s × %s"),
    TOOLTIP_QUEST_FIRSTDAY("Survive for one day", "存活一天"),
    TOOLTIP_QUEST_HEALTH("Gain experience to increase your %s to %s", "获取经验以提升你的%s至%s"),

    /**
     * Spells
     */
    SPELL_CONVERGE_NAME("Converge", "汇聚"),
    SPELL_CONVERGE_DESCRIPTION(
            "Launching a spell to explode at the landing point and make enemies converged.",
            "发射一道咒语, 在落点处爆炸并汇聚敌人."
    ),
    SPELL_PHASE_NAME("Phase", "相位"),
    SPELL_PHASE_DESCRIPTION(
            "Apply phase {effect_amplifier} effect to oneself for {effect_duration} seconds. Phase: You are free as a bird, even more than a bird.",
            "施法者获得相位{effect_amplifier}效果, 持续{effect_duration}秒. 相位: 你自由了."
    ),
    SPELL_BLAST_NAME("Blast", "热爆"),
    SPELL_BLAST_DESCRIPTION(
            "Causing a powerful flame explosion, repelling surrounding creatures and causing {damage} fire spell damage.",
            "造成一次威力巨大的烈焰爆炸, 击退周围生物并造成{damage}点伤害."
    ),
    SPELL_IGNITE_NAME("Ignite", "引火"),
    SPELL_IGNITE_DESCRIPTION(
            "Apply ignite effect to oneself. Ignite: " + BlazingMark.DESC_EN,
            "施法者获得引火效果. 引火: " + BlazingMark.DESC_ZH
    ),
    SPELL_AURA_NAME("Aura", "霜环"),
    SPELL_AURA_DESCRIPTION(
            "Apply aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Aura: " + FrostAuraEffect.DESC_EN,
            "施法者获得霜环{effect_amplifier}效果, 持续{effect_duration}秒. 霜环: " + FrostAuraEffect.DESC_ZH
    ),
    SPELL_NUCLEUS_NAME("Nucleus", "冰核"),
    SPELL_NUCLEUS_DESCRIPTION(
            "Freeze the target's heart into a ice nucleus, explode in " + Nucleus.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.",
            "将敌人的心脏化作一个冰核, " + Nucleus.TOTAL_DURATION / 20F + "秒后爆炸, 并向周围射出冰刺."
    ),
    SPELL_ICICLE_NAME("Icicle", "冰刺"),
    SPELL_ICICLE_DESCRIPTION(
            "Shooting an icicle, which deals {damage} frost spell damage to the hit enemy and can cause a chain reaction.",
            "发射一道冰刺, 对命中的敌人造成{damage}点伤害, 冰刺可以引发链式反应."
    ),
    SPELL_POWER_NAME("Power", "力量"),
    SPELL_POWER_DESCRIPTION(
            "Apply strength {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得力量{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    SPELL_REGEN_NAME("Regen", "再生"),
    SPELL_REGEN_DESCRIPTION(
            "Apply regen {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得生命再生{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    SPELL_RESIST_NAME("Resist", "抗性"),
    SPELL_RESIST_DESCRIPTION(
            "Apply resist {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得抗性提升{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    SPELL_LIGHTMOON_NAME("Light Moon", "浅月"),
    SPELL_LIGHTMOON_DESCRIPTION(
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云, 造成{damage}点伤害."
    ),
    SPELL_GREATMOON_NAME("Great Moon", "漫月"),
    SPELL_GREATMOON_DESCRIPTION(
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云, 造成{damage}点伤害."
    ),
    SPELL_FORLORN_NAME("Scythe Dance", "镰舞"),
    SPELL_FORLORN_DESCRIPTION(
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    SPELL_KAYN_NAME("Scythe Dance", "镰舞"),
    SPELL_KAYN_DESCRIPTION(
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    SPELL_RHAAST_NAME("Scythe Dance", "镰舞"),
    SPELL_RHAAST_DESCRIPTION(
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    SPELL_BLOODTHIRSTER_NAME("Blood Thirster", "渴血"),
    SPELL_BLOODTHIRSTER_DESCRIPTION(
            "Build up your strength and swing your sword forward to deal {damage} melee damage and heal {heal} health.",
            "蓄力后向前挥剑造成{damage}点近战伤害并回复{heal}点生命值."
    ),
    SPELL_FEATHERLIGHT_NAME("Featherlight", "羽轻"),
    SPELL_FEATHERLIGHT_DESCRIPTION(
            "Gain a short-lived Speed and Slow-Falling effect",
            "获得短暂的迅捷和缓降效果."
    ),
    SPELL_CRUCIBLE_NAME("Empyrean", "擎天"),
    SPELL_CRUCIBLE_DESCRIPTION(
            "Two-Week Turn of the Greatsword, dealing {damage} fire spell damage  and slowing and catching the target on fire.",
            "横转巨剑两周, 造成{damage}点火焰法术伤害并使目标着火和缓速."
    ),
    SPELL_LICHBANE_NAME("Lich Bane", "妖斩"),
    SPELL_LICHBANE_DESCRIPTION(
            "Splash multiple times, inflicting {damage} damage, and additional damage when the target's maximum blood level is below 40.",
            "连续劈出多道剑斩, 造成{damage}点伤害, 当目标最大血量低于40时造成额外伤害."
    ),
    SPELL_DAWNBREAKER_NAME("Dawnbreaker", "破晓"),
    SPELL_DAWNBREAKER_DESCRIPTION(
            "Meteor Dawn, Strikes the target, dealing {damage} damage and stunning the target.",
            "流星破晓, 冲击目标, 造成{damage}点伤害并使目标晕眩."
    ),
    // Fix
    HEALING_POWER("attribute.name.spell_power.healing", "Healing Spell Power", "治愈法术强度");


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
        String[] split = this.name().split("_", 2);
        if (split.length != 2) throw new RuntimeException("SDTexts: Incorrect name!");
        this.key = Util.createTranslationKey(
                split[0].toLowerCase(Locale.ROOT),
                SpellDimension.modLoc(split[1].replace('_', '.').toLowerCase(Locale.ROOT))
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
        addItemTranslation();
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

    private static void addItemTranslation()
    {
        SpellDimension.EN_TEXTS.addItem(AllItems.DEBUG_STAFF, getDefaultName(AllItems.DEBUG_STAFF));
        SpellDimension.EN_TEXTS.addItem(AllItems.DISENCHANTED_ESSENCE, getDefaultName(AllItems.DISENCHANTED_ESSENCE));
        SpellDimension.EN_TEXTS.addItem(AllItems.MENDING_ESSENCE, getDefaultName(AllItems.MENDING_ESSENCE));
        SpellDimension.EN_TEXTS.addItem(AllItems.QUEST_SCROLL, getDefaultName(AllItems.QUEST_SCROLL));
        SpellDimension.EN_TEXTS.addItem(AllItems.ABYSS_GUARD, getDefaultName(AllItems.ABYSS_GUARD));
        SpellDimension.EN_TEXTS.addItem(AllItems.ACCURSED_BLACKSTONE, getDefaultName(AllItems.ACCURSED_BLACKSTONE));

        SpellDimension.ZH_TEXTS.addItem(AllItems.DEBUG_STAFF, "调试法杖");
        SpellDimension.ZH_TEXTS.addItem(AllItems.DISENCHANTED_ESSENCE, "祛魔精华");
        SpellDimension.ZH_TEXTS.addItem(AllItems.MENDING_ESSENCE, "修复精华");
        SpellDimension.ZH_TEXTS.addItem(AllItems.QUEST_SCROLL, "任务卷轴");
        SpellDimension.ZH_TEXTS.addItem(AllItems.ABYSS_GUARD, "深渊守护");
        SpellDimension.ZH_TEXTS.addItem(AllItems.ACCURSED_BLACKSTONE, "朽咒黑石");
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

    private static String getDefaultName(Item item)
    {
        Identifier id = Registries.ITEM.getId(item);
        return getDefaultName(id);
    }

    private static String getDefaultName(String name)
    {
        String[] words = name.split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words)
            if (!word.isEmpty())
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
        return sb.toString().trim();
    }

    private static String getDefaultName(Identifier id)
    {
        return getDefaultName(id.getPath());
    }
}
