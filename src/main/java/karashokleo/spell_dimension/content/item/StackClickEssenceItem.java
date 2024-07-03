package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.data.LangData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class StackClickEssenceItem extends SpellEssenceItem
{
    protected abstract boolean applyEffect(ItemStack essence, ItemStack target);

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player)
    {
        if (clickType == ClickType.RIGHT &&
                !player.getItemCooldownManager().isCoolingDown(stack.getItem()) &&
                !slot.getStack().isEmpty() &&
                applyEffect(stack, slot.getStack()))
        {
            success(stack, player);
            return true;
        } else return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(LangData.TOOLTIP_ESSENCE_USE_1));
        tooltip.add(ScreenTexts.EMPTY);
        tooltip.add(Text.translatable(LangData.TOOLTIP_EFFECT));
    }
}
