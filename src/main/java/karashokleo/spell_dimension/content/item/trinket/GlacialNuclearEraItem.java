package karashokleo.spell_dimension.content.item.trinket;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlacialNuclearEraItem extends SingleEpicTrinketItem
{
    public GlacialNuclearEraItem()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$GLACIAL_NUCLEAR_ERA$1.get());
        tooltip.add(SDTexts.TOOLTIP$GLACIAL_NUCLEAR_ERA$2.get());
        super.appendTooltip(stack, world, tooltip, context);
    }
}
