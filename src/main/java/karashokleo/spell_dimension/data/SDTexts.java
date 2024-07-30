package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.spell.BlazingMark;
import karashokleo.spell_dimension.content.spell.NucleusSpell;
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
    TEXT_BLANK_BOOK("Blank spell book!", "空白法术书!"),
    TEXT_SPELL_POWER_INFO("Spell Power Information","魔法信息"),

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
    TOOLTIP_EFFECT("Effect:", "效果:"),
    TOOLTIP_LEVEL("Level: %s", "束魔等级: %s"),
    TOOLTIP_THRESHOLD("Threshold: %s", "阈值: %s"),
    TOOLTIP_MODIFIER("Modifier:", "属性修饰语:"),
    TOOLTIP_DISENCHANT("Remove attribute modifiers from the item.", "从物品上移除所有通过束魔精华获得的属性修饰符."),
    TOOLTIP_MENDING("Completely repair the item and eliminate the repair cost of the item.", "完全修复物品, 并且清除物品的修复惩罚."),

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
            "Apply ignite effect to oneself. Ignite: Your attack will leave a mark on the enemy, and some of the damage you inflict during this period will damage the enemy again in " + BlazingMark.getTriggerTime() + " seconds.",
            "施法者获得引火效果. 引火: 你的攻击会在敌人身上留下一个印记, " + BlazingMark.getTriggerTime() + "秒内你造成的部分伤害将会在" + BlazingMark.getTriggerTime() + "秒后再次打击敌人, 并使其虚弱."
    ),
    SPELL_AURA_NAME("Aura", "霜环"),
    SPELL_AURA_DESCRIPTION(
            "Apply aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Aura: Within the frozen aura, livings get Frosted.",
            "施法者获得霜环{effect_amplifier}效果, 持续{effect_duration}秒. 霜环: 在霜环内, 所有生物被霜冻."
    ),
    SPELL_NUCLEUS_NAME("Nucleus", "冰核"),
    SPELL_NUCLEUS_DESCRIPTION(
            "Freeze the target's heart into a ice nucleus, explode in " + NucleusSpell.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.",
            "将敌人的心脏化作一个冰核, " + NucleusSpell.TOTAL_DURATION / 20F + "秒后爆炸, 并向周围射出冰刺."
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
    // Fix
    HEALING_POWER("attribute.name.spell_power.healing", "Healing Spell Power", "治愈法术强度")
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
}
