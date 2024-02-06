package net.karashokleo.spelldimension.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

public class AttributeUtil
{
    public static void addModifier(LivingEntity entity, String uuid, String name, double value, EntityAttributeModifier.Operation operation)
    {
        EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeModifier modifier = new EntityAttributeModifier(java.util.UUID.fromString(uuid), name, value, operation);
        if (instance == null || instance.hasModifier(modifier)) return;
        instance.addPersistentModifier(modifier);
    }

    public static void removeModifier(LivingEntity entity, String uuid)
    {
        EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (instance != null)
            instance.removeModifier(java.util.UUID.fromString(uuid));
    }
}
