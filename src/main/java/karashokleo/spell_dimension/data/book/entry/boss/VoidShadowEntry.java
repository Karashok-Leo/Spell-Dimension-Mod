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
import net.adventurez.init.ItemInit;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class VoidShadowEntry extends BaseEntryProvider
{
    public VoidShadowEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Void Shadow";
    }

    @Override
    protected String nameZH()
    {
        return "虚空之影";
    }

    @Override
    protected String descEN()
    {
        return "Dark magic on the prowl...";
    }

    @Override
    protected String descZH()
    {
        return "黑暗魔法徘徊...";
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
                        Defeat the Eye to spawn a floating island, enter the void shadow realm through the portal on it.
                        """.formatted(TextConstants.BOSS_LEVELS[4])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        击败眼球后将生成一个浮岛，通过其上的传送门可进入虚空之影的领域。
                        """.formatted(TextConstants.BOSS_LEVELS[4])
        );
        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(ItemInit.SOURCE_STONE))
                .build();

        context.page("behavior");
        this.lang().add(context.pageText(),
                """
                        When Void Shadow has at least half its health, you can deal damage to its body by killing the Servants He summons. Each hit deals damage equal to the Servant's max health, but not more than 1%% of Void Shadow's max health.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        当虚空之影至少有一半生命值时，你可以通过击杀祂召唤的侍从对其本体造成伤害。每次击杀造成的伤害等于侍从的最大生命值，但不会超过虚空之影的最大生命值的1%%。
                        """
        );
        BookTextPageModel behavior = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("state");
        this.lang().add(context.pageText(),
                """
                        When Void Shadow has less than half of its health left, He will enter a berserk state. At this point, if you destroy all the Void Fragments on the platform, he will stop rotating around the platform and come to the center to duel with you.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        当虚空之影只剩下不到一半生命值时，祂将进入狂暴状态。此时如果你摧毁了平台上的所有虚空碎片，祂将停止围绕平台旋转，并来到平台中心与你决战。
                        """
        );
        BookTextPageModel state = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel shadow = BookEntityPageModel
                .builder()
                .withOffset(0.4f)
                .withEntityId("adventurez:void_shadow")
                .build();
        BookEntityPageModel shade = BookEntityPageModel
                .builder()
                .withOffset(0.3f)
                .withEntityId("adventurez:void_shade")
                .build();
        BookEntityPageModel fragment = BookEntityPageModel
                .builder()
                .withOffset(0.4f)
                .withEntityId("adventurez:void_fragment")
                .build();

        return List.of(boss, behavior, state, shadow, shade, fragment);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ItemInit.SOURCE_STONE);
    }

    @Override
    protected String entryId()
    {
        return "void_shadow";
    }
}
