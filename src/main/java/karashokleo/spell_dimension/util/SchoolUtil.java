package karashokleo.spell_dimension.util;

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

    public static String getName(SpellSchool school)
    {
        return school.id.getPath();
    }

    public static boolean isMagic(SpellSchool school)
    {
        return school.archetype == SpellSchool.Archetype.MAGIC;
    }

    public static int getSchoolColorOrDefault(@Nullable SpellSchool school, int defaultColor)
    {
        return school == null ? defaultColor : school.color;
    }

    public static int getSchoolColorOrWhite(@Nullable SpellSchool school)
    {
        return getSchoolColorOrDefault(school, 0xFFFFFF);
    }
}
