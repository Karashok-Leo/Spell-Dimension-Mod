package karashokleo.spell_dimension.data.book.lang;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.data.DataOutput;

public class BookChineseProvider extends AbstractModonomiconLanguageProvider
{
    public BookChineseProvider(DataOutput output, ModonomiconLanguageProvider cachedProvider)
    {
        super(output, BookGenUtil.NAMESPACE, "zh_cn", cachedProvider);
    }

    @Override
    protected void addTranslations()
    {
    }
}
