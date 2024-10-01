package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class DisenchantedEntry extends BaseEntryProvider
{
    public DisenchantedEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Disenchanted Essence";
    }

    @Override
    protected String nameZH()
    {
        return "祛魔精华";
    }

    @Override
    protected String descEN()
    {
        return "'Tears of remorse'";
    }

    @Override
    protected String descZH()
    {
        return "“悔恨之泪”";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("disenchant");
        this.lang().add(context.pageTitle(), "Disenchanted Essence");
        this.lang().add(context.pageText(),
                """
                        \\
                        Disenchanted Essence removes all the magic infused into an item by Enchanted Essences. The only way to obtain an Disenchanted Essence is to purchase it from a Goblin Trader.""");
        this.lang("zh_cn").add(context.pageTitle(), "祛魔精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        祛魔精华能够消除束魔精华注入在一件物品上的所有魔力。获取祛魔精华的唯一途径是从哥布林商人手中购买。
                        """
        );

        BookSpotlightPageModel disenchant = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.DISENCHANTED_ESSENCE))
                .build();

        return List.of(disenchant);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.DISENCHANTED_ESSENCE);
    }

    @Override
    protected String entryId()
    {
        return "disenchant";
    }
}
