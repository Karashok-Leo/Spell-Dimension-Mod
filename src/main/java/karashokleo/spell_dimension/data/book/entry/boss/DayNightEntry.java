package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class DayNightEntry extends BaseEntryProvider
{
    public DayNightEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Day Stalker & Night Prowler";
    }

    @Override
    protected String nameZH()
    {
        return "昼从者 & 夜伏者";
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
                        \\
                        Level: 250+
                        \\
                        \\
                        Use Chaos Orb to summon.
                        \\
                        \\
                        Drops **Celestial Luminary** upon defeating Night Prowler.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: 250+
                        \\
                        \\
                        使用混沌宝珠召唤。
                        \\
                        \\
                        击败夜伏者后掉落**无尽星辉**。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.CELESTIAL_LUMINARY))
                .build();

        BookEntityPageModel day = BookEntityPageModel
                .builder()
                .withEntityId("soulsweapons:day_stalker")
                .build();
        BookEntityPageModel night = BookEntityPageModel
                .builder()
                .withEntityId("soulsweapons:night_prowler")
                .build();

        return List.of(boss, day, night);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("soulsweapons:arkenstone")));
    }

    @Override
    protected String entryId()
    {
        return "day_night";
    }
}
