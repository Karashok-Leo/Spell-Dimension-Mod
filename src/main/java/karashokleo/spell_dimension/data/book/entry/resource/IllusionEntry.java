package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class IllusionEntry extends BaseEntryProvider
{
    public IllusionEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Illusionary Transformation";
    }

    @Override
    protected String nameZH()
    {
        return "幻化";
    }

    @Override
    protected String descEN()
    {
        return "Remain essentially the same despite all apparent changes";
    }

    @Override
    protected String descZH()
    {
        return "万变不离其宗";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Illusion Containers can devour equipment (such as items under the tag #spell-dimension:epic/gear) and all enchanted items, and transform them into random materials of the same tier or the Book of Omniscience. This is also the only way to obtain the Book of Omniscience.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        幻化容器可以吞噬大量装备（如标签#spell-dimension:epic/gear下的物品）以及一切带有附魔的物品，并将它们转化为同阶级的随机材料或者全知之书。这也是全知之书的唯一获取途径。
                        """
        );
        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.ILLUSION_CONTAINER, AllItems.ILLUSION_UPGRADE))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Illusion Upgrade is similar to Pickup Upgrade, but it can automatically convert picked up items.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        幻化升级类似于背包的拾取升级，但是可以自动转化拾取的物品。
                        """
        );
        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(prev, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.ILLUSION_CONTAINER);
    }

    @Override
    protected String entryId()
    {
        return "illusion";
    }
}
