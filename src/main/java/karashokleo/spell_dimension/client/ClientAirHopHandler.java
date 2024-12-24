package karashokleo.spell_dimension.client;

import artifacts.item.wearable.belt.CloudInABottleItem;
import artifacts.network.DoubleJumpPacket;
import artifacts.network.NetworkHandler;
import dev.architectury.event.events.client.ClientTickEvent;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientAirHopHandler
{
    private static int jumpTicks;

    public static void register()
    {
        ClientTickEvent.CLIENT_POST.register(ClientAirHopHandler::onClientTick);
    }

    private static void onClientTick(MinecraftClient instance)
    {
        ClientPlayerEntity player = instance.player;
        if (player == null || player.input == null) return;
        if (jumpTicks > 0) jumpTicks--;
        handleCloudInABottleInput(player);
    }

    private static void handleCloudInABottleInput(ClientPlayerEntity player)
    {
        if (player.input.jumping && jumpTicks == 0 && player.hasStatusEffect(AllStatusEffects.AIR_HOP))
        {
            NetworkHandler.CHANNEL.sendToServer(new DoubleJumpPacket());
            CloudInABottleItem.jump(player);
            player.setVelocity(player.getVelocity().x, 0.5, player.getVelocity().z);
            jumpTicks = 10;
        }
    }
}
