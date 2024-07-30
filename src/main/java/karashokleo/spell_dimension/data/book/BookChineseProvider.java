package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.data.DataOutput;

public class BookChineseProvider extends AbstractModonomiconLanguageProvider
{
    public BookChineseProvider(DataOutput output, ModonomiconLanguageProvider cachedProvider)
    {
        super(output, SpellDimension.MOD_ID, "zh_cn", cachedProvider);
    }

    @Override
    protected void addTranslations()
    {
    }
}
