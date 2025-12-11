package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class EtherealEvasionEffect extends StatusEffect
{
    public static String getDesc(boolean en)
    {
        return (en ?
            "Disperses the caster's soul across all soul minions, placing the caster in a suspended state where it cannot be harmed but are unable to move" :
            "将灵魂分散到所有灵仆中，使施法者进入凝滞状态，不会受到伤害但无法移动");
    }

    public EtherealEvasionEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0x3fb6bc);
    }
}
