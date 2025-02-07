package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllStacks;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;
import net.paladins.PaladinsMod;
import net.wizards.WizardsMod;

import java.util.List;

public class BookEntry extends BaseEntryProvider
{
    public BookEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Book";
    }

    @Override
    protected String nameZH()
    {
        return "法术书";
    }

    @Override
    protected String descEN()
    {
        return "A carrier for recording spells.";
    }

    @Override
    protected String descZH()
    {
        return "记录法术的载体。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Spell Book");
        this.lang().add(context.pageText(),
                """
                        A spell book is a vehicle for recording spells. You can use the Spell Binding Table to transform a normal book into the most basic apprentices spell book. But this is just the beginning, you can then use the craft table to craft it into primary, intermediate, and advanced spell books.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术书");
        this.lang("zh_cn").add(context.pageText(),
                """
                        法术书是记录法术的载体。你可以使用法术绑定台将普通的书转化为最基本的学徒法术书。但这只是一个开始，之后你可以使用工作台将其合成至初级、中级、高级法术书。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(
                        Identifier.of(WizardsMod.ID, "arcane_spell_book"),
                        Identifier.of(WizardsMod.ID, "fire_spell_book"),
                        Identifier.of(WizardsMod.ID, "frost_spell_book"),
                        Identifier.of(PaladinsMod.ID, "paladin_spell_book"),
                        Identifier.of(PaladinsMod.ID, "priest_spell_book")
                ))
                .build();

        context.page("next");
        this.lang().add(context.pageTitle(), "Advanced Spell Book");
        this.lang().add(context.pageText(),
                """
                        Advanced spell books require special materials to craft. The more advanced the spell book, the more spell slots it provides.
                        \\
                        \\
                        You can put spell scrolls into primary, intermediate, and advanced spell books (just as you would into a Bundle). Apprentice spell books do not have this feature.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "高级法术书");
        this.lang("zh_cn").add(context.pageText(),
                """
                        高级的法术书需要特殊材料来合成。越高级的法术书提供越多的法术槽位。
                        \\
                        \\
                        你可以将法术卷轴放入初级、中级、高级法术书中（就像放入收纳袋那样），学徒法术书则没有这个功能。
                        """
        );

        BookSpotlightPageModel next = BookSpotlightPageModel
                .builder()
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(AllStacks.SPELL_BOOK_STACKS))
                .build();

        return List.of(prev, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(Identifier.of(WizardsMod.ID, "arcane_spell_book")));
    }

    @Override
    protected String entryId()
    {
        return "book";
    }
}
