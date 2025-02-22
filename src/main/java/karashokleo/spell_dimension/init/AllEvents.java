package karashokleo.spell_dimension.init;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.event.TrinketDropCallback;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.feature.EntityFeature;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.event.*;
import karashokleo.spell_dimension.content.item.SpellScrollItem;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.content.network.C2SSelectQuest;
import karashokleo.spell_dimension.content.spell.LightSpell;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellPowerTags;
import net.trique.mythicupgrades.MythicUpgradesDamageTypes;

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

        // attempt to fix NaN health
        LivingEntityEvents.LivingTickEvent.TICK.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.age % 10 != 0) return;
            if (!Float.isNaN(entity.getHealth())) return;
            entity.setHealth(0);
            System.out.printf("Fixed NaN health for %s, why is this happening?", entity);
        });

        PlayerInteractionEvents.LEFT_CLICK_EMPTY.register(event ->
        {
            if (event.getItemStack().isOf(AllItems.QUEST_SCROLL))
                AllPackets.toServer(new C2SSelectQuest(null, event.getHand()));
        });

        // dungeon item blacklist
        UseItemCallback.EVENT.register((player, world, hand) ->
        {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isIn(AllTags.DUNGEON_BANNED) &&
                AllWorldGen.disableInWorld(world))
                return TypedActionResult.fail(stack);
            return TypedActionResult.pass(stack);
        });

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

        // Deflection
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!event.getSource().isOf(MythicUpgradesDamageTypes.DEFLECTING_DAMAGE_TYPE))
                return;
            LivingEntity entity = event.getEntity();
            if (EntityFeature.MAGIC_REJECT.test(entity) ||
                TrinketCompat.hasItemInTrinket(entity, TrinketItems.RING_DIVINITY))
                event.setCanceled(true);
        });

        DamagePhase.SHIELD.registerModifier(0, access ->
        {
            if (!access.getSource().isOf(MythicUpgradesDamageTypes.DEFLECTING_DAMAGE_TYPE))
                return;
            float maxDamage = access.getEntity().getMaxHealth();
            access.addModifier(originalDamage -> Math.min(originalDamage, maxDamage));
        });

        // cancel offhand block placement interaction while holding spell scroll
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
                (world.isClient() &&
                 hand == Hand.OFF_HAND &&
                 player.getOffHandStack().getItem() instanceof BlockItem &&
                 player.getMainHandStack().getItem() instanceof SpellScrollItem) ? ActionResult.FAIL : ActionResult.PASS);

        LivingEntityEvents.LivingTickEvent.TICK.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.getWorld().getFluidState(entity.getBlockPos()).isIn(AllTags.CONSCIOUSNESS))
                entity.setVelocity(entity.getVelocity().add(0.0, 0.08, 0.0));
        });

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
