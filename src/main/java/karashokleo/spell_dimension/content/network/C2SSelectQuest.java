package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@SerialClass
public record C2SSelectQuest(@Nullable Identifier questId, Hand hand) implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        ItemStack stackInHand = player.getStackInHand(hand);
        AllItems.QUEST_SCROLL.setQuest(stackInHand, questId);
    }
}
