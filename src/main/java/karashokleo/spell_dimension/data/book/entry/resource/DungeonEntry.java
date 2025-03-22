package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.item.Items;

import java.util.List;

public class DungeonEntry extends BaseEntryProvider
{
    public DungeonEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Dungeon Trial";
    }

    @Override
    protected String nameZH()
    {
        return "地牢试炼";
    }

    @Override
    protected String descEN()
    {
        return "Fortune favors the bold";
    }

    @Override
    protected String descZH()
    {
        return "富贵险中求";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("text");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Through special entrances on the surface, you can enter a special dungeon dimension with a lot of special loot, but also powerful monsters, you need to defeat the boss to safely return.
                        \\
                        \\
                        You can use the Locate spell to find the dungeon entrance.
                        \\
                        \\
                        Defeat the boss in the last room or enter the command **/dungeon leave** to leave the dungeon.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        通过地表上的特殊入口，你可以进入一个特殊的地牢维度，其中有着大量的特殊战利品，但也有着强大的怪物，你需要击败Boss才能安全返回。
                        \\
                        \\
                        你可以通过定位法术寻找地牢入口。
                        \\
                        \\
                        击败最后一个房间中的Boss或者输入命令**/dungeon leave**即可离开地牢。
                        """
        );
        BookTextPageModel text = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("entries");
        this.lang().add(context.pageText(),
                """
                        Below will list the types of special loot contained in various dungeons:
                        \\
                        \\
                        Dark Dungeon: Large amounts of Enchanted Essence and Enlightening Essence.
                        \\
                        \\
                        Desert Dungeon: Work in progress...
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        下面将列出各种地牢包含的特殊战利品类型:
                        \\
                        \\
                        暗黑地牢: 大量束魔精华和源启精华。
                        \\
                        \\
                        沙漠地牢: 尚未完成...
                        """
        );
        BookTextPageModel entries = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(text, entries);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.MOSSY_STONE_BRICKS);
    }

    @Override
    protected String entryId()
    {
        return "dungeon";
    }
}
