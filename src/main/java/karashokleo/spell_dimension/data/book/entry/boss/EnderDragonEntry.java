package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class EnderDragonEntry extends BaseEntryProvider
{
    public EnderDragonEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Ender Dragon";
    }

    @Override
    protected String nameZH()
    {
        return "末影龙";
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
        this.lang().add(context.pageTitle(), "Ender Dragon");
        this.lang().add(context.pageText(),
                """
                        \\
                        Level: %d+
                        \\
                        \\
                        To enter the End, you need to collect 12 special Ender Eyes, defeat the Night Prowler, and use the Celestial Luminary it drop to give yourself the right to enter the End.
                        """.formatted(TextConstants.BOSS_LEVELS[3])
        );
        this.lang("zh_cn").add(context.pageTitle(), "末影龙");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: %d+
                        \\
                        \\
                        欲进入末地，你需要先集齐12个特殊的末影之眼，击败夜伏者后，使用其掉落的无尽星辉赋予自己进入末地的权柄。
                        """.formatted(TextConstants.BOSS_LEVELS[3])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.DRAGON_HEAD))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:ender_dragon")
                .withOffset(-0.4f)
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.DRAGON_HEAD);
    }

    @Override
    protected String entryId()
    {
        return "dragon";
    }
}
