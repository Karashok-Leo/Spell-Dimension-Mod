package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class SpellLeechEnchantment extends UnobtainableEnchantment
{
    public static final float HEAL_MULTIPLIER = 0.01F;
    public static final float REDUCTION_MULTIPLIER = 0.05F;

    public SpellLeechEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return 10;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.RED;
    }
}
