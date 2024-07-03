package karashokleo.spell_dimension.content.spell;

import it.unimi.dsi.fastutil.Function;
import net.spell_engine.api.spell.CustomSpellHandler;

public interface ISpellHandler extends Function<CustomSpellHandler.Data, Boolean>
{
    default Boolean handle(CustomSpellHandler.Data data)
    {
        return true;
    }

    @Override
    default Boolean get(Object o)
    {
        return this.handle((CustomSpellHandler.Data) o);
    }
}
