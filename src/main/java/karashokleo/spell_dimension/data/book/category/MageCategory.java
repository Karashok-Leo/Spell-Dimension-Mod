package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStacks;
import karashokleo.spell_dimension.util.BookGenUtil;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.paladins.PaladinsMod;
import net.runes.api.RuneItems;
import net.spell_engine.spellbinding.SpellBindingBlock;
import net.wizards.WizardsMod;
import net.wizards.item.Weapons;

public class MageCategory extends CategoryProvider
{
    public MageCategory(BookProvider parent)
    {
        super(parent, "mage");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "_____________________",
                "________d___m________",
                "______c_______l______",
                "__________e__________",
                "_____________________",
                "______u___a___h______",
                "_____________________",
                "__________n__________",
                "______k_______r______",
                "_____________________",
                "_____________________",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel cast = this.entryCast();
        BookEntryModel school = this.entrySchool();
        BookEntryModel rune = this.entryRune();
        BookEntryModel essence = this.entryEssence();
        BookEntryModel enchant = this.entryEnchant();
        BookEntryModel enlighten = this.entryEnlighten();
        BookEntryModel disenchant = this.entryDisenchant();
        BookEntryModel mending = this.entryMending();
        BookEntryModel bind = this.entryBind();
        BookEntryModel book = this.entryBook();
        BookEntryModel scroll = this.entryScroll();
        BookGenUtil.setParent(school, cast);
        BookGenUtil.setParent(rune, cast);
        BookGenUtil.setParent(essence, cast);
        BookGenUtil.setParent(enchant, essence);
        BookGenUtil.setParent(enlighten, essence);
        BookGenUtil.setParent(disenchant, essence);
        BookGenUtil.setParent(mending, essence);
        BookGenUtil.setParent(bind, cast);
        BookGenUtil.setParent(book, bind);
        BookGenUtil.setParent(scroll, bind);
        this.add(cast);
        this.add(school);
        this.add(rune);
        this.add(essence);
        this.add(enchant);
        this.add(enlighten);
        this.add(disenchant);
        this.add(mending);
        this.add(bind);
        this.add(book);
        this.add(scroll);
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
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/1.png"));
    }

    private BookEntryModel entryCast()
    {
        BookContextHelper context = this.context();

        context.entry("cast");
        this.lang().add(context.entryName(), "Spell Casting");
        this.lang().add(context.entryDescription(), "The basic skill of a mage.");
        this.lang("zh_cn").add(context.entryName(), "施法");
        this.lang("zh_cn").add(context.entryDescription(), "魔法师的基本功。");

        context.page("cast");
        this.lang().add(context.pageTitle(), "Spell Casting");
        this.lang().add(context.pageText(),
                """
                        \\
                        Spell casting is the basic skill of a mage. Common spell casting items are staffs and swords. Which spells a mage can cast depends on their spell pool. A mage's spell pool is determined by the item they are holding and the spell book they have equipped.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "施法");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        施法是魔法师的基本功。常见的施法物品有法杖和剑。魔法师能够施展哪些法术取决于他们的法术池。魔法师的法术池由他们手持的物品和装备的法术书决定。
                        """
        );

        BookSpotlightPageModel cast = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(Weapons.entries.stream().map(entry -> entry.item().getDefaultStack())))
                .build();

