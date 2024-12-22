package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class ReturningKnightEntry extends BaseEntryProvider
{
    public ReturningKnightEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Returning Knight";
    }

    @Override
    protected String nameZH()
    {
        return "复仇骑士";
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
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        Level: 100+
                        \\
                        \\
                        Use the Lost Soul on the Old Moon Altar to summon.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: 100+
                        \\
                        \\
                        对破旧的月光祭坛使用迷失的灵魂召唤。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("soulsweapons:nightfall"))))
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("soulsweapons:returning_knight")
                .withOffset(0.2f)
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("soulsweapons:nightfall")));
    }

    @Override
    protected String entryId()
    {
        return "returning_knight";
    }
}
