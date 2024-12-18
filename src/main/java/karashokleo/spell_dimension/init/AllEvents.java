package karashokleo.spell_dimension.init;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.event.*;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.content.spell.LightSpell;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPowerTags;

import java.util.Optional;

public class AllEvents
{
    public static void init()
    {
        PlayerHealthEvent.init();
        EnchantmentEvent.init();
        DifficultyEvent.init();
        TrinketEvent.init();
        LightSpell.init();
        ConsciousOceanEvent.init();

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) ->
        {
            if (entity instanceof PlayerEntity player)
            {
                PlayerInventory inventory = player.getInventory();
                AllItems.SPELL_CONTAINER.addKillCredit(inventory, killedEntity);
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) ->
        {
            // 刷怪笼掉落
            if (!(blockEntity instanceof MobSpawnerBlockEntity spawner)) return;
            ItemStack spawnerSoulStack = AllItems.SPAWNER_SOUL.getStack((ISpawnerExtension) spawner.getLogic());
            if (spawnerSoulStack.isEmpty()) return;
            Block.dropStack(world, pos, spawnerSoulStack);

            // 刷怪笼破坏计数
            AllItems.SPELL_CONTAINER.addBreakSpawnerCredit(player.getInventory());
        });

        // 调试棒伤害
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            if (!world.isClient() &&
                player.getStackInHand(hand).isOf(AllItems.DEBUG_STAFF))
            {
                SchoolUtil.getEntitySchool(player).stream().findFirst().ifPresent(school ->
                        entity.damage(SpellDamageSource.player(school, player), 999999));
            }
            return ActionResult.PASS;
        });

        // 深渊守护（远古守卫者）锁船长阶段
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            PlayerInventory inventory = player.getInventory();
            if (!world.isClient() &&
                stack.isOf(AquamiraeItems.SHELL_HORN) &&
                !inventory.containsAny(itemStack -> itemStack.isOf(AllItems.ABYSS_GUARD)))
            {
                player.sendMessage(SDTexts.TEXT$ABYSS_GUARD.get(), true);
                return TypedActionResult.fail(stack);
            }
            return TypedActionResult.pass(stack);
        });

        adaptiveCompat();
    }

    private static final String ADAPTIVE_CACHE = "[SpellCache]";

    private static void adaptiveCompat()
    {
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
                    data.adapt(ADAPTIVE_CACHE + spellInfo.id().toString(), adaptLv);
            }
        });
    }

    /**
     * @return true to cancel further processing
     */
    public static boolean adaptSpell(int level, LivingEntity entity, LivingHurtEvent event)
    {
        if (event.getSource().isIn(SpellPowerTags.DamageType.ALL))
        {
            var cap = MobDifficulty.get(entity);
            if (cap.isEmpty()) return false;

            AdaptingTrait.Data data = cap.get().getOrCreateData(LHTraits.ADAPTIVE.getId(), AdaptingTrait.Data::new);
            Optional<String> firstToken = data.memory.stream().filter(string -> string.startsWith(ADAPTIVE_CACHE)).findFirst();
            if (firstToken.isEmpty()) return false;

            data.adapt(firstToken.get(), level)
                    .ifPresent(factor -> event.setAmount(event.getAmount() * factor));
            return true;
        }
        return false;
    }
}
