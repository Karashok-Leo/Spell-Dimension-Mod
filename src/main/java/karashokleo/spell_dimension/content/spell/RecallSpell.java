package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
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

        SoulControl.teleportNearSomeone(player, self);

        SoulControl.setControllingMinion(player, null);
    }
}
