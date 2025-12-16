package karashokleo.spell_dimension.content.enchantment;

import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.spell_power.api.SpellPowerMechanics;

public class SpellBladeHasteEnchantment extends SpellBladeAmplifyEnchantment
{
    public SpellBladeHasteEnchantment()
    {
        super();
    }

    @Override
    public void operateModifiers(ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> modifiers)
    {
        double sum = modifiers.get(EntityAttributes.GENERIC_ATTACK_SPEED)
            .stream()
            .filter(m -> m.getOperation() == EntityAttributeModifier.Operation.ADDITION)
            .mapToDouble(EntityAttributeModifier::getValue)
            .sum();
        sum += 4;
        sum *= 0.25;
        if (sum <= 0)
        {
            return;
        }
        EntityAttributeModifier modifier = new EntityAttributeModifier(SpellBladePowerEnchantment.MODIFIER_ID, "Spell Blade Enchantment", sum, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        modifiers.put(SpellPowerMechanics.HASTE.attribute, modifier);
    }
}
