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

public class ChaosMonarchEntry extends BaseEntryProvider
{
    public ChaosMonarchEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Monarch of Chaos";
    }

    @Override
    protected String nameZH()
    {
        return "混沌君主";
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
                        Level: %d+
                        \\
                        \\
                        Use the Shard of Uncertainty on the Blackstone Pedestal to summon.
                        """.formatted(TextConstants.BOSS_LEVELS[1])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: %d+
                        \\
                        \\
                        对黑石基座使用不确定性的残片召唤。
                        """.formatted(TextConstants.BOSS_LEVELS[1])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("soulsweapons:chaos_crown"))))
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("soulsweapons:chaos_monarch")
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("soulsweapons:chaos_crown")));
    }

    @Override
    protected String entryId()
    {
        return "chaos_monarch";
    }
}
