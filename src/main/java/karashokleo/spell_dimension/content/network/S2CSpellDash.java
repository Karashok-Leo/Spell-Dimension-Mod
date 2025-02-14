package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.mixin.client.RollManagerInvoker;
import net.combatroll.internals.RollingEntity;
import net.minecraft.client.network.ClientPlayerEntity;

@SerialClass
public record S2CSpellDash() implements SerialPacketS2C
{
    @Override
    public void handle(ClientPlayerEntity player)
    {
        if (player instanceof RollingEntity rolling)
            ((RollManagerInvoker) rolling.getRollManager()).invokeRechargeRoll(player);
    }
}
