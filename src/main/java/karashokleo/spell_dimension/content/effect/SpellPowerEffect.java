package karashokleo.spell_dimension.content.effect;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.spell_power.api.SpellSchool;

public class SpellPowerEffect extends StatusEffect
{
    public SpellPowerEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xd100ff);
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            this.addAttributeModifier(
                school.attribute,
                UuidUtil.getUUIDFromString(SpellDimension.MOD_ID + ":" + school.id.toString()).toString(),
                0.1D,
                EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            );
        }
    }
}
