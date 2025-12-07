package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.client.render.DamageBloodOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;

@SerialClass
public record S2CBloodOverlay() implements SerialPacketS2C
{
    @Environment(EnvType.CLIENT)
    @Override
    public void handle(ClientPlayerEntity player)
    {
        DamageBloodOverlay.show();
    }
}
