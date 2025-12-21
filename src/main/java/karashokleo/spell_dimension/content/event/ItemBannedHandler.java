package karashokleo.spell_dimension.content.event;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class ItemBannedHandler implements UseItemCallback, UseBlockCallback
{
    private boolean use(ItemStack stack, PlayerEntity player, World world)
    {
        if (stack.isIn(AllTags.BANNED))
        {
            if (world.isClient())
            {
                player.sendMessage(SDTexts.TEXT$BANNED_ITEM.get().formatted(Formatting.RED));
            }
            return false;
        }
        // dimension banned items
        else if (stack.isIn(AllTags.DUNGEON_BANNED) &&
            AllWorldGen.disableInWorld(world))
        {
            if (world.isClient())
            {
                player.sendMessage(SDTexts.TEXT$DIMENSION_BANNED_ITEM.get().formatted(Formatting.RED));
            }
            return false;
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> interact(PlayerEntity player, World world, Hand hand)
    {
        ItemStack stack = player.getStackInHand(hand);
        return use(stack, player, world) ?
            TypedActionResult.pass(stack) :
            TypedActionResult.fail(stack);
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult blockHitResult)
    {
        ItemStack stack = player.getStackInHand(hand);
        return use(stack, player, world) ?
            ActionResult.PASS :
            ActionResult.FAIL;
    }
}
