package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.spell_dimension.content.component.EndStageComponent;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EndStageItem extends Item
{
    public EndStageItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if (user instanceof ServerPlayerEntity player)
        {
            if (EndStageComponent.canEnterEnd(user))
            {
                if (player.getAbilities().creativeMode && player.isSneaking())
                {
                    player.sendMessage(SDTexts.TEXT$PROGRESS_ROLLBACK.get(), true);
                    EndStageComponent.setCanEnterEnd(user, true);
                }
            } else
            {
                ServerPlayNetworking.send(player, new S2CTitle(SDTexts.TEXT$END_STAGE.get()));
                S2CUndying packet = new S2CUndying(player);
                LHNetworking.toClientPlayer(player, packet);
                LHNetworking.toTracking(player, packet);
                EndStageComponent.setCanEnterEnd(user, true);
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(SDTexts.TOOLTIP$END_STAGE.get());
    }
}
