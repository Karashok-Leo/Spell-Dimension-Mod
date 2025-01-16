package karashokleo.spell_dimension.api;

import karashokleo.spell_dimension.mixin.modded.SpellRegistryAccessor;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellPool;
import net.spell_engine.internals.SpellRegistry;

public interface SpellRegistryHelper
{
    static void registerSpell(Identifier spellId, SpellRegistry.SpellEntry spellEntry)
    {
        SpellRegistryAccessor.getSpells().put(spellId, spellEntry);
        SpellRegistryAccessor.invokeSpellsUpdated();
    }

    static void registerPool(Identifier poolId, SpellPool pool)
    {
        SpellRegistryAccessor.getPools().put(poolId, pool);
    }
}
