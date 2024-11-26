package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Formatting;

public abstract class EffectImmunityEnchantment extends UnobtainableEnchantment
{
    public EffectImmunityEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{});
    }

    public abstract boolean test(StatusEffectInstance effectInstance);

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.GOLD;
    }
}
