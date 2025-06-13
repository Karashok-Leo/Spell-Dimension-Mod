package karashokleo.spell_dimension.data.book.entry.power;

import artifacts.registry.ModItems;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.content.event.PlayerHealthEvents;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;

import java.util.List;

public class HealthEntry extends BaseEntryProvider
{
    public HealthEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Health Mechanics";
    }

    @Override
    protected String nameZH()
    {
        return "生命机制";
    }

    @Override
    protected String descEN()
    {
        return "Starting at 10...";
    }

    @Override
    protected String descZH()
    {
        return "从10开始...";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("health");
        this.lang().add(context.pageTitle(), "Health Mechanics");
        this.lang().add(context.pageText(),
                """
                        As you can see, in the beginning you only have a max health of %d. Early in the game, you gain a heart when you kill monsters with a higher hostility level than you. You can only gain up to %d max health this way.
                        """.formatted(
                        PlayerHealthEvents.INITIAL_MAX_HEALTH,
                        PlayerHealthEvents.FROM_KILL_THRESHOLD
                )
        );
        this.lang("zh_cn").add(context.pageTitle(), "生命机制");
        this.lang("zh_cn").add(context.pageText(),
                """
                        如你所见，开局你只有%d点生命值。在游戏初期，当你击杀恶意等级比你高的怪物时，你会获得一颗心。你最多只能通过这种方式获取%d点生命上限。
                        """.formatted(
                        PlayerHealthEvents.INITIAL_MAX_HEALTH,
                        PlayerHealthEvents.FROM_KILL_THRESHOLD
                )
        );
        BookTextPageModel health = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("food");
        this.lang().add(context.pageText(),
                """
                        However, you can always gain extra max health by either:
                        \\
                        \\
                        Consuming specific foods (e.g. Enchanted Golden Apples). you can search **#spell-dimension:heart_food** to see all available foods.
                        \\
                        \\
                        Earning achievements. Only achievements that will announce to chat when earned.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        但你始终可以通过以下两种方法来获得额外的最大生命值：
                        \\
                        \\
                        食用特定食物(比如附魔金苹果)。你可以搜索**#spell-dimension:heart_food**查看所有可用的食物。
                        \\
                        \\
                        获得成就。仅限获得时会在聊天栏通知的成就。
                        """
        );
        BookTextPageModel food = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("rally");
        this.lang().add(context.pageTitle(), "Rally");
        this.lang().add(context.pageText(),
                """
                        After being hit by an enemy, you may jump back in the action and attack your opponent to quickly refill lost health.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "反击");
        this.lang("zh_cn").add(context.pageText(),
                """
                        被敌人击中后，如果你迅速反击对手，可以重获失去的生命值。
                        """
        );

        BookTextPageModel rally = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        return List.of(health, food, rally);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(ModItems.CRYSTAL_HEART.get());
    }

    @Override
    protected String entryId()
    {
        return "health";
    }
}
