package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class MendingEntry extends BaseEntryProvider
{
    public MendingEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Mending Essence";
    }

    @Override
    protected String nameZH()
    {
        return "修复精华";
    }

    @Override
    protected String descEN()
    {
        return "Untouched.";
    }

    @Override
    protected String descZH()
    {
        return "完好如初。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("mending");
        this.lang().add(context.pageTitle(), "Mending Essence");
        this.lang().add(context.pageText(),
                """
                        \\
                        Mending Essence completely repairs an item and removes its accumulated repair penalties. Mending Essence cannot be crafted and can only be obtained from loot.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "修复精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        修复精华可以完全修复一件物品, 并且清除其累积的修复惩罚。修复精华不能合成，只能从战利品中获取。
                        """
        );

        BookSpotlightPageModel mending = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.MENDING_ESSENCE))
                .build();

        return List.of(mending);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.MENDING_ESSENCE);
    }

    @Override
    protected String entryId()
    {
        return "mending";
    }
}
