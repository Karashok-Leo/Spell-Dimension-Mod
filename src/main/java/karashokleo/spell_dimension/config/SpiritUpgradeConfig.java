package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpiritUpgradeConfig
{
    private static final UUID SPIRIT_UPGRADE_UUID = UuidUtil.getUUIDFromString("spell-dimension:spirit_upgrade");
    private static final Map<Identifier, SpiritUpgrade> UPGRADES = new HashMap<>();

    public static void init()
    {
        register(EntityAttributes.GENERIC_MAX_HEALTH, 1, 10, 0.07);
        register(EntityAttributes.GENERIC_ARMOR, 1, 10, 0.07);
        register(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1, 15, 0.07);
        register(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.1, 20, 0.09);
        register(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01, 20, 0.09);
        register(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1, 15, 0.11);
        register(EntityAttributes.GENERIC_ATTACK_SPEED, 0.1, 20, 0.11);
        register(EntityAttributes.GENERIC_LUCK, 1, 15, 0.11);
        register(SpellSchools.SOUL.attribute, 1, 10, 0.06);
        register(SpellPowerMechanics.CRITICAL_CHANCE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE, 20, 0.13);
        register(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE, 20, 0.13);
        register(SpellPowerMechanics.HASTE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE, 15, 0.15);
    }

    private static void register(EntityAttribute attribute, double amount, int cost, double costGrowthPerLevel)
    {
        register(attribute, amount, EntityAttributeModifier.Operation.ADDITION, cost, costGrowthPerLevel);
    }

    private static void register(EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation, int cost, double costGrowthPerLevel)
    {
        Identifier id = Registries.ATTRIBUTE.getId(attribute);
        if (id == null)
        {
            return;
        }
        UPGRADES.put(id, new SpiritUpgrade(attribute, amount, operation, cost, costGrowthPerLevel));
    }

    @Nullable
    public static SpiritUpgrade get(EntityAttribute attribute)
    {
        Identifier id = attribute == null ? null : Registries.ATTRIBUTE.getId(attribute);
        return id == null ? null : UPGRADES.get(id);
    }

    @Nullable
    public static SpiritUpgrade get(Identifier attributeId)
    {
        return attributeId == null ? null : UPGRADES.get(attributeId);
    }

    public record SpiritUpgrade(
        EntityAttribute attribute,
        double amount,
        EntityAttributeModifier.Operation operation,
        int baseCost,
        double costGrowthPerLevel
    )
    {
        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean canUpgrade(PlayerEntity player)
        {
            if (player == null)
            {
                return false;
            }
            EntityAttributeInstance instance = player.getAttributeInstance(attribute);
            if (instance == null)
            {
                return false;
            }
            double maxValue = attribute instanceof ClampedEntityAttribute clamped ? clamped.getMaxValue() : Double.POSITIVE_INFINITY;
            return !Double.isFinite(maxValue) ||
                instance.getValue() + 1.0E-8 < maxValue;
        }

        public int getCost(PlayerEntity player)
        {
            if (player == null)
            {
                return baseCost;
            }
            double level = getUpgradeLevel(player);
            double scale = 1.0 + Math.max(0.0, level) * costGrowthPerLevel;
            double multiplier = scale * scale;
            long scaled = (long) Math.ceil(baseCost * multiplier);
            if (scaled < baseCost)
            {
                return baseCost;
            }
            return scaled > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) scaled;
        }

        public EnlighteningModifier toModifier()
        {
            return new EnlighteningModifier(
                attribute,
                SPIRIT_UPGRADE_UUID,
                amount,
                operation
            );
        }

        private double getUpgradeLevel(PlayerEntity player)
        {
            EntityAttributeInstance instance = player.getAttributeInstance(attribute);
            if (instance == null || amount == 0.0)
            {
                return 0.0;
            }
            EntityAttributeModifier modifier = instance.getModifier(SPIRIT_UPGRADE_UUID);
            if (modifier == null)
            {
                return 0.0;
            }
            double current = modifier.getValue();
            if (operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            {
                if (amount <= -1.0 || current <= -1.0)
                {
                    return 0.0;
                }
                return Math.log1p(current) / Math.log1p(amount);
            }
            return current / amount;
        }
    }
}
