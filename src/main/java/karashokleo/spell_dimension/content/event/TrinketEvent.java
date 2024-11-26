package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.leobrary.damage.api.modify.DamageModifier;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.content.item.trinket.AtomicBreastplateItem;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import karashokleo.spell_dimension.init.AllDamageTypes;
import karashokleo.spell_dimension.init.AllItems;
import net.combatroll.api.event.ServerSideRollEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class TrinketEvent
{
    public static void init()
    {
        // Heart Steel
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) ->
        {
            if (source.getAttacker() instanceof LivingEntity attacker)
                for (var e : TrinketCompat.getTrinketItems(attacker, e -> e.isOf(AllItems.HEART_STEEL)))
                    AllItems.HEART_STEEL.accumulate(e, attacker, entity, source, amount);
            return true;
        });

        // Rejuvenating Blossom
        LivingDamageEvent.DAMAGE.register(event ->
        {
            LivingEntity entity = event.getEntity();
            int num = TrinketCompat.getTrinketItems(
                    entity,
                    e -> e.isOf(AllItems.REJUVENATING_BLOSSOM)
            ).size();
            if (num == 0) return;
            AllItems.REJUVENATING_BLOSSOM.rejuvenate(entity, (int) (num * event.getAmount()));
        });

        // Armor of Convergence
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, vec3d) ->
        {
            if (TrinketCompat.hasItemInTrinket(player, AllItems.ARMOR_OF_CONVERGENCE))
                ConvergeSpell.convergeImpact(player, player, player.getPos().add(0, 1, 0));
        });

        AtomicBreastplateItem.registerUpgradeEvents();

        // Flex Breastplate
        // Oblivion Breastplate
        DamagePhase.APPLY.registerModifier(0, damageAccess ->
        {
            LivingEntity entity = damageAccess.getEntity();
            if (TrinketCompat.hasItemInTrinket(entity, AllItems.FLEX_BREASTPLATE))
            {
                damageAccess.addModifier(DamageModifier.multiply(AllItems.FLEX_BREASTPLATE.getDamageFactor(entity)));
            } else if (!damageAccess.getSource().isOf(AllDamageTypes.OBLIVION_BREASTPLATE))
            {
                List<ItemStack> trinketItems = TrinketCompat.getTrinketItems(entity, e -> e.isOf(AllItems.OBLIVION_BREASTPLATE));
                if (!trinketItems.isEmpty())
                {
                    ItemStack stack = trinketItems.get(0);
                    float originalDamage = damageAccess.getOriginalDamage();
                    float oblivionAmount = AllItems.OBLIVION_BREASTPLATE.getOblivionAmount(stack);
                    if (oblivionAmount != 0)
                    {
                        if (originalDamage > oblivionAmount)
                        {
                            damageAccess.addModifier(DamageModifier.add(-oblivionAmount));
                            AllItems.OBLIVION_BREASTPLATE.increaseOblivionAmount(stack, -oblivionAmount);
                        } else
                        {
                            damageAccess.addModifier(DamageModifier.add(-originalDamage));
                            AllItems.OBLIVION_BREASTPLATE.increaseOblivionAmount(stack, -originalDamage);
                        }
                    }
                }
            }
        });

        // Flicker Breastplate
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!(event.getSource().getAttacker() instanceof LivingEntity attacker)) return;
            LivingEntity entity = event.getEntity();
            if (TrinketCompat.hasItemInTrinket(entity, AllItems.FLICKER_BREASTPLATE)
                && AllItems.FLICKER_BREASTPLATE.willFlicker(entity, attacker))
                event.setCanceled(true);
        });
    }
}
