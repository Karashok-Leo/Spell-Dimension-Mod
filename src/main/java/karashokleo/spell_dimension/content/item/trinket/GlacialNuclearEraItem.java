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

public class GlacialNuclearEraItem extends SingleEpicTrinketItem
{
    public static final float CHANCE = 0.12F;

    public GlacialNuclearEraItem()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$GLACIAL_NUCLEAR_ERA$1.get(Math.round(CHANCE * 100)).formatted(Formatting.AQUA));
        tooltip.add(SDTexts.TOOLTIP$GLACIAL_NUCLEAR_ERA$2.get(Math.round(CHANCE * 100)).formatted(Formatting.DARK_AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
