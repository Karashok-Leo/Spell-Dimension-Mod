package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;

public class RebirthEffect extends StatusEffect
{
    public static final int TOTAL_DURATION = 20 * 10;

    public RebirthEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0x55ffe8);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 5 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        StatusEffectInstance instance = entity.getStatusEffect(this);
        assert instance != null;
        float ratio = MathHelper.clamp(1 - (float) instance.getDuration() / TOTAL_DURATION, 0.001f, 1f);
        entity.setHealth(entity.getMaxHealth() * ratio);
    }
}
