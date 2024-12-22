package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.adventurez.init.ItemInit;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class VoidShadowEntry extends BaseEntryProvider
{
    public VoidShadowEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Void Shadow";
    }

    @Override
    protected String nameZH()
    {
        return "虚空之影";
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
                        Level: 300
                        \\
                        \\
                        Defeat the Eye to spawn a floating island, enter the void shadow realm through the portal on it.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: 300
                        \\
                        \\
                        击败眼球后将生成一个浮岛，通过其上的传送门可进入虚空之影的领域。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(ItemInit.SOURCE_STONE))
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("adventurez:void_shadow")
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ItemInit.SOURCE_STONE);
    }

    @Override
    protected String entryId()
    {
        return "void_shadow";
    }
}
