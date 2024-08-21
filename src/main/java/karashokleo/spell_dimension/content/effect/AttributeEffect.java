package karashokleo.spell_dimension.content.effect;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class AttributeEffect extends StatusEffect
{
    public AttributeEffect(StatusEffectCategory category, int color, String name, EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation)
    {
        super(category, color);
        this.addAttributeModifier(attribute, UuidUtil.getUUIDFromString(SpellDimension.modLoc(name).toString()).toString(), amount, operation);
    }
}
