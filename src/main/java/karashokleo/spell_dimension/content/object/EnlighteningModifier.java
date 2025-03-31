package karashokleo.spell_dimension.content.object;

import com.google.common.collect.Multimap;
import karashokleo.spell_dimension.content.component.EnlighteningComponent;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.AttributeResolver;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public record EnlighteningModifier(
        EntityAttribute attribute,
        UUID uuid, double amount,
        EntityAttributeModifier.Operation operation
)
{
    public static void init()
    {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) ->
        {
            for (EnlighteningModifier modifier : EnlighteningComponent.get(newPlayer).getModifiers())
                modifier.applyToEntity(newPlayer);
        });
    }

    public static final String NBT_KEY = "EnlighteningModifiers";
    private static final String MODIFIER_NAME = "EnlighteningModifier";
    private static final String ATTRIBUTE_KEY = "Attribute";
    private static final String UUID_KEY = "Uuid";
    private static final String AMOUNT_KEY = "Amount";
    private static final String OPERATION_KEY = "Operation";

    public EnlighteningModifier
    {
        if (attribute == null || uuid == null || operation == null)
            throw new IllegalArgumentException("Field 'attribute' or 'uuid' or 'operation' can not be null.");
    }

    public double getNewAmount(double oldAmount)
    {
        // Do special modification while Operation == MULTIPLY_TOTAL
        return operation == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? ((1 + amount) * (1 + oldAmount) - 1) : (amount + oldAmount);
    }

    public boolean applyToEntityOrPlayer(LivingEntity entity)
    {
        boolean apply = this.applyToEntity(entity);
        if (apply && entity instanceof PlayerEntity player)
            this.applyToComponent(player);
        return apply;
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

    public void applyToComponent(PlayerEntity player)
    {
        List<EnlighteningModifier> modifiers = EnlighteningComponent.get(player).getModifiers();
        Predicate<EnlighteningModifier> predicate = modifier -> modifier.attribute == this.attribute && modifier.uuid.equals(this.uuid);
        Optional<EnlighteningModifier> oldModifier = modifiers.stream().filter(predicate).findAny();
        double newAmount = oldModifier.map(modifier -> getNewAmount(modifier.amount())).orElse(amount);
        modifiers.removeIf(predicate);
        modifiers.add(new EnlighteningModifier(attribute, uuid, newAmount, operation));
    }

    public void applyToStack(Multimap<EntityAttribute, EntityAttributeModifier> modifiers)
    {
        modifiers.put(attribute, toModifier());
    }

    public EntityAttributeModifier toModifier()
    {
        return new EntityAttributeModifier(uuid, MODIFIER_NAME, amount, operation);
    }

    public EntityAttributeModifier toModifier(double newAmount)
    {
        return new EntityAttributeModifier(uuid, MODIFIER_NAME, newAmount, operation);
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
    public static EnlighteningModifier fromNbt(NbtCompound nbt)
    {
        try
        {
            return new EnlighteningModifier(
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
