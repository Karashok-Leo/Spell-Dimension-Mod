package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class SpellInfusionEntry extends BaseEntryProvider
{
    public SpellInfusionEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Infusion";
    }

    @Override
    protected String nameZH()
    {
        return "魔力灌注";
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
        context.page("text");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        With spell infusion, you can craft most spell scrolls and some trinkets.
                        \\
                        \\
                        Place the first ingredient on the spell infusion pedestal, then right-click the pedestal with the second ingredient to start crafting.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        通过魔力灌注，你可以合成大部分法术卷轴和一部分饰品。
                        \\
                        \\
                        将第一个原材料放到魔力灌注基座上，然后用另一种材料右键基座即可开始合成。
                        """
        );
        BookSpotlightPageModel text = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllBlocks.SPELL_INFUSION_PEDESTAL.item()))
                .build();

        return List.of(text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllBlocks.SPELL_INFUSION_PEDESTAL.item());
    }

    @Override
    protected String entryId()
    {
        return "spell_infusion";
    }
}
