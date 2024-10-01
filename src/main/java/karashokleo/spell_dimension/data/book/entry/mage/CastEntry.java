package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.wizards.item.Weapons;

import java.util.List;

public class CastEntry extends BaseEntryProvider
{
    public CastEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Casting";
    }

    @Override
    protected String nameZH()
    {
        return "施法";
    }

    @Override
    protected String descEN()
    {
        return "The basic skill of a mage.";
    }

    @Override
    protected String descZH()
    {
        return "魔法师的基本功。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("cast");
        this.lang().add(context.pageTitle(), "Spell Casting");
        this.lang().add(context.pageText(),
                """
                        \\
                        Spell casting is the basic skill of a mage. Common spell casting items are staffs and swords. Which spells a mage can cast depends on their spell pool. A mage's spell pool is determined by the item they are holding and the spell book they have equipped.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "施法");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        施法是魔法师的基本功。常见的施法物品有法杖和剑。魔法师能够施展哪些法术取决于他们的法术池。魔法师的法术池由他们手持的物品和装备的法术书决定。
                        """
        );

        BookSpotlightPageModel cast = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(Weapons.entries.stream().map(entry -> entry.item().getDefaultStack())))
                .build();

        return List.of(cast);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Weapons.arcaneWand.item());
    }

    @Override
    protected String entryId()
    {
        return "cast";
    }
}
