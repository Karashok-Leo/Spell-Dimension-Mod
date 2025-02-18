package karashokleo.spell_dimension.content.item;

import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.content.network.S2CTitle;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EndStageItem extends Item
{
    public EndStageItem()
    {
        super(new FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if (user instanceof ServerPlayerEntity player)
        {
            if (GameStageComponent.canEnterEnd(user))
            {
                if (player.getAbilities().creativeMode && player.isSneaking())
                {
                    player.sendMessage(SDTexts.TEXT$PROGRESS_ROLLBACK.get(), true);
                    GameStageComponent.setCanEnterEnd(player, false);
                }
            } else
            {
                GameStageComponent.setCanEnterEnd(player, true);
                S2CTitle title = new S2CTitle(SDTexts.TEXT$END_STAGE.get());
                AllPackets.toClientPlayer(player, title);
                S2CUndying undyingParticles = new S2CUndying(player);
                LHNetworking.toClientPlayer(player, undyingParticles);
                LHNetworking.toTracking(player, undyingParticles);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, player.getSoundCategory(), 1.0F, 1.0F);
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
