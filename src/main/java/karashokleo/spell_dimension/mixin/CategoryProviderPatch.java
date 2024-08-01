package karashokleo.spell_dimension.mixin;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = CategoryProvider.class, remap = false)
public abstract class CategoryProviderPatch
{
    @Shadow
    protected BookProvider parent;

    /**
     * @author Karashok Leo
     * @reason it should be like this
     */
    @Overwrite
    protected ModonomiconLanguageProvider lang(String locale)
    {
        return ((BookProviderPatch) (this.parent)).invokeLang(locale);
    }
}
