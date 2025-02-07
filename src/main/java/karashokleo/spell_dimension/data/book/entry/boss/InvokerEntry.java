package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class InvokerEntry extends BaseEntryProvider
{
    public InvokerEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Invoker";
    }

    @Override
    protected String nameZH()
    {
        return "祈灵师";
    }

    @Override
    protected String descEN()
    {
        return "";
    }

    @Override
    protected String descZH()
    {
        return "";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("boss");
        this.lang().add(context.pageTitle(), nameEN());
        this.lang().add(context.pageText(),
                """
                        Level: %d+
                        \\
                        \\
                        Spawns inside the **Mansion**(minecraft:mansion).
                        This structure can be located in the Overworld using the Locate Spell.
                        \\
                        \\
                        Drops **Primal Essence** upon defeating Invoker.
                        """.formatted(TextConstants.BOSS_LEVELS[0])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
                """
                        等级: %d+
                        \\
                        \\
                        生成于**林地府邸**(minecraft:mansion)内。
                        可通过定位法术在主世界中定位到此结构。
                        \\
                        \\
                        击败祈灵师后掉落**始源精华**。
                        """.formatted(TextConstants.BOSS_LEVELS[0])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(fuzs.illagerinvasion.init.ModRegistry.PRIMAL_ESSENCE_ITEM.get()))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("illagerinvasion:invoker")
                .withOffset(0.35F)
                .build();

        return List.of(boss, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(fuzs.illagerinvasion.init.ModRegistry.PRIMAL_ESSENCE_ITEM.get());
    }

    @Override
    protected String entryId()
    {
        return "invoker";
    }
}
