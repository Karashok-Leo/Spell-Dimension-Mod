package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import karashokleo.spell_dimension.init.AllItems;
import net.combatroll.api.event.ServerSideRollEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;

public class TrinketEvent
{
    public static void init()
    {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) ->
        {
            if (source.getAttacker() instanceof LivingEntity attacker)
                for (var e : TrinketCompat.getTrinketItems(attacker, e -> e.isOf(AllItems.HEART_STEEL)))
                    AllItems.HEART_STEEL.accumulate(e, attacker, entity, source, amount);
            return true;
        });

        LivingDamageEvent.DAMAGE.register(event ->
        {
            LivingEntity entity = event.getEntity();
            int num = TrinketCompat.getTrinketItems(
                    entity,
                    e -> e.isOf(AllItems.REJUVENATING_BLOSSOM)
            ).size();
            AllItems.REJUVENATING_BLOSSOM.rejuvenate(entity, (int) (num * event.getAmount()));
        });

        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, vec3d) ->
        {
            if (TrinketCompat.hasItemInTrinket(player, AllItems.ARMOR_OF_CONVERGENCE))
                ConvergeSpell.convergeImpact(player, player, player.getPos().add(0, 1, 0));
        });
    }
}
