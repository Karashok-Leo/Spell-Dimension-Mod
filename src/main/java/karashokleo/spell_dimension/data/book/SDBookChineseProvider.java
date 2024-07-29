package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.data.DataOutput;

public class SDBookChineseProvider extends AbstractModonomiconLanguageProvider
{
    public SDBookChineseProvider(DataOutput output, ModonomiconLanguageProvider cachedProvider)
    {
        super(output, SpellDimension.MOD_ID, "zh_cn", cachedProvider);
    }

    @Override
    protected void addTranslations()
    {
    }
}
