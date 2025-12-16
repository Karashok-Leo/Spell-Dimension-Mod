package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class VoidBlossomEntry extends BaseEntryProvider
{
    public VoidBlossomEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Void Blossom";
    }

    @Override
    protected String nameZH()
    {
        return "虚空之花";
    }

    @Override
    protected String descEN()
    {
        return "Blooming in the abyss...";
    }

    @Override
    protected String descZH()
    {
        return "于深渊中绽放...";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                Level: %d+
                \\
                \\
                Spawns in rare caves at the bottom of the world(bosses_of_mass_destruction:void_blossom).
                \\
                This structure can be located in the Overworld using the Void Lily.
                """.formatted(TextConstants.BOSS_LEVELS[2])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                等级: %d+
                \\
                \\
                生成于世界底部稀有的洞穴(bosses_of_mass_destruction:void_blossom)内。
                \\
                可用虚空百合在主世界寻找该结构。
                """.formatted(TextConstants.BOSS_LEVELS[2])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:void_thorn"))))
            .build();

        BookEntityPageModel entity = BookEntityPageModel
            .builder()
            .withEntityId("bosses_of_mass_destruction:void_blossom")
            .withOffset(-4.5f)
            .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:void_thorn")));
    }

    @Override
    protected String entryId()
    {
        return "void_blossom";
    }
}
