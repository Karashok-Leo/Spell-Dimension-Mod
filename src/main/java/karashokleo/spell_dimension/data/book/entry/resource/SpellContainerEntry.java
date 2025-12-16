package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class SpellContainerEntry extends BaseEntryProvider
{
    public SpellContainerEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Container";
    }

    @Override
    protected String nameZH()
    {
        return "法术容器";
    }

    @Override
    protected String descEN()
    {
        return "Put to good use";
    }

    @Override
    protected String descZH()
    {
        return "化为己用";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("spell_container");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                When your hostility level reaches a certain point, monsters have a chance to carry powerful spell traits.
                \\
                \\
                At this point, wearing a **spell container** is the best choice to defend against spell damage from traits.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                当你的恶意难度等级提高到一定程度时，怪物有概率携带强力的法术词条。
                \\
                \\
                此时，佩戴**法术容器**是抵御来自词条的法术伤害的最佳选择。
                """
        );

        BookSpotlightPageModel spell_container = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(AllItems.SPELL_CONTAINER))
            .build();

        return List.of(spell_container);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.SPELL_CONTAINER);
    }

    @Override
    protected String entryId()
    {
        return "spell_container";
    }
}
