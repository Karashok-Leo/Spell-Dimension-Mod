package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class SecondaryEntry extends BaseEntryProvider
{
    public SecondaryEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Secondary Spell School";
    }

    @Override
    protected String nameZH()
    {
        return "副法术学派";
    }

    @Override
    protected String descEN()
    {
        return "Make Magic Great Again";
    }

    @Override
    protected String descZH()
    {
        return "再创辉煌";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                Once you are proficient in a single spell school, you may choose to study a secondary school. To do so, you will need to craft specific magical items that will serve as the basis for your school of study.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                当你精通了单一法术学派后，可以选择研习一个副学派。为此你需要制作一些特定的魔法物品，以此作为学派的研习基础。
                """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.fromTag(AllTags.SECONDARY_SCHOOL_SLOT))
            .build();

        context.page("next");
        this.lang().add(context.pageText(),
            """
                Search **#trinkets:chest/secondary_school** for the corresponding items.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                搜索**#trinkets:chest/secondary_school**可查看对应的物品。
                """
        );
        BookTextPageModel next = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        return List.of(prev, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.RIDDLE_BOOK);
    }

    @Override
    protected String entryId()
    {
        return "secondary";
    }
}
