package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class GuardianEntry extends BaseEntryProvider
{
    public GuardianEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Elder Guardian";
    }

    @Override
    protected String nameZH()
    {
        return "远古守卫者";
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
        this.lang().add(context.pageTitle(), "Elder Guardian");
        this.lang().add(context.pageText(),
                """
                        \\
                        In the **Ocean Monument**.
                        \\
                        \\
                        *Ocean Monument: Structure ID minecraft:monument*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "远古守卫者");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        出没于**海底神殿**。
                        \\
                        \\
                        *海底神殿: 结构ID为 minecraft:monument*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(ComplementItems.GUARDIAN_EYE))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:elder_guardian")
                .withScale(0.3F)
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ComplementItems.GUARDIAN_EYE);
    }

    @Override
    protected String entryId()
    {
        return "guardian";
    }
}
