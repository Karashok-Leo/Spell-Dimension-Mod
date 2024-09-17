package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SpellResistanceEnchantment extends UnobtainableEnchantment
{
    public SpellResistanceEnchantment()
    {
        super(
                Rarity.RARE,
                EnchantmentTarget.ARMOR,
                new EquipmentSlot[]{
                        EquipmentSlot.CHEST,
                        EquipmentSlot.HEAD,
                        EquipmentSlot.LEGS,
                        EquipmentSlot.FEET
                }
        );
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}
