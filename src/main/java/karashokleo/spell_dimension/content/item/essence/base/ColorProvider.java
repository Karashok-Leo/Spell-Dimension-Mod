package karashokleo.spell_dimension.content.item.essence.base;

import net.minecraft.item.ItemStack;

public interface ColorProvider
{
    default int getColor(ItemStack stack)
    {
        return 0xffffff;
    }
}
