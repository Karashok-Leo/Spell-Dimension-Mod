package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulMarkSpell
{
    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        if (targets.isEmpty())
        {
            return;
        }
        if (!(targets.get(0) instanceof LivingEntity target))
        {
            return;
        }

        SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
        List<MobEntity> activeMinions = controllerComponent.getActiveMinions();
        for (MobEntity minion : activeMinions)
        {
            if (minion.distanceTo(target) > minion.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE))
            {
                continue;
            }
            minion.setTarget(target);
        }
    }
}
