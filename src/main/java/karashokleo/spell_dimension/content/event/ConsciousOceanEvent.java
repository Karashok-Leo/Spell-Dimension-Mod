package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.spell_dimension.api.buff.Buff;
import karashokleo.spell_dimension.content.buff.Conscious;
import karashokleo.spell_dimension.content.component.ConsciousModeComponent;
import karashokleo.spell_dimension.init.AllWorldGen;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class ConsciousOceanEvent
{
    public static void init()
    {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) ->
        {
            if (!destination.getRegistryKey().equals(AllWorldGen.OC_WORLD)) return;

            // Set player level to 100 if it's lower
            DifficultyLevel playerLevel = PlayerDifficulty.get(player).getLevel();
            playerLevel.level = Math.max(playerLevel.level, 100);

            ConsciousModeComponent mode = ConsciousModeComponent.get(player);
            // Set conscious mode to true
            mode.consciousMode = true;

            // Set remaining time if cost
            if (mode.cost)
            {
                // Apply the Conscious buff
                Buff.apply(player, Conscious.TYPE, new Conscious(playerLevel.level * 60), player);
            } else mode.cost = true;

            // Set spawn point to the ocean if it's not already set in the ocean
            if (!player.getSpawnPointDimension().equals(AllWorldGen.OC_WORLD))
                player.setSpawnPoint(AllWorldGen.OC_WORLD, player.getBlockPos(), player.getSpawnAngle(), true, true);
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) ->
        {
            if (origin.getRegistryKey().equals(AllWorldGen.OC_WORLD))
                Buff.remove(player, Conscious.TYPE);
        });

        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if ((stack.isOf(ConsumableItems.HOSTILITY_ORB) ||
                 stack.isOf(ConsumableItems.BOTTLE_SANITY)) &&
                ConsciousModeComponent.get(player).consciousMode)
                return TypedActionResult.fail(stack);
            return TypedActionResult.pass(stack);
        });
    }
}
