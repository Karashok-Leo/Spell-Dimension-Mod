package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class SpellResistanceEnchantment extends UnobtainableEnchantment
{
    public static final float MULTIPLIER = 0.01F;

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

    @Override
    public Formatting getColor()
    {
        return Formatting.BLUE;
    }
}
