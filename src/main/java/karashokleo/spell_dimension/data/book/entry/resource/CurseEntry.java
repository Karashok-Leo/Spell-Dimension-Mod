package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Item;

import java.util.List;

public class CurseEntry extends BaseEntryProvider
{
    public CurseEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Curse";
    }

    @Override
    protected String nameZH()
    {
        return "诅咒";
    }

    @Override
    protected String descEN()
    {
        return "\"...but at what cost?\"";
    }

    @Override
    protected String descZH()
    {
        return "“但代价是什么呢？”";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("curse");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
            """
                Wearing a curse can get you some special resources, but it will bring some negative effects.
                \\
                \\
                For example, the **Curse of Envy** will give you a chance to drop mob traits when you kill an enemy, but it will raise your hostility level.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                佩戴诅咒可以获取一些特殊的资源，但会带来一些负面影响。
                \\
                \\
                比如，**嫉妒诅咒**会让你在击杀敌人时有概率掉落词条，但会提高你的恶意等级。
                """
        );

        List<Item> curses = List.of(
            TrinketItems.CURSE_ENVY,
            TrinketItems.CURSE_PRIDE,
            TrinketItems.CURSE_GLUTTONY,
            TrinketItems.CURSE_GREED,
            TrinketItems.CURSE_LUST,
            TrinketItems.CURSE_SLOTH,
            TrinketItems.CURSE_WRATH
        );

        BookSpotlightPageModel curse = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(BookGenUtil.getIngredient(curses.stream().map(Item::getDefaultStack)))
            .build();

        return List.of(curse);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(TrinketItems.CURSE_ENVY);
    }

    @Override
    protected String entryId()
    {
        return "curse";
    }
}