        return this.entry('a')
                .withIcon(Weapons.arcaneWand.item())
                .withPage(cast);
    }

    private BookEntryModel entrySchool()
    {
        BookContextHelper context = this.context();

        context.entry("school");
        this.lang().add(context.entryName(), "Spell School");
        this.lang().add(context.entryDescription(), "The six branches of the magic system.");
        this.lang("zh_cn").add(context.entryName(), "魔法学派");
        this.lang("zh_cn").add(context.entryDescription(), "魔法体系的六大分支。");

        context.page("school");
        this.lang().add(context.pageTitle(), "Spell School");
        this.lang().add(context.pageText(),
                """
                        \\
                        There are six schools in the magic system, namely Arcane, Fire, Frost, Healing, Lightning, and Soul. The last two of these are only mastered by a very small number of people, as it is almost impossible to find information related to them.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "魔法学派");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        魔法体系中共有六大学派，分别为奥秘、火焰、寒冰、治愈、雷电、灵魂。其中最后两种只被极少数人掌握，因为几乎无法找到与其有关的资料。
                        """
        );

        BookTextPageModel school = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        return this.entry('h')
                .withIcon(Weapons.frostStaff.item())
                .withPage(school);
    }

    private BookEntryModel entryRune()
    {
        BookContextHelper context = this.context();

        context.entry("rune");
        this.lang().add(context.entryName(), "Rune");
        this.lang().add(context.entryDescription(), "\"Magic Ammo\"");
        this.lang("zh_cn").add(context.entryName(), "符文");
        this.lang("zh_cn").add(context.entryDescription(), "“魔法弹药”");

        context.page("rune");
        this.lang().add(context.pageTitle(), "Rune");
        this.lang().add(context.pageText(),
                """
                        \\
                        Casting certain spells requires runes. You can craft runes directly in your hand, or craft a rune altar to make more runes with the same ingredients.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "符文");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        施放某些法术需要消耗符文。你可以直接在手中合成符文，或是制作一个符文祭坛以相同的原料制作更多的符文。
                        """
        );

        BookSpotlightPageModel rune = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(RuneItems.entries.stream().map(entry -> entry.item().getDefaultStack())))
                .build();

        return this.entry('u')
                .withIcon(RuneItems.get(RuneItems.RuneType.ARCANE))
                .withPage(rune);
    }

    private BookEntryModel entryEssence()
    {
        BookContextHelper context = this.context();

        context.entry("essence");
        this.lang().add(context.entryName(), "Spell Essence");
        this.lang().add(context.entryDescription(), "Subtle crystals of magic.");
        this.lang("zh_cn").add(context.entryName(), "魔法精华");
        this.lang("zh_cn").add(context.entryDescription(), "魔法凝结而成的微妙结晶。");

        context.page("base");
        this.lang().add(context.pageTitle(), "Base Essence");
        this.lang().add(context.pageText(), "Perhaps you have noticed that there is a probability of dropping some magic essences on spell hits. They are important materials on a mage's growth path, and can be used to craft some magic items, such as spell books, runes and enchanted essences.");
        this.lang("zh_cn").add(context.pageTitle(), "基础精华");
        this.lang("zh_cn").add(context.pageText(), "也许你已经注意到，法术命中时有概率掉落一些魔法精华。它们是魔法师成长路上的重要材料，可以用来合成一些魔法物品，比如法术书、符文和束魔精华。");

        BookSpotlightPageModel base = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(AllStacks.BASE_ESSENCE_STACKS))
                .build();

        return this.entry('e')
                .withIcon(AllItems.BASE_ESSENCES.get(SchoolUtil.SCHOOLS.get(0)).get(1))
                .withPage(base);
    }

    private BookEntryModel entryEnchant()
    {
        BookContextHelper context = this.context();

        context.entry("enchant");
        this.lang().add(context.entryName(), "Enchanted Essence");
        this.lang().add(context.entryDescription(), "Enchant your equipment.");
        this.lang("zh_cn").add(context.entryName(), "束魔精华");
        this.lang("zh_cn").add(context.entryDescription(), "为你的装备注入魔力。");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Enchanted Essence");
        this.lang().add(context.pageText(),
                """
                        \\
                        Enchanted Essence can imbue an item with magic power to increase some of its attributes. Be aware that Enchanted Essence can only work on an item if its Threshold is not less than the item's Enchanted Level.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "束魔精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        束魔精华可以为物品注入魔力，使其增加部分属性。要注意只有当束魔精华的阈值不小于物品的束魔等级时才能作用于该物品。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getNbtIngredient(AllStacks.ECES_STACKS))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        Primary, Intermediate, and Advanced Basic Essences can be crafted into Intermediate Essences with a Threshold of 10, 20, and 30 respectively. The maximum Threshold of Enchanted Essence that can be obtained from loot is 60.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        初级、中级、高级的基础精华分别可以合成阈值为10、20、30的束魔精华。战利品中最高能获取到阈值为60的束魔精华。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return this.entry('c')
                .withIcon(BookIconModel.create(AllStacks.ECES_STACKS.get(0)))
                .withPages(prev, next);
    }

    private BookEntryModel entryEnlighten()
    {
        BookContextHelper context = this.context();

        context.entry("enlighten");
        this.lang().add(context.entryName(), "Enlightening Essence");
        this.lang().add(context.entryDescription(), "Strike at the heart.");
        this.lang("zh_cn").add(context.entryName(), "源启精华");
        this.lang("zh_cn").add(context.entryDescription(), "直击心灵。");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Enlightening Essence");
        this.lang().add(context.pageText(),
                """
                        \\
                        Enlightening Essence is similar to Enchanted Essence, except that while Enchanted Essence works on items, Enlightening Essence works on the mage himself and is not limited by a threshold.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "源启精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        源启精华与束魔精华类似，不同的是束魔精华作用于物品，而源启精华作用于魔法师自身，且不受限于阈值。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getNbtIngredient(AllStacks.ELES_STACKS))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        Enlightening Essence cannot be crafted and can only be obtained from loot.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        源启精华不能合成，只能从战利品中获取。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return this.entry('l')
                .withIcon(BookIconModel.create(AllStacks.ELES_STACKS.get(0)))
                .withPages(prev, next);
    }

    private BookEntryModel entryDisenchant()
    {
        BookContextHelper context = this.context();

        context.entry("disenchant");
        this.lang().add(context.entryName(), "Disenchanted Essence");
        this.lang().add(context.entryDescription(), "'Tears of remorse'");
        this.lang("zh_cn").add(context.entryName(), "祛魔精华");
        this.lang("zh_cn").add(context.entryDescription(), "“悔恨之泪”");

        context.page("disenchant");
        this.lang().add(context.pageTitle(), "Disenchanted Essence");
        this.lang().add(context.pageText(),
                """
                        \\
                        Disenchanted Essence removes all the magic infused into an item by Enchanted Essences. The only way to obtain an Disenchanted Essence is to purchase it from a Goblin Trader.""");
        this.lang("zh_cn").add(context.pageTitle(), "祛魔精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        祛魔精华能够消除束魔精华注入在一件物品上的所有魔力。获取祛魔精华的唯一途径是从哥布林商人手中购买。
                        """
        );

        BookSpotlightPageModel disenchant = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.DISENCHANTED_ESSENCE))
                .build();

        return this.entry('d')
                .withIcon(AllItems.DISENCHANTED_ESSENCE)
                .withPage(disenchant);
    }

    private BookEntryModel entryMending()
    {
        BookContextHelper context = this.context();

        context.entry("mending");
        this.lang().add(context.entryName(), "Mending Essence");
        this.lang().add(context.entryDescription(), "Untouched.");
        this.lang("zh_cn").add(context.entryName(), "修复精华");
        this.lang("zh_cn").add(context.entryDescription(), "完好如初。");

        context.page("mending");
        this.lang().add(context.pageTitle(), "Mending Essence");
        this.lang().add(context.pageText(),
                """
                        \\
                        Mending Essence completely repairs an item and removes its accumulated repair penalties. Mending Essence cannot be crafted and can only be obtained from loot.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "修复精华");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        修复精华可以完全修复一件物品, 并且清除其累积的修复惩罚。修复精华不能合成，只能从战利品中获取。
                        """
        );

        BookSpotlightPageModel mending = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(AllItems.MENDING_ESSENCE))
                .build();

        return this.entry('m')
                .withIcon(AllItems.MENDING_ESSENCE)
                .withPage(mending);
    }

    private BookEntryModel entryBind()
    {
        BookContextHelper context = this.context();

        context.entry("bind");
        this.lang().add(context.entryName(), "Spell Binding");
        this.lang().add(context.entryDescription(), "Bind your spells!");
        this.lang("zh_cn").add(context.entryName(), "法术绑定");
        this.lang("zh_cn").add(context.entryDescription(), "绑定你的法术！");

        context.page("bind");
        this.lang().add(context.pageTitle(), "Spell Binding");
        this.lang().add(context.pageText(),
                """
                        \\
                        Use the Spell Binding Table to get your first spell book and bind some spells! You can find a Spell Binding Table in the village or craft one yourself.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术绑定");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        使用法术绑定台来获取你的第一本法术书，并绑定一些法术！你可以在村庄里找到法术绑定台或是自己合成一个。
                        """
        );

        BookSpotlightPageModel bind = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(SpellBindingBlock.ITEM))
                .build();

        return this.entry('n')
                .withIcon(SpellBindingBlock.ITEM)
                .withPage(bind);
    }

    private BookEntryModel entryBook()
    {
        BookContextHelper context = this.context();

        context.entry("book");
        this.lang().add(context.entryName(), "Spell Book");
        this.lang().add(context.entryDescription(), "A carrier for recording spells.");
        this.lang("zh_cn").add(context.entryName(), "法术书");
        this.lang("zh_cn").add(context.entryDescription(), "记录法术的载体。");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Spell Book");
        this.lang().add(context.pageText(),
                """
                        \\
                        A spell book is a vehicle for recording spells. You can use the Spell Binding Table to transform a normal book into the most basic spell book.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术书");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        法术书是记录法术的载体。你可以使用法术绑定台将普通的书转化为最基本的法术书。
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(
                        Identifier.of(WizardsMod.ID, "arcane_spell_book"),
                        Identifier.of(WizardsMod.ID, "fire_spell_book"),
                        Identifier.of(WizardsMod.ID, "frost_spell_book"),
                        Identifier.of(PaladinsMod.ID, "paladin_spell_book"),
                        Identifier.of(PaladinsMod.ID, "priest_spell_book")
                ))
                .build();

        context.page("next");
        this.lang().add(context.pageTitle(), "Spell Book");
        this.lang().add(context.pageText(),
                """
                        \\
                        Advanced spell books require special materials to synthesise. The more advanced the spell book, the more spell slots it provides.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术书");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        高级的法术书需要特殊材料来合成。越高级的法术书提供越多的法术槽位。
                        """
        );

        BookSpotlightPageModel next = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(AllStacks.SPELL_BOOK_STACKS))
                .build();

        return this.entry('k')
                .withIcon(BookGenUtil.getItem(Identifier.of(WizardsMod.ID, "arcane_spell_book")))
                .withPages(prev, next);
    }

    private BookEntryModel entryScroll()
    {
        BookContextHelper context = this.context();

        context.entry("scroll");
        this.lang().add(context.entryName(), "Spell Scroll");
        this.lang().add(context.entryDescription(), "\"The Wisdom of the Ancients\"");
        this.lang("zh_cn").add(context.entryName(), "法术卷轴");
        this.lang("zh_cn").add(context.entryDescription(), "“古人的智慧”");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Spell Scroll");
        this.lang().add(context.pageText(),
                """
                        \\
                        There are a limited number of spells that you can learn through the Spell Binding Table. To learn to use more spells, you need to have Spell Scrolls.
                        \\
                        \\
                        Search for spell scrolls to see how to get them.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "法术卷轴");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        通过法术绑定台能够学习到的法术有限。要想学习使用更多魔法，你需要拥有法术卷轴。
                        \\
                        \\
                        搜索法术卷轴以查阅其获取方式。
                        """
        );

        ItemStack stack = AllItems.SPELL_SCROLL.getStack(new Identifier(WizardsMod.ID, "fire_breath"));

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getNbtIngredient(stack))
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        Spell Scrolls can be used directly to cast spells, or they can be freely bound or unbound to Spell Books. Spell Scrolls are usually obtained by killing specific monsters or crafted from specific materials.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        法术卷轴可以直接用于施法，也可以自由地与法术书绑定或解绑。法术卷轴通常需要通过击杀特定的怪物获取，或是用特定的材料合成。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return this.entry('r')
                .withIcon(BookIconModel.create(stack))
                .withPages(prev, next);
    }
}
