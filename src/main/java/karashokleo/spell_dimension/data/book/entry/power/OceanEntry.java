package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class OceanEntry extends BaseEntryProvider
{
    public OceanEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Ocean of Consciousness";
    }

    @Override
    protected String nameZH()
    {
        return "识之海";
    }

    @Override
    protected String descEN()
    {
        return "";
    }

    @Override
    protected String descZH()
    {
        return "";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("text");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        The Ocean of Consciousness is a world of nothingness.
                        \\
                        \\
                        Once you enter this world, your spawn point will be permanently anchored here, and your hostility level will be raised to at least 100 levels.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        识之海是一个一无所有的世界。
                        \\
                        \\
                        一旦你进入这个世界，你的出生点将被永远锚定在这里，你的恶意等级将被提升至至少100级。
                        """
        );
        BookSpotlightPageModel text = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.MAGIC_MIRROR))
                .build();

        return List.of(text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.MAGIC_MIRROR);
    }

    @Override
    protected String entryId()
    {
        return "ocean";
    }
}
