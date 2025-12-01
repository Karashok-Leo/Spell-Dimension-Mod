package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

public class SoulControlEvents
{
    public static void init()
    {
        // Soul Container capture mob
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) ->
        {
            if (!(entity instanceof MobEntity mob))
            {
                return true;
            }
            if (!(damageSource.getAttacker() instanceof PlayerEntity player))
            {
                return true;
            }
            ItemStack stack = player.getOffHandStack();
            if (!stack.isOf(AllItems.SOUL_CONTAINER))
            {
                return true;
            }
            return !AllItems.SOUL_CONTAINER.tryCaptureEntity(stack, player, mob);
        });

        // control minion interaction
        PlayerInteractionEvents.INTERACT_ENTITY_GENERAL.register((player, entity, hand) ->
        {
            if (hand == Hand.OFF_HAND)
            {
                return ActionResult.PASS;
            }
            if (!(player instanceof ServerPlayerEntity serverPlayer))
            {
                return ActionResult.PASS;
            }
            if (!(entity instanceof MobEntity mob))
            {
                return ActionResult.PASS;
            }
            if (!serverPlayer.isSneaking())
            {
                return ActionResult.PASS;
            }
            var component = SoulControl.getSoulMinion(mob);
            if (component == null)
            {
                return ActionResult.PASS;
            }
            if (component.getOwner() != serverPlayer)
            {
                return ActionResult.PASS;
            }
            SoulControl.setControllingMinion(serverPlayer, mob);
            return ActionResult.SUCCESS;
        });

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

        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!SoulControl.isSoulMinion(event.getEntity(), event.getSource().getAttacker()))
            {
                return;
            }
            event.setCanceled(true);
        });

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

            player.sendMessage(Text.literal("You are receiving damage!!!").formatted(Formatting.RED));
        });
    }
}
