package karashokleo.spell_dimension.data.book.entry.power;

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

public class TraitEntry extends BaseEntryProvider
{
    public TraitEntry(CategoryProvider parent)
    {
        super(parent);
    }

    @Override
    protected String nameEN()
    {
        return "Mob Trait";
    }

    @Override
    protected String nameZH()
    {
        return "怪物词条";
    }

    @Override
    protected String descEN()
    {
        return "Counter & Counterbalance";
    }

    @Override
    protected String descZH()
    {
        return "克制与反制";
    }

    @Override
    protected List<BookPageModel> pages(BookContextHelper context)
    {
        context.page("trait");
        this.lang().add(context.pageTitle(), "Mob Trait");
        this.lang().add(context.pageText(),
                """
                        Although monsters with traits are powerful, they are not invincible. Here are some ways to counteract traits.
                        \\
                        \\
                        Invisible: Wearing **Detector Glasses** allows you to see invisible monsters.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "怪物词条");
        this.lang("zh_cn").add(context.pageText(),
                """
                        带有词条的怪物虽然强大，但并非不可反制。接下来将介绍一些词条的克制方法。
                        \\
                        \\
                        隐身: 佩戴**侦测眼镜**可以看到隐身的怪物。
                        """
        );

        BookSpotlightPageModel trait = BookSpotlightPageModel
                .builder()
                .withItem(Ingredient.fromTag(LHTags.TRAIT_ITEM))
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("trait1");
        this.lang().add(context.pageText(),
                """
                        Repelling/Pulling: **Insulator** enchantment can mitigate the target's repelling/pulling effect on you.
                        \\
                        \\
                        Regenerate/Undying: Applying a curse effect to monsters can prevent them from regenerating health. This can be achieved by using the **Spell Curse** enchantment.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        排斥/吸引: **绝缘**附魔可以缓解目标对你的排斥/吸引效果。
                        \\
                        \\
                        再生/不死: 对怪物施加诅咒效果可以阻止它们回血。这可以通过**法术诅咒**附魔来实现。
                        """
        );
        BookTextPageModel trait1 = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("trait2");
        this.lang().add(context.pageText(),
                """
                        Weakener/Stray/Poisonous/Withering/Blinder/Distorter/Levitater/Soul Burner/Freezing/Cursed: **Ring of Divinity** or **Ring of Reflection** can make you immune to almost all negative effects. But as an alternative, you can also use the **exclusive enchantments** of the Breastplate to be immune to specific negative effects.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        虚弱/流沙/剧毒/凋零/致盲/扭曲/升空/业火/寒流/诅咒: **圣光守护之戒**或**诅咒扭曲之戒**可以让你免疫几乎所有的负面效果。但作为替代，你也可以使用护心镜的**专属附魔**来免疫特定的负面效果。
                        """
        );
        BookTextPageModel trait2 = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("trait3");
        this.lang().add(context.pageText(),
                """
                        Adaptive: In addition to using various negative effects, you can refresh a monster's adaptation by switching spells. Each spell is equivalent to a damage type. Or use Spell Prism or Imagine Breaker to penetrate the protection.
                        \\
                        \\
                        Dispell: **Spell Tearing** enchantment, Spell Prism and Imagine Breaker all allow spell damage to penetrate the protection of Dispell.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        适应: 除了使用各种负面效果，你还可以通过切换法术来刷新怪物的适应性。每种法术都相当于一种伤害类型。或者使用法术棱镜或破咒遗骨来穿透适应的保护。
                        \\
                        \\
                        破魔: **法术穿透**附魔、法术棱镜、破咒遗骨都可以使法术伤害穿透破魔的保护。
                        """
        );
        BookTextPageModel trait3 = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("trait4");
        this.lang().add(context.pageText(),
                """
                        Arena: You can place or break blocks in arena with **Remote Manipulation** spell or **Remote Destruction** spell.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        领域压制: 你可以使用**远端操控**法术或者**远端瓦解**法术在领域内破坏或放置方块。
                        """
        );
        BookTextPageModel trait4 = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return List.of(trait, trait1, trait2, trait3, trait4);
    }

    @Override
    protected BookIconModel entryIcon()
    {
        return BookIconModel.create(LHTraits.CURSED);
    }

    @Override
    protected String entryId()
    {
        return "trait";
    }
}
