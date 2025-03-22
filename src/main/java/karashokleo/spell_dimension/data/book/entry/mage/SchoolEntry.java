package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.wizards.item.Weapons;

import java.util.List;

public class SchoolEntry extends BaseEntryProvider
{
    public SchoolEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell School";
    }

    @Override
    protected String nameZH()
    {
        return "魔法学派";
    }

    @Override
    protected String descEN()
    {
        return "The six branches of the magic system";
    }

    @Override
    protected String descZH()
    {
        return "魔法体系的六大分支";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("school");
        this.lang().add(context.pageTitle(), "Spell School");
        this.lang().add(context.pageText(),
                """
                        There are six schools in the magic system, namely Arcane, Fire, Frost, Healing, Lightning, and Soul. The last two of these are only mastered by a very small number of people, as it is almost impossible to find information related to them.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "魔法学派");
        this.lang("zh_cn").add(context.pageText(),
                """
                        魔法体系中共有六大学派，分别为奥秘、火焰、寒冰、治愈、雷电、灵魂。其中最后两种只被极少数人掌握，因为几乎无法找到与其有关的资料。
                        """
        );

        BookTextPageModel school = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        return List.of(school);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Weapons.frostStaff.item());
    }

    @Override
    protected String entryId()
    {
        return "school";
    }
}
