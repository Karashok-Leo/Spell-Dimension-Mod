package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class TheEyeEntry extends BaseEntryProvider
{
    public TheEyeEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "The Eye";
    }

    @Override
    protected String nameZH()
    {
        return "眼球";
    }

    @Override
    protected String descEN()
    {
        return "Only lifeless remains";
    }

    @Override
    protected String descZH()
    {
        return "只余毫无生气的残骸";
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
                        Use 25 obsidian, 9 crying obsidian, and 8 end rods to build the altar in **The End**, wait a moment to summon.
                        """.formatted(TextConstants.BOSS_LEVELS[3])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        使用25块黑曜石，9块哭泣的黑曜石和8根末地在**末地**搭建祭坛，等待片刻后即可召唤。
                        """.formatted(TextConstants.BOSS_LEVELS[3])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.ENDER_EYE))
                .build();
        BookMultiblockPageModel alter = BookMultiblockPageModel
                .builder()
                .withMultiblockId("spell-dimension-book:ender_eye_altar")
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("adventurez:the_eye")
                .build();

        return List.of(boss, alter, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.ENDER_EYE);
    }

    @Override
    protected String entryId()
    {
        return "the_eye";
    }
}
