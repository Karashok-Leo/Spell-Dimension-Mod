package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import karashokleo.spell_dimension.data.book.entry.boss.*;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;

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
                "a_b_c_d_e_f",
                "___________",
                "g_h_i_j_k_l",
                "___________",
                "__m_n_o_p__",
                "___________",
                "_____q_____",
                "___________",
                "__r_s_t_u__"
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel oldChampion = new OldChampionEntry(this).generate('a');
        BookEntryModel decayingKing = new DecayingKingEntry(this).generate('b');
        BookEntryModel elderGuardian = new ElderGuardianEntry(this).generate('c');
        BookEntryModel wither = new WitherEntry(this).generate('d');
        BookEntryModel invoker = new InvokerEntry(this).generate('e');
        BookEntryModel warden = new WardenEntry(this).generate('f');

        BookEntryModel moonKnight = new MoonKnightEntry(this).generate('g');
        BookGenUtil.setParent(moonKnight, oldChampion);
        BookEntryModel blackStoneGolem = new BlackStoneGolemEntry(this).generate('h');
        BookGenUtil.setParent(blackStoneGolem, decayingKing);
        BookEntryModel captainCornelia = new CaptainCorneliaEntry(this).generate('i');
        BookGenUtil.setParent(captainCornelia, elderGuardian);
        BookEntryModel chaosMonarch = new ChaosMonarchEntry(this).generate('j');
        BookGenUtil.setParent(chaosMonarch, wither);
        BookEntryModel returningKnight = new ReturningKnightEntry(this).generate('k');
        BookGenUtil.setParent(returningKnight, invoker);
        BookEntryModel stalker = new StalkerEntry(this).generate('l');
        BookGenUtil.setParent(stalker, warden);

        BookEntryModel graveyardLich = new GraveyardLichEntry(this).generate('m');
        BookEntryModel bomdLich = new BomdLichEntry(this).generate('n');
        BookEntryModel voidBlossom = new VoidBlossomEntry(this).generate('o');
        BookEntryModel gauntlet = new GauntletEntry(this).generate('p');

        BookEntryModel dayNight = new DayNightEntry(this).generate('q');

        BookGenUtil.setParent(dayNight, graveyardLich);
        BookGenUtil.setParent(dayNight, bomdLich);
        BookGenUtil.setParent(dayNight, voidBlossom);
        BookGenUtil.setParent(dayNight, gauntlet);

        BookEntryModel enderDragon = new EnderDragonEntry(this).generate('r');
        BookEntryModel obsidlith = new ObsidlithEntry(this).generate('s');
        BookEntryModel theEye = new TheEyeEntry(this).generate('t');
        BookEntryModel voidShadow = new VoidShadowEntry(this).generate('u');

        BookGenUtil.setParent(enderDragon, dayNight);
        BookGenUtil.setParent(obsidlith, dayNight);
        BookGenUtil.setParent(theEye, dayNight);
        BookGenUtil.setParent(voidShadow, dayNight);

        this.add(List.of(oldChampion, decayingKing, elderGuardian, wither, invoker, warden, moonKnight, blackStoneGolem, captainCornelia, chaosMonarch, returningKnight, stalker, graveyardLich, bomdLich, voidBlossom, gauntlet, dayNight, enderDragon, obsidlith, theEye, voidShadow));
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Conquer by All Means");
        this.lang("zh_cn").add(context.categoryName(), "尽数征服");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.NETHER_STAR)
                .withBackground(BookGenUtil.id("textures/background/2.png"));
    }
}
