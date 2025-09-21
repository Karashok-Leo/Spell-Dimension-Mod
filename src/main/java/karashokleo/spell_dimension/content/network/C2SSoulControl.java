package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.object.SoulInput;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

@SerialClass
public record C2SSoulControl(SoulInput input) implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        MobEntity minion = SoulControl.getSoulMinion(player).getMinion(player.getServerWorld());
        if (minion == null ||
            minion.isDead() ||
            minion.isRemoved())
        {
            SoulControl.setControllingMinion(player, null);
            return;
        }

        if (input.controlling)
        {
            handleMobMovement(minion, player);
        } else
        {
            SoulControl.setControllingMinion(player, null);
        }
    }

    private void handleMobMovement(MobEntity mob, PlayerEntity player)
    {
//        mob.setYaw(player.getYaw());
//        mob.setBodyYaw(mob.getYaw());
//        mob.setHeadYaw(mob.getYaw());
//        mob.prevYaw = mob.getYaw();
//        mob.setPitch(player.getPitch());
//        mob.prevPitch = mob.getPitch();
        SoulControllerComponent component = SoulControl.getSoulController(mob);
        if (component == null)
        {
            return;
        }
        component.setInput(input);
    }
}
