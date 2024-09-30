package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.init.AllItems;
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
    }
}
