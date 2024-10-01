package karashokleo.spell_dimension.data.book.entry.tips;

import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.data.book.entry.BaseEntryProvider;
import net.minecraft.recipe.Ingredient;

import java.util.List;

public class HostilityEntry extends BaseEntryProvider
{
    public HostilityEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Lightland Hostility";
    }

    @Override
    protected String nameZH()
    {
        return "莱特兰·恶意";
    }

    @Override
    protected String descEN()
    {
        return "This hostile world.";
    }

    @Override
    protected String descZH()
    {
        return "这充斥着恶意的世界。";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        String link = "patchouli://l2hostility:hostility_guide//traits/tank";

        context.page("prev");
        this.lang().add(context.pageTitle(), "Lightland Hostility");
        this.lang().add(context.pageText(),
                """
                        \\
                        The game uses **L2 Hostility** to control the difficulty. Difficulty increases only when you kill strong enemies, and decreases when you die. Monsters are generated with special abilities called **'Trait'**.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "莱特兰·恶意");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        游戏采用**莱特兰·恶意**控制难度。只有在杀死强大怪物时才增加难度，死亡时降低难度。怪物生成时会带有特殊能力，称为 **"词条"**。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.fromTag(LHTags.TRAIT_ITEM))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        The higher the level of the monster, the higher their attributes such as damage and health, and the more powerful the traits they possess. Each trait has a unique way of dealing with it.
                        \\
                        \\
                        [See the *L2Hostility Guide* for more details](%s)""".formatted(link)
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        怪物的等级越高，它们的伤害与血量等属性也就越高，拥有的词条越强大。每一种词条都有独特的应对方法。
                        \\
                        \\
                        [更多详细信息见《莱特兰恶意教程》](%s)""".formatted(link)
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
        return BookIconModel.create(LHTraits.ENDER.asItem());
    }

    @Override
    protected String entryId()
    {
        return "hostility";
    }
}
