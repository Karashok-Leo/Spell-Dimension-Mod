package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.buff.BuffType;
import karashokleo.spell_dimension.api.buff.BuffTypeRegistry;
import karashokleo.spell_dimension.content.buff.BlazingMark;
import karashokleo.spell_dimension.content.buff.Electrocution;
import karashokleo.spell_dimension.content.buff.Nucleus;

public class AllBuffs
{
    public static void register()
    {
        register("blazing_mark", BlazingMark.TYPE);
        register("nucleus", Nucleus.TYPE);
        register("electrocution", Electrocution.TYPE);
    }

    public static void register(String path, BuffType<?> type)
    {
        BuffTypeRegistry.register(SpellDimension.modLoc(path), type);
    }
}
