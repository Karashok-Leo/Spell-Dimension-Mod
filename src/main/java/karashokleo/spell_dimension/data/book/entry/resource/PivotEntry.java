package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllBlocks;

import java.util.List;

public class PivotEntry extends BaseEntryProvider
{
    public PivotEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Consciousness Pivot";
    }

    @Override
    protected String nameZH()
    {
        return "识之枢纽";
    }

    @Override
    protected String descEN()
    {
        return "The beacon will lead you home";
    }

    @Override
    protected String descZH()
    {
        return "信标会指引你回家";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("pivot");
        this.lang().add(context.pageTitle(), "Consciousness Pivot");
        this.lang().add(context.pageText(),
            """
                Consciousness Pivots are distributed on the surface of the ocean of consciousness, right-clicking on the **Consciousness Core** will activate the pivot and trigger the Invasion Event, monsters will appear in waves and attack the player, defeating all monsters will grant special rewards.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "识之枢纽");
        this.lang("zh_cn").add(context.pageText(),
            """
                意识枢纽分布在在识之海维度表面，右击其上的**识之核心**即可激活枢纽，同时触发入侵事件，怪物将会分波次出现并攻击玩家，击败所有怪物即可获得特殊奖励。
                """
        );
        BookTextPageModel pivot = BookTextPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .build();

        context.page("beacon");
        this.lang().add(context.pageText(),
            """
                After the Invasion Event, a light column will be generated from the Consciousness Core. Standing on the light column will teleport you to the corresponding position in the overworld.
                \\
                \\
                Activated consciousness pivots can affect the surrounding storage system, acting as a beacon.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                入侵事件结束后识之核心会产生一道光柱，站在光柱上即可传送至主世界对应位置。
                \\
                \\
                已激活的意识枢纽可以影响周围的存储系统，起到相当于信标的作用。
                """
        );
        BookTextPageModel beacon = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        return List.of(pivot, beacon);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllBlocks.CONSCIOUSNESS_CORE.item());
    }

    @Override
    protected String entryId()
    {
        return "pivot";
    }
}
