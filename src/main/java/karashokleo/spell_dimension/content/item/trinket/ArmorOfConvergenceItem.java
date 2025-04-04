package karashokleo.spell_dimension.content.item.trinket;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArmorOfConvergenceItem extends SingleEpicTrinketItem
{
    public ArmorOfConvergenceItem()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$ARMOR_OF_CONVERGENCE.get().formatted(Formatting.DARK_PURPLE));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
