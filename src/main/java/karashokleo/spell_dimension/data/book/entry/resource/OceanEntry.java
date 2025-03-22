package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class OceanEntry extends BaseEntryProvider
{
    public OceanEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Ocean of Consciousness";
    }

    @Override
    protected String nameZH()
    {
        return "识之海";
    }

    @Override
    protected String descEN()
    {
        return "Travel to infinity";
    }

    @Override
    protected String descZH()
    {
        return "遨游无尽";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("world");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        The Ocean of Consciousness is a world of nothingness.
                        \\
                        Once you enter this world, your game difficulty will be greatly increased:
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        识之海是一个一无所有的世界。
                        \\
                        \\
                        一旦你进入这个世界，你的游戏难度将大幅提升:
                        """
        );
        BookSpotlightPageModel world = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.MAGIC_MIRROR))
                .build();

        context.page("text");
        this.lang().add(context.pageText(),
                """
                        Your hostility extra level will increase by 100.
                        \\
                        \\
                        You cannot use Waystones to enter or exit the Ocean of Consciousness, the only way to enter is to use the **Magic Mirror**, and the only exit is the **Consciousness Pivot**.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        你的恶意额外难度将提升100级。
                        \\
                        \\
                        你不能使用传送石碑出入识之海，唯一的进入方法是使用**魔镜**，唯一的出口是**意识枢纽**。
                        """
        );
        BookTextPageModel text = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(world, text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.MAGIC_MIRROR);
    }

    @Override
    protected String entryId()
    {
        return "ocean";
    }
}
