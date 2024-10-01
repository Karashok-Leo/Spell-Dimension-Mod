package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import karashokleo.spell_dimension.data.book.entry.boss.*;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class BossCategory extends CategoryProvider
{
    public BossCategory(BookProvider parent)
    {
        super(parent, "boss");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "r_g_d_w_",
                "_______",
                "t_p_e_m_",
                "_______",
                "t_l____",
                "_______",
        };
    }

    @Override
    protected void generateEntries()
    {
        this.add(
                List.of(
                        new WitherEntry(this).generate('r'),
                        new GuardianEntry(this).generate('g'),
                        new DragonEntry(this).generate('d'),
                        new WardenEntry(this).generate('w'),
                        new StalkerEntry(this).generate('t'),
                        new CaptainEntry(this).generate('p')
                )
        );
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Conquer All");
        this.lang("zh_cn").add(context.categoryName(), "尽数征服");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.NETHER_STAR)
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/base.png"));
    }
}
