package karashokleo.spell_dimension.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_power.api.SpellSchools;

public class NirvanaEffect extends StatusEffect
{
    public static final float NIRVANA_FACTOR = 0.6F;
    public static final int FIRE_TICKS = 40;
    private static final String UUID = "f2b0c3a4-1d5e-4f8b-9c7d-6a2e5f3b8c1d";

    public NirvanaEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xFF0000);
        addAttributeModifier(
                EntityAttributes.GENERIC_MAX_HEALTH,
                UUID,
                NIRVANA_FACTOR - 1,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );
        addAttributeModifier(
                SpellSchools.FIRE.attribute,
                UUID,
                NIRVANA_FACTOR - 1,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier)
    {
        return Math.pow(NIRVANA_FACTOR, amplifier + 1) - 1;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 20 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        entity.setFrozenTicks(0);
        if (entity.getFireTicks() < FIRE_TICKS)
            entity.setFireTicks(FIRE_TICKS);
    }
}
