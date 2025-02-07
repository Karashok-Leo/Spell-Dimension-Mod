package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

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
                        As you can see, in the beginning you only have a max health of 10. Early in the game, you gain a heart when you kill monsters with a higher max health and hostility level than you, but as your max health approaches %d, you will no longer be able to grow your max health in this way.
                        """.formatted(PlayerHealthEvent.HEALTH_THRESHOLD)
        );
        this.lang("zh_cn").add(context.pageTitle(), "生命机制");
        this.lang("zh_cn").add(context.pageText(),
                """
                        如你所见，开局你只有10点生命值。在游戏初期，当你击杀最大生命值和恶意等级都比你高的怪物时，你会获得一颗心，但随着你的最大生命值逼近%d点，你将不再能通过这种方式增长血量。
                        """.formatted(PlayerHealthEvent.HEALTH_THRESHOLD)
        );
        BookTextPageModel health = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("food");
        this.lang().add(context.pageText(),
                """
                        However, you can always gain extra max health by eating specific foods (e.g. Enchanted Golden Apples), you can search #spell-dimension:heart_food to see all available foods.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        但你始终可以通过食用特定食物(比如附魔金苹果)来获得额外的最大生命值，你可以搜索#spell-dimension:heart_food查看所有可用的食物。
                        """
        );
        BookSpotlightPageModel food = BookSpotlightPageModel
                .builder()
                .withItem(Ingredient.fromTag(AllTags.HEART_FOOD))
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
        return BookIconModel.create(Items.ENCHANTED_GOLDEN_APPLE);
    }

    @Override
    protected String entryId()
    {
        return "health";
    }
}
