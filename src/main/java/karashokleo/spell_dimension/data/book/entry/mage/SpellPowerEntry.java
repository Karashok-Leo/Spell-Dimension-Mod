package karashokleo.spell_dimension.data.book.entry.mage;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.wizards.item.Armors;

import java.util.List;

public class SpellPowerEntry extends BaseEntryProvider
{
    public SpellPowerEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Spell Power";
    }

    @Override
    protected String nameZH()
    {
        return "法术强度";
    }

    @Override
    protected String descEN()
    {
        return "The core power of the mage";
    }

    @Override
    protected String descZH()
    {
        return "魔法师的核心力量";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("prev");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Spell Power is the core attribute of a mage, and it directly determines the power and effectiveness of spells. By default, both Enchanted Essence and Enlightening Essence obtained from loot belong to the spell school with the highest spell power, i.e., 'the spell school you are skilled in'.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        法术强度是法师的核心属性，它直接决定了法术的威力和效果。默认情况下，从战利品中获取到的束魔精华和源启精华都属于法师法术强度最高的魔法学派，即“擅长的法术学派”。
                        """
        );

        BookTextPageModel prev = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        The following lists several ways to increase spell power:
                        \\
                        \\
                        Essences: Enchanted Essence and Enlightening Essence
                        \\
                        Enchantments: Spell Power, Sunfire, Soulfrost, Energized, Spell Blade
                        \\
                        Equipments: Armor set bonuses and special trinkets
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        以下列举了法术强度的几种提升方式：
                        \\
                        \\
                        精华：束魔精华和源启精华
                        \\
                        魔咒：术式魔能、阳炎、灵冻、蓄能、咒剑
                        \\
                        装备：盔甲套装效果以及特殊饰品
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(prev, next);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(Armors.wizardRobeSet.head);
    }

    @Override
    protected String entryId()
    {
        return "power";
    }
}
