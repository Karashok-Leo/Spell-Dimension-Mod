package karashokleo.spell_dimension.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class AttributeUtil
{
    /**
     * @param attribute entity attribute
     * @return Identifier of the attribute in registry
     */
    public static String getAttributeId(EntityAttribute attribute)
    {
        Identifier id = Registries.ATTRIBUTE.getId(attribute);
        return id == null ? "" : id.toString();
    }

    /**
     * Add tooltips from an attribute modifier
     *
     * @param tooltip   tooltip
     * @param attribute attribute
     * @param amount    amount
     * @param operation operation
     */
    public static void addTooltip(List<Text> tooltip, EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation)
    {
        MutableText text = getTooltip(attribute, amount, operation);
        if (text != null) tooltip.add(text);
    }

    @Nullable
    public static MutableText getTooltip(EntityAttribute attribute, double amount, EntityAttributeModifier.Operation operation)
    {
        double e = operation == EntityAttributeModifier.Operation.MULTIPLY_BASE || operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? amount * 100.0 : (attribute.equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? amount * 10.0 : amount);

        if (amount > 0.0)
        {
            return Text.translatable("attribute.modifier.plus." + operation.getId(), ItemStack.MODIFIER_FORMAT.format(e), Text.translatable(attribute.getTranslationKey())).formatted(Formatting.BLUE);
        } else if (amount < 0.0)
            return Text.translatable("attribute.modifier.take." + operation.getId(), ItemStack.MODIFIER_FORMAT.format(e * -1.0), Text.translatable(attribute.getTranslationKey())).formatted(Formatting.RED);
        else return null;
    }

    /**
     * Add a persistent attribute modifier to a living
     *
     * @param entity    entity
     * @param attribute attribute
     * @param uuid      uuid
     * @param name      name
     * @param value     value
     * @param operation operation
     */
    public static void addModifier(LivingEntity entity, EntityAttribute attribute, UUID uuid, String name, double value, EntityAttributeModifier.Operation operation)
    {
        EntityAttributeInstance instance = entity.getAttributeInstance(attribute);
        EntityAttributeModifier modifier = new EntityAttributeModifier(uuid, name, value, operation);
        if (instance == null || instance.hasModifier(modifier)) return;
        instance.addPersistentModifier(modifier);
    }

    /**
     * Remove the attribute modifier with the given uuid
     *
     * @param entity    entity
     * @param attribute attribute
     * @param uuid      uuid
     */
    public static void removeModifier(LivingEntity entity, EntityAttribute attribute, UUID uuid)
    {
        EntityAttributeInstance instance = entity.getAttributeInstance(attribute);
        if (instance != null)
            instance.removeModifier(uuid);
    }
}
