package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.S2CBloodOverlay;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.DamageUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_power.api.SpellSchools;

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
        // extra soul magic damage
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!(event.getSource().getAttacker() instanceof MobEntity mob))
            {
                return;
            }

            SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
            PlayerEntity owner = minionComponent.getOwner();

            LivingEntity living = event.getEntity();
            // prevent attack owner
            if (living == owner)
            {
                event.setCanceled(true);
                return;
            }

            if (owner == null)
            {
                return;
            }

            // extra soul magic damage
            float factor = 0.15f;

            // passive
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(owner.getMainHandStack(), owner);
            if (spellContainer != null && spellContainer.spell_ids.contains(AllSpells.SOUL_DUET.toString()))
            {
                factor *= 2;
            }

            float amount = (float) DamageUtil.calculateDamage(owner, SpellSchools.SOUL, factor);
            // at least 1 damage
            amount = Math.max(1.0f, amount);
            DamageUtil.spellDamage(living, SpellSchools.SOUL, owner, amount, false);
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

        // default return true
        // minion added
        LivingEntityEvents.ON_JOIN_WORLD.register((entity, world, loadedFromDisk) ->
        {
            if (world.isClient())
            {
                return true;
            }

            if (!(entity instanceof MobEntity mob))
            {
                return true;
            }

            SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
            PlayerEntity owner = minionComponent.getOwner();
            if (owner == null)
            {
                return true;
            }

            SoulControllerComponent controllerComponent = SoulControl.getSoulController(owner);
            controllerComponent.onMinionAdded(mob);
            return true;
        });

        // minion removed
        LivingEntityEvents.ON_REMOVE.register((entity, reason) ->
        {
            if (entity.getWorld().isClient())
            {
                return;
            }

            if (!(entity instanceof MobEntity mob))
            {
                return;
            }

            SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
            PlayerEntity owner = minionComponent.getOwner();
            if (owner == null)
            {
                return;
            }

            SoulControllerComponent controllerComponent = SoulControl.getSoulController(owner);
            controllerComponent.onMinionRemoved(mob);
        });
    }
}
