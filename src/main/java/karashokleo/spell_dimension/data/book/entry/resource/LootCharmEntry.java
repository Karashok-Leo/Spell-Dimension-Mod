package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Item;

import java.util.List;

public class LootCharmEntry extends BaseEntryProvider
{
    public LootCharmEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Looting Charm";
    }

    @Override
    protected String nameZH()
    {
        return "抢夺宝珠";
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
        context.page("charm");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        Wearing a looting charm can get you some special resources.
                        \\
                        \\
                        You can press U on the looting charm to check their additional looting effects.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        佩戴抢夺宝珠可以获取一些特殊的资源。
                        \\
                        \\
                        你可以对抢夺宝珠按下U键查看它们的额外抢夺效果。
                        """
        );

        List<Item> charms = List.of(
                TrinketItems.LOOT_1,
                TrinketItems.LOOT_2,
                TrinketItems.LOOT_3,
                TrinketItems.LOOT_4
        );

        BookSpotlightPageModel charm = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(charms.stream().map(Item::getDefaultStack)))
                .build();

        context.page("obtain");
        this.lang().add(context.pageText(),
                """
                        \\
                        To obtain looting charms of levels 1-4, you need to complete different tasks:
                        \\
                        \\
                        1: Kill The Decaying King
                        2: Kill Monarch of Chaos
                        3: Kill Void Blossom
                        4: Kill Ender Dragon
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        获取1~4级的抢夺宝珠需要完成不同任务，分别为：
                        \\
                        \\
                        1: 击杀腐朽之王
                        2: 击杀混沌君主
                        3: 击杀虚空之花
                        4: 击杀末影龙
                        """
        );

        BookTextPageModel obtain = BookTextPageModel.builder()
                .withText(context.pageText())
                .build();

        return List.of(charm, obtain);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(TrinketItems.LOOT_4);
    }

    @Override
    protected String entryId()
    {
        return "loot_charm";
    }
}
