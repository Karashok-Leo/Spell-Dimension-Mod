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

public class BomdLichEntry extends BaseEntryProvider
{
    public BomdLichEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Night Lich";
    }

    @Override
    protected String nameZH()
    {
        return "暗夜巫妖";
    }

    @Override
    protected String descEN()
    {
        return "Returning in the midnight";
    }

    @Override
    protected String descZH()
    {
        return "于极夜中归来";
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
                        Spawns inside the **Lich Tower**(bosses_of_mass_destruction:lich_tower).
                        \\
                        This structure can be located in the Overworld using the Soul Star.
                        """.formatted(TextConstants.BOSS_LEVELS[2])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        生成于**巫妖塔**(bosses_of_mass_destruction:lich_tower)内。
                        \\
                        可用灵魂之星在主世界寻找该结构。
                        """.formatted(TextConstants.BOSS_LEVELS[2])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:soul_star"))))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Embed the Soul Star into the four altars on the tower to summon.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        在塔上的四个祭坛嵌入灵魂之星即可召唤。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("bosses_of_mass_destruction:lich")
                .build();

        return List.of(boss, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:ancient_anima")));
    }

    @Override
    protected String entryId()
    {
        return "bomd_lich";
    }
}
