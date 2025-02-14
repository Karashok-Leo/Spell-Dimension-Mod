package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

@SerialClass
public record S2CFloatingItem(ItemStack stack) implements SerialPacketS2C
{
    @Override
    public void handle(ClientPlayerEntity player)
    {
        MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
    }
}
