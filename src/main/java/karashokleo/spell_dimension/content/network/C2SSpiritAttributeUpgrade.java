package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.config.SpiritUpgradeConfig;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

@SerialClass
public record C2SSpiritAttributeUpgrade(Identifier attributeId) implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(attributeId);
        if (upgrade == null)
        {
            return;
        }
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        if (!component.tryConsumeSpirit(upgrade.getCost(player)))
        {
            return;
        }
        upgrade.toModifier().applyToEntityOrPlayer(player);
    }
}
