package karashokleo.spell_dimension.content.spell;

import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SoulRallySpell
{
    public static final int DURATION = 20 * 12;
    public static final int AMPLIFIER = 2;

    public static void handle(World world, LivingEntity caster, List<Entity> targets, SpellInfo spellInfo)
    {
        if (!(caster instanceof ServerPlayerEntity player))
        {
            return;
        }

        List<MobEntity> activeMinions = SoulControl.getSoulController(player).getActiveMinions();
        for (MobEntity minion : activeMinions)
        {
            if (minion.distanceTo(player) > 6)
            {
                SoulControl.teleportNearSomeone(minion, player, true);
            }

            minion.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, DURATION, AMPLIFIER, false, false, true));
            minion.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, DURATION, AMPLIFIER, false, false, true));
            minion.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, DURATION, AMPLIFIER, false, false, true));
        }
    }
}
