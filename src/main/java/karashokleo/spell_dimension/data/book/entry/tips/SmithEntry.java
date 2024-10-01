package karashokleo.spell_dimension.data.book.entry.tips;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class SmithEntry extends BaseEntryProvider
{

    public static final Item TABLE = BookGenUtil.getItem(Identifier.of("equipment_standard", "reforge_table"));

    public SmithEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Smithing";
    }

    @Override
    protected String nameZH()
    {
        return "重铸";
    }

    @Override
    protected String descEN()
    {
        return "Equipment Modifier!";
    }

    @Override
    protected String descZH()
    {
        return "装备加成！";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("modifier");
        this.lang().add(context.pageTitle(), "Modifier");
        this.lang().add(context.pageText(),
                """
                        \\
                        Every tool and armor piece you obtain will have special modifiers, which provide additional stat bonuses and abilities.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "装备词条");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你获得的每件工具和盔甲都有特殊的词条，可以提供额外的属性加成。
                        """
        );

        BookSpotlightPageModel modifier = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(TABLE))
                .build();

        context.page("reforge");
        this.lang().add(context.pageTitle(), "Reforge");
        this.lang().add(context.pageText(),
                """
                        \\
                        You can use reforge scrolls to reforge your equipment on the reforge table, and the higher the level of reforge scrolls the higher the probability of getting better modifiers.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "重铸");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你可以使用重铸卷轴在重铸台上重铸你的装备，越高级的重铸卷轴能获得较好词条的概率越高。
                        """
        );

        BookSpotlightPageModel reforge = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(
                        Identifier.of("equipment_standard", "reforge_scroll_lv1"),
                        Identifier.of("equipment_standard", "reforge_scroll_lv2"),
                        Identifier.of("equipment_standard", "reforge_scroll_lv3")
                ))
                .build();

        return List.of(modifier, reforge);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(TABLE);
    }

    @Override
    protected String entryId()
    {
        return "smith";
    }
}
