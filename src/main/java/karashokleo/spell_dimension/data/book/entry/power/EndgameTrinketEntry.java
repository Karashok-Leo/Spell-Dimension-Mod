package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class EndgameTrinketEntry extends BaseEntryProvider
{
    public EndgameTrinketEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Endgame Trinket";
    }

    @Override
    protected String nameZH()
    {
        return "终局饰品";
    }

    @Override
    protected String descEN()
    {
        return "Complement each other";
    }

    @Override
    protected String descZH()
    {
        return "相得益彰";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("text");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                After defeating more powerful bosses, you can craft some special trinkets with unique spell effects.
                \\
                \\
                Search **#spell-dimension:endgame_trinkets** to see all relative items.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                在击败了更为强大的Boss之后，你可以制作一些特殊饰品，它们具有一些独特的法术特效。
                \\
                \\
                搜索**#spell-dimension:endgame_trinkets**以查看所有相关物品。
                """
        );
        BookSpotlightPageModel text = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.fromTag(AllTags.ENDGAME_TRINKETS))
            .build();
        return List.of(text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.ARCANE_THRONE);
    }

    @Override
    protected String entryId()
    {
        return "endgame_trinket";
    }
}
