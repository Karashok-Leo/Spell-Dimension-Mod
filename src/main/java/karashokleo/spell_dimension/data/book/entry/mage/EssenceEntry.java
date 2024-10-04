package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStacks;
import karashokleo.spell_dimension.util.BookGenUtil;
import karashokleo.spell_dimension.util.SchoolUtil;

import java.util.List;

public class EssenceEntry extends BaseEntryProvider
{
    public EssenceEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Essence";
    }

    @Override
    protected String nameZH()
    {
        return "魔法精华";
    }

    @Override
    protected String descEN()
    {
        return "Subtle crystals of magic.";
    }

    @Override
    protected String descZH()
    {
        return "魔法凝结而成的微妙结晶。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("base");
        this.lang().add(context.pageTitle(), "Base Essence");
        this.lang().add(context.pageText(), "Perhaps you have noticed that there is a probability of dropping some magic essences on spell hits. They are important materials on a mage's growth path, and can be used to craft some magic items, such as spell books, runes and enchanted essences.");
        this.lang("zh_cn").add(context.pageTitle(), "基础精华");
        this.lang("zh_cn").add(context.pageText(), "也许你已经注意到，法术命中时有概率掉落一些魔法精华。它们是魔法师成长路上的重要材料，可以用来合成一些魔法物品，比如法术书、符文和束魔精华。");

        BookSpotlightPageModel base = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(AllStacks.BASE_ESSENCE_STACKS))
                .build();

        return List.of(base);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.BASE_ESSENCES.get(SchoolUtil.SCHOOLS.get(0)).get(1));
    }

    @Override
    protected String entryId()
    {
        return "essence";
    }
}