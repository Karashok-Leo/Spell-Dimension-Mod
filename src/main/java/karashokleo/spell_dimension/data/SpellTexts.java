package karashokleo.spell_dimension.data;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Nucleus;
import karashokleo.spell_dimension.content.effect.FrostAuraEffect;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Locale;

public enum SpellTexts
{
    LIGHT(
            "Spell Light",
            "魔力之光",
            "Launch a spell, if it touches a block, it will light up the surrounding area.",
            "发射一道咒语, 若其触碰到方块，则会点亮周围一片区域."
    ),
    LOCATE(
            "Spell Locate",
            "魔力定位",
            "Launch a spell, if it touches a lodestone, it will consume the item in your off-hand as a key item to find the corresponding structure or biome. If found, it will shatter the lodestone with a probability of 10%% to summon a portal to the corresponding structure or biome. Press U on Lodestone to view all recipes.",
            "发射一道咒语，若其触碰到磁石，则会以消耗你副手的物品作为索引物，寻找相应的结构或群系。若找到，则有10%%的概率击碎磁石，召唤出一个通往对应结构或群系的传送门。对磁石按U可以查看所有配方。"
    ),
    SUMMON(
            "Spell Summon",
            "魔力召唤",
            "Launch a spell, if it touches a spawner, it will use the item in your off-hand as a key item to replace the mob in the spawner. Press U on Spawner to view all recipes.",
            "发射一道咒语，若其触碰到刷怪笼，则会以你副手的物品为索引物，替换刷怪笼中的生物。对刷怪笼按U可以查看所有配方。"
    ),
    PLACE(
            "Remote Manipulation",
            "远端操控",
            "Launch a spell, if it touches a block, it will use the item in your off-hand on the block.",
            "发射一道咒语，若其触碰到方块，则会对其使用你副手的物品。"
    ),
    BREAK(
            "Remote Destruction",
            "远端瓦解",
            "Launch a spell, if it touches a block, it will break it and drop the item it was supposed to drop.",
            "发射一道咒语，若其触碰到方块，则会将其破坏并掉落其本应掉落的物品。"
    ),
    MOON_SWIM(
            "Moon Swim",
            "月泳",
            "Apply Air Hop and Slow Falling effects to oneself for {effect_duration} seconds.",
            "施法者获得空中跳跃效果和缓降效果，持续{effect_duration}秒。"
    ),
    INCARCERATE(
            "Incarcerate",
            "禁锢",
            "Incarcerate the target for {effect_duration} seconds.",
            "禁锢目标，持续{effect_duration}秒。"
    ),
    FORCE_LANDING(
            "Force Landing",
            "迫降",
            "Make all targets within a certain range get the Force Landing {effect_amplifier} effect for {effect_duration} seconds.",
            "使一定范围内所有目标获得迫降{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    CONVERGE(
            "Converge",
            "汇聚",
            "Launch a spell to explode at the landing point and make enemies converged.",
            "发射一道咒语，在落点处爆炸并汇聚敌人。"
    ),
    PHASE(
            "Phase",
            "相位",
            "Apply Phase {effect_amplifier} effect to oneself for {effect_duration} seconds. Phase: Free to fly through walls.",
            "施法者获得相位{effect_amplifier}效果，持续{effect_duration}秒。相位：自由穿墙飞行。"
    ),
    SHIFT(
            "Shift",
            "移形换影",
            "Launch a spell, if it touches a mob, exchange your position with that mob.",
            "发射一道咒语，若其触碰到生物，则交换你与该生物的位置。"
    ),
    ARCANE_BARRIER(
            "Arcane Barrier",
            "奥术屏障",
            "Generate an arcane barrier around oneself.",
            "在自身周围生成一个奥术屏障。"
    ),
    BLAST(
            "Blast",
            "热爆",
            "Causing a powerful flame explosion, repelling surrounding creatures and causing {damage} fire spell damage.",
            "造成一次威力巨大的烈焰爆炸，击退周围生物并造成{damage}点伤害。"
    ),
    IGNITE(
            "Ignite",
            "引火",
            "Apply Ignite effect to oneself. Ignite: " + BlazingMark.DESC_EN,
            "施法者获得引火效果。引火：" + BlazingMark.DESC_ZH
    ),
    FIRE_OF_RETRIBUTION(
            "Fire of Retribution",
            "业火",
            "Burn a random trait of the target every second. The higher the levelFactor of the trait consumed, the higher the damage caused.",
            "每秒灼烧目标的随机一个词条，该词条的等级消耗越高，造成的伤害越高。"
    ),
    AURA(
            "Aura",
            "霜环",
            "Apply Frost Aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Frost Aura: " + FrostAuraEffect.DESC_EN,
            "施法者获得霜环{effect_amplifier}效果，持续{effect_duration}秒。霜环：" + FrostAuraEffect.DESC_ZH
    ),
    FROST_BLINK(
            "Frost Blink",
            "冰瞬",
            "Teleport forward and detonate some icicles at your original position.",
            "传送至前方，在原地引爆冰锥。"
    ),
    FROZEN(
            "Frozen",
            "冰封",
            "Freeze all targets within a certain range for {effect_duration} seconds.",
            "冻结一定范围内所有目标，持续{effect_duration}秒。"
    ),
    NUCLEUS(
            "Nucleus",
            "冰核",
            "Freeze the target's heart into a ice nucleus, explode in " + Nucleus.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.",
            "将敌人的心脏化作一个冰核，" + Nucleus.TOTAL_DURATION / 20F + "秒后爆炸，并向周围射出冰刺。"
    ),
    ICICLE(
            "Icicle",
            "冰刺",
            "Shooting an icicle, which deals {damage} frost spell damage to the hit enemy and can cause a chain reaction.",
            "发射一道冰刺，对命中的敌人造成{damage}点伤害，冰刺可以引发链式反应。"
    ),
    SPELL_POWER(
            "Empowering Presence",
            "魔力增强",
            "Apply Empowering Presence {effect_amplifier} effect to target or oneself for {effect_duration} seconds. Empowering Presence: Increases spell power by 10% per level.",
            "目标或施法者获得魔力增强{effect_amplifier}效果，持续{effect_duration}秒。魔力增强：每级增加10%法术强度。"
    ),
    SPELL_POWER_ADVANCED(
            "Empowering Presence",
            "魔力增强",
            "Apply Empowering Presence {effect_amplifier} effect to target or oneself for {effect_duration} seconds. Empowering Presence: Increases spell power by 10% per level.",
            "目标或施法者获得魔力增强{effect_amplifier}效果，持续{effect_duration}秒。魔力增强：每级增加10%法术强度。"
    ),
    REGEN(
            "Regen",
            "再生",
            "Apply Regenerate {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得生命再生{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    REGEN_ADVANCED(
            "Advanced Regen",
            "高级再生",
            "Apply Regenerate {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得生命再生{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    RESIST(
            "Resist",
            "抗性",
            "Apply Resistance {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得抗性提升{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    RESIST_ADVANCED(
            "Advanced Resist",
            "高级抗性",
            "Apply Resistance {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得抗性提升{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    HASTE(
            "Spell Haste",
            "施法急速",
            "Apply Spell Haste {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得施法加速{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    HASTE_ADVANCED(
            "Advanced Spell Haste",
            "高级施法急速",
            "Apply Spell Haste {effect_amplifier} effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得施法加速{effect_amplifier}效果，持续{effect_duration}秒。"
    ),
    CRITICAL_HIT(
            "Spell Volatility",
            "法术波动",
            "Target or caster gains a 50% spell critical chance for {effect_duration} seconds.",
            "目标或施法者获得50%法术暴击几率，持续{effect_duration}秒。"
    ),
    CLEANSE(
            "Cleanse",
            "净化",
            "Apply Cleanse effect to target or oneself for {effect_duration} seconds.",
            "目标或施法者获得净化效果，持续{effect_duration}秒"
    ),
    EXORCISM(
            "Exorcism",
            "驱邪",
            "Reduce the target's levelFactor to half of the original, and reacquire the traits, while the caster takes damage equal to the target's original levelFactor.",
            "使目标等级降至原来的一半，并且重新获取词条，同时施法者受到相当于目标原有等级的伤害。"
    ),
    LIGHTMOON(
            "Light Moon",
            "浅月",
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云，造成{damage}点伤害。"
    ),
    GREATMOON(
            "Great Moon",
            "漫月",
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云，造成{damage}点伤害。"
    ),
    FORLORN(
            "Scythe Dance",
            "镰舞",
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰，造成{damage}点伤害。"
    ),
    KAYN(
            "Scythe Dance",
            "镰舞",
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰，造成{damage}点伤害。"
    ),
    RHAAST(
            "Scythe Dance",
            "镰舞",
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰，造成{damage}点伤害。"
    ),
    BLOODTHIRSTER(
            "Blood Thirster",
            "渴血",
            "Build up your strength and swing your sword forward to deal {damage} melee damage and heal {heal} health.",
            "蓄力后向前挥剑造成{damage}点近战伤害并回复{heal}点生命值。"
    ),
    FEATHERLIGHT(
            "Featherlight",
            "羽轻",
            "Gain a short-lived Speed and Slow-Falling effect",
            "获得短暂的迅捷和缓降效果。"
    ),
    CRUCIBLE(
            "Empyrean",
            "擎天",
            "Two-Week Turn of the Greatsword, dealing {damage} fire spell damage  and slowing and catching the target on fire.",
            "横转巨剑两周，造成{damage}点火焰法术伤害并使目标着火和缓速。"
    ),
    LICHBANE(
            "Lich Bane",
            "妖斩",
            "Splash multiple times, inflicting {damage} damage, and additional damage when the target's maximum blood levelFactor is below 40.",
            "连续劈出多道剑斩，造成{damage}点伤害，当目标最大血量低于40时造成额外伤害。"
    ),
    DAWNBREAKER(
            "Dawnbreaker",
            "破晓",
            "Meteor Dawn, Strikes the target, dealing {damage} damage and stunning the target.",
            "流星破晓，冲击目标，造成{damage}点伤害并使目标晕眩。"
    ),
    ;

    private final String nameEN;
    private final String nameZH;
    private final String descEN;
    private final String descZH;

    SpellTexts(String nameEN, String nameZH, String descEN, String descZH)
    {
        this.nameEN = nameEN;
        this.nameZH = nameZH;
        this.descEN = descEN;
        this.descZH = descZH;
    }

    public String getName()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    public String getNameKey()
    {
        return "spell.spell-dimension.%s.name".formatted(getName());
    }

    public String getDescKey()
    {
        return "spell.spell-dimension.%s.description".formatted(getName());
    }

    public MutableText getNameText(Object... args)
    {
        return Text.translatable(getNameKey(), args);
    }

    public MutableText getDescText(Object... args)
    {
        return Text.translatable(getDescKey(), args);
    }

    public static void register()
    {
        for (SpellTexts value : SpellTexts.values())
        {
            String nameKey = value.getNameKey();
            String descKey = value.getDescKey();
            SpellDimension.EN_TEXTS.addText(nameKey, value.nameEN);
            SpellDimension.ZH_TEXTS.addText(nameKey, value.nameZH);
            SpellDimension.EN_TEXTS.addText(descKey, value.descEN);
            SpellDimension.ZH_TEXTS.addText(descKey, value.descZH);
        }
    }
}
