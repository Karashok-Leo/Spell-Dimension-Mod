package karashokleo.spell_dimension.init;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.event.EnchantmentEvent;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.spell_power.api.SpellDamageSource;

import java.util.Optional;

public class AllEvents
{
    public static void init()
    {
        PlayerHealthEvent.init();
        EnchantmentEvent.init();

        // 调试棒伤害
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            if (!world.isClient() &&
                player.getStackInHand(hand).isOf(AllItems.DEBUG_STAFF))
            {
                SchoolUtil.getPlayerSchool(player).stream().findFirst().ifPresent(school ->
                        entity.damage(SpellDamageSource.player(school, player), 999999));
            }
            return ActionResult.PASS;
        });

        // 深渊守护（远古守卫者）锁船长阶段
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if (!world.isClient() &&
                stack.isOf(AquamiraeItems.SHELL_HORN) &&
                !player.getInventory().contains(AllTags.SHELL_HORN_REQUIREMENT))
            {
                player.sendMessage(SDTexts.TEXT$ABYSS_GUARD.get(), true);
                return TypedActionResult.fail(stack);
            }
            return TypedActionResult.pass(stack);
        });

        adaptiveCompat();
    }

    private static void adaptiveCompat()
    {
        String adaptiveDataPrefix = "[SpellCache]";

        SpellImpactEvents.BEFORE.register((world, caster, targets, spellInfo) ->
        {
            for (Entity target : targets)
            {
                Optional<MobDifficulty> optional = MobDifficulty.get(target);
                if (optional.isEmpty()) return;
                MobDifficulty diff = optional.get();
                AdaptingTrait.Data data = diff.getOrCreateData(LHTraits.ADAPTIVE.getId(), AdaptingTrait.Data::new);
                int adaptLv = diff.getTraitLevel(LHTraits.ADAPTIVE);
                if (adaptLv > 0)
                    data.adapt(adaptiveDataPrefix + spellInfo.id().toString(), adaptLv);
            }
        });

        AdaptingTrait.EVENT.register((level, entity, event, data) ->
        {
            if (event.getSource().isOf(DamageTypes.MAGIC))
            {
                Optional<String> first = data.memory.stream().filter(s -> s.startsWith(adaptiveDataPrefix)).findFirst();
                if (first.isPresent()) return first.get();
            }
            return null;
        });
    }
}
