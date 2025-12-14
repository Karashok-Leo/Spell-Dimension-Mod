package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.S2CBloodOverlay;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

public class SoulControlEvents
{
    public static void init()
    {
        // player death stops soul control
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) ->
        {
            if (!(entity instanceof ServerPlayerEntity player))
            {
                return true;
            }

            SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
            if (!controllerComponent.isControlling())
            {
                return true;
            }

            SoulControl.setControllingMinion(player, null);
            return false;
        });

        // prevent minion attack owner
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!SoulControl.isSoulMinion(event.getEntity(), event.getSource().getAttacker()))
            {
                return;
            }
            event.setCanceled(true);
        });

        // fake player self damage feedback
        DamagePhase.APPLY.registerModifier(9999, access ->
        {
            if (!(access.getEntity() instanceof FakePlayerEntity fakePlayer))
            {
                return;
            }

            if (!(fakePlayer.getPlayer() instanceof ServerPlayerEntity player))
            {
                return;
            }

            float finalDamage = access.getModifiedDamage(access.getOriginalDamage());
            if (finalDamage <= 0.0f)
            {
                return;
            }

            player.sendMessage(SDTexts.TEXT$SOUL_CONTROL$DAMAGE.get("%.1f".formatted(finalDamage)).formatted(Formatting.RED, Formatting.BOLD));

            AllPackets.toClientPlayer(player, new S2CBloodOverlay());
        });
    }
}
