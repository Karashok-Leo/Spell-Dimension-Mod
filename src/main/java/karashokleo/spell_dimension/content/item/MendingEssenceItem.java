package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.data.LangData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MendingEssenceItem extends StackClickEssenceItem
{
    public MendingEssenceItem()
    {
        super();
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target)
    {
        if (target.getItem().isDamageable())
        {
            target.setDamage(0);
            target.setRepairCost(0);
            return true;
        } else return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_MENDING));
    }
}
