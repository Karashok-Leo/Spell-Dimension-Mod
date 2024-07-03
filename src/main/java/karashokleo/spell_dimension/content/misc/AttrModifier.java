package karashokleo.spell_dimension.content.misc;

import com.google.common.collect.Multimap;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.AttributeResolver;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record AttrModifier(
        EntityAttribute attribute,
        UUID uuid, double amount,
        EntityAttributeModifier.Operation operation
)
{
    private static final String ATTR_MODIFIER_KEY = "AttrModifier";
    private static final String ATTRIBUTE_KEY = "Attribute";
    private static final String UUID_KEY = "Uuid";
    private static final String AMOUNT_KEY = "Amount";
    private static final String OPERATION_KEY = "Operation";

    public AttrModifier
    {
        if (attribute == null || uuid == null || operation == null)
            throw new IllegalArgumentException("Field 'attribute' or 'uuid' or 'operation' can not be null.");
    }

    public double getNewAmount(double oldAmount)
    {
        // Do special modification while Operation == MULTIPLY_TOTAL
        return operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? ((1 + amount) * (1 + oldAmount) - 1) : (amount + oldAmount);
    }

    public boolean applyToEntity(LivingEntity entity)
    {
        EntityAttributeInstance attributeInstance = entity.getAttributeInstance(attribute);
        if (attributeInstance == null) return false;
        EntityAttributeModifier oldModifier = attributeInstance.getModifier(uuid);
        double newAmount = oldModifier == null ? amount : getNewAmount(oldModifier.getValue());
        attributeInstance.removeModifier(uuid);
        attributeInstance.addPersistentModifier(toModifier(newAmount));
        return true;
    }

    public void applyToStack(Multimap<EntityAttribute, EntityAttributeModifier> modifiers)
    {
        modifiers.put(attribute, toModifier());
    }

    public EntityAttributeModifier toModifier()
    {
        return new EntityAttributeModifier(uuid, ATTR_MODIFIER_KEY, amount, operation);
    }

    public EntityAttributeModifier toModifier(double newAmount)
    {
        return new EntityAttributeModifier(uuid, ATTR_MODIFIER_KEY, newAmount, operation);
    }

    public NbtCompound toNbt()
    {
        NbtCompound compound = new NbtCompound();
        writeNbt(compound);
        return compound;
    }

    public boolean overWriteNbt(NbtCompound oldModifier)
    {
        if (AttributeUtil.getAttributeId(attribute).equals(oldModifier.getString(ATTRIBUTE_KEY)) &&
                uuid.equals(oldModifier.getUuid(UUID_KEY)))
        {
            oldModifier.putDouble(AMOUNT_KEY, getNewAmount(oldModifier.getDouble(AMOUNT_KEY)));
            return true;
        } else return false;
    }

    public void writeNbt(NbtCompound nbt)
    {
        nbt.putString(ATTRIBUTE_KEY, AttributeUtil.getAttributeId(attribute));
        nbt.putUuid(UUID_KEY, uuid);
        nbt.putDouble(AMOUNT_KEY, amount);
        nbt.putInt(OPERATION_KEY, operation.getId());
    }

    @Nullable
    public static AttrModifier fromNbt(NbtCompound nbt)
    {
        try
        {
            return new AttrModifier(
                    AttributeResolver.get(new Identifier(nbt.getString(ATTRIBUTE_KEY))),
                    nbt.getUuid(UUID_KEY),
                    nbt.getDouble(AMOUNT_KEY),
                    EntityAttributeModifier.Operation.fromId(nbt.getInt(OPERATION_KEY))
            );
        } catch (Exception e)
        {
            return null;
        }
    }
}