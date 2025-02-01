package karashokleo.spell_dimension.content.enchantment;

import com.google.common.collect.Multimap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.spell_power.api.SpellSchool;

import java.util.Set;

public class SpellBladePowerEnchantment extends SpellBladeAmplifyEnchantment
{
    private final Set<SpellSchool> schools;

    public SpellBladePowerEnchantment(SpellSchool... schools)
    {
        super();
        this.schools = Set.of(schools);
    }

    @Override
    public void operateModifiers(ItemStack stack, Multimap<EntityAttribute, EntityAttributeModifier> modifiers)
    {
        double sum = modifiers.get(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                .stream()
                .filter(m -> m.getOperation() == EntityAttributeModifier.Operation.ADDITION)
                .mapToDouble(EntityAttributeModifier::getValue)
                .sum();
        sum += EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);
        if (sum <= 0) return;
        for (SpellSchool school : this.schools)
        {
            EntityAttributeModifier modifier = new EntityAttributeModifier(SpellBladePowerEnchantment.MODIFIER_ID, "Spell Blade Enchantment", sum, EntityAttributeModifier.Operation.ADDITION);
            modifiers.put(school.attribute, modifier);
        }
    }
}
