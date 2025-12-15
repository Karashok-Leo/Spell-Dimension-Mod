package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulSwapSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
        if (!controllerComponent.isControlling())
        {
            return;
        }

        FakePlayerEntity self = controllerComponent.getFakePlayerSelf();
        assert self != null;

        ServerWorld targetWorld = (ServerWorld) self.getWorld();
        double targetX = self.getX();
        double targetY = self.getY();
        double targetZ = self.getZ();
        float targetYaw = self.getYaw();
        float targetPitch = self.getPitch();

        self.teleport(
            player.getServerWorld(),
            player.getX(),
            player.getY(),
            player.getZ(),
            PositionFlag.VALUES,
            player.getYaw(),
            player.getPitch()
        );

        player.teleport(
            targetWorld,
            targetX,
            targetY,
            targetZ,
            targetYaw,
            targetPitch
        );
    }
}
