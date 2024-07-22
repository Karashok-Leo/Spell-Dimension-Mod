package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.util.AttributeUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.entity.attribute.EntityAttribute;
import net.spell_power.api.SpellSchool;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class AttributeColorConfig
{
    private static final Map<String, Integer> map = new HashMap<>();

    static
    {
        // Generic
        map.put("minecraft:generic.armor", 0x888888);
        map.put("minecraft:generic.armor_toughness", 0xbdeeff);
        map.put("minecraft:generic.attack_speed", 0xffb06b);
        map.put("minecraft:generic.knockback_resistance", 0xffce6b);
        map.put("minecraft:generic.luck", 0xb8ff6b);
        map.put("minecraft:generic.max_health", 0xff6b6b);
        map.put("minecraft:generic.movement_speed", 0x6bbcff);
        map.put("minecraft:generic.haste", 0xdf94ff);
        map.put("minecraft:generic.critical_damage", 0xfaff94);
        map.put("minecraft:generic.critical_chance", 0x94ffda);

        // Spell School
        for (SpellSchool school : SchoolUtil.SCHOOLS)
            map.put(AttributeUtil.getAttributeId(school.attribute), school.color);
    }

    public static int get(@Nullable EntityAttribute attribute)
    {
        return map.getOrDefault(AttributeUtil.getAttributeId(attribute), 0xffffff);
    }
}
