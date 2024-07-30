package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.util.Identifier;
import net.spell_engine.spellbinding.SpellBindingBlock;
import net.wizards.WizardsMod;
import net.wizards.item.Weapons;

public class MageProvider extends CategoryProvider
{
    public MageProvider(BookProvider parent)
    {
        super(parent, "mage");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "c----------",
                "-------b---"
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel cast = this.entryCast();
        BookEntryModel book = this.entryBook();
        BookEntryModel bind = this.entryBind();
        BookEntryModel essence = this.entryEssence();
        book.withParent(cast);
        essence.withParent(cast);
        this.add(cast);
        this.add(book);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Path of the Mage");
        this.lang("zh_cn").add(context.categoryName(), "魔法师之路");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Weapons.arcaneWand.item())
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/base.png"));
    }

    private BookEntryModel entryCast()
    {
        BookContextHelper context = this.context();

        context.entry("cast");
        this.lang().add(context.entryName(), "Spell Casting");
        this.lang().add(context.entryDescription(), "The basic skill of a mage.");
        this.lang("zh_cn").add(context.entryName(), "施法");
        this.lang("zh_cn").add(context.entryDescription(), "魔法师的基本功。");

        context.page("intro");
        this.lang().add(context.pageTitle(), "Spell Casting");
        this.lang().add(context.pageText(), "Spell casting is the basic skill of a mage. Common spell casting items are staffs and swords. Which spells a mage can cast depends on their spell pool. A mage's spell pool is determined by the item they are holding and the spell book they have equipped.");
        this.lang("zh_cn").add(context.pageTitle(), "施法");
        this.lang("zh_cn").add(context.pageText(), "施法是魔法师的基本功。常见的施法物品有法杖和剑。魔法师能够施展哪些法术取决于他们的法术池。魔法师的法术池由他们手持的物品和装备的法术书决定。");

        return this.entry('c')
                .withIcon(Weapons.arcaneWand.item());
    }

    private BookEntryModel entrySchool()
    {
        BookContextHelper context = this.context();

        context.entry("school");
        this.lang().add(context.entryName(), "Spell Schools");
        this.lang().add(context.entryDescription(), "The six branches of the magic system.");
        this.lang("zh_cn").add(context.entryName(), "魔法学派");
        this.lang("zh_cn").add(context.entryDescription(), "魔法体系的六大分支。");

        context.page("intro");
        this.lang().add(context.pageTitle(), "Spell Schools");
        this.lang().add(context.pageText(), "There are six schools in the magic system, namely Arcane, Fire, Frost, Healing, Lightning, and Soul. The last two of these are only mastered by a very small number of people, as it is almost impossible to find information related to them.");
        this.lang("zh_cn").add(context.pageTitle(), "魔法学派");
        this.lang("zh_cn").add(context.pageText(), "魔法体系中共有六大学派，分别为奥秘、火焰、寒冰、治愈、雷电、灵魂。其中最后两种只被极少数人掌握，因为几乎无法找到与其有关的资料。");

        return this.entry('c')
                .withIcon(Weapons.arcaneWand.item());
    }

    private BookEntryModel entryBook()
    {
        BookContextHelper context = this.context();

        context.entry("book");
        this.lang().add(context.entryName(), "Spell Book");
        this.lang().add(context.entryDescription(), "A carrier for recording spells.");
        this.lang("zh_cn").add(context.entryName(), "法术书");
        this.lang("zh_cn").add(context.entryDescription(), "记录法术的载体。");

        context.page("intro");
        this.lang().add(context.pageTitle(), "Spell Book");
        this.lang().add(context.pageText(), "A spell book is a vehicle for recording spells. You can use the Spell Binding Table to transform a normal book into the most basic spell book. Advanced spell books require special materials to synthesise. The more advanced the spell book, the more spell slots it provides.");
        this.lang("zh_cn").add(context.pageTitle(), "法术书");
        this.lang("zh_cn").add(context.pageText(), "法术书是记录法术的载体。你可以使用法术绑定台将普通的书转化为最基本的法术书。高级的法术书需要特殊材料来合成。越高级的法术书提供越多的法术槽位。");

        BookTextPageModel intro = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .withTitle(context.pageTitle())
                .build();

        return this.entry('b')
                .withIcon(new Identifier(WizardsMod.ID, "arcane_spell_book"))
                .withPage(intro);
    }

    private BookEntryModel entryBind()
    {
        BookContextHelper context = this.context();

        context.entry("bind");
        this.lang().add(context.entryName(), "Spell Bind");
        this.lang().add(context.entryDescription(), "Bind your spells!");
        this.lang("zh_cn").add(context.entryName(), "法术绑定");
        this.lang("zh_cn").add(context.entryDescription(), "绑定你的法术！");

        context.page("intro");
        this.lang().add(context.pageTitle(), "Spell Bind");
        this.lang().add(context.pageText(), "Use the Spell Binding Table to get your first spell book and bind some spells! You can find a Spell Binding Table in the village or craft one yourself.");
        this.lang("zh_cn").add(context.pageTitle(), "法术绑定");
        this.lang("zh_cn").add(context.pageText(), "使用法术绑定台来获取你的第一本法术书，并绑定一些法术！你可以在村庄里找到法术绑定台或是自己合成一个。");

        BookTextPageModel intro = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .withTitle(context.pageTitle())
                .build();

        return this.entry('b')
                .withIcon(SpellBindingBlock.ITEM)
                .withPage(intro);
    }

    private BookEntryModel entryRune()
    {
        BookContextHelper context = this.context();

        context.entry("rune");
        this.lang().add(context.entryName(), "Runes");
        this.lang().add(context.entryDescription(), "\"Magic Ammo\"");
        this.lang("zh_cn").add(context.entryName(), "符文");
        this.lang("zh_cn").add(context.entryDescription(), "“魔法弹药”");

        context.page("intro");
        this.lang().add(context.pageTitle(), "Runes");
        this.lang().add(context.pageText(), "Casting certain spells requires runes. You can craft runes directly in your hand, or craft a rune altar to make more runes with the same ingredients.");
        this.lang("zh_cn").add(context.pageTitle(), "符文");
        this.lang("zh_cn").add(context.pageText(), "施放某些法术需要消耗符文。你可以直接在手中合成符文，或是制作一个符文祭坛以相同的原料制作更多的符文。");

        BookTextPageModel intro = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .withTitle(context.pageTitle())
                .build();

        return this.entry('b')
                .withIcon(SpellBindingBlock.ITEM)
                .withPage(intro);
    }

    private BookEntryModel entryEssence()
    {
        BookContextHelper context = this.context();

        context.entry("essence");
        this.lang().add(context.entryName(), "Spell Essence");
        this.lang().add(context.entryDescription(), "Subtle crystals of magic.");
        this.lang("zh_cn").add(context.entryName(), "魔法精华");
        this.lang("zh_cn").add(context.entryDescription(), "魔法凝结而成的微妙结晶。");

        context.page("intro");
        this.lang().add(context.pageTitle(), "Spell Essence");
        this.lang().add(context.pageText(), "Perhaps you have noticed that there is a probability of dropping some magic essences on spell hits. They are important materials on a mage's growth path, and can be used to craft some magic items, such as spell books, runes and enchanted essences.");
        this.lang("zh_cn").add(context.pageTitle(), "魔法精华");
        this.lang("zh_cn").add(context.pageText(), "也许你已经注意到，法术命中时有概率掉落一些魔法精华。它们是魔法师成长路上的重要材料，可以用来合成一些魔法物品，比如法术书、符文和束魔精华。");

        BookTextPageModel intro = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .withTitle(context.pageTitle())
                .build();

        return this.entry('b')
                .withIcon(SpellBindingBlock.ITEM)
                .withPage(intro);
    }
}
