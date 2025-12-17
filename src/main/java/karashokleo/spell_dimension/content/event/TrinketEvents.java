package karashokleo.spell_dimension.content.event;

import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.event.TrinketDropCallback;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import karashokleo.spell_dimension.content.item.trinket.breastplate.AtomicBreastplateItem;
import karashokleo.spell_dimension.content.spell.ConvergeSpell;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.combatroll.api.event.ServerSideRollEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchool;

public class TrinketEvents
{
    public static final double PRIDE_BONUS = 0.02F;

    public static void init()
    {
        // keep trinkets
        TrinketDropCallback.EVENT.register((rule, stack, ref, entity) ->
        {
            return entity instanceof PlayerEntity ?
                TrinketEnums.DropRule.KEEP : rule;
//            if (entity instanceof PlayerEntity player &&
//                GameStageComponent.keepInventory(player))
//            {
//                return TrinketEnums.DropRule.KEEP;
//            }
//            return rule;
        });

        // Armor of Convergence
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, vec3d) ->
        {
            if (TrinketCompat.hasItemInTrinket(player, AllItems.ARMOR_OF_CONVERGENCE))
            {
                ConvergeSpell.convergeImpact(player, player, player.getPos().add(0, 1, 0), new SpellInfo(SpellRegistry.getSpell(AllSpells.CONVERGE), AllSpells.CONVERGE));
            }
        });

        // To Flicker Breastplate
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, vec3d) ->
        {
            for (ItemStack stack : TrinketCompat.getTrinketItems(player, stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
            {
                AtomicBreastplateItem.Upgrade.FLICKER.addProgress(stack, 1);
            }
        });

        // To Oblivion Breastplate
        LivingHealCallback.EVENT.register(event ->
        {
            for (ItemStack stack : TrinketCompat.getTrinketItems(event.getEntity(), stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
            {
                AtomicBreastplateItem.Upgrade.OBLIVION.addProgress(stack, event.getAmount());
            }
            return true;
        });

        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            school.addSource(
                SpellSchool.Trait.POWER,
                SpellSchool.Apply.MULTIPLY,
                args ->
                {
                    if (TrinketCompat.hasItemInTrinket(args.entity(), TrinketItems.CURSE_PRIDE))
                    {
                        int level = DifficultyLevel.ofAny(args.entity());
                        return level * PRIDE_BONUS;
                    }
                    return 0D;
                }

            );
        }
    }
}
