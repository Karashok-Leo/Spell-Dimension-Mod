package karashokleo.spell_dimension.data.book.entry.power;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;

import java.util.List;

public class SummonSpellEntry extends BaseEntryProvider
{
    public SummonSpellEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Summon Spell";
    }

    @Override
    protected String nameZH()
    {
        return "召唤法术";
    }

    @Override
    protected String descEN()
    {
        return "Fettered Souls in the Cage";
    }

    @Override
    protected String descZH()
    {
        return "牢笼中的被缚之魂";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("spell");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        When the off-hand holds a specific catalyst, cast the summon spell on a **Spawner** to summon some special mobs.
                        \\
                        \\
                        You can press U on the Spawner to see the specific recipe.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        副手持有特定催化剂时对**刷怪笼**施放召唤法术可以召唤一些特殊的生物。
                        \\
                        \\
                        你可以对刷怪笼按下U键查看具体配方。
                        """
        );
        BookSpotlightPageModel spell = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getNbtIngredient(AllItems.SPELL_SCROLL.getStack(AllSpells.SUMMON)))
                .build();

        context.page("boss");
        this.lang().add(context.pageText(),
                """
                        Some bosses can also be summoned using summon spell, but it must be in the place where they would have been.
                        \\
                        \\
                         For example, Stalker must be summoned in the **Otherside** (Deeper Darker Dimension).
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        有些Boss也可以使用召唤法术进行召唤，但必须位于他们本来所在的场所。
                        \\
                        \\
                        比如追猎者必须在**幽冥异界**（深暗维度）才能召唤。
                        """
        );
        BookTextPageModel boss = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(spell, boss);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Items.SPAWNER);
    }

    @Override
    protected String entryId()
    {
        return "summon_spell";
    }
}
