package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllStacks;
import karashokleo.spell_dimension.util.BookGenUtil;

import java.util.List;

public class EnlighteningEntry extends BaseEntryProvider
{
    public EnlighteningEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Enlightening Essence";
    }

    @Override
    protected String nameZH()
    {
        return "源启精华";
    }

    @Override
    protected String descEN()
    {
        return "Strike at the heart";
    }

    @Override
    protected String descZH()
    {
        return "直击心灵";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Enlightening Essence");
        this.lang().add(context.pageText(),
                """
                        Enlightening Essence is similar to Enchanted Essence, except that while Enchanted Essence works on items, Enlightening Essence works on the mage himself and is not limited by a threshold.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "源启精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        源启精华与束魔精华类似，不同的是束魔精华作用于物品，而源启精华作用于魔法师自身，且不受限于阈值。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getNbtIngredient(AllStacks.getEnlighteningEssences()))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Enlightening Essence cannot be crafted and can only be obtained from loot.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        源启精华不能合成，只能从战利品中获取。
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
        return BookIconModel.create(AllStacks.getEnlighteningEssences().get(0));
    }

    @Override
    protected String entryId()
    {
        return "enlighten";
    }
}
