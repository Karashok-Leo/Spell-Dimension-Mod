package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public class CustomStatusEffect extends StatusEffect
{
    public CustomStatusEffect(StatusEffectCategory category, int color)
    {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity)
    {
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return false;
    }
}
