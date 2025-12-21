package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents;
import karashokleo.enchantment_infusion.api.event.InfusionCompleteCallback;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.event.GenericEvents;
import karashokleo.l2hostility.content.feature.EntityFeature;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.trait.common.ReflectTrait;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.damage.api.modify.DamageAccess;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.item.DynamicSpellBookItem;
import karashokleo.spell_dimension.content.item.SpellScrollItem;
import karashokleo.spell_dimension.content.misc.SpawnerExtension;
import karashokleo.spell_dimension.content.network.C2SSelectQuest;
import karashokleo.spell_dimension.init.*;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.adventurez.entity.VoidShadeEntity;
import net.adventurez.entity.VoidShadowEntity;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellContainerHelper;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;
import net.trique.mythicupgrades.MythicUpgradesDamageTypes;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MiscEvents
{
    private record DamageTracker(DamagePhase phase) implements Consumer<DamageAccess>
    {
        @Override
        public void accept(DamageAccess access)
        {
            if (!(access.getEntity() instanceof PlayerEntity player))
            {
                return;
            }
            GameRules gameRules = player.getWorld().getGameRules();

            boolean enable = gameRules.getBoolean(AllGameRules.ENABLE_DAMAGE_TRACKER);
            if (!enable)
            {
                return;
            }

            boolean immune = gameRules.getBoolean(AllGameRules.IMMUNE_TRACKED_DAMAGE);
            if (immune && phase == DamagePhase.APPLY)
            {
                // set to 0 or 0.1 ?
                access.addModifier(originalDamage -> 0);
            }

            int threshold = gameRules.getInt(AllGameRules.DAMAGE_TRACKER_THRESHOLD);
            float damage = access.getOriginalDamage();
            if (damage <= player.getMaxHealth() * threshold / 100)
            {
                return;
            }
            // info
            Text playerName = player.getName();
            Text attackerName = Optional.ofNullable(access.getAttacker()).map(Entity::getName).orElse(Text.literal("None"));
            String damageType = access.getDamageType().msgId();
            // log
            if (phase == DamagePhase.SHIELD)
            {
                SpellDimension.LOGGER.info("--Spell Dimension Damage Tracker--");
                SpellDimension.LOGGER.info("Player: {}", playerName.getString());
                SpellDimension.LOGGER.info("Attacker: {}", attackerName.getString());
                SpellDimension.LOGGER.info("Damage Type: {}", damageType);
                SpellDimension.LOGGER.info("Damage Amount: ");

                Thread.dumpStack();
            }
            SpellDimension.LOGGER.info("- {} phase: {}", phase.name(), damage);
            // send message
            boolean notify = gameRules.getBoolean(AllGameRules.NOTIFY_DAMAGE_TRACKER);
            if (!notify)
            {
                return;
            }
            if (phase == DamagePhase.SHIELD)
            {
                player.sendMessage(Text.literal("--Spell Dimension Damage Tracker--").formatted(Formatting.DARK_RED));
                player.sendMessage(Text.literal("Player: ").append(playerName).formatted(Formatting.RED));
                player.sendMessage(Text.literal("Attacker: ").append(attackerName).formatted(Formatting.RED));
                player.sendMessage(Text.literal("Damage Type: ").append(damageType).formatted(Formatting.RED));
                player.sendMessage(Text.literal("Damage Amount: ").formatted(Formatting.RED));
            }
            player.sendMessage(Text.literal("- %s phase: %f".formatted(phase.name(), damage)).formatted(Formatting.RED));
        }
    }

    public static void init()
    {
        // Damage Tracker
        for (DamagePhase phase : DamagePhase.values())
        {
            phase.addListener(9999, new DamageTracker(phase));
        }

        // Fix dynamic spell book nbt copy
        InfusionCompleteCallback.EVENT.register((world, pos, output, inventory, recipe) ->
        {
            if (!(output.getItem() instanceof DynamicSpellBookItem bookItem))
            {
                return;
            }
            SpellContainer container = SpellContainerHelper.containerFromItemStack(output).copy();
            SpellContainer modifiedContainer = container.copy();
            modifiedContainer.max_spell_count = bookItem.getMaxSpellCount();
            SpellContainerHelper.addContainerToItemStack(modifiedContainer, output);
        });

        // Spell Prism
        DamagePhase.SHIELD.addListener(0, access ->
        {
            if (!access.getSource().isIn(LHTags.MAGIC))
            {
                return;
            }
            if (!(access.getAttacker() instanceof LivingEntity attacker))
            {
                return;
            }
            ItemStack stack = attacker.getOffHandStack();
            if (!(stack.isOf(AllItems.SPELL_PRISM)))
            {
                return;
            }
            stack.damage(1, attacker, e -> e.sendToolBreakStatus(Hand.OFF_HAND));
            access.getSource().setBypassMagic();
        });

        // attempt to fix NaN health
        LivingEntityEvents.LivingTickEvent.TICK.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.age % 10 != 0)
            {
                return;
            }
            if (!Float.isNaN(entity.getHealth()))
            {
                return;
            }
            entity.setHealth(0);
            System.out.printf("Fixed NaN health for %s, why is this happening?", entity);
        });

        // banned items
        ItemBannedHandler handler = new ItemBannedHandler();
        UseItemCallback.EVENT.register(handler);
        UseBlockCallback.EVENT.register(handler);

        // quest scroll
        PlayerInteractionEvents.LEFT_CLICK_EMPTY.register(event ->
        {
            if (event.getItemStack().isOf(AllItems.QUEST_SCROLL))
            {
                AllPackets.toServer(new C2SSelectQuest(null, event.getHand()));
            }
        });

        // Deflection
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!event.getSource().isOf(MythicUpgradesDamageTypes.DEFLECTING_DAMAGE_TYPE))
            {
                return;
            }
            LivingEntity entity = event.getEntity();
            if (EntityFeature.MAGIC_REJECT.test(entity) ||
                TrinketCompat.hasItemInTrinket(entity, TrinketItems.RING_DIVINITY))
            {
                event.setCanceled(true);
            }
        });
        DamagePhase.SHIELD.addListener(0, access ->
        {
            if (!access.getSource().isOf(MythicUpgradesDamageTypes.DEFLECTING_DAMAGE_TYPE))
            {
                return;
            }
            float maxDamage = access.getEntity().getMaxHealth();
            access.addModifier(originalDamage -> Math.min(originalDamage, maxDamage));
        });

        // cancel offhand block placement interaction while holding spell scroll
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
            (world.isClient() &&
                hand == Hand.OFF_HAND &&
                player.getOffHandStack().getItem() instanceof BlockItem &&
                player.getMainHandStack().getItem() instanceof SpellScrollItem) ? ActionResult.FAIL : ActionResult.PASS);

        // Spawner Soul
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) ->
        {
            // 刷怪笼掉落
            if (!(blockEntity instanceof MobSpawnerBlockEntity spawner))
            {
                return;
            }
            ItemStack spawnerSoulStack = AllItems.SPAWNER_SOUL.getStack((SpawnerExtension) spawner.getLogic());
            if (spawnerSoulStack.isEmpty())
            {
                return;
            }
            Block.dropStack(world, pos, spawnerSoulStack);
        });

        // 调试棒伤害
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            if (!world.isClient() &&
                player.getStackInHand(hand).isOf(AllItems.DEBUG_STAFF))
            {
                SchoolUtil.getLivingSchools(player).stream().findFirst().ifPresent(school ->
                    entity.damage(SpellDamageSource.player(school, player), 999999));
            }
            return ActionResult.PASS;
        });

        // 虚空之影伤害
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) ->
        {
            if (!(entity instanceof VoidShadeEntity))
            {
                return true;
            }
            if (!(damageSource.getAttacker() instanceof LivingEntity living))
            {
                return true;
            }
            World world = entity.getWorld();
            if (world.isClient())
            {
                return true;
            }
            List<VoidShadowEntity> list = world.getEntitiesByClass(VoidShadowEntity.class, entity.getBoundingBox().expand(128), EntityPredicates.EXCEPT_SPECTATOR);
            if (list.isEmpty())
            {
                return true;
            }
            VoidShadowEntity shadow = list.get(0);
            float shadowMaxHealth = shadow.getMaxHealth();
            if (shadow.getHealth() < shadowMaxHealth * 0.5f)
            {
                return true;
            }
            float damage = Math.min(shadowMaxHealth * 0.05f, damageAmount);
            shadow.damage(living.getDamageSources().indirectMagic(entity, living), damage);
            return true;
        });

        // Quantum Field
        DamagePhase.SHIELD.addListener(0, access ->
        {
            DamageSource source = access.getSource();
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            {
                return;
            }
            if (source.hasState(ReflectTrait.REFLECT_STATE::equals))
            {
                return;
            }
            LivingEntity entity = access.getEntity();
            if (!entity.hasStatusEffect(AllStatusEffects.QUANTUM_FIELD))
            {
                return;
            }

            if (!(source.getAttacker() instanceof LivingEntity attacker))
            {
                return;
            }

            float distance = entity.distanceTo(attacker);
            float ratio = AllStatusEffects.QUANTUM_FIELD.getReflectRatio(distance);

            access.addModifier(originalDamage -> originalDamage * ratio);
            float reflect = access.getOriginalDamage() * (1 - ratio);
            if (reflect <= 0)
            {
                return;
            }

            DamageSource damageSource = SpellDamageSource.create(SpellSchools.LIGHTNING, attacker);
            damageSource.addState(ReflectTrait.REFLECT_STATE);
            GenericEvents.schedule(() -> attacker.damage(damageSource, reflect));
        });

        // Criterion for mined ores
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) ->
        {
            if (!state.isIn(ConventionalBlockTags.ORES))
            {
                return;
            }
            player.incrementStat(AllStats.MINED_ORES);
            if (player instanceof ServerPlayerEntity serverPlayer)
            {
                AllCriterions.MINED_ORES.trigger(serverPlayer);
            }
        });
    }
}
