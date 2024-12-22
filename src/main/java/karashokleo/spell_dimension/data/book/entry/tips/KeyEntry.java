package karashokleo.spell_dimension.data.book.entry.tips;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.item.Items;

import java.util.List;

public class KeyEntry extends BaseEntryProvider
{
    public KeyEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Key Binds";
    }

    @Override
    protected String nameZH()
    {
        return "键位绑定";
    }

    @Override
    protected String descEN()
    {
        return "Some keys you need to know.";
    }

    @Override
    protected String descZH()
    {
        return "一些你需要知道的键位。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Key Binds");
        this.lang().add(context.pageText(),
                """
                        \\
                        ` (~): Chain Mining
                        \\
                        \\
                        R: Combat Roll
                        \\
                        \\
                        B: Open Backpack
                        \\
                        \\
                        G: Open Ender Bag
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "键位绑定");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        ` (~): 连锁采集
                        \\
                        \\
                        R: 翻滚
                        \\
                        \\
                        B: 打开背包
                        \\
                        \\
                        G: 打开末影袋
                        """
        );

        BookTextPageModel prev = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        J: Turn on/off auto step-up
                        \\
                        \\
                        Middle Button / Double-click: Sort Inventory
                        \\
                        \\
                        Left Shift + W: Wall-Jumping
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        J: 开启/关闭自动上坡
                        \\
                        \\
                        鼠标中键 / 双击: 整理物品栏
                        \\
                        \\
                        左Shift + W: 攀墙跳
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
        return BookIconModel.create(Items.BIRCH_HANGING_SIGN);
    }

    @Override
    protected String entryId()
    {
        return "key";
    }
}
