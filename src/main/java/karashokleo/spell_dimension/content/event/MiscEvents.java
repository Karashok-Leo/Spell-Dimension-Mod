package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.feature.EntityFeature;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.content.item.SpellScrollItem;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import karashokleo.spell_dimension.content.network.C2SSelectQuest;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.init.AllWorldGen;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.spell_power.api.SpellDamageSource;
import net.trique.mythicupgrades.MythicUpgradesDamageTypes;

public class MiscEvents
{
    public static void init()
    {
        // attempt to fix NaN health
        LivingEntityEvents.LivingTickEvent.TICK.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.age % 10 != 0) return;
            if (!Float.isNaN(entity.getHealth())) return;
            entity.setHealth(0);
            System.out.printf("Fixed NaN health for %s, why is this happening?", entity);
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

        // quest scroll
        PlayerInteractionEvents.LEFT_CLICK_EMPTY.register(event ->
        {
            if (event.getItemStack().isOf(AllItems.QUEST_SCROLL))
                AllPackets.toServer(new C2SSelectQuest(null, event.getHand()));
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

        // Spell Container
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) ->
        {
            if (entity instanceof PlayerEntity player)
            {
                PlayerInventory inventory = player.getInventory();
                AllItems.SPELL_CONTAINER.addKillCredit(inventory, killedEntity);
            }
        });

        // Spawner Soul
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
    }
}
