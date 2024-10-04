package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;

import java.util.List;

public class StalkerEntry extends BaseEntryProvider
{
    public StalkerEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Stalker";
    }

    @Override
    protected String nameZH()
    {
        return "Stalker";
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
        context.page("boss");
        this.lang().add(context.pageTitle(), "Stalker");
        this.lang().add(context.pageText(),
                """
                        \\
                        Spawns rarely when an **Ancient Vase** is broken.
                        \\
                        \\
                        *Ancient Vase: Generated in the Ancient Temple in the Deeper Darker.*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "Stalker");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        在**远古花瓶**被打破时有小概率生成。
                        \\
                        \\
                        *远古花瓶: 生成于深暗维度的远古神庙。*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(new Identifier("deeperdarker", "ancient_vase")))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("deeperdarker:stalker")
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        *Ancient Temple: Structure ID deeperdarker:ancient_temple*
                        \\
                        \\
                        *Deeper Darker: Use the Heart of the Deep to open the portal within the ancient city to enter.*
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        *远古神庙: 结构ID为 deeperdarker:ancient_temple*
                        \\
                        \\
                        *深暗维度: 使用深渊之心开启古代城市内的传送门进入。*
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(boss, entity, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("deeperdarker", "soul_crystal")));
    }

    @Override
    protected String entryId()
    {
        return "stalker";
    }
}