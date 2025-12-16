package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.runes.api.RuneItems;

import java.util.List;

public class RuneEntry extends BaseEntryProvider
{
    public RuneEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Rune";
    }

    @Override
    protected String nameZH()
    {
        return "符文";
    }

    @Override
    protected String descEN()
    {
        return "\"Magic Ammo\"";
    }

    @Override
    protected String descZH()
    {
        return "“魔法弹药”";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("rune");
        this.lang().add(context.pageTitle(), "Rune");
        this.lang().add(context.pageText(),
            """
                Casting certain spells requires runes. You can craft runes directly in your hand, or craft a rune altar to make more runes with the same ingredients.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "符文");
        this.lang("zh_cn").add(context.pageText(),
            """
                施放某些法术需要消耗符文。你可以直接在手中合成符文，或是制作一个符文祭坛以相同的原料制作更多的符文。
                """
        );

        BookSpotlightPageModel rune = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(BookGenUtil.getIngredient(RuneItems.entries.stream().map(entry -> entry.item().getDefaultStack())))
            .build();

        return List.of(rune);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(RuneItems.get(RuneItems.RuneType.ARCANE));
    }

    @Override
    protected String entryId()
    {
        return "rune";
    }
}
