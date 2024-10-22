package karashokleo.spell_dimension.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.random.Random;
import net.spell_power.api.SpellSchool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RandomUtil
{
    public static SpellSchool randomSchool(Random random)
    {
        return randomFromList(random, SchoolUtil.SCHOOLS);
    }

    public static EquipmentSlot randomSlot(Random random)
    {
        return randomEnum(random, EquipmentSlot.class);
    }

    public static <T> T randomFromList(Random random, List<T> list)
    {
        if (list.isEmpty())
            throw new IllegalArgumentException("The list must not be empty.");
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static <T> T randomFromSet(Random random, Set<T> set)
    {
        if (set.isEmpty())
            throw new IllegalArgumentException("The set must not be empty.");
        return randomFromList(random, new ArrayList<>(set));
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
