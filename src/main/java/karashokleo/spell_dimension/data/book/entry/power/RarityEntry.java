package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class RarityEntry extends BaseEntryProvider
{
    public RarityEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Item Rarity";
    }

    @Override
    protected String nameZH()
    {
        return "物品稀有度";
    }

    @Override
    protected String descEN()
    {
        return "";
    }

    @Override
    protected String descZH()
    {
        return "";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("rarity");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        \\
                        All materials and gear are divided into different rarities, from lowest to highest they are **Common**, **Uncommon**, **Rare**, **Epic** and **Legendary**.
                        \\
                        \\
                        Searching for specific keywords (starting with **#**) will show the corresponding items. For example:
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        所有材料和装备被分为不同的稀有度，从低到高分别是**普通**(common)、**不凡**(uncommon)、**稀有**(rare)、**史诗**(epic)和**传奇**(legendary)。
                        \\
                        \\
                        搜索特定的关键词(以**#**起始)可查看对应的物品。如:
                        """
        );
        List<Item> items = new ArrayList<>();
        items.addAll(AllItems.RANDOM_MATERIAL);
        items.addAll(AllItems.RANDOM_GEAR);
        items.addAll(AllItems.RANDOM_BOOK);
        BookSpotlightPageModel rarity = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(items.stream().map(Item::getDefaultStack)))
                .build();

        context.page("text");
        this.lang().add(context.pageText(),
                """
                        \\
                        **#uncommon/material**: All uncommon materials
                        **#rare/gear**: All rare gear
                        **#epic/armor**: All epic armor
                        **#legendary/weapon**: All legendary weapons
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        **#uncommon/material**: 所有不凡材料
                        **#rare/gear**: 所有稀有装备
                        **#epic/armor**: 所有史诗盔甲
                        **#legendary/weapon**: 所有传奇武器
                        """
        );
        BookTextPageModel text = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(rarity, text);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(AllItems.RANDOM_GEAR.get(0));
    }

    @Override
    protected String entryId()
    {
        return "rarity";
    }
}
