package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.content.item.SoulAlbumItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

/**
 * @param index -1 represents deselection
 * @param hand
 */
@SerialClass
public record C2SSelectSoulAlbum(int index, Hand hand) implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        ItemStack stack = player.getStackInHand(hand);
        if (!(stack.getItem() instanceof SoulAlbumItem album))
        {
            return;
        }
        album.select(stack, index, player.getWorld());
    }
}
