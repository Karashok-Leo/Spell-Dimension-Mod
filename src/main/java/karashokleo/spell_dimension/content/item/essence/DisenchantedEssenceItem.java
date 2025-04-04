package karashokleo.spell_dimension.content.item.essence;

import karashokleo.spell_dimension.content.item.essence.base.StackClickEssenceItem;
import karashokleo.spell_dimension.content.object.EnchantedModifier;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisenchantedEssenceItem extends StackClickEssenceItem
{
    public DisenchantedEssenceItem()
    {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target, PlayerEntity player)
    {
        return EnchantedModifier.remove(target);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$DISENCHANT.get().formatted(Formatting.GRAY));
    }
}
