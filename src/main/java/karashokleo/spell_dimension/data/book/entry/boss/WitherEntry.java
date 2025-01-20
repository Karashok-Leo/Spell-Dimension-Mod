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
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class WitherEntry extends BaseEntryProvider
{
    public WitherEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Wither";
    }

    @Override
    protected String nameZH()
    {
        return "凋灵";
    }

    @Override
    protected String descEN()
    {
        return "Everything withers...";
    }

    @Override
    protected String descZH()
    {
        return "万物凋零...";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        Level: %d+
                        \\
                        \\
                        Wither now has an additional charge skill. During the charge, the Wither will ignore hardness and destroy all blocks it touches (including bedrock), dealing a large amount of damage to the target.
                        """.formatted(TextConstants.BOSS_LEVELS[0])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: %d+
                        \\
                        \\
                        凋灵现在额外拥有一个冲撞技能，冲撞过程中，凋灵会无视硬度破坏它碰到的所有方块(包括基岩)，并对目标造成大量伤害。
                        """.formatted(TextConstants.BOSS_LEVELS[0])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("soulsweapons:shard_of_uncertainty"))))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        Drops **Shard of Uncertainty** upon defeating Wither.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        击败凋灵后掉落**不确定性的残片**。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:wither")
                .build();

        return List.of(boss, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.NETHER_STAR);
    }

    @Override
    protected String entryId()
    {
        return "wither";
    }
}
