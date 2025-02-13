package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class ConsciousOceanEvent
{
    public static void init()
    {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) ->
        {
            if (!destination.getRegistryKey().equals(AllWorldGen.OC_WORLD)) return;

            AttributeUtil.addModifier(player, LHMiscs.ADD_LEVEL, UuidUtil.getUUIDFromString("spell_dimension:conscious_difficulty"), "Conscious Difficulty Bonus", 100, EntityAttributeModifier.Operation.ADDITION);
        });

        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if (GameStageComponent.isNormalMode(player))
                return TypedActionResult.pass(stack);
            if (!stack.isOf(ConsumableItems.HOSTILITY_ORB) &&
                !stack.isOf(ConsumableItems.BOTTLE_SANITY))
                return TypedActionResult.pass(stack);
            player.sendMessage(SDTexts.TEXT$DIFFICULTY$BAN_ITEM.get(), true);
            return TypedActionResult.fail(stack);
        });
    }
}
