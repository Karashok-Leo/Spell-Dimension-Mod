package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class WardenEntry extends BaseEntryProvider
{
    public WardenEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Warden";
    }

    @Override
    protected String nameZH()
    {
        return "监守者";
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
        this.lang().add(context.pageTitle(), "Warden");
        this.lang().add(context.pageText(),
                """
                        \\
                        When **Sculk Sensor** is activated at least 4 times, Warden burrows out of the ground.
                        \\
                        \\
                        *Sculk Sensor: Generated in the Deep Dark.*
                        \\
                        \\
                        Drops **Heart of the Deep** upon defeating Warden.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "监守者");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        当**幽匿尖啸体**被激活至少4次时，监守者会从地面钻出。
                        \\
                        \\
                        *幽匿尖啸体: 生成于深暗之域。*
                        \\
                        \\
                        击败监守者后掉落**深渊之心**。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.SCULK_SHRIEKER))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:warden")
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("deeperdarker", "heart_of_the_deep")));
    }

    @Override
    protected String entryId()
    {
        return "warden";
    }
}
