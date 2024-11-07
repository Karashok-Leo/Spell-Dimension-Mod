package karashokleo.spell_dimension.content.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.effect.api.event.LivingHeal;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.combatroll.api.event.ServerSideRollEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class AtomicBreastplateItem extends TrinketItem
{
    public static final int ARMOR_ADDITION = 16;
    public static final int ARMOR_TOUGHNESS_ADDITION = 4;

    public static void registerUpgradeEvents()
    {
        // To Flex Breastplate / Enchanted Breastplate
        LivingDamageEvent.DAMAGE.register(event ->
        {
            float amount = event.getAmount();
            TrinketCompat.getItemAccessInTrinket(event.getEntity())
                    .stream()
                    .filter(access -> access.get().isOf(AllItems.ATOMIC_BREASTPLATE))
                    .forEach(access ->
                            AllItems.ATOMIC_BREASTPLATE.addProgress(access.get(), AtomicBreastplateItem.Upgrade.FLEX, amount).ifPresent(access::set)
                    );
            DamageSource source = event.getSource();
            if (source.isIn(LHTags.MAGIC) &&
                source.getAttacker() instanceof LivingEntity attacker)
                TrinketCompat.getItemAccessInTrinket(attacker)
                        .stream()
                        .filter(access -> access.get().isOf(AllItems.ATOMIC_BREASTPLATE))
                        .forEach(access ->
                                AllItems.ATOMIC_BREASTPLATE.addProgress(access.get(), AtomicBreastplateItem.Upgrade.ENCHANTED, amount).ifPresent(access::set)
                        );
        });

        // To Flicker Breastplate
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, vec3d) ->
        {
            TrinketCompat.getItemAccessInTrinket(player)
                    .stream()
                    .filter(access -> access.get().isOf(AllItems.ATOMIC_BREASTPLATE))
                    .forEach(access ->
                            AllItems.ATOMIC_BREASTPLATE.addProgress(access.get(), AtomicBreastplateItem.Upgrade.FLICKER, 1).ifPresent(access::set)
                    );
        });

        // To Oblivion Breastplate
        LivingHeal.EVENT.register((entity, amount, ci) ->
        {
            TrinketCompat.getItemAccessInTrinket(entity)
                    .stream()
                    .filter(access -> access.get().isOf(AllItems.ATOMIC_BREASTPLATE))
                    .forEach(access ->
                            AllItems.ATOMIC_BREASTPLATE.addProgress(access.get(), Upgrade.OBLIVION, amount).ifPresent(access::set)
                    );
        });
    }

    public AtomicBreastplateItem()
    {
        super(
                new FabricItemSettings()
                        .maxCount(1)
                        .fireproof()
                        .rarity(Rarity.EPIC)
        );
    }

    public Optional<ItemStack> addProgress(ItemStack stack, Upgrade upgrade, double toAdd)
    {
        double progress = upgrade.getProgress(stack);
        progress += toAdd;
        if (progress >= upgrade.maxProgress)
        {
            stack.decrement(1);
            return Optional.of(upgrade.itemSupplier.get());
        } else upgrade.setProgress(stack, progress);
        return Optional.empty();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(
                EntityAttributes.GENERIC_ARMOR,
                new EntityAttributeModifier(uuid, "Atomic Breastplate Armor", ARMOR_ADDITION, EntityAttributeModifier.Operation.ADDITION)
        );
        modifiers.put(
                EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                new EntityAttributeModifier(uuid, "Atomic Breastplate Armor Toughness", ARMOR_TOUGHNESS_ADDITION, EntityAttributeModifier.Operation.ADDITION)
        );
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_TO_ENCHANTED.get(
                        Upgrade.ENCHANTED.maxProgress
                ).formatted(Formatting.DARK_PURPLE)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS.get(
                        Upgrade.ENCHANTED.getProgressText(stack)
                ).formatted(Formatting.DARK_PURPLE)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_TO_FLEX.get(
                        Upgrade.FLEX.maxProgress
                ).formatted(Formatting.DARK_RED)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS.get(
                        Upgrade.FLEX.getProgressText(stack)
                ).formatted(Formatting.DARK_RED)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_TO_FLICKER.get(
                        Upgrade.FLICKER.maxProgress
                ).formatted(Formatting.GOLD)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS.get(
                        Upgrade.FLICKER.getProgressText(stack)
                ).formatted(Formatting.GOLD)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_TO_OBLIVION.get(
                        Upgrade.OBLIVION.maxProgress
                ).formatted(Formatting.AQUA)
        );
        tooltip.add(
                SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS.get(
                        Upgrade.OBLIVION.getProgressText(stack)
                ).formatted(Formatting.AQUA)
        );
    }

    public enum Upgrade
    {
        ENCHANTED("EnchantedProgress", 20000, AllItems.ENCHANTED_BREASTPLATE::getDefaultStack),
        FLEX("FlexProgress", 1000, AllItems.FLEX_BREASTPLATE::getDefaultStack),
        FLICKER("FlickerProgress", 1000, AllItems.FLICKER_BREASTPLATE::getDefaultStack),
        OBLIVION("OblivionProgress", 4000, AllItems.OBLIVION_BREASTPLATE::getDefaultStack);

        public final String progressKey;
        public final double maxProgress;
        public final Supplier<ItemStack> itemSupplier;

        Upgrade(String progressKey, double maxProgress, Supplier<ItemStack> itemSupplier)
        {
            this.progressKey = progressKey;
            this.maxProgress = maxProgress;
            this.itemSupplier = itemSupplier;
        }

        public String getProgressText(ItemStack stack)
        {
            return String.format("%.1f/%f", getProgress(stack), maxProgress);
        }

        public double getProgress(ItemStack stack)
        {
            return stack.getOrCreateNbt().getDouble(progressKey);
        }

        public void setProgress(ItemStack stack, double progress)
        {
            stack.getOrCreateNbt().putDouble(progressKey, progress);
        }
    }
}
