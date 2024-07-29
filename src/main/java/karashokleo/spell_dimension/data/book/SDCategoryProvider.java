package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.util.Identifier;
import net.spell_engine.spellbinding.SpellBindingBlock;
import net.wizards.item.Weapons;

public class SDCategoryProvider extends CategoryProvider
{
    public SDCategoryProvider(BookProvider parent, String categoryId)
    {
        super(parent, categoryId);
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "c          ",
                "       b   "
        };
    }

    @Override
    protected void generateEntries()
    {
        this.add(this.entryCastSpell());
        this.add(this.entryBeginner());
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        return BookCategoryModel
                .create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/base.png"))
                .withIcon(SpellBindingBlock.ITEM);
    }

    private BookEntryModel entryCastSpell()
    {
        BookContextHelper context = this.context();
        context.entry("cast_spell");
        context.page("intro");

        return this.entry('c')
                .withIcon(Weapons.arcaneWand.item());
    }

    private BookEntryModel entryBeginner()
    {
        BookContextHelper context = this.context();
        context.entry("beginner");
        context.page("intro");
        BookTextPageModel pageModel = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .withTitle(context.pageTitle())
                .build();

        return this.entry('b')
                .withIcon(SpellBindingBlock.ITEM)
                .withPage(pageModel);
    }
}
