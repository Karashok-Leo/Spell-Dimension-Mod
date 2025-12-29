package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.LivingEntityExtensions;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import tocraft.walkers.api.PlayerShape;

import java.util.List;

public class SoulSacrificeSpell
{
    public static final float HEALTH_RATIO = 0.5F;

    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        Entity target = targets.isEmpty() ? player : targets.get(0);

        if (target instanceof MobEntity mob)
        {
            if (SoulControl.isSoulMinion(player, mob))
            {
                sacrifice(player, mob, mob);
            }
        } else if (target == player)
        {
            SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
            if (controllerComponent.isControlling())
            {
                LivingEntity shape = PlayerShape.getCurrentShape(player);
                if (shape != null)
                {
                    sacrifice(player, player, shape);
                }
            }
        }
    }

    private static void sacrifice(PlayerEntity caster, LivingEntity minionToDamage, LivingEntity minionToDrop)
    {
        // damage
        DamageSource damageSource = minionToDamage.getDamageSources().magic();
        float amount = minionToDamage.getMaxHealth() * HEALTH_RATIO;
        minionToDamage.damage(damageSource, amount);

        // drop
        ((LivingEntityExtensions) minionToDrop).dropSacrificeLoot(caster);
    }
}
