package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.client.screen.SoulAlbumScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;

@SerialClass
public record S2COpenSoulAlbumScreen(Hand hand) implements SerialPacketS2C
{
    @Environment(EnvType.CLIENT)
    @Override
    public void handle(ClientPlayerEntity player)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null)
        {
            return;
        }
        client.setScreen(new SoulAlbumScreen(hand));
    }
}
