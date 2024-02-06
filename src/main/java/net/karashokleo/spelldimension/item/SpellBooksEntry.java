package net.karashokleo.spelldimension.item;

import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.item.mod_item.MageSpellBookItem;
import net.karashokleo.spelldimension.mixin.SpellBooksAccessor;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.trinket.SpellBookItem;
import net.spell_engine.api.item.trinket.SpellBooks;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.MagicSchool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellBooksEntry
{
    public MagicSchool school;
    public SpellBookItem primary;
    public Map<MageMajor, List<MageSpellBookItem>> majors = new HashMap<>();

    public SpellBooksEntry(MagicSchool school, List<MageMajor> majors)
    {
        this.school = school;
        this.primary = SpellBooks.create(SpellDimension.modLoc(AllItems.GRADES[0] + school.spellName()));
        for (MageMajor major : majors)
            addMajor(school, major);
    }

    public void addMajor(MagicSchool school, MageMajor major)
    {
        List<MageSpellBookItem> books = new ArrayList<>();
        for (int i = 1; i <= 3; i++)
            books.add(createMageSpellBook(i, school, major));
        majors.put(major, books);
    }

    public MageSpellBookItem createMageSpellBook(int grade, MagicSchool school, MageMajor major)
    {
        Identifier poolId = SpellDimension.modLoc(major.majorName() + "_" + AllItems.GRADES[grade] + school.spellName());
        SpellContainer container = new SpellContainer(SpellContainer.ContentType.MAGIC, false, poolId.toString(), 0, List.of());
        SpellRegistry.book_containers.put(SpellBooks.itemIdFor(poolId), container);
        MageSpellBookItem book = new MageSpellBookItem(grade, school, major);
        SpellBooksAccessor.getAll().add(book);
        return book;
    }

    public void register()
    {
        AllItems.ALL.add(primary);
        Registry.register(Registries.ITEM, SpellBooks.itemIdFor(primary.poolId), primary);
        majors.values().forEach(books -> books.forEach(book ->
        {
            AllItems.ALL.add(book);
            Registry.register(Registries.ITEM, SpellBooks.itemIdFor(book.poolId), book);
        }));
    }
}
