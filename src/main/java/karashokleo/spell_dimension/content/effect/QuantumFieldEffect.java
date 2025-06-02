package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class QuantumFieldEffect extends StatusEffect
{
    public static String getDesc(boolean en)
    {
        return (en ?
                "Immunize and reflect damage, with the greatest percentage of damage countered at a distance from the source of the damage equal to the natural constant e (≈2.7)" :
                "免疫并反弹部分伤害，与伤害来源的距离等于自然常数e(≈2.7)时反伤比例最大");
    }

    public QuantumFieldEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xb5f0ff);
    }

    public float getReflectRatio(float distance)
    {
        float e = (float) Math.E;
        float v = e - distance;
        v = Math.abs(v);
        v /= e;
        v += 0.2f;
        if (v > 1.0f)
        {
            v = 1.0f;
        }
        return v;
    }
}
