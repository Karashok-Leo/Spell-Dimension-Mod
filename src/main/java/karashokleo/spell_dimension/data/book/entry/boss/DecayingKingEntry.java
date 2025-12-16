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
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class DecayingKingEntry extends BaseEntryProvider
{
    public DecayingKingEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "The Decaying King";
    }

    @Override
    protected String nameZH()
    {
        return "腐朽之王";
    }

    @Override
    protected String descEN()
    {
        return "Kingdom that fell under the lava sea";
    }

    @Override
    protected String descZH()
    {
        return "沦陷于熔岩之海的王国";
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
                Spawns inside the **Decaying Kingdom**(soulsweapons:decaying_kingdom).
                \\
                This structure can be located in the Nether using the Moonstone Compass.
                """.formatted(TextConstants.BOSS_LEVELS[0])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                等级: %d+
                \\
                \\
                生成于**腐朽王国**(soulsweapons:decaying_kingdom)内。
                \\
                可用月石罗盘在下界寻找该结构。
                """.formatted(TextConstants.BOSS_LEVELS[0])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(AllItems.ACCURSED_BLACKSTONE))
            .build();

        context.page("next");
        this.lang().add(context.pageText(),
            """
                Or use Withered Demon Heart on the Blackstone Pedestal to summon.
                \\
                \\
                Drops **Accursed Blackstone** upon defeating the Decaying King.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                或者对黑石基座使用凋零恶魔之心召唤。
                \\
                \\
                击败腐朽之王后掉落**朽咒黑石**。
                """
        );

        BookTextPageModel next = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();
        BookEntityPageModel entity = BookEntityPageModel
            .builder()
            .withEntityId("soulsweapons:accursed_lord_boss")
            .withScale(0.8F)
            .withOffset(0.15F)
            .build();

        return List.of(boss, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("soulsweapons:darkin_blade")));
    }

    @Override
    protected String entryId()
    {
        return "decaying_king";
    }
}
