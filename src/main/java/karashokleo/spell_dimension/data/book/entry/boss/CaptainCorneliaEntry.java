package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.util.Identifier;

import java.util.List;

public class CaptainCorneliaEntry extends BaseEntryProvider
{
    public CaptainCorneliaEntry(CategoryProvider parent)
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
        return "The sparse trumpets of the survivors would have awakened her soul";
    }

    @Override
    protected String descZH()
    {
        return "求生者吹响的稀疏号角自会惊醒她的灵魂";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Level: %d+
                        \\
                        \\
                        When you hold the **Shell Horn** to blow it on the seashore of the Frozen Deep, Captain Cornelia Undead will emerge from the water.
                        """.formatted(TextConstants.BOSS_LEVELS[1])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        当你手持**贝壳号角**并在冰冻深海的海边吹响，船长科妮莉亚亡灵会从水中浮现。
                        """.formatted(TextConstants.BOSS_LEVELS[1])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(new Identifier("aquamirae", "shell_horn")))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Shell Horn: Can be obtained by killing the Marauders staying in the Captain's Room in the **Stranded Ship**(aquamirae:ship).
                        \\
                        \\
                        This structure can be located in the Overworld using the Locate Spell.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        贝壳号角: 击杀**搁浅舰船**遗迹(aquamirae:ship)中待在船长室的掠夺者可获得。
                        \\
                        可通过定位法术在主世界中定位到此结构。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("aquamirae:captain_cornelia")
                .withOffset(0.3F)
                .build();

        return List.of(boss, next, entity);
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
