package karashokleo.spell_dimension.content.spell;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllDamageStates;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EtherealEvasionSpell
{
    public static void onAttack(LivingAttackEvent event)
    {
        if (!(event.getEntity() instanceof ServerPlayerEntity player) ||
            !player.hasStatusEffect(AllStatusEffects.ETHEREAL_EVASION))
        {
            return;
        }

        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
            source.hasState(AllDamageStates.ETHEREAL_EVASION::equals))
        {
            return;
        }

        if (player.getRandom().nextBoolean())
        {
            event.setCanceled(true);
            showNegationParticles(player);
            return;
        }

        List<MobEntity> minions = SoulControl.getSoulController(player)
            .getActiveMinions()
            .stream()
            .filter(minion -> minion.isAlive() && !minion.isRemoved() && !minion.hasStatusEffect(AllStatusEffects.REBIRTH))
            .toList();
        if (minions.isEmpty())
        {
            return;
        }

        MobEntity minion = minions.get(player.getRandom().nextInt(minions.size()));
        source.addState(AllDamageStates.ETHEREAL_EVASION);
        event.setCanceled(true);
        showTransferParticles(player, minion);
        minion.damage(source, event.getAmount());
    }

    private static void showNegationParticles(ServerPlayerEntity player)
    {
        ServerWorld world = player.getServerWorld();
        world.spawnParticles(
            ParticleTypes.SOUL_FIRE_FLAME,
            player.getX(),
            player.getY() + player.getHeight() * 0.5,
            player.getZ(),
            4,
            0.35,
            0.5,
            0.35,
            0.03
        );
    }

    private static void showTransferParticles(ServerPlayerEntity player, MobEntity minion)
    {
        ServerWorld world = player.getServerWorld();
        Vec3d start = player.getPos().add(0, player.getHeight() * 0.5, 0);
        Vec3d end = minion.getPos().add(0, minion.getHeight() * 0.5, 0);
        Vec3d step = end.subtract(start).multiply(1.0 / 12);

        for (int i = 0; i <= 12; i++)
        {
            Vec3d pos = start.add(step.multiply(i));
            world.spawnParticles(
                ParticleTypes.SOUL,
                pos.x,
                pos.y,
                pos.z,
                1,
                0,
                0,
                0,
                0
            );
        }
    }
}
