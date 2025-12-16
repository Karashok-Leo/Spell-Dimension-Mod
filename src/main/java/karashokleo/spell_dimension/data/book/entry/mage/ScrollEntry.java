package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.wizards.WizardsMod;

import java.util.List;

public class ScrollEntry extends BaseEntryProvider
{
    public static final ItemStack STACK = AllItems.SPELL_SCROLL.getStack(new Identifier(WizardsMod.ID, "fire_breath"));

    public ScrollEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Scroll";
    }

    @Override
    protected String nameZH()
    {
        return "法术卷轴";
    }

    @Override
    protected String descEN()
    {
        return "\"The Wisdom of the Ancients\"";
    }

    @Override
    protected String descZH()
    {
        return "“古人的智慧”";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Spell Scroll");
        this.lang().add(context.pageText(),
            """
                There are a limited number of spells that you can learn through the Spell Binding Table. To learn to use more spells, you need to have Spell Scrolls.
                \\
                \\
                Search for spell scrolls to see how to get them.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术卷轴");
        this.lang("zh_cn").add(context.pageText(),
            """
                通过法术绑定台能够学习到的法术有限。要想学习使用更多魔法，你需要拥有法术卷轴。
                \\
                \\
                搜索法术卷轴以查阅其获取方式。
                """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(BookGenUtil.getNbtIngredient(STACK))
            .build();

        context.page("next");
        this.lang().add(context.pageText(),
            """
                Spell Scrolls can be used directly to cast spells, or they can be freely bound or unbound to Spell Books. Spell Scrolls are usually obtained by infusing specific materials with magic.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                法术卷轴可以直接用于施法，也可以自由地与法术书绑定或解绑。法术卷轴通常需要使用特定的材料进行魔力灌注获得。
                """
        );

        BookTextPageModel next = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        return List.of(prev, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(STACK);
    }

    @Override
    protected String entryId()
    {
        return "scroll";
    }
}
