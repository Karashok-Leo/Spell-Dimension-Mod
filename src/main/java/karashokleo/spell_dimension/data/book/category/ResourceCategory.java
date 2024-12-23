package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.resource.*;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;

public class ResourceCategory extends CategoryProvider
{
    public ResourceCategory(BookProvider parent)
    {
        super(parent, "resource");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "a_b_c",
                "_____",
                "d_e_f",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel alloy = new AlloyEntry(this).generate('a');
        BookEntryModel trader = new TraderEntry(this).generate('b');
        BookEntryModel dungeon = new DungeonEntry(this).generate('c');
        BookEntryModel event = new PivotEntry(this).generate('d');
        BookEntryModel lootCharm = new LootCharmEntry(this).generate('e');
        BookEntryModel curse = new CurseEntry(this).generate('f');

        this.add(alloy);
        this.add(trader);
        this.add(dungeon);
        this.add(event);
        this.add(lootCharm);
        this.add(curse);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Resource");
        this.lang("zh_cn").add(context.categoryName(), "资源");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.DIAMOND)
                .withBackground(BookGenUtil.id("textures/background/4.png"));
    }
}
