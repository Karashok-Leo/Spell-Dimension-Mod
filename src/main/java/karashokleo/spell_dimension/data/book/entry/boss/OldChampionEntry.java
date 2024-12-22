package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class OldChampionEntry extends BaseEntryProvider
{
    public OldChampionEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Old Champion's Remains";
    }

    @Override
    protected String nameZH()
    {
        return "古英雄的遗骸";
    }

    @Override
    protected String descEN()
    {
        return "The deceased would not sleep";
    }

    @Override
    protected String descZH()
    {
        return "死者不眠";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        Level: 100+
                        \\
                        \\
                        Spawns inside the **Champion's Grave**(soulsweapons:champions_grave).
                        \\
                        This structure can be located in the Overworld using the Moonstone Compass.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        等级: 100+
                        \\
                        \\
                        生成于**古英雄的坟墓**(soulsweapons:champions_grave)内。
                        \\
                        可用月石罗盘在主世界寻找该结构。
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.ATOMIC_BREASTPLATE))
                .build();

        context.page("prev");
        this.lang().add(context.pageText(),
                """
                        \\
                        Or use Draugr on the Old Moon Altar to summon.
                        \\
                        \\
                        Spawns **Frenzied Shade** upon death.
                        \\
                        Frenzied Shade can phase through walls and inflicts various debuffs such as blindness and wither on attack.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        或者对破旧的月光祭坛使用德拉古尔召唤。
                        \\
                        \\
                        死后生成**暗夜之影**。
                        \\
                        暗夜之影可以穿墙，攻击时会使目标获得失明、凋零等多种负面效果。
                        """
        );

        BookTextPageModel prev = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        Drops **Atom Breastplate** upon defeating the Champion's Remains.
                        \\
                        \\
                        Drops **Essence of Eventide** upon defeating the Frenzied Shade.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        击败古英雄的遗骸后掉落**原子护心镜**。
                        \\
                        \\
                        击败暗夜之影后掉落**日暮精粹**。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();
        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("soulsweapons:draugr_boss")
                .withScale(0.8f)
                .withOffset(0.2f)
                .build();
        BookEntityPageModel shade = BookEntityPageModel
                .builder()
                .withEntityId("soulsweapons:night_shade")
                .build();

        return List.of(boss, prev, next, entity, shade);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("soulsweapons:essence_of_eventide")));
    }

    @Override
    protected String entryId()
    {
        return "old_champion";
    }
}
