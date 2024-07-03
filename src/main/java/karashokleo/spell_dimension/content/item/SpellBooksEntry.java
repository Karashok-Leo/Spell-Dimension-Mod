package karashokleo.spell_dimension.content.item;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.misc.MageMajor;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.trinket.SpellBookItem;
import net.spell_engine.api.item.trinket.SpellBooks;
import net.spell_engine.api.spell.SpellContainer;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellBooksEntry
{
    public SpellSchool school;
    public SpellBookItem primary;
    public Map<MageMajor, List<MageSpellBookItem>> majors = new HashMap<>();

    public SpellBooksEntry(SpellSchool school, List<MageMajor> majors)
    {
        this.school = school;
        this.primary = SpellBooks.create(SpellDimension.modLoc(school.id.getPath() + "_0"));
        for (MageMajor major : majors)
            addMajor(school, major);
    }

    public void addMajor(SpellSchool school, MageMajor major)
    {
        List<MageSpellBookItem> books = new ArrayList<>();
        for (int i = 1; i <= 3; i++)
            books.add(createMageSpellBook(i, school, major));
        majors.put(major, books);
    }

    public MageSpellBookItem createMageSpellBook(int grade, SpellSchool school, MageMajor major)
    {
        Identifier poolId = SpellDimension.modLoc(SchoolUtil.getName(school) + "_" + major.majorName() + "_" + grade);
        SpellContainer container = new SpellContainer(SpellContainer.ContentType.MAGIC, false, poolId.toString(), 0, List.of());
        SpellRegistry.book_containers.put(SpellBooks.itemIdFor(poolId), container);
        MageSpellBookItem book = new MageSpellBookItem(poolId, grade, school, major);
//        SpellBooksAccessor.getAll().add(book);
        return book;
    }

    public void register()
    {
        SpellBooks.register(primary);
        majors.values().forEach(books -> books.forEach(SpellBooks::register));
    }
}
