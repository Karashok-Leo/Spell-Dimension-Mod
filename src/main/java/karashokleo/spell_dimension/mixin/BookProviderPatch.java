package karashokleo.spell_dimension.mixin;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = BookProvider.class, remap = false)
public interface BookProviderPatch
{
    @Invoker("lang")
    ModonomiconLanguageProvider invokeLang(String locale);
}
