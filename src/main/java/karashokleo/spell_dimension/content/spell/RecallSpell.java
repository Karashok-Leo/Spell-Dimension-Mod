package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class RecallSpell
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

        ServerWorld serverWorld = (ServerWorld) self.getWorld();

        Vec3d dir = self.getRotationVector()
            .multiply(1, 0, 1)
            .normalize();

        Vec3d targetPos = self.getPos();
        final float step = 1f;
        for (int i = 2; i >= 0; i--)
        {
            float distance = i * step;
            targetPos = self.getPos().add(dir.multiply(distance));
            Box box = player.getType().getDimensions().getBoxAt(targetPos);
            if (serverWorld.isSpaceEmpty(player, box))
            {
                break;
            }
        }
        player.teleport(
            serverWorld,
            targetPos.getX(),
            targetPos.getY(),
            targetPos.getZ(),
            self.getYaw(),
            self.getPitch()
        );

        SoulControl.setControllingMinion(player, null);
    }
}
