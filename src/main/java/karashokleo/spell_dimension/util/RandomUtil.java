package karashokleo.spell_dimension.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.SpellSchool;

import java.util.List;

public class RandomUtil
{
    public static SpellSchool randomSchool(Random random)
    {
        return randomList(random, SchoolUtil.SCHOOLS);
    }

    public static EquipmentSlot randomSlot(Random random)
    {
        return randomEnum(random, EquipmentSlot.class);
    }

    public static <T> T randomList(Random random, List<T> list)
    {
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static <T extends Enum<T>> T randomEnum(Random random, Class<T> enumClass)
    {
        if (!enumClass.isEnum())
            throw new IllegalArgumentException("The class must be an enum.");
        T[] enumValues = enumClass.getEnumConstants();
        int randomIndex = random.nextInt(enumValues.length);
        return enumValues[randomIndex];
    }
}
