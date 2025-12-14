package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PossessSpell
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
        Entity target = targets.get(0);
        if (target instanceof MobEntity mob)
        {
            if (SoulControl.isSoulMinion(player, mob))
            {
                SoulControl.setControllingMinion(player, mob);
            }
        } else if (target instanceof FakePlayerEntity)
        {
            SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
            if (controllerComponent.getFakePlayerSelf() == target)
            {
                SoulControl.setControllingMinion(player, null);
            }
        }
    }

    @Nullable
    private static MobEntity findTarget(ServerPlayerEntity player, List<Entity> targets)
    {
        if (targets.isEmpty())
        {
            return null;
        }
        Entity target = targets.get(0);
        if (!(target instanceof MobEntity mob))
        {
            return null;
        }
        if (!SoulControl.isSoulMinion(player, mob))
        {
            return null;
        }
        return mob;
    }
}
