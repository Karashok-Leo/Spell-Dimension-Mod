package karashokleo.spell_dimension.content.item.trinket.endgame;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (!(entity instanceof PlayerEntity player))
        {
            return false;
        }
        if (SpiritTomeComponent.get(player).getSpirit() < 0)
        {
            return false;
        }
        return super.canEquip(stack, slot, entity);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        return entity instanceof PlayerEntity player &&
            player.getAbilities().creativeMode;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
    }
}
