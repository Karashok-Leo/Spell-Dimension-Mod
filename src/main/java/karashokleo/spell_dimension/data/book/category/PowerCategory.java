package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.power.*;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.wizards.item.Weapons;

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
                "_a_b_c_",
                "_______",
                "d_e_f_g",
                "_______",
                "h_i_j_k",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel health = new HealthEntry(this).generate('a');
        BookEntryModel hostility = new HostilityEntry(this).generate('b');
        BookEntryModel trait = new TraitEntry(this).generate('c');
        BookEntryModel smith = new SmithEntry(this).generate('d');
        BookEntryModel armor_set = new ArmorSetEntry(this).generate('e');
        BookEntryModel enchant = new EnchantEntry(this).generate('f');
        BookEntryModel spell_infusion = new SpellInfusionEntry(this).generate('g');
        BookEntryModel locate = new LocateSpellEntry(this).generate('h');
        BookEntryModel summon = new SummonSpellEntry(this).generate('i');
        BookEntryModel rarity = new RarityEntry(this).generate('j');
        BookEntryModel durability = new DurabilityEntry(this).generate('k');

        this.add(health);
        this.add(hostility);
        this.add(trait);
        this.add(smith);
        this.add(armor_set);
        this.add(enchant);
        this.add(spell_infusion);
        this.add(locate);
        this.add(summon);
        this.add(rarity);
        this.add(durability);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Domination");
        this.lang("zh_cn").add(context.categoryName(), "主宰");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Weapons.arcaneWand.item())
                .withBackground(BookGenUtil.id("textures/background/3.png"));
    }
}
