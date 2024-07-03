package karashokleo.spell_dimension.content.misc;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum MageMajor
{
    CONVERGE(SpellSchools.ARCANE),//在落点产生爆炸，但不推开实体，反而汇聚实体
    PHASE(SpellSchools.ARCANE),//穿墙，临时的旁观者模式
    FLOURISH(SpellSchools.ARCANE),//奥术华彩
    BREATH(SpellSchools.FIRE),//烈焰吐息
    BLAST(SpellSchools.FIRE),//艺术爆炸
    IGNITE(SpellSchools.FIRE),//点燃
    ICICLE(SpellSchools.FROST),//凌冰，冰锥
    NUCLEUS(SpellSchools.FROST),//冰核，延迟4秒后爆炸
    AURA(SpellSchools.FROST),//光环，对周围生物施加冰冻效果
    POWER(SpellSchools.HEALING),//力量效果
    REGEN(SpellSchools.HEALING),//再生效果
    RESIST(SpellSchools.HEALING);//抗性效果

//    STRIKE(MagicSchool.LIGHTNING),

//    REALM(null),
//    BLACKHOLE(null),//召唤黑洞
//    ETERNAL(null),//透支生命，不死效果
//    KINGDOM(null);

    public final SpellSchool school;

    MageMajor(SpellSchool school)
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

    public static List<MageMajor> getMajors(SpellSchool school)
    {
        return Arrays.stream(MageMajor.values()).filter(major -> major.school == school).toList();
    }
}
