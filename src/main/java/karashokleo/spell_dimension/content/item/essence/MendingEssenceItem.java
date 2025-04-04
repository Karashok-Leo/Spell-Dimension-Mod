package karashokleo.spell_dimension.content.item.essence;

import karashokleo.spell_dimension.content.item.essence.base.StackClickEssenceItem;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MendingEssenceItem extends StackClickEssenceItem
{
    public MendingEssenceItem()
    {
        super(new FabricItemSettings());
    }

    @Override
    protected boolean applyEffect(ItemStack essence, ItemStack target, PlayerEntity player)
    {
        NbtCompound nbt = target.getNbt();
        if (nbt == null)
        {
            return false;
        } else
        {
            if (nbt.contains("Damage"))
                target.setDamage(0);
            if (nbt.contains("RepairCost"))
                target.setRepairCost(0);
            return true;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$MENDING.get().formatted(Formatting.GRAY));
    }
}
