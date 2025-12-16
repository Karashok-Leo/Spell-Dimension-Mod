package karashokleo.spell_dimension.data.book.entry.boss;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import karashokleo.spell_dimension.data.loot_bag.TextConstants;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

public class ObsidlithEntry extends BaseEntryProvider
{
    public ObsidlithEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Obsidlith";
    }

    @Override
    protected String nameZH()
    {
        return "黑曜巨石柱";
    }

    @Override
    protected String descEN()
    {
        return "Last Stand";
    }

    @Override
    protected String descZH()
    {
        return "屹立不倒";
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
                Spawns inside the **Obsidilith Arena**(bosses_of_mass_destruction:obsidilith_arena).
                \\
                This structure can be located in the End using the Locate Spell.
                """.formatted(TextConstants.BOSS_LEVELS[4])
        );
        this.lang("zh_cn").add(context.pageTitle(), nameZH());
        this.lang("zh_cn").add(context.pageText(),
            """
                等级: %d+
                \\
                \\
                生成于**黑曜巨石柱竞技场**(bosses_of_mass_destruction:obsidilith_arena)内。
                \\
                可通过定位法术在末地中定位到此结构。
                """.formatted(TextConstants.BOSS_LEVELS[4])
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
            .builder()
            .withTitle(context.pageTitle())
            .withText(context.pageText())
            .withItem(Ingredient.ofItems(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:obsidian_heart"))))
            .build();

        context.page("next");
        this.lang().add(context.pageText(),
            """
                Use Ender Eye on the Obsidian Altar to summon.
                \\
                \\
                Obsidlith summons Obsidian Runes during transformation, and stays **invulnerable** while the runes are not destroyed.
                \\
                \\
                You can use spell **Remote Destruction** to destroy runes.
                """
        );
        this.lang("zh_cn").add(context.pageText(),
            """
                对黑曜石祭坛使用末影之眼召唤。
                \\
                \\
                黑曜巨石柱转换阶段时会召唤黑曜石符文，符文未被破坏时处于**无敌**状态。
                \\
                \\
                你可以使用法术**远端瓦解**来摧毁符文。
                """
        );

        BookTextPageModel next = BookTextPageModel
            .builder()
            .withText(context.pageText())
            .build();
        BookEntityPageModel entity = BookEntityPageModel
            .builder()
            .withEntityId("bosses_of_mass_destruction:obsidilith")
            .withOffset(-4.5F)
            .build();

        return List.of(boss, next, entity);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(BookGenUtil.getItem(new Identifier("bosses_of_mass_destruction:obsidian_heart")));
    }

    @Override
    protected String entryId()
    {
        return "obsidilith";
    }
}
