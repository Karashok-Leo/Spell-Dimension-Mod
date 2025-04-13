package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

@SerialClass
public record S2CTitle(Text text) implements SerialPacketS2C
{
    @Environment(EnvType.CLIENT)
    @Override
    public void handle(ClientPlayerEntity player)
    {
        InGameHud inGameHud = MinecraftClient.getInstance().inGameHud;
        inGameHud.setTitle(text);
    }
}
