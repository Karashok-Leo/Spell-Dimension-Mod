package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.logic.LevelEditor;
import karashokleo.spell_dimension.content.component.ConsciousModeComponent;
import karashokleo.spell_dimension.data.SDTexts;
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
            PlayerDifficulty cap = PlayerDifficulty.get(player);
            int playerLevel = cap.getLevel().level;
            if (playerLevel < 100)
            {
                LevelEditor editor = cap.getLevelEditor();
                editor.setBase(100);
                PlayerDifficulty.sync(player);
            }

            ConsciousModeComponent mode = ConsciousModeComponent.get(player);
            // Set conscious mode to true
            mode.setConsciousMode(true, player);

            // 0.5.2 removed the spawn point restriction
            // Set spawn point to the ocean if it's not already set in the ocean
//            if (!player.getSpawnPointDimension().equals(AllWorldGen.OC_WORLD))
//                player.setSpawnPoint(AllWorldGen.OC_WORLD, player.getBlockPos(), player.getSpawnAngle(), true, true);
        });

        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if ((stack.isOf(ConsumableItems.HOSTILITY_ORB) ||
                 stack.isOf(ConsumableItems.BOTTLE_SANITY)) &&
                ConsciousModeComponent.get(player).isConsciousMode())
            {
                player.sendMessage(SDTexts.TEXT$CONSCIOUS$BAN_ITEM.get(), true);
                return TypedActionResult.fail(stack);
            }
            return TypedActionResult.pass(stack);
        });
    }
}
