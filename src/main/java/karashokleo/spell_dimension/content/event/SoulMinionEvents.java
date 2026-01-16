package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.S2CBloodOverlay;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.*;
import karashokleo.spell_dimension.util.DamageUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.ConfigurableKnockback;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class SoulMinionEvents
{
    public static final float REQUIEM_FACTOR = 0.15f;
    public static final float SOUL_MARK_FACTOR = 1.5f;

    public static void init()
    {
        // player death stops soul control
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) ->
        {
            if (entity instanceof ServerPlayerEntity player)
            {
                SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
                if (!controllerComponent.isControlling())
                {
                    return true;
                }

                // controlling
                // rebirth
                if (TrinketCompat.hasItemInTrinket(player, AllItems.REBIRTH_SIGIL))
                {
                    AllItems.REBIRTH_SIGIL.rebirth(player, player);
                    return false;
                }

                // release control
                SoulControl.setControllingMinion(player, null);
                return false;
            } else if (entity instanceof MobEntity mob)
            {
                SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
                PlayerEntity owner = minionComponent.getOwner();
                if (owner != null &&
                    TrinketCompat.hasItemInTrinket(owner, AllItems.REBIRTH_SIGIL))
                {
                    AllItems.REBIRTH_SIGIL.rebirth(mob, owner);
                    return false;
                }
            }
            return true;
        });

        // prevent minion attack owner
        // requiem: extra soul magic damage
        LivingDamageEvent.DAMAGE.register(event ->
        {
            PlayerEntity owner = null;
            DamageSource source = event.getSource();
            Entity attacker = source.getAttacker();
            LivingEntity living = event.getEntity();

            World world = living.getWorld();
            if (world.isClient())
            {
                return;
            }

            if (attacker instanceof MobEntity mob)
            {
                SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
                owner = minionComponent.getOwner();
                // prevent attack owner
                if (living == owner)
                {
                    event.setCanceled(true);
                    return;
                }
            } else if (attacker instanceof PlayerEntity player)
            {
                SoulControllerComponent controllerComponent = SoulControl.getSoulController(player);
                if (controllerComponent.isControlling())
                {
                    owner = player;
                }
            }

            if (owner == null)
            {
                return;
            }

            if (source.hasState(AllDamageStates.REQUIEM))
            {
                return;
            }

            // passive
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(owner.getMainHandStack(), owner);

            if (spellContainer == null)
            {
                return;
            }

            if (!spellContainer.spell_ids.contains(AllSpells.REQUIEM.toString()))
            {
                return;
            }

            // extra soul magic damage
            float factor = REQUIEM_FACTOR;
            if (spellContainer.spell_ids.contains(AllSpells.SOUL_DUET.toString()))
            {
                factor *= 2;
            }

            float amount = (float) DamageUtil.calculateDamage(owner, SpellSchools.SOUL, factor);
            // at least 1 damage
            amount = Math.max(1.0f, amount);
            DamageSource damageSource = SpellDamageSource.create(SpellSchools.SOUL, owner);
            damageSource.setBypassCooldown();
            damageSource.addState(AllDamageStates.REQUIEM);
            // apply damage
            ((ConfigurableKnockback) living).pushKnockbackMultiplier_SpellEngine(0);
            living.damage(damageSource, amount);
            ((ConfigurableKnockback) living).popKnockbackMultiplier_SpellEngine();

            SpellImpactEvents.POST.invoker().invoke(world, owner, List.of(living), new SpellInfo(SpellRegistry.getSpell(AllSpells.REQUIEM), AllSpells.REQUIEM));
        });

        // fake player self damage feedback
        DamagePhase.APPLY.addListener(9999, access ->
        {
            if (!(access.getEntity() instanceof FakePlayerEntity fakePlayer))
            {
                return;
            }

            if (!(fakePlayer.getPlayer() instanceof ServerPlayerEntity player))
            {
                return;
            }

            // this is safe because DamageAccess#getModifiedDamage is not called in DamageAccess#addModifier
            float finalDamage = access.getModifiedDamage();
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

        LivingDamageEvent.DAMAGE.register(event ->
        {
            if (!event.getEntity().hasStatusEffect(AllStatusEffects.SOUL_MARK))
            {
                return;
            }
            if (!event.getSource().isOf(SpellSchools.SOUL.damageType))
            {
                return;
            }
            event.setAmount(event.getAmount() * SOUL_MARK_FACTOR);
        });

        // Rebirth Sigil
        // Soul Net - damage
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!(event.getEntity() instanceof MobEntity mob))
            {
                return;
            }

            // Rebirth
            if (mob.hasStatusEffect(AllStatusEffects.REBIRTH))
            {
                event.setCanceled(true);
                return;
            }

            SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
            PlayerEntity owner = minionComponent.getOwner();
            if (owner == null)
            {
                return;
            }

            // passive
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(owner.getMainHandStack(), owner);
            if (spellContainer == null ||
                !spellContainer.spell_ids.contains(AllSpells.PHANTOM_SYNDICATE.toString()))
            {
                return;
            }

            DamageSource source = event.getSource();
            if (source.hasState(AllDamageStates.SOUL_NET::equals))
            {
                return;
            }
            source.addState(AllDamageStates.SOUL_NET);

            SoulControllerComponent controllerComponent = SoulControl.getSoulController(owner);
            List<MobEntity> activeMinions = controllerComponent.getActiveMinions();

            // split damage
            float amount = event.getAmount() / activeMinions.size();
            for (MobEntity minion : activeMinions)
            {
                minion.damage(source, amount);
            }

            event.setCanceled(true);
        });

        // Soul Net - heal
        LivingHealCallback.EVENT.register(event ->
        {
            if (!(event.getEntity() instanceof MobEntity mob))
            {
                return true;
            }

            SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
            PlayerEntity owner = minionComponent.getOwner();
            if (owner == null)
            {
                return true;
            }

            // passive
            SpellContainer spellContainer = SpellContainerHelper.getEquipped(owner.getMainHandStack(), owner);
            if (spellContainer == null ||
                !spellContainer.spell_ids.contains(AllSpells.PHANTOM_SYNDICATE.toString()))
            {
                return true;
            }

            if (minionComponent.soulNetFlag > 0)
            {
                return true;
            }

            // prevent recursion
            minionComponent.soulNetFlag++;

            SoulControllerComponent controllerComponent = SoulControl.getSoulController(owner);
            List<MobEntity> activeMinions = controllerComponent.getActiveMinions();

            // split heal
            float amount = event.getAmount() / activeMinions.size();
            for (MobEntity minion : activeMinions)
            {
                minion.heal(amount);
            }

            minionComponent.soulNetFlag--;

            event.setAmount(0);
            return true;
        });
    }
}
