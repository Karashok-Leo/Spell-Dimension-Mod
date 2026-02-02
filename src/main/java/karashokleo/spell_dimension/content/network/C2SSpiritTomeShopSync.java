package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import net.minecraft.server.network.ServerPlayerEntity;

@SerialClass
public record C2SSpiritTomeShopSync() implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        SpiritTomeComponent.get(player).refreshShop();
    }
}
