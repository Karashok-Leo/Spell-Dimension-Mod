package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class ElderGuardianEntry extends BaseEntryProvider
{
    public ElderGuardianEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Elder Guardian";
    }

    @Override
    protected String nameZH()
    {
        return "远古守卫者";
    }

    @Override
    protected String descEN()
    {
        return "Gazing from the abyss";
    }

    @Override
    protected String descZH()
    {
        return "来自深渊的凝视";
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
                        Spawns inside the **Ocean Monument**(betteroceanmonuments:ocean_monument).
                        \\
                        This structure can be located in the Overworld using the Locate Spell.
                        """.formatted(TextConstants.BOSS_LEVELS[0])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        生成于**海底神殿**(betteroceanmonuments:ocean_monument)内。
                        \\
                        可通过定位法术在主世界中定位到此结构。
                        """.formatted(TextConstants.BOSS_LEVELS[0])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.ABYSS_GUARD))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Drops **Abyss Guard** upon defeating Elder Guardian.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        击败远古守卫者后掉落**深渊守护**。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:elder_guardian")
                .withScale(0.3F)
                .build();

        return List.of(boss, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ComplementItems.GUARDIAN_EYE);
    }

    @Override
    protected String entryId()
    {
        return "guardian";
    }
}
