package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.client.screen.QuestScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;

@SerialClass
public record S2COpenQuestScreen(Hand hand) implements SerialPacketS2C
{
    @Environment(EnvType.CLIENT)
    @Override
    public void handle(ClientPlayerEntity player)
    {
        var client = L2HostilityClient.getClient();
        if (client == null) return;
        client.setScreen(new QuestScreen(hand, QuestUsage.getCurrentQuests(player)));
    }
}
