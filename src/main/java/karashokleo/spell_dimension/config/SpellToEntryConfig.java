package karashokleo.spell_dimension.config;

import com.mojang.datafixers.util.Pair;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.PatchouliLookupCallback;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellInfo;

import java.util.HashMap;
import java.util.Map;

public class SpellToEntryConfig
{
    private static final Map<Identifier, Pair<Identifier, Integer>> SPELL_TO_ENTRY = new HashMap<>();

    public static void init()
    {
        register(AllSpells.LOCATE, "mage/spell", 0);
        register(AllSpells.SUMMON, "mage/spell", 0);
        register(AllSpells.PLACE, "mage/spell", 1);
        register(AllSpells.BREAK, "mage/spell", 1);
        register(AllSpells.POSSESS, "mage/soul_minion", 1);
        register(AllSpells.RECALL, "mage/soul_minion", 1);
        register(AllSpells.SOUL_COMMAND, "mage/soul_minion", 1);

        PatchouliLookupCallback.EVENT.register(stack ->
        {
            if (!stack.isOf(AllItems.SPELL_SCROLL))
            {
                return null;
            }
            SpellInfo spellInfo = AllItems.SPELL_SCROLL.getSpellInfo(stack);
            if (spellInfo == null)
            {
                return null;
            }
            return SPELL_TO_ENTRY.get(spellInfo.id());
        });
    }

    public static void register(Identifier spell, String entryId, int page)
    {
        register(spell, SpellDimension.modLoc(entryId), page);
    }

    public static void register(Identifier spell, Identifier entryId, int page)
    {
        SPELL_TO_ENTRY.put(spell, Pair.of(entryId, page));
    }
}
