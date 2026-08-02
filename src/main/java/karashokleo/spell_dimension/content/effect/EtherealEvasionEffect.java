package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class EtherealEvasionEffect extends StatusEffect
{
    public static String getDesc(boolean en)
    {
        return (en ?
            "When taking damage, has a 50% chance to negate it; otherwise, a random active soul minion takes the damage instead. If no eligible soul minion exists, the damage is not negated" :
            "受到伤害时，有50%概率完全免疫此次伤害；否则由一个随机的活跃灵仆代为承受。没有可用灵仆时，代偿失败，施法者正常受到伤害");
    }

    public EtherealEvasionEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0x3fb6bc);
    }
}
