package karashokleo.spell_dimension.data.book.entry.power;

import com.glisco.victus.item.VictusItems;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllTags;
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
                        \\
                        As you can see, in the beginning you only have a max health of 10. Early in the game, you have a probability of getting a heart when you gain experience values greater than %d at once, but as your max health approaches %d, you will no longer be able to grow your max health in this way.
                        """.formatted(PlayerHealthEvent.XP_THRESHOLD, PlayerHealthEvent.HEALTH_THRESHOLD)
        );
        this.lang("zh_cn").add(context.pageTitle(), "生命机制");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        如你所见，开局你只有10点生命值。在游戏初期，当你一次性获得大于%d的经验值时，你有概率获得一颗心，但随着你的最大生命值逼近%d点，你将不再能通过这种方式增长血量。
                        """.formatted(PlayerHealthEvent.XP_THRESHOLD, PlayerHealthEvent.HEALTH_THRESHOLD)
        );
        BookTextPageModel health = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("food");
        this.lang().add(context.pageText(),
                """
                        \\
                        However, you can always gain extra max health by eating specific foods (e.g. Enchanted Golden Apples), you can search #spell-dimension:heart_food to see all available foods.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        但你始终可以通过食用特定食物(比如附魔金苹果)来获得额外的最大生命值，你可以搜索#spell-dimension:heart_food查看所有可用的食物。
                        """
        );
        BookSpotlightPageModel food = BookSpotlightPageModel
                .builder()
                .withItem(Ingredient.fromTag(AllTags.HEART_FOOD))
                .withText(context.pageText())
                .build();

        context.page("victus");
        this.lang().add(context.pageTitle(), "Victus");
        this.lang().add(context.pageText(),
                """
                        \\
                        You craft custom hearts, the so called Heart Aspects which you can then apply on top of your health bar for interesting and often useful effects when they get broken.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "Victus");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你可以制作定制的心，即所谓的 “心之相位”，然后将其应用于你的血条上，当它们碎掉时，可以产生有趣且有用的效果。
                        """
        );

        BookSpotlightPageModel victus = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(
                        VictusItems.GRILLED_HEART_ASPECT,
                        VictusItems.BUNDLE_HEART_ASPECT,
                        VictusItems.CREEPER_HEART_ASPECT,
                        VictusItems.DIAMOND_HEART_ASPECT,
                        VictusItems.LIGHT_HEART_ASPECT,
                        VictusItems.OCEAN_HEART_ASPECT,
                        VictusItems.TOTEM_HEART_ASPECT,
                        VictusItems.POTION_HEART_ASPECT,
                        VictusItems.ARCHERY_HEART_ASPECT,
                        VictusItems.BLAZING_HEART_ASPECT,
                        VictusItems.DRACONIC_HEART_ASPECT,
                        VictusItems.EMERALD_HEART_ASPECT,
                        VictusItems.EVOKING_HEART_ASPECT,
                        VictusItems.GOLDEN_HEART_ASPECT,
                        VictusItems.ICY_HEART_ASPECT,
                        VictusItems.IRON_HEART_ASPECT,
                        VictusItems.LAPIS_HEART_ASPECT,
                        VictusItems.SWEET_HEART_ASPECT,
                        VictusItems.CHEESE_HEART_ASPECT
                ))
                .build();

        context.page("rally");
        this.lang().add(context.pageTitle(), "Rally");
        this.lang().add(context.pageText(),
                """
                        \\
                        After being hit by an enemy, you may jump back in the action and attack your opponent to quickly refill lost health.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "反击");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        被敌人击中后，如果你迅速反击对手，可以重获失去的生命值。
                        """
        );

        BookTextPageModel rally = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        return List.of(health, food, victus, rally);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(VictusItems.BUNDLE_HEART_ASPECT);
    }

    @Override
    protected String entryId()
    {
        return "health";
    }
}
