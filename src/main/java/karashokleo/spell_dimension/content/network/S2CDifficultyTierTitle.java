package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;

@SerialClass
public record S2CDifficultyTierTitle(int difficulty) implements SerialPacketS2C
{
    @Override
    public void handle(ClientPlayerEntity player)
    {
        InGameHud inGameHud = MinecraftClient.getInstance().inGameHud;
        inGameHud.setTitle(
                SDTexts.TOOLTIP$DIFFICULTY_TIER$ENTER.get(
                        SDTexts.getDifficultyTierText(difficulty)
                )
        );
    }
}
