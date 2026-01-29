package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpiritUpgradeConfig
{
    private static final Map<Identifier, SpiritUpgrade> UPGRADES = new HashMap<>();

    public static void init()
    {
        register(EntityAttributes.GENERIC_MAX_HEALTH, 1, 1);
        register(EntityAttributes.GENERIC_ARMOR, 1, 1);
        register(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1, 1);
        register(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.1, 10);
        register(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01, 1);
        register(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1, 1);
        register(EntityAttributes.GENERIC_ATTACK_SPEED, 0.1, 1);
        register(EntityAttributes.GENERIC_LUCK, 1, 1);
        register(SpellSchools.SOUL.attribute, 1, 1);
        register(SpellPowerMechanics.CRITICAL_CHANCE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE, 1);
        register(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE, 1);
        register(SpellPowerMechanics.HASTE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE, 1);
    }

    private static void register(EntityAttribute attribute, double amount, int cost)
    {
        register(attribute, amount, EntityAttributeModifier.Operation.ADDITION, cost);
    }

    private static void register(EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation, int cost)
    {
        Identifier id = Registries.ATTRIBUTE.getId(attribute);
        if (id == null)
        {
            return;
        }
        UPGRADES.put(id, new SpiritUpgrade(attribute, amount, operation, cost));
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

    public record SpiritUpgrade(EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation, int cost)
    {
        public EnlighteningModifier toModifier()
        {
            return new EnlighteningModifier(
                attribute,
                UuidUtil.getSelfUuid(operation),
                amount,
                operation
            );
        }
    }
}
