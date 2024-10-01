package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;

import java.util.List;

public class CaptainEntry extends BaseEntryProvider
{
    public CaptainEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Ghost of Captain Cornelia";
    }

    @Override
    protected String nameZH()
    {
        return "船长科妮莉亚亡灵";
    }

    @Override
    protected String descEN()
    {
        return "The sparse trumpets of the survivors would have awakened her soul.";
    }

    @Override
    protected String descZH()
    {
        return "求生者吹响的稀疏号角自会惊醒她的灵魂。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), "Ghost of Captain Cornelia");
        this.lang().add(context.pageText(),
                """
                        \\
                        When you hold the **Shell Horn** to blow it on the seashore of the Frozen Deep, Captain Cornelia Undead will emerge from the water.
                        \\
                        \\
                        *Shell Horn: Can be obtained by killing the Marauders staying in the Captain's Room in the *Stranded Ship*.*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "船长科妮莉亚亡灵");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        当你手持**贝壳号角**并在冰冻深海的海边吹响，船长科妮莉亚亡灵会从水中浮现。
                        \\
                        \\
                        *贝壳号角: 击杀*搁浅舰船*遗迹中待在船长室的掠夺者可获得。*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(new Identifier("aquamirae", "shell_horn")))
                .build();

        context.page("entity");
        this.lang().add(context.pageText(),
                """
                        \\
                        *Stranded Ship: Structure ID aquamirae:ship*
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        *搁浅舰船: 结构ID为aquamirae:ship*
                        """
        );

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withText(context.pageText())
                .withEntityId("aquamirae:captain_cornelia")
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("aquamirae", "three_bolt_helmet")));
    }

    @Override
    protected String entryId()
    {
        return "captain";
    }
}
