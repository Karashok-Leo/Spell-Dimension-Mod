package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.tom.storagemod.Content;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class StorageEntry extends BaseEntryProvider
{
    public StorageEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Storage";
    }

    @Override
    protected String nameZH()
    {
        return "存储";
    }

    @Override
    protected String descEN()
    {
        return "Well organized.";
    }

    @Override
    protected String descZH()
    {
        return "井然有序。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("storage");
        this.lang().add(context.pageTitle(), "Tom's Simple Storage");
        this.lang().add(context.pageText(),
                """
                        Place a **Storage Connector** with a **Storage Terminal** or **Crafting Terminal** next to your chests, after which you can manage the items of all your chests in a unified way through the terminal.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "汤姆的简易存储");
        this.lang("zh_cn").add(context.pageText(),
                """
                        在你的箱子旁放置**存储连接器**与**存储终端**或者**合成终端**，之后你便可以通过终端统一管理所有箱子里的物品。
                        """
        );

        BookSpotlightPageModel storage = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Content.connector.get(), Content.terminal.get(), Content.craftingTerminal.get()))
                .build();

        context.page("wireless");
        this.lang().add(context.pageTitle(), "Wireless Storage");
        this.lang().add(context.pageText(),
                """
                        Build a level 4+ **Beacon**, or place your storage system next to a high enough **Consciousness Pivot**, bind to the terminal using the **Advanced Wireless Terminal** (Shift + Right-click), and use **Chunk Loader** to load surrounding chunks to enable inter-dimensional remote access to your storage system.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "无线存储");
        this.lang("zh_cn").add(context.pageText(),
                """
                        建造一个4级以上的**信标**，或将你的存储系统安置在足够高的**意识枢纽**旁边，使用**高级无线终端**Shift+右键绑定到终端上，并使用**区块加载器**加载区块，即可实现跨维度远距离访问你的存储系统。
                        """
        );

        BookSpotlightPageModel wireless = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.BEACON, Content.advWirelessTerminal.get(), BookGenUtil.getItem(new Identifier("kibe:chunk_loader"))))
                .build();

        return List.of(storage, wireless);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Content.connector.get());
    }

    @Override
    protected String entryId()
    {
        return "storage";
    }
}
