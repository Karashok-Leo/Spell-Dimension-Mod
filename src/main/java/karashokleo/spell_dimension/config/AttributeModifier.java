package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.content.object.EnlighteningModifier;
import karashokleo.spell_dimension.util.RandomUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.UuidUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchool;

import java.util.ArrayList;
import java.util.List;

public record AttributeModifier(EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation)
{
    /**
     * Generic Attributes
     */
    private static final List<AttributeModifier> modifiers = List.of(
        new AttributeModifier(EntityAttributes.GENERIC_ARMOR, 1, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 1, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, 0.05, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.1, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_LUCK, 1, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, 1, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.01, EntityAttributeModifier.Operation.ADDITION),
        new AttributeModifier(SpellPowerMechanics.HASTE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE),
        new AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE),
        new AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.attribute, 0.01, EntityAttributeModifier.Operation.MULTIPLY_BASE)
    );

    public static final float GENERIC_CHANCE = 0.16F;

    /**
     * Get a random Attribute Modifier
     *
     * @param random random
     * @return an attribute modifier with GENERIC_CHANCE to modify generic attributes, otherwise modify spell power attributes
     */
    public static AttributeModifier getRandom(Random random, List<SpellSchool> schools)
    {
        return random.nextFloat() < GENERIC_CHANCE ?
            RandomUtil.randomFromList(random, modifiers) :
            new AttributeModifier(
                RandomUtil.randomFromList(random, schools).attribute,
                1,
                EntityAttributeModifier.Operation.ADDITION
            );
    }

    public static List<AttributeModifier> getAll()
    {
        List<AttributeModifier> all = new ArrayList<>();
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            all.add(new AttributeModifier(school.attribute, 1, EntityAttributeModifier.Operation.ADDITION));
        }
        all.addAll(modifiers);
        return all;
    }

    public EnlighteningModifier toELM()
    {
        return new EnlighteningModifier(
            this.attribute(),
            UuidUtil.getSelfUuid(this.operation()),
            this.amount(),
            this.operation()
        );
    }

    public EnlighteningModifier toELM(EquipmentSlot slot)
    {
        return new EnlighteningModifier(
            this.attribute(),
            UuidUtil.getEquipmentUuid(slot, this.operation()),
            this.amount(),
            this.operation()
        );
    }
}
