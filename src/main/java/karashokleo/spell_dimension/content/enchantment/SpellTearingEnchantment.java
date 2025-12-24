package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class SpellTearingEnchantment extends UnobtainableEnchantment
{
    public static final float MULTIPLIER = 0.2F;

    public SpellTearingEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.BLUE;
    }
}
