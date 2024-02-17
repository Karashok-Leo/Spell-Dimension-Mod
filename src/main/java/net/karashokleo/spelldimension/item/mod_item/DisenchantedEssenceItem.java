package net.karashokleo.spelldimension.item.mod_item;

import net.karashokleo.spelldimension.data.LangData;
import net.karashokleo.spelldimension.item.ExtraModifier;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisenchantedEssenceItem extends SpellEssenceItem
{
    public DisenchantedEssenceItem()
    {
        super();
    }

    @Override
    protected boolean applyEffect(ItemStack stack, PlayerEntity player)
    {
        return ExtraModifier.remove(player.getOffHandStack());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_DISENCHANT));
    }
}
