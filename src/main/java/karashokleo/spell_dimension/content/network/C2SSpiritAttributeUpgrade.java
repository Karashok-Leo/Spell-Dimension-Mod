package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.config.SpiritUpgradeConfig;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

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
        if (!upgrade.canUpgrade(player))
        {
            return;
        }
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        if (!component.tryConsumeSpirit(upgrade.getCost(player)))
        {
            return;
        }
        if (!upgrade.toModifier().applyToEntityOrPlayer(player))
        {
            return;
        }
        EntityAttributeInstance instance = player.getAttributeInstance(upgrade.attribute());
        if (instance == null)
        {
            return;
        }
        player.networkHandler.sendPacket(new EntityAttributesS2CPacket(player.getId(), List.of(instance)));
    }
}
