package karashokleo.spell_dimension.content.item.trinket.breastplate;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.SoundUtil;
import net.aleganza.plentyofarmors.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class AtomicBreastplateItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public static final int ARMOR_ADDITION = 16;
    public static final int ARMOR_TOUGHNESS_ADDITION = 4;

    public AtomicBreastplateItem()
    {
        super();
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        // Blacklist
        if (event.getEntity()
            .getType()
            .isIn(AllTags.ESSENCE_LOOT_ENTITY_BLACKLIST))
        {
            return;
        }
        Upgrade.ENCHANTED.addProgress(stack, event.getAmount());
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        Upgrade.FLEX.addProgress(stack, event.getAmount());
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference)
    {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player))
        {
            for (Upgrade upgrade : Upgrade.values())
            {
                if (upgrade.getProgressRate(stack) >= 1 &&
                    otherStack.isOf(upgrade.ingredientSupplier.get()))
                {
                    slot.setStack(upgrade.itemSupplier.get());
                    otherStack.decrement(1);
                    SoundUtil.playSound(player, SoundUtil.ANVIL);
                    return true;
                }
            }
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
        for (Upgrade upgrade : Upgrade.values())
        {
            upgrade.appendTooltip(tooltip, stack);
        }
        super.appendTooltip(stack, world, tooltip, context);
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
            () -> ComplementItems.CAPTURED_BULLET,
            "FlickerProgress",
            100,
            Formatting.GOLD,
            SDTexts.TOOLTIP$ATOMIC_TO_FLICKER
        ),
        OBLIVION(
            AllItems.OBLIVION_BREASTPLATE::getDefaultStack,
            () -> Registries.ITEM.get(new Identifier("bosses_of_mass_destruction:brimstone_nectar")),
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
            {
                tooltip.add(
                    SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_UPGRADEABLE.get(
                        this.ingredientSupplier.get().getName()
                    ).formatted(Formatting.GREEN)
                );
            }
        }
    }
}
