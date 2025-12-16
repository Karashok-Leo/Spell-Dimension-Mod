package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;

import java.util.List;

public class LocateSpellEntry extends BaseEntryProvider
{
    public LocateSpellEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Locate Spell";
    }

    @Override
    protected String nameZH()
    {
        return "定位法术";
    }

    @Override
    protected String descEN()
    {
        return "No compass required";
    }

    @Override
    protected String descZH()
    {
        return "无需指南针";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("spell");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                When the off-hand holds a specific key item, cast the locate spell on a **Lodestone** to locate some special structures.
                \\
                \\
                You can press U on the Lodestone to see the specific recipe. Or search for the structure name in EMI/REI to find it.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                副手持有特定索引物品时对**磁石**施放定位法术可以定位到一些特殊的结构。
                \\
                \\
                你可以对磁石按下U键查看具体配方。或者在EMI/REI中搜索结构名称进行查找。
                """
        );
        BookSpotlightPageModel spell = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(BookGenUtil.getNbtIngredient(AllItems.SPELL_SCROLL.getStack(AllSpells.LOCATE)))
            .build();

        return List.of(spell);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.COMPASS);
    }

    @Override
    protected String entryId()
    {
        return "locate_spell";
    }
}
