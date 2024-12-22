package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.tips.DummyEntry;
import karashokleo.spell_dimension.data.book.entry.tips.KeyEntry;
import karashokleo.spell_dimension.data.book.entry.tips.StorageEntry;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;

public class TipsCategory extends CategoryProvider
{
    public TipsCategory(BookProvider parent)
    {
        super(parent, "tips");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "a_b_c",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel key = new KeyEntry(this).generate('a');
        BookEntryModel storage = new StorageEntry(this).generate('b');
        BookEntryModel dummy = new DummyEntry(this).generate('c');
        this.add(key);
        this.add(storage);
        this.add(dummy);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Tips");
        this.lang("zh_cn").add(context.categoryName(), "提示");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.LANTERN)
                .withBackground(BookGenUtil.id("textures/background/5.png"));
    }
}
