package karashokleo.spell_dimension.data.book.entry.tips;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;
import wraith.alloyforgery.forges.ForgeRegistry;

import java.util.List;

public class AlloyEntry extends BaseEntryProvider
{
    public AlloyEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Alloy Forging";
    }

    @Override
    protected String nameZH()
    {
        return "合金冶炼";
    }

    @Override
    protected String descEN()
    {
        return "An easy way to increase ore yield.";
    }

    @Override
    protected String descZH()
    {
        return "提高矿石产量的简单方法。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("alloy");
        this.lang().add(context.pageTitle(), "Alloy Forging");
        this.lang().add(context.pageText(),
                """
                        \\
                        Alloy Forging serves as an easy way to increase ore yield.
                        \\
                        \\
                        To construct a forge, simply follow the convenient guide shown in the picture using any combination of blocks that forge type supports.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "合金冶炼");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        合金冶炼是提高矿石产量的简单方法。
                        \\
                        \\
                        要建造一个锻造炉，只需按照图中所示的指南，使用冶炼炉类型所支持的任何方块组合即可。
                        """
        );

        BookSpotlightPageModel alloy = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(ForgeRegistry.controllerBlocksView().stream().map(block -> block.asItem().getDefaultStack())))
                .build();

        Identifier id = BookGenUtil.id("textures/alloy_forge/");

        BookImagePageModel image = BookImagePageModel
                .builder()
                .withImages(
                        id.withSuffixedPath("1.png"),
                        id.withSuffixedPath("2.png"),
                        id.withSuffixedPath("3.png")
                )
                .build();

        return List.of(alloy, image);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ForgeRegistry.getControllerBlocks().get(0));
    }

    @Override
    protected String entryId()
    {
        return "alloy";
    }
}
