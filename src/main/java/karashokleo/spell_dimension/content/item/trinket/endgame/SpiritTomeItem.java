package karashokleo.spell_dimension.content.item.trinket.endgame;

import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritTomeItem extends SingleEpicTrinketItem
{
    public SpiritTomeItem()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
