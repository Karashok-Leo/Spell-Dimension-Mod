package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class ForceLandingEffect extends StatusEffect
{
    public ForceLandingEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x999999);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        Vec3d velocity = entity.getVelocity();
        if (!entity.getWorld().getBlockState(entity.getBlockPos().down()).isAir())
            return;
        entity.setVelocity(velocity.x, Math.min(velocity.y, -1), velocity.z);
    }
}
