package karashokleo.spell_dimension.util;

import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellContainer;

import java.util.ArrayList;

public class SpellContainerUtil
{
    /**
     * Add a Spell to a SpellContainer without sorting.
     *
     * @param container container
     * @param spellId   spellId
     * @return container
     */
    public static SpellContainer addSpell(SpellContainer container, Identifier spellId)
    {
        ArrayList<String> spellIds = new ArrayList<>(container.spell_ids);
        spellIds.add(spellId.toString());
        container.spell_ids = spellIds;
        return container;
    }

    /**
     * Remove last Spell of a SpellContainer without sorting.
     *
     * @param container container
     * @return container
     */
    public static SpellContainer removeLastSpell(SpellContainer container)
    {
        ArrayList<String> spellIds = new ArrayList<>(container.spell_ids);
        spellIds.remove(spellIds.size() - 1);
        container.spell_ids = spellIds;
        return container;
    }
}
