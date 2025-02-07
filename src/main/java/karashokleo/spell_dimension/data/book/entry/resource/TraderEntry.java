package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.item.Items;

import java.util.List;

public class TraderEntry extends BaseEntryProvider
{
    public TraderEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Trader";
    }

    @Override
    protected String nameZH()
    {
        return "商人";
    }

    @Override
    protected String descEN()
    {
        return "He really loves apples.";
    }

    @Override
    protected String descZH()
    {
        return "他真的很喜欢吃苹果。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Goblin Trader");
        this.lang().add(context.pageText(),
                """
                        Perhaps you have a chance to meet goblin traders, a cute race of people who like apples, and unlike wandering traders with llamas, the items they sell tend to be very helpful to you.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "哥布林商人");
        this.lang("zh_cn").add(context.pageText(),
                """
                        也许你有机会遇到哥布林商人，他们是喜欢吃苹果的可爱种族，与牵着羊驼的流浪商人不同，他们出售的物品往往对你很有帮助。
                        """
        );

        BookTextPageModel prev = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Goblin traders are often found in the overworld, while vein goblin traders are often found in the nether, with the latter selling much rarer items.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        哥布林商人常在主世界出没，熔岩哥布林商人则常在下界出没，后者出售的物品更加稀有。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel goblin = BookEntityPageModel
                .builder()
                .withEntityId("goblintraders:goblin_trader")
                .build();

        BookEntityPageModel vein = BookEntityPageModel
                .builder()
                .withEntityId("goblintraders:vein_goblin_trader")
                .build();

        return List.of(prev, next, goblin, vein);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.APPLE);
    }

    @Override
    protected String entryId()
    {
        return "trader";
    }
}
