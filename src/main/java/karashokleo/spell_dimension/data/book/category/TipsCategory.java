package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.tips.*;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class TipsCategory extends CategoryProvider
{
    public TipsCategory(BookProvider parent)
    {
        super(parent, "tips");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "k_h_s_g_",
                "_______",
                "f___e_m_",
                "_______",
                "t_l____",
                "_______",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel key = new KeyEntry(this).generate('k');
        BookEntryModel health = new HealthEntry(this).generate('h');
        BookEntryModel smith = new SmithEntry(this).generate('s');
        BookEntryModel storage = new StorageEntry(this).generate('g');
        BookEntryModel difficulty = new HostilityEntry(this).generate('f');
        BookEntryModel enchant = new EnchantEntry(this).generate('e');
        BookEntryModel dummy = new DummyEntry(this).generate('m');
        BookEntryModel trader = new TraderEntry(this).generate('t');
        BookEntryModel alloy = new AlloyEntry(this).generate('l');
        this.add(key);
        this.add(health);
        this.add(smith);
        this.add(storage);
        this.add(difficulty);
        this.add(enchant);
        this.add(dummy);
        this.add(trader);
        this.add(alloy);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Tips");
        this.lang("zh_cn").add(context.categoryName(), "提示");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.LANTERN)
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/2.png"));
    }
}
