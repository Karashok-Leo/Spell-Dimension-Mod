package karashokleo.spell_dimension.util;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
