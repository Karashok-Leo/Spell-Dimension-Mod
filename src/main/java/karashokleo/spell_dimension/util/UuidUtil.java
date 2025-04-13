package karashokleo.spell_dimension.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributeModifier.Operation;

public class UuidUtil
{
    private static final Map<Operation, UUID> forSelf = new HashMap<>();
    private static final Map<EquipmentSlot, Map<Operation, UUID>> forEquipment = new HashMap<>();

    static
    {
        for (Operation operation : Operation.values())
        {
            forSelf.put(operation, getUUIDFromString("Self-" + operation.name()));
        }
        for (EquipmentSlot slot : EquipmentSlot.values())
        {
            HashMap<Operation, UUID> map = new HashMap<>();
            for (Operation operation : Operation.values())
                map.put(operation, getUUIDFromString("Equipment-" + slot.name() + "-" + operation.name()));
            forEquipment.put(slot, map);
        }
    }

    public static UUID getSelfUuid(EntityAttributeModifier.Operation operation)
    {
        return forSelf.get(operation);
    }

    public static UUID getEquipmentUuid(EquipmentSlot slot, EntityAttributeModifier.Operation operation)
    {
        return forEquipment.get(slot).get(operation);
    }

    public static UUID getUUIDFromString(String str)
    {
        int hash = str.hashCode();
        Random r = new Random(hash);
        long l0 = r.nextLong();
        long l1 = r.nextLong();
        return new UUID(l0, l1);
    }
}
