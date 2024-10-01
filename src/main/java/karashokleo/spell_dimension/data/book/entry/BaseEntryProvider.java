package karashokleo.spell_dimension.data.book.entry;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.mojang.datafixers.util.Pair;

import java.util.List;

public abstract class BaseEntryProvider extends EntryProvider
{
    protected abstract String nameEN();

    protected abstract String nameZH();

    protected abstract String descEN();

    protected abstract String descZH();

    protected abstract List<BookPageModel> pages(BookContextHelper context);

    public BaseEntryProvider(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected void generatePages()
    {
        this.add(this.pages(this.context()));
    }

    @Override
    protected String entryName()
    {
        this.lang("zh_cn").add(this.context().entryName(), nameZH());
        return nameEN();
    }

    @Override
    protected String entryDescription()
    {
        this.lang("zh_cn").add(this.context().entryDescription(), descZH());
        return descEN();
    }

    @Override
    protected Pair<Integer, Integer> entryBackground()
    {
        return new Pair<>(0, 0);
    }
}
