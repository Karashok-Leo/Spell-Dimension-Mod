package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.enchantment_infusion.api.recipe.EnchantmentIngredient;
import karashokleo.enchantment_infusion.init.EIItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.spell_power.api.enchantment.Enchantments_SpellPower;

import java.util.List;

public class EnchantEntry extends BaseEntryProvider
{
    public EnchantEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Enchant";
    }

    @Override
    protected String nameZH()
    {
        return "附魔";
    }

    @Override
    protected String descEN()
    {
        return "Enchanting & Disenchanting.";
    }

    @Override
    protected String descZH()
    {
        return "附魔与祛魔。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("infuse");
        this.lang().add(context.pageTitle(), "Enchantment Infusion");
        this.lang().add(context.pageText(),
            """
                Enchantment Infusion is a directional enchanting method. The key blocks of Infusion are the Enchantment Infusion Table and the Pedestal. Use item to right-click them to put it in, and empty-handed right-click to remove item from them.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "魔咒灌注");
        this.lang("zh_cn").add(context.pageText(),
            """
                魔咒灌注是一种定向附魔的方式。魔咒灌注的核心方块是附魔灌注台和附魔灌注基座。使用物品右键它们将其放入，空手右键取出其中物品。
                """
        );

        BookSpotlightPageModel infuse = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(EIItems.INFUSION_TABLE_ITEM, EIItems.INFUSION_PEDESTAL_ITEM))
            .build();

        context.page("image");
        this.lang().add(context.pageTitle(), "Positioning of table and pedestals");
        this.lang("zh_cn").add(context.pageTitle(), "附魔灌注台与基座的位置");

        BookMultiblockPageModel multiblock = BookMultiblockPageModel
            .builder()
            .withMultiblockId("spell-dimension-book:enchantment_infusion")
            .build();

        context.page("pos");
        this.lang().add(context.pageText(),
            """
                You need to place an enchantment infusion table (centre position) and pedestals (surrounding position) like the picture, then place the items on the pedestals according to the recipe (the order doesn't matter), and finally place the item (level no less than that required for the recipe) you want to enchant on the infusion table in the centre, and wait for seconds for it to finish enchanting.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                你需要像图中这样摆放魔咒灌注台（中心位置）与魔咒灌注基座（周围的位置），然后按照配方在基座上摆放对应的物品（次序不重要），最后在中心的灌注台上摆放你想要附魔的物品（魔咒等级不小于配方需求即可），等待若干秒即可完成附魔。
                """
        );

        BookTextPageModel pos = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        context.page("disenchant");
        this.lang().add(context.pageTitle(), "Disenchant");
        this.lang().add(context.pageText(),
            """
                You can transfer enchantments to books by placing equipment with enchantments and books on the grindstone.
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "祛魔");
        this.lang("zh_cn").add(context.pageText(),
            """
                你可以把带有附魔的装备和书放在砂轮上，从而将魔咒转移至书上。
                """
        );

        BookSpotlightPageModel disenchant = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(Items.GRINDSTONE))
            .build();

        context.page("restriction");
        this.lang().add(context.pageTitle(), "Enchantment Restriction");
        this.lang().add(context.pageText(),
            """
                You can check applicable items and conflicting enchantments for specific enchantment in EMI (press U on the enchanted book).
                """
        );
        this.lang("zh_cn").add(context.pageTitle(), "附魔限制");
        this.lang("zh_cn").add(context.pageText(),
            """
                你可以在EMI中查看魔咒的可应用物品和冲突魔咒（对附魔书按下U）。
                """
        );

        BookSpotlightPageModel restriction = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(DefaultCustomIngredients.any(
                EnchantmentIngredient.of(Enchantments_SpellPower.SUNFIRE, 5),
                EnchantmentIngredient.of(Enchantments_SpellPower.SOULFROST, 5),
                EnchantmentIngredient.of(Enchantments_SpellPower.ENERGIZE, 5)
            ))
            .build();

        context.page("amplify");
        this.lang().add(context.pageText(),
            """
                In addition, enchantments that increase spell power (such as Sunfire, Soulfrost, and Energize) also require the item to carry the corresponding school of spell power, otherwise it cannot be enchanted.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                除此之外，增加法术强度的魔咒（如：阳炎、灵冻、蓄能）还要求物品携带对应学派的法术强度，否则无法附魔。
                """
        );

        BookTextPageModel amplify = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();

        return List.of(infuse, multiblock, pos, disenchant, restriction, amplify);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(EIItems.INFUSION_TABLE_ITEM);
    }

    @Override
    protected String entryId()
    {
        return "enchant";
    }
}
