package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.tom.storagemod.Content;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class StorageEntry extends BaseEntryProvider
{
    public StorageEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Storage";
    }

    @Override
    protected String nameZH()
    {
        return "存储";
    }

    @Override
    protected String descEN()
    {
        return "Well organized.";
    }

    @Override
    protected String descZH()
    {
        return "井然有序。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("storage");
        this.lang().add(context.pageTitle(), "Tom's Simple Storage");
        this.lang().add(context.pageText(),
                """
                        \\
                        Place a storage connector with a terminal next to your chests, after which you can manage the items of all your chests in a unified way through the terminal.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "汤姆的简易存储");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        在你的箱子旁放置存储整合器与终端，之后你便可以通过终端统一管理所有箱子里的物品。
                        """
        );

        BookSpotlightPageModel storage = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Content.connector.get(), Content.terminal.get(), Content.craftingTerminal.get()))
                .build();

        return List.of(storage);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Content.connector.get());
    }

    @Override
    protected String entryId()
    {
        return "storage";
    }
}
