package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.item.consumable.DrinkableBottleItem;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottleOfSoulBinding extends DrinkableBottleItem
{
    public BottleOfSoulBinding(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void doServerLogic(ServerPlayerEntity player)
    {
        boolean value = !GameStageComponent.keepInventory(player);
        GameStageComponent.setKeepInventory(player, value);
        player.sendMessage(value ? SDTexts.TEXT$SOUL_BIND.get() : SDTexts.TEXT$SOUL_UNBIND.get(), true);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(SDTexts.TOOLTIP$BOTTLE_SOUL_BINDING.get().formatted(Formatting.YELLOW));
        tooltip.add(SDTexts.TOOLTIP$BOTTLE_SOUL_BINDING$WARNING.get().formatted(Formatting.RED));
    }
}
