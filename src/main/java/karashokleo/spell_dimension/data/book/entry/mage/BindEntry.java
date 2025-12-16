package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.recipe.Ingredient;
import net.spell_engine.spellbinding.SpellBindingBlock;

import java.util.List;

public class BindEntry extends BaseEntryProvider
{
    public BindEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Binding";
    }

    @Override
    protected String nameZH()
    {
        return "法术绑定";
    }

    @Override
    protected String descEN()
    {
        return "Bind your spells!";
    }

    @Override
    protected String descZH()
    {
        return "绑定你的法术！";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("bind");
        this.lang().add(context.pageTitle(), "Spell Binding");
        this.lang().add(context.pageText(),
            """
                Use the Spell Binding Table to get your first spell book and bind some spells! You can find a Spell Binding Table in the village or craft one yourself. You need to place some Bookshelves around the Spell Binding Table to bind more spells.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术绑定");
        this.lang("zh_cn").add(context.pageText(),
            """
                使用法术绑定台来获取你的第一本法术书，并绑定一些法术！你可以在村庄里找到法术绑定台或是自己合成一个。你需要在法术绑定台周围放置一些书架才能绑定更多的法术。
                """
        );

        BookSpotlightPageModel bind = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(SpellBindingBlock.ITEM))
            .build();

        return List.of(bind);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(SpellBindingBlock.ITEM);
    }

    @Override
    protected String entryId()
    {
        return "bind";
    }
}
