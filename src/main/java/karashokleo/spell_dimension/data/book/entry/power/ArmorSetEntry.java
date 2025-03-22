package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.aleganza.plentyofarmors.item.ModItems;

import java.util.List;

public class ArmorSetEntry extends BaseEntryProvider
{
    public ArmorSetEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Armor Set Bonus";
    }

    @Override
    protected String nameZH()
    {
        return "套装效果";
    }

    @Override
    protected String descEN()
    {
        return "Resonance of similarity";
    }

    @Override
    protected String descZH()
    {
        return "相似相合的共鸣";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("text");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Wearing certain sets of armor will grant you special set bonuses.
                        \\
                        \\
                        Search **set** to see all available set bonuses.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        穿戴一些装备可以获得特殊套装效果。
                        \\
                        \\
                        搜索**套装**（或拼音）以查看所有可用的套装效果。
                        """
        );
        BookTextPageModel text = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        return List.of(text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ModItems.STARDUSITE_CHESTPLATE);
    }

    @Override
    protected String entryId()
    {
        return "armor_set";
    }
}
