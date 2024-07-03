package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class IgniteEffect extends StatusEffect
{
    public IgniteEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xFF0000);
    }
}
