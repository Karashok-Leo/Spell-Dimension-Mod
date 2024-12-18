package karashokleo.spell_dimension.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SchoolUtil
{
    public static final List<SpellSchool> SCHOOLS = List.of(
            SpellSchools.ARCANE,
            SpellSchools.FIRE,
            SpellSchools.FROST,
            SpellSchools.HEALING,
            SpellSchools.LIGHTNING,
            SpellSchools.SOUL
    );

    public static List<SpellSchool> getEntitySchool(LivingEntity player)
    {
        Map<SpellSchool, Double> map = SCHOOLS.stream().collect(Collectors.toMap(
                school -> school,
                school -> SpellPower.getSpellPower(school, player).baseValue()
        ));
        Double max = Collections.max(map.values());
        return SCHOOLS.stream().filter(school -> map.get(school).equals(max)).toList();
    }

    public static double getEntitySpellPower(LivingEntity player)
    {
        List<SpellSchool> schools = SchoolUtil.getEntitySchool(player);
        if (!schools.isEmpty())
            return SpellPower.getSpellPower(schools.get(0), player).baseValue();
        return 0;
    }

    @Nullable
    public static SpellSchool getDamageSchool(DamageSource source)
    {
        for (SpellSchool school : SCHOOLS)
            if (source.isOf(school.damageType))
                return school;
        return null;
    }

    @Nullable
    public static SpellSchool getAttributeSchool(EntityAttribute attribute)
    {
        for (SpellSchool school : SCHOOLS)
            if (attribute == school.attribute)
                return school;
        return null;
    }
}
