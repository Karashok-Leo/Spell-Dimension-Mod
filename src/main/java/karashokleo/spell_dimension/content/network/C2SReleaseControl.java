package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.server.network.ServerPlayerEntity;

public record C2SReleaseControl() implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        SoulControl.setControllingMinion(player, null);
    }
}
