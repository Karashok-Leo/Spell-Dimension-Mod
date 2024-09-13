package karashokleo.spell_dimension.init;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.api.quest.QuestUsage;
import karashokleo.spell_dimension.config.ScrollLootConfig;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;

import java.util.Optional;
import java.util.Set;

public class AllEvents
{
    public static void init()
    {
        PlayerHealthEvent.init();

        // 调试棒伤害
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            if (!world.isClient() &&
                player.getStackInHand(hand).isOf(AllItems.DEBUG_STAFF))
            {
                entity.damage(player.getDamageSources().create(DamageTypes.MAGIC, player), 999999);
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

        registerCauldronBehavior();
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

    private static void registerCauldronBehavior()
    {
        // Spell Scroll Craft
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.PAPER, (state, world, pos, player, hand, stack) ->
        {
            if (!world.isClient() && hand == Hand.MAIN_HAND)
            {
                Item item = stack.getItem();
                Identifier craftSpellId = ScrollLootConfig.getCraftSpellId(player.getOffHandStack());
                if (craftSpellId != null)
                {
                    player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, AllItems.SPELL_SCROLL.getStack(craftSpellId)));
                    LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                    player.incrementStat(Stats.USE_CAULDRON);
                    player.incrementStat(Stats.USED.getOrCreateStat(item));
                }
            }
            return ActionResult.success(world.isClient());
        });

        // Quest Scroll Craft
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(Items.WRITABLE_BOOK, (state, world, pos, player, hand, stack) ->
        {
            if (!world.isClient() && hand == Hand.MAIN_HAND)
            {
                Set<RegistryEntry<Quest>> currentQuests = QuestUsage.getCurrentQuests(player);
                currentQuests.stream()
                        .map(AllItems.QUEST_SCROLL::getStack)
                        .forEach(itemStack -> player.getInventory().offerOrDrop(itemStack));
                if (!currentQuests.isEmpty())
                {
                    LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                    player.incrementStat(Stats.USE_CAULDRON);
                    player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                    if (!player.getAbilities().creativeMode)
                        stack.decrement(1);
                }
            }
            return ActionResult.success(world.isClient());
        });
    }
}
