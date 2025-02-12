package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.l2hostility.util.MathHelper;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.attribute.EntityAttributeInstance;
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

            EntityAttributeInstance attributeInstance = player.getAttributeInstance(LHMiscs.ADD_LEVEL);
            if (attributeInstance == null) return;
            EntityAttributeModifier modifier = new EntityAttributeModifier(MathHelper.getUUIDFromString("spell_dimension:conscious_difficulty"), "Conscious Difficulty Bonus", 1, EntityAttributeModifier.Operation.ADDITION);
            if (attributeInstance.hasModifier(modifier)) return;
            attributeInstance.addPersistentModifier(modifier);
        });

        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if ((stack.isOf(ConsumableItems.HOSTILITY_ORB) ||
                 stack.isOf(ConsumableItems.BOTTLE_SANITY)) &&
                GameStageComponent.getDifficulty(player) > GameStageComponent.NORMAL)
            {
                player.sendMessage(SDTexts.TEXT$DIFFICULTY$BAN_ITEM.get(), true);
                return TypedActionResult.fail(stack);
            }
            return TypedActionResult.pass(stack);
        });
    }
}
