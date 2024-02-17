package net.karashokleo.spelldimension.misc;

import net.karashokleo.spelldimension.SpellDimension;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum MageMajor
{
    CONVERGE(MagicSchool.ARCANE),//在落点产生爆炸，但不推开实体，反而汇聚实体
    PHASE(MagicSchool.ARCANE),//穿墙，临时的旁观者模式
    FLOURISH(MagicSchool.ARCANE),//奥术华彩
    BREATH(MagicSchool.FIRE),//烈焰吐息
    BLAST(MagicSchool.FIRE),//艺术爆炸
    IGNITE(MagicSchool.FIRE),//点燃
    ICICLE(MagicSchool.FROST),//凌冰，冰锥
    NUCLEUS(MagicSchool.FROST),//冰核，延迟4秒后爆炸
    AURA(MagicSchool.FROST),//光环，对周围生物施加冰冻效果
    POWER(MagicSchool.HEALING),//力量效果
    REGEN(MagicSchool.HEALING),//再生效果
    RESIST(MagicSchool.HEALING);//抗性效果

//    STRIKE(MagicSchool.LIGHTNING),

//    REALM(null),
//    BLACKHOLE(null),//召唤黑洞
//    ETERNAL(null),//透支生命，不死效果
//    KINGDOM(null);

    public final MagicSchool school;

    MageMajor(MagicSchool school)
    {
        this.school = school;
    }

    public String majorName()
    {
        return this.toString().toLowerCase(Locale.ENGLISH);
    }

    public Identifier spellId()
    {
        return SpellDimension.modLoc(this.majorName());
    }

    public static List<MageMajor> getMajors(MagicSchool school)
    {
        return Arrays.stream(MageMajor.values()).filter(major -> major.school == school).toList();
    }
}
