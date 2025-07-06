package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.content.item.essence.EnchantedEssenceItem;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllStacks;
import karashokleo.spell_dimension.util.BookGenUtil;

import java.util.List;

public class EnchantedEntry extends BaseEntryProvider
{
    public EnchantedEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Enchanted Essence";
    }

    @Override
    protected String nameZH()
    {
        return "束魔精华";
    }

    @Override
    protected String descEN()
    {
        return "Enchant your equipment";
    }

    @Override
    protected String descZH()
    {
        return "为你的装备注入魔力";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), "Enchanted Essence");
        this.lang().add(context.pageText(),
                """
                        Enchanted Essence can imbue an item with magic power to increase some of its attributes. Be aware that Enchanted Essence can only work on an item if its Threshold is not less than the item's Enchanted Level.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "束魔精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        束魔精华可以为物品注入魔力，使其增加部分属性。要注意只有当束魔精华的阈值不小于物品的束魔等级时才能作用于该物品。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getNbtIngredient(AllStacks.getEnchantedEssences()))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        Primary, Intermediate, and Advanced Base Essences can be crafted into Enchanted Essences with a threshold of %d, %d, and %d respectively. The max threshold of Enchanted Essence that can be obtained from the loot depends on your difficulty level.
                        \\
                        \\
                        At the Nightmare difficulty tier, Enchanted Essences can be merged into one with a higher threshold.
                        """.formatted(
                        EnchantedEssenceItem.CRAFT_THRESHOLD[0],
                        EnchantedEssenceItem.CRAFT_THRESHOLD[1],
                        EnchantedEssenceItem.CRAFT_THRESHOLD[2]
                )
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        初级、中级、高级的基础精华分别可以合成阈值为%d、%d、%d的束魔精华。战利品中能获取到的束魔精华的阈值上限取决于你的恶意难度等级。
                        \\
                        \\
                        在梦魇难度层级下，你可以手动合并束魔精华以获得更高阈值的束魔精华。
                        """.formatted(
                        EnchantedEssenceItem.CRAFT_THRESHOLD[0],
                        EnchantedEssenceItem.CRAFT_THRESHOLD[1],
                        EnchantedEssenceItem.CRAFT_THRESHOLD[2]
                )
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(prev, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllStacks.getEnchantedEssences().get(0));
    }

    @Override
    protected String entryId()
    {
        return "enchant";
    }
}
