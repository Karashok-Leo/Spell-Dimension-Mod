package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.enchantment_infusion.api.recipe.EnchantmentIngredient;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.item.Items;

import java.util.List;

public class DurabilityEntry extends BaseEntryProvider
{
    public DurabilityEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Armor Durability";
    }

    @Override
    protected String nameZH()
    {
        return "装备耐久";
    }

    @Override
    protected String descEN()
    {
        return "Ahhh--My armor!!!";
    }

    @Override
    protected String descZH()
    {
        return "啊啊啊——我的神甲！！！";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("hardened");
        this.lang().add(context.pageTitle(), "Hardened");
        this.lang().add(context.pageText(),
                """
                        Typically, the more damage a monster does to you, the more damage it does to your armor. *Hardened* can make durability loss of items up to 1.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "硬化");
        this.lang("zh_cn").add(context.pageText(),
                """
                        通常，怪物对你造成的伤害越多，对你的盔甲造成的损伤也就越多。*硬化*魔咒可以使物品的单次耐久损耗最多为1。
                        """
        );

        BookSpotlightPageModel hardened = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(EnchantmentIngredient.of(LHEnchantments.HARDENED, LHEnchantments.HARDENED.getMaxLevel()))
                .build();

        context.page("durable");
        this.lang().add(context.pageTitle(), "Durable Armor");
        this.lang().add(context.pageText(),
                """
                        *Durable* can make your armor have higher durability. It is incompatible with Unbreaking.
                        \\
                        \\
                        Note: For some reason, this enchantment is currently not working!
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "盔甲耐久");
        this.lang("zh_cn").add(context.pageText(),
                """
                        *盔甲耐久*魔咒可以使你的盔甲获得更高的耐久。它和耐久附魔不兼容。
                        \\
                        \\
                        注意：由于某些原因，该魔咒暂时无法生效！
                        """
        );

        BookSpotlightPageModel durable = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(EnchantmentIngredient.of(LHEnchantments.DURABLE_ARMOR, LHEnchantments.DURABLE_ARMOR.getMaxLevel()))
                .build();

        context.page("safeguard");
        this.lang().add(context.pageTitle(), "Safeguard");
        this.lang().add(context.pageText(),
                """
                        *Safeguard* can make items with durability greater than 1 keep at least 1 durability if damaged. If the original durability is not restored after a while, Safeguard enchantment will be removed.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "保险");
        this.lang("zh_cn").add(context.pageText(),
                """
                        *保险*魔咒可以使耐久大于1的此物品受到耐久损耗时，最多削减耐久到1。一段时间后如果没有恢复原来的耐久，则移除该附魔。
                        """
        );

        BookSpotlightPageModel safeguard = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(EnchantmentIngredient.of(LHEnchantments.SAFEGUARD, LHEnchantments.SAFEGUARD.getMaxLevel()))
                .build();

        return List.of(hardened, durable, safeguard);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.DIAMOND_CHESTPLATE);
    }

    @Override
    protected String entryId()
    {
        return "durability";
    }
}
