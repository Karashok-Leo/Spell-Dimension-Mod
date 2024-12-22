package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.adventurez.init.ItemInit;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class BlackStoneGolemEntry extends BaseEntryProvider
{
    public BlackStoneGolemEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Blackstone Golem";
    }

    @Override
    protected String nameZH()
    {
        return "黑石傀儡";
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
                        Use 4 chiseled polished blackstone holders and 81 polished blackstones to build the altar, place the gilded blackstone shards on the altar to summon.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: 100+
                        \\
                        \\
                        使用4块錾制黑石基座，81块磨制黑石搭建祭坛，在祭坛上放置镶金黑石碎片即可召唤。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(ItemInit.BLACKSTONE_GOLEM_HEART))
                .build();
        BookMultiblockPageModel alter = BookMultiblockPageModel
                .builder()
                .withMultiblockId("spell-dimension-book:blackstone_altar")
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("adventurez:blackstone_golem")
                .build();

        return List.of(boss, alter, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ItemInit.BLACKSTONE_GOLEM_HEART);
    }

    @Override
    protected String entryId()
    {
        return "blackstone_golem";
    }
}
