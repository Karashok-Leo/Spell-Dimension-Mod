package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class AllStats
{
    public static final Identifier MINED_ORES = SpellDimension.modLoc("mined_ores");

    public static void register()
    {
        Registry.register(Registries.CUSTOM_STAT, MINED_ORES, MINED_ORES);
        Stats.CUSTOM.getOrCreateStat(MINED_ORES, StatFormatter.DEFAULT);
    }
}
