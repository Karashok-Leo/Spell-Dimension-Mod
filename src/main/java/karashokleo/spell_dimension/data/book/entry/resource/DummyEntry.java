package karashokleo.spell_dimension.data.book.entry.resource;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class DummyEntry extends BaseEntryProvider
{
    public DummyEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Dummy";
    }

    @Override
    protected String nameZH()
    {
        return "假人";
    }

    @Override
    protected String descEN()
    {
        return "MmmMmmMmmMmm";
    }

    @Override
    protected String descZH()
    {
        return "MmmMmmMmmMmm";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Target Dummy");
        this.lang().add(context.pageText(),
            """
                The dummy can be placed in the world, where it'll stand silently, judging your every action. It'll wiggle funny if you hit it, screaming large numbers of damage at you. It can be dressed up to look even more awesome. ~~This will please the dummy, making the numbers smaller.~~
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "训练假人");
        this.lang("zh_cn").add(context.pageText(),
            """
                训练假人可以放置在世界中。它静静伫立在那里，判断你的一举一动。如果你击中它，它会滑稽地扭动起来，向你发出蛤人的尖叫。它甚至可以打扮得更加漂亮。~~这会让假人高兴，把你的数据变小。~~
                """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(Dummmmmmy.DUMMY_ITEM.get()))
            .build();

        BookEntityPageModel entity = BookEntityPageModel
            .builder()
            .withEntityId(Dummmmmmy.res(Dummmmmmy.TARGET_DUMMY_NAME).toString())
            .build();

        context.page("next");
        this.lang().add(context.pageText(),
            """
                TL; DR: target dummies show damage dealt to them and can be equipped with armor.
                \\
                \\
                Usage: Right click on a block with a target dummy to place it.He can be rotated 16 different directions depending on the way you face when you place it, similar to an armor stand.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                太长不看？简而言之，训练假人记录并显示你对其造成的伤害，并且可以装备盔甲。
                \\
                \\
                用法: 右键单击方块放置它。它可以根据放置时面对的方向旋转 16个不同的方向，类似于盔甲架。
                """
        );

        BookTextPageModel next = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        context.page("last");
        this.lang().add(context.pageText(),
            """
                You can start dressing the little dude with all kind of armors and equipment.Just right click him with the desired item.To unequip a certain armor piece just click on his corresponding body part.
                \\
                \\
                Got tired of testing your dps? You can remove the dummy just by shift left clicking him with an empty hand!
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                你可以给这家伙穿上各种盔甲和装备。只需用物品右键单击它即可。要取消装备某个盔甲，只需单击其相应的身体部位即可。
                \\
                \\
                开始对测试DPS感到厌倦？只需空手左键即可移除假人！
                """
        );

        BookTextPageModel last = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        return List.of(prev, entity, next, last);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Dummmmmmy.DUMMY_ITEM.get());
    }

    @Override
    protected String entryId()
    {
        return "dummy";
    }
}
