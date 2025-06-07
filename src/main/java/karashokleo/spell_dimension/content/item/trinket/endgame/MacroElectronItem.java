package karashokleo.spell_dimension.content.item.trinket.endgame;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MacroElectronItem extends SingleEpicTrinketItem
{
    public MacroElectronItem()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$MACRO_ELECTRON.get().formatted(Formatting.AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
