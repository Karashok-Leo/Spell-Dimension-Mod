package karashokleo.spell_dimension.data.book;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.registry.ItemRegistry;
import karashokleo.spell_dimension.data.book.category.BossProvider;
import karashokleo.spell_dimension.data.book.category.MageProvider;
import karashokleo.spell_dimension.data.book.category.TipsCategory;
import karashokleo.spell_dimension.init.AllGroups;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.data.DataOutput;

public class MagicGuidanceProvider extends BookProvider
{
    public static final String BOOK_NAME = "magic_guidance";

    public MagicGuidanceProvider(DataOutput packOutput, ModonomiconLanguageProvider defaultLang, ModonomiconLanguageProvider... translations)
    {
        super(BOOK_NAME, packOutput, BookGenUtil.NAMESPACE, defaultLang, translations);
    }

    @Override
    protected BookModel generateBook()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.bookName(), "Magic Guidance");
        this.lang().add(context.bookTooltip(), "Guidebook for playing Spell Dimension.");
        this.lang("zh_cn").add(context.bookName(), "魔力接引");
        this.lang("zh_cn").add(context.bookTooltip(), "三岁小孩也能看懂的入门魔法书。");
        return BookModel
                .create(BookGenUtil.id(BOOK_NAME), context.bookName())
                .withTooltip(context.bookTooltip())
                .withModel(ItemRegistry.MODONOMICON_PURPLE.getId())
                .withCreativeTab(AllGroups.MISC_GROUP_KEY.getValue())
                .withGenerateBookItem(true)
                .withCategories(
                        new MageProvider(this).generate(),
                        new TipsCategory(this).generate(),
                        new BossProvider(this).generate()
                );
    }

    @Override
    protected void registerDefaultMacros()
    {
    }
}
