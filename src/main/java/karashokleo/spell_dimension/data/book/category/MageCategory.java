package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.mage.*;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.wizards.item.Weapons;

public class MageCategory extends CategoryProvider
{
    public MageCategory(BookProvider parent)
    {
        super(parent, "mage");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "__d___m__",
                "c_______l",
                "____e____",
                "_________",
                "u___a___h",
                "_________",
                "____n____",
                "k_______r",
                "____s____",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel cast = new CastEntry(this).generate('a');
        BookEntryModel school = new SchoolEntry(this).generate('h');
        BookEntryModel rune = new RuneEntry(this).generate('u');
        BookEntryModel essence = new EssenceEntry(this).generate('e');
        BookEntryModel enchant = new EnchantedEntry(this).generate('c');
        BookEntryModel enlighten = new EnlighteningEntry(this).generate('l');
        BookEntryModel disenchant = new DisenchantedEntry(this).generate('d');
        BookEntryModel mending = new MendingEntry(this).generate('m');
        BookEntryModel bind = new BindEntry(this).generate('n');
        BookEntryModel book = new BookEntry(this).generate('k');
        BookEntryModel scroll = new ScrollEntry(this).generate('r');
        BookEntryModel power = new SpellPowerEntry(this).generate('s');
        BookGenUtil.setParent(school, cast);
        BookGenUtil.setParent(rune, cast);
        BookGenUtil.setParent(essence, cast);
        BookGenUtil.setParent(enchant, essence);
        BookGenUtil.setParent(enlighten, essence);
        BookGenUtil.setParent(disenchant, essence);
        BookGenUtil.setParent(mending, essence);
        BookGenUtil.setParent(bind, cast);
        BookGenUtil.setParent(book, bind);
        BookGenUtil.setParent(scroll, bind);
        BookGenUtil.setParent(power, bind);
        this.add(cast);
        this.add(school);
        this.add(rune);
        this.add(essence);
        this.add(enchant);
        this.add(enlighten);
        this.add(disenchant);
        this.add(mending);
        this.add(bind);
        this.add(book);
        this.add(scroll);
        this.add(power);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Path of the Mage");
        this.lang("zh_cn").add(context.categoryName(), "魔法师之路");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Weapons.arcaneWand.item())
                .withBackground(BookGenUtil.id("textures/background/1.png"));
    }
}
