package net.karashokleo.spelldimension.mixin;

import net.spell_engine.api.item.trinket.SpellBookItem;
import net.spell_engine.api.item.trinket.SpellBooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.ArrayList;

@Mixin(SpellBooks.class)
public interface SpellBooksAccessor
{
    @Accessor(value = "all", remap = false)
    static ArrayList<SpellBookItem> getAll()
    {
        throw new AssertionError();
    }
}
