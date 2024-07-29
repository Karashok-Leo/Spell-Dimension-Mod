package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.registry.ItemRegistry;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.init.AllGroups;
import net.minecraft.data.DataOutput;

public class SDBookProvider extends BookProvider
{
    public static final String BOOK_NAME = "magic_guidance";

    public SDBookProvider(DataOutput packOutput, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations)
    {
        super(BOOK_NAME, packOutput, SpellDimension.MOD_ID, defaultLang, translations);
    }

    @Override
    protected BookModel generateBook()
    {
        BookCategoryModel category = new SDCategoryProvider(this, "category").generate();
        BookModel bookModel = BookModel
                .create(SpellDimension.modLoc(BOOK_NAME), this.context().bookName())
                .withTooltip(this.context().bookTooltip())
                .withGenerateBookItem(true)
                .withCreativeTab(AllGroups.MISC_GROUP_KEY.getValue())
                .withCategory(category)
                .withModel(ItemRegistry.MODONOMICON_PURPLE.getId());
        return bookModel;
    }

    @Override
    protected void registerDefaultMacros()
    {
    }
}
