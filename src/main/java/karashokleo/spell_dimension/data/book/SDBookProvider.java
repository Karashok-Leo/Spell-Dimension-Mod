package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.data.DataOutput;

public class SDBookProvider extends BookProvider
{
    public static final String BOOK_NAME="enchanted_guidance";

    public SDBookProvider(DataOutput packOutput, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations)
    {
        super(BOOK_NAME, packOutput, SpellDimension.MOD_ID, defaultLang, translations);
    }

    @Override
    protected void registerDefaultMacros()
    {
        BookModel bookModel = BookModel.create(SpellDimension.modLoc(BOOK_NAME), this.context().bookName());
    }

    @Override
    protected BookModel generateBook()
    {
        return null;
    }
}
