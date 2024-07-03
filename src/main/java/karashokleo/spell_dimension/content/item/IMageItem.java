package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.content.misc.Mage;
import net.minecraft.item.ItemStack;

public interface IMageItem extends IMageGetter<ItemStack>
{
    @Override
    default Mage getMage(ItemStack stack)
    {
        return Mage.readFromStack(stack);
    }
}
