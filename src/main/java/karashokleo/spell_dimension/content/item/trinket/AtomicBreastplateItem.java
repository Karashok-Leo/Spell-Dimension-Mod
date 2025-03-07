package karashokleo.spell_dimension.content.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import karashokleo.spell_dimension.config.EssenceLootConfig;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.SoundUtil;
import net.aleganza.plentyofarmors.item.ModItems;
import net.combatroll.api.event.ServerSideRollEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
            // Blacklist
            if (EssenceLootConfig.BASE_CONFIG.blacklist().contains(event.getEntity().getType()))
                return;
            float amount = event.getAmount();
            for (ItemStack stack : TrinketCompat.getTrinketItems(event.getEntity(), stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
                Upgrade.FLEX.addProgress(stack, amount);
            DamageSource source = event.getSource();
            if (source.isIn(LHTags.MAGIC) &&
                source.getAttacker() instanceof LivingEntity attacker)
                for (ItemStack stack : TrinketCompat.getTrinketItems(attacker, stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
                    Upgrade.ENCHANTED.addProgress(stack, amount);
        });

        // To Flicker Breastplate
        ServerSideRollEvents.PLAYER_START_ROLLING.register((player, vec3d) ->
        {
            for (ItemStack stack : TrinketCompat.getTrinketItems(player, stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
                Upgrade.FLICKER.addProgress(stack, 1);
        });

        // To Oblivion Breastplate
        LivingHealCallback.EVENT.register(event ->
        {
            for (ItemStack stack : TrinketCompat.getTrinketItems(event.getEntity(), stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
                Upgrade.OBLIVION.addProgress(stack, event.getAmount());
            return true;
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

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player))
            for (Upgrade upgrade : Upgrade.values())
                if (upgrade.getProgressRate(stack) >= 1 &&
                    otherStack.isOf(upgrade.ingredientSupplier.get()))
                {
                    slot.setStack(upgrade.itemSupplier.get());
                    cursorStackReference.get().decrement(1);
                    SoundUtil.playSound(player, SoundUtil.ANVIL);
                    return true;
                }
        return false;
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
        for (Upgrade upgrade : Upgrade.values())
            upgrade.appendTooltip(tooltip, stack);
    }

    public enum Upgrade
    {
        ENCHANTED(
                AllItems.ENCHANTED_BREASTPLATE::getDefaultStack,
                () -> Items.NETHERITE_BLOCK,
                "EnchantedProgress",
                100000,
                Formatting.DARK_PURPLE,
                SDTexts.TOOLTIP$ATOMIC_TO_ENCHANTED
        ),
        FLEX(
                AllItems.FLEX_BREASTPLATE::getDefaultStack,
                () -> ModItems.HEART_OF_THE_END,
                "FlexProgress",
                1000,
                Formatting.DARK_RED,
                SDTexts.TOOLTIP$ATOMIC_TO_FLEX
        ),
        FLICKER(
                AllItems.FLICKER_BREASTPLATE::getDefaultStack,
                () -> ComplementItems.CAPTURED_WIND,
                "FlickerProgress",
                300,
                Formatting.GOLD,
                SDTexts.TOOLTIP$ATOMIC_TO_FLICKER
        ),
        OBLIVION(
                AllItems.OBLIVION_BREASTPLATE::getDefaultStack,
                () -> Registries.ITEM.get(new Identifier("simplyswords:runic_tablet")),
                "OblivionProgress",
                1000,
                Formatting.AQUA,
                SDTexts.TOOLTIP$ATOMIC_TO_OBLIVION
        );

        public final Supplier<ItemStack> itemSupplier;
        public final Supplier<Item> ingredientSupplier;
        public final String progressKey;
        public final double maxProgress;
        public final Formatting formatting;
        public final SDTexts tooltipText;

        Upgrade(
                Supplier<ItemStack> itemSupplier,
                Supplier<Item> ingredientSupplier,
                String progressKey,
                double maxProgress,
                Formatting formatting,
                SDTexts tooltipText
        )
        {
            this.itemSupplier = itemSupplier;
            this.ingredientSupplier = ingredientSupplier;
            this.progressKey = progressKey;
            this.maxProgress = maxProgress;
            this.formatting = formatting;
            this.tooltipText = tooltipText;
        }

        public double getProgress(ItemStack stack)
        {
            return stack.getOrCreateNbt().getDouble(progressKey);
        }

        public double getProgressRate(ItemStack stack)
        {
            return getProgress(stack) / maxProgress;
        }

        public void addProgress(ItemStack stack, double toAdd)
        {
            stack.getOrCreateNbt().putDouble(this.progressKey, this.getProgress(stack) + toAdd);
        }

        public void appendTooltip(List<Text> tooltip, ItemStack stack)
        {
            tooltip.add(
                    this.tooltipText.get(
                            this.maxProgress
                    ).formatted(this.formatting)
            );
            double progressRate = getProgressRate(stack);
            tooltip.add(
                    SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS.get(
                            "%.1f%%".formatted(progressRate * 100)
                    ).formatted(this.formatting)
            );
            if (progressRate >= 1)
                tooltip.add(
                        SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_UPGRADEABLE.get(
                                this.ingredientSupplier.get().getName()
                        ).formatted(Formatting.GREEN)
                );
        }
    }
}
