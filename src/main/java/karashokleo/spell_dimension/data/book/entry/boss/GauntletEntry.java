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
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class GauntletEntry extends BaseEntryProvider
{
    public GauntletEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Gauntlet";
    }

    @Override
    protected String nameZH()
    {
        return "下界铁掌";
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
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Level: %d+
                        \\
                        \\
                        Spawns inside the **Gauntlet Arena**(bosses_of_mass_destruction:gauntlet_arena).
                        \\
                        This structure can be located in the Nether using the Locate Spell.
                        """.formatted(TextConstants.BOSS_LEVELS[2])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        生成于**下界铁掌竞技场**(bosses_of_mass_destruction:gauntlet_arena)内。
                        \\
                        可通过定位法术在下界中定位到此结构。
                        """.formatted(TextConstants.BOSS_LEVELS[2])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:blazing_eye"))))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Spawns after the Ancient Carved Blackstone in the center of Gauntlet Arena gets destroyed.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        破坏竞技场中心的远古雕纹黑石后生成。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("bosses_of_mass_destruction:gauntlet")
                .build();

        return List.of(boss, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:blazing_eye")));
    }

    @Override
    protected String entryId()
    {
        return "gauntlet";
    }
}
