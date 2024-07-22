package karashokleo.spell_dimension.mixin;

import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.SpellPool;
import net.spell_engine.internals.SpellRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(value = SpellRegistry.class, remap = false)
public interface SpellRegistryAccessor
{
    @Accessor("spells")
    static Map<Identifier, SpellRegistry.SpellEntry> getSpells()
    {
        throw new AssertionError();
    }

    @Accessor("pools")
    static Map<Identifier, SpellPool> getPools()
    {
        throw new AssertionError();
    }

    @Invoker("spellsUpdated")
    static void invokeSpellsUpdated()
    {
        throw new AssertionError();
    }

    @Invoker("encodeContent")
    static void invokeEncodedContent()
    {
        throw new AssertionError();
    }
}
