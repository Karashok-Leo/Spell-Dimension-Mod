package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.misc.Mage;
import net.minecraft.item.ItemStack;

public interface IMageItem
{
    default Mage getMage(ItemStack stack)
    {
        return Mage.readFromStack(stack);
    }
}
