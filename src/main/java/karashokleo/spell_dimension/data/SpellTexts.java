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
            "Launch a spell, if it touches a lodestone, it will use the item in your off-hand as a key item to find the corresponding structure or biome. If found, it will shatter the lodestone and the key item to summon a portal to the corresponding structure or biome.",
            "发射一道咒语, 若其触碰到磁石, 则会以你副手的物品为索引物, 寻找相应的结构或群系. 若找到，则击碎磁石和索引物, 召唤出一个通往对应结构或群系的传送门."
    ),
    SUMMON(
            "Spell Summon",
            "魔力召唤",
            "Launch a spell, if it touches a spawner, it will use the item in your off-hand as a key item to replace the mob in the spawner.",
            "发射一道咒语, 若其触碰到刷怪笼, 则会以你副手的物品为索引物, 替换刷怪笼中的生物."
    ),
    PLACE(
            "Remote Manipulation",
            "远端操控",
            "Launch a spell, if it touches a block, it will use the item in your off-hand on the block.",
            "发射一道咒语, 若其触碰到方块, 则会对其使用你副手的物品."
    ),
    CONVERGE(
            "Converge",
            "汇聚",
            "Launch a spell to explode at the landing point and make enemies converged.",
            "发射一道咒语, 在落点处爆炸并汇聚敌人."
    ),
    PHASE(
            "Phase",
            "相位",
            "Apply phase {effect_amplifier} effect to oneself for {effect_duration} seconds. Phase: You are free as a bird, even more than a bird.",
            "施法者获得相位{effect_amplifier}效果, 持续{effect_duration}秒. 相位: 你自由了."
    ),
    SHIFT(
            "Shift",
            "移形换影",
            "Launch a spell, if it touches a mob, exchange your position with that mob.",
            "发射一道咒语, 若其触碰到生物，则交换你与该生物的位置."
    ),
    BLAST(
            "Blast",
            "热爆",
            "Causing a powerful flame explosion, repelling surrounding creatures and causing {damage} fire spell damage.",
            "造成一次威力巨大的烈焰爆炸, 击退周围生物并造成{damage}点伤害."
    ),
    IGNITE(
            "Ignite",
            "引火",
            "Apply ignite effect to oneself. Ignite: " + BlazingMark.DESC_EN,
            "施法者获得引火效果. 引火: " + BlazingMark.DESC_ZH
    ),
    AURA(
            "Aura",
            "霜环",
            "Apply aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Aura: " + FrostAuraEffect.DESC_EN,
            "施法者获得霜环{effect_amplifier}效果, 持续{effect_duration}秒. 霜环: " + FrostAuraEffect.DESC_ZH
    ),
    FROST_BLINK(
            "Frost Blink",
            "冰瞬",
            "Teleport forward and detonate some icicles at your original position.",
            "传送至前方, 在原地引爆冰锥."
    ),
    FROZEN(
            "Frozen",
            "冰封",
            "Freeze all targets within a certain range.",
            "冻结一定范围内所有目标."
    ),
    NUCLEUS(
            "Nucleus",
            "冰核",
            "Freeze the target's heart into a ice nucleus, explode in " + Nucleus.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.",
            "将敌人的心脏化作一个冰核, " + Nucleus.TOTAL_DURATION / 20F + "秒后爆炸, 并向周围射出冰刺."
    ),
    ICICLE(
            "Icicle",
            "冰刺",
            "Shooting an icicle, which deals {damage} frost spell damage to the hit enemy and can cause a chain reaction.",
            "发射一道冰刺, 对命中的敌人造成{damage}点伤害, 冰刺可以引发链式反应."
    ),
    POWER(
            "Power",
            "力量",
            "Apply strength {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得力量{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    REGEN(
            "Regen",
            "再生",
            "Apply regen {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得生命再生{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    RESIST(
            "Resist",
            "抗性",
            "Apply resist {effect_amplifier} effect to oneself for {effect_duration} seconds.",
            "施法者获得抗性提升{effect_amplifier}效果, 持续{effect_duration}秒."
    ),
    LIGHTMOON(
            "Light Moon",
            "浅月",
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云, 造成{damage}点伤害."
    ),
    GREATMOON(
            "Great Moon",
            "漫月",
            "Leaves a cloud of spells on the target, dealing {damage} damage.",
            "在目标处留下法术云, 造成{damage}点伤害."
    ),
    FORLORN(
            "Scythe Dance",
            "镰舞",
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    KAYN(
            "Scythe Dance",
            "镰舞",
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    RHAAST(
            "Scythe Dance",
            "镰舞",
            "Continuously swing the scythe in your hand, dealing {damage} damage.",
            "持续挥舞手中巨镰, 造成{damage}点伤害."
    ),
    BLOODTHIRSTER(
            "Blood Thirster",
            "渴血",
            "Build up your strength and swing your sword forward to deal {damage} melee damage and heal {heal} health.",
            "蓄力后向前挥剑造成{damage}点近战伤害并回复{heal}点生命值."
    ),
    FEATHERLIGHT(
            "Featherlight",
            "羽轻",
            "Gain a short-lived Speed and Slow-Falling effect",
            "获得短暂的迅捷和缓降效果."
    ),
    CRUCIBLE(
            "Empyrean",
            "擎天",
            "Two-Week Turn of the Greatsword, dealing {damage} fire spell damage  and slowing and catching the target on fire.",
            "横转巨剑两周, 造成{damage}点火焰法术伤害并使目标着火和缓速."
    ),
    LICHBANE(
            "Lich Bane",
            "妖斩",
            "Splash multiple times, inflicting {damage} damage, and additional damage when the target's maximum blood level is below 40.",
            "连续劈出多道剑斩, 造成{damage}点伤害, 当目标最大血量低于40时造成额外伤害."
    ),
    DAWNBREAKER(
            "Dawnbreaker",
            "破晓",
            "Meteor Dawn, Strikes the target, dealing {damage} damage and stunning the target.",
            "流星破晓, 冲击目标, 造成{damage}点伤害并使目标晕眩."
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
