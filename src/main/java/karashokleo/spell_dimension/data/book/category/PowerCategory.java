package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.power.*;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.util.BookGenUtil;

public class PowerCategory extends CategoryProvider
{
    public PowerCategory(BookProvider parent)
    {
        super(parent, "power");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "a_b_c_d_e_f",
                "___________",
                "g_h_i_j_k_l",
                "___________",
                "m_n_o_p_q_r",
                "___________",
                "s_t_u_v_w_x",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel health = new HealthEntry(this).generate('a');
        BookEntryModel hostility = new HostilityEntry(this).generate('c');
        BookEntryModel trait = new TraitEntry(this).generate('d');
        BookEntryModel smith = new SmithEntry(this).generate('e');
        BookEntryModel armor_set = new ArmorSetEntry(this).generate('b');
        BookEntryModel spell_infusion = new SpellInfusionEntry(this).generate('g');
        BookEntryModel locate = new LocateSpellEntry(this).generate('h');
        BookEntryModel summon = new SummonSpellEntry(this).generate('i');
        BookEntryModel enchant = new EnchantEntry(this).generate('j');
        BookEntryModel rarity = new RarityEntry(this).generate('k');

        this.add(health);
        this.add(hostility);
        this.add(trait);
        this.add(smith);
        this.add(armor_set);
        this.add(spell_infusion);
        this.add(locate);
        this.add(summon);
        this.add(enchant);
        this.add(rarity);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Domination");
        this.lang("zh_cn").add(context.categoryName(), "主宰");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(AllItems.REJUVENATING_BLOSSOM)
                .withBackground(BookGenUtil.id("textures/background/3.png"));
    }
}
