package net.karashokleo.spelldimension.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.MagicSchool;

public class EnumUtil
{
    public static MagicSchool randomSchool(Random random)
    {
        return randomEnum(MagicSchool.class, random);
    }

    public static EquipmentSlot randomSlot(Random random)
    {
        return randomEnum(EquipmentSlot.class, random);
    }

    public static <T extends Enum<T>> T randomEnum(Class<T> enumClass, Random random)
    {
        if (!enumClass.isEnum())
            throw new IllegalArgumentException("The class must be an enum.");
        T[] enumValues = enumClass.getEnumConstants();
        int randomIndex = random.nextInt(enumValues.length);
        return enumValues[randomIndex];
    }
}
