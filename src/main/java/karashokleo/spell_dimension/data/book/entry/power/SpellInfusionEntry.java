package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.enchantment_infusion.init.EIItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
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
        return "Infinite possibilities are born from the convergence of magic";
    }

    @Override
    protected String descZH()
    {
        return "魔力汇聚下诞生的是无限的可能性";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("text");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                Enchantment Infusion can be used for enchanting in addition to craft most Spell Scrolls and some special items.
                \\
                \\
                Crafting items using the Infusion Table is the same as enchanting.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                魔咒灌注除了可以用于附魔，还可以合成大部分法术卷轴和一部分特殊物品。
                \\
                \\
                使用灌注台合成物品的操作方法与附魔相同。
                """
        );
        BookSpotlightPageModel text = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(EIItems.INFUSION_TABLE_ITEM, EIItems.INFUSION_PEDESTAL_ITEM))
            .build();

        return List.of(text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.SPELL_PRISM);
    }

    @Override
    protected String entryId()
    {
        return "spell_infusion";
    }
}
