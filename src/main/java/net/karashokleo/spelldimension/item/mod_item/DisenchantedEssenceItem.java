package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.misc.EnchantedModifier;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisenchantedEssenceItem extends StackClickEssenceItem
{
    public DisenchantedEssenceItem()
    {
        super();
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target)
    {
        return EnchantedModifier.remove(target);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_DISENCHANT));
    }
}
