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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.TreeMap;
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
        LivingHeal.EVENT.register((entity, amount, amountRef, ci) ->
        {
            for (ItemStack stack : TrinketCompat.getTrinketItems(entity, stack -> stack.isOf(AllItems.ATOMIC_BREASTPLATE)))
                Upgrade.OBLIVION.addProgress(stack, amount);
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        TreeMap<Double, Upgrade> map = new TreeMap<>();
        for (Upgrade upgrade : Upgrade.values())
            map.put(upgrade.getProgressRate(stack), upgrade);
        var entry = map.lastEntry();
        if (entry.getKey() >= 1)
            return TypedActionResult.success(entry.getValue().itemSupplier.get());
        return super.use(world, user, hand);
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
                "EnchantedProgress",
                30000,
                Formatting.DARK_PURPLE,
                SDTexts.TOOLTIP$ATOMIC_TO_ENCHANTED
        ),
        FLEX(
                AllItems.FLEX_BREASTPLATE::getDefaultStack,
                "FlexProgress",
                2000,
                Formatting.DARK_RED,
                SDTexts.TOOLTIP$ATOMIC_TO_FLEX
        ),
        FLICKER(
                AllItems.FLICKER_BREASTPLATE::getDefaultStack,
                "FlickerProgress",
                1000,
                Formatting.GOLD,
                SDTexts.TOOLTIP$ATOMIC_TO_FLICKER
        ),
        OBLIVION(
                AllItems.OBLIVION_BREASTPLATE::getDefaultStack,
                "OblivionProgress",
                1000,
                Formatting.AQUA,
                SDTexts.TOOLTIP$ATOMIC_TO_OBLIVION
        );

        public final Supplier<ItemStack> itemSupplier;
        public final String progressKey;
        public final double maxProgress;
        public final Formatting formatting;
        public final SDTexts tooltipText;

        Upgrade(
                Supplier<ItemStack> itemSupplier,
                String progressKey,
                double maxProgress,
                Formatting formatting,
                SDTexts tooltipText
        )
        {
            this.itemSupplier = itemSupplier;
            this.progressKey = progressKey;
            this.maxProgress = maxProgress;
            this.formatting = formatting;
            this.tooltipText = tooltipText;
        }

        public String getProgressText(ItemStack stack)
        {
            return String.format("%.1f%%", getProgressRate(stack) * 100);
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
            tooltip.add(
                    SDTexts.TOOLTIP$ATOMIC_BREASTPLATE_PROGRESS.get(
                            this.getProgressText(stack)
                    ).formatted(this.formatting)
            );
        }
    }
}
