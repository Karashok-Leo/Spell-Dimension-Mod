package karashokleo.spell_dimension.data.book.category;

import com.glisco.victus.item.VictusItems;
import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.tom.storagemod.Content;
import karashokleo.enchantment_infusion.init.EIItems;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.spell_dimension.content.event.PlayerHealthEvent;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import wraith.alloyforgery.forges.ForgeRegistry;

public class TipsCategory extends CategoryProvider
{
    public TipsCategory(BookProvider parent)
    {
        super(parent, "tips");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "k_h_s_g_",
                "_______",
                "f___e_m_",
                "_______",
                "t_l____",
                "_______",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel key = this.entryKey();
        BookEntryModel health = this.entryHealth();
        BookEntryModel smith = this.entrySmith();
        BookEntryModel storage = this.entryStorage();
        BookEntryModel difficulty = this.entryHostility();
        BookEntryModel enchant = this.entryEnchant();
        BookEntryModel dummy = this.entryDummy();
        BookEntryModel trader = this.entryTrader();
        BookEntryModel alloy = this.entryAlloy();
        this.add(key);
        this.add(health);
        this.add(smith);
        this.add(storage);
        this.add(difficulty);
        this.add(enchant);
        this.add(dummy);
        this.add(trader);
        this.add(alloy);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Tips");
        this.lang("zh_cn").add(context.categoryName(), "提示");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.LANTERN)
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/2.png"));
    }

    private BookEntryModel entryKey()
    {
        BookContextHelper context = this.context();

        context.entry("key");
        this.lang().add(context.entryName(), "Key Binds");
        this.lang().add(context.entryDescription(), "Some keys you need to know.");
        this.lang("zh_cn").add(context.entryName(), "键位绑定");
        this.lang("zh_cn").add(context.entryDescription(), "一些你需要知道的键位。");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Key Binds");
        this.lang().add(context.pageText(),
                """
                        \\
                        ` (~): Chain Mining
                        \\
                        \\
                        R: Combat Roll
                        \\
                        \\
                        R: Refresh Trades
                        \\
                        \\
                        B: Open Backpack
                        \\
                        \\
                        G: Open Ender Bag
                        \\
                        \\
                        K: Open Skill Menu
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "键位绑定");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        ` (~): 连锁采集
                        \\
                        \\
                        R: 翻滚
                        \\
                        \\
                        R: 刷新交易
                        \\
                        \\
                        B: 打开背包
                        \\
                        \\
                        G: 打开末影袋
                        \\
                        \\
                        K: 打开技能菜单
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
                        \\
                        J: Turn on/off auto step-up
                        \\
                        \\
                        Middle Button / Double-click: Sort Inventory
                        \\
                        \\
                        Left Shift + W: Wall-Jumping
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        J: 开启/关闭自动上坡
                        \\
                        \\
                        鼠标中键 / 双击: 整理物品栏
                        \\
                        \\
                        左Shift + W: 攀墙跳
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return this.entry('k')
                .withIcon(Items.BIRCH_HANGING_SIGN)
                .withPages(prev, next);
    }

    private BookEntryModel entryHealth()
    {
        BookContextHelper context = this.context();

        context.entry("health");
        this.lang().add(context.entryName(), "Health Mechanics");
        this.lang().add(context.entryDescription(), "Starting at 10...");
        this.lang("zh_cn").add(context.entryName(), "生命机制");
        this.lang("zh_cn").add(context.entryDescription(), "从10开始...");

        context.page("health");
        this.lang().add(context.pageTitle(), "Health Mechanics");
        this.lang().add(context.pageText(),
                """
                        \\
                        As you can see, in the beginning you only have a max health of 10. Early in the game, you have a probability of getting a heart when you gain experience values greater than %d at once, but as your max health approaches %d, you will no longer be able to grow your max health in this way.
                        """.formatted(PlayerHealthEvent.XP_THRESHOLD, PlayerHealthEvent.HEALTH_THRESHOLD)
        );
        this.lang("zh_cn").add(context.pageTitle(), "生命机制");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        如你所见，开局你只有10点生命值。在游戏初期，当你一次性获得大于%d的经验值时，你有概率获得一颗心，但随着你的最大生命值逼近%d点，你将不再能通过这种方式增长血量。
                        """.formatted(PlayerHealthEvent.XP_THRESHOLD, PlayerHealthEvent.HEALTH_THRESHOLD)
        );

        BookTextPageModel health = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        context.page("victus");
        this.lang().add(context.pageTitle(), "Victus");
        this.lang().add(context.pageText(),
                """
                        \\
                        You craft custom hearts, the so called Heart Aspects which you can then apply on top of your health bar for interesting and often useful effects when they get broken.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "Victus");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你可以制作定制的心，即所谓的 “心之相位”，然后将其应用于你的血条上，当它们碎掉时，可以产生有趣且有用的效果。
                        """
        );

        BookSpotlightPageModel victus = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(
                        VictusItems.GRILLED_HEART_ASPECT,
                        VictusItems.BUNDLE_HEART_ASPECT,
                        VictusItems.CREEPER_HEART_ASPECT,
                        VictusItems.DIAMOND_HEART_ASPECT,
                        VictusItems.LIGHT_HEART_ASPECT,
                        VictusItems.OCEAN_HEART_ASPECT,
                        VictusItems.TOTEM_HEART_ASPECT,
                        VictusItems.POTION_HEART_ASPECT,
                        VictusItems.ARCHERY_HEART_ASPECT,
                        VictusItems.BLAZING_HEART_ASPECT,
                        VictusItems.DRACONIC_HEART_ASPECT,
                        VictusItems.EMERALD_HEART_ASPECT,
                        VictusItems.EVOKING_HEART_ASPECT,
                        VictusItems.GOLDEN_HEART_ASPECT,
                        VictusItems.ICY_HEART_ASPECT,
                        VictusItems.IRON_HEART_ASPECT,
                        VictusItems.LAPIS_HEART_ASPECT,
                        VictusItems.SWEET_HEART_ASPECT,
                        VictusItems.CHEESE_HEART_ASPECT
                ))
                .build();

        context.page("rally");
        this.lang().add(context.pageTitle(), "Rally");
        this.lang().add(context.pageText(),
                """
                        \\
                        After being hit by an enemy, you may jump back in the action and attack your opponent to quickly refill lost health.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "反击");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        被敌人击中后，如果你迅速反击对手，可以重获失去的生命值。
                        """
        );

        BookTextPageModel rally = BookTextPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .build();

        return this.entry('h')
                .withIcon(VictusItems.BUNDLE_HEART_ASPECT)
                .withPages(health, victus, rally);
    }

    private BookEntryModel entrySmith()
    {
        BookContextHelper context = this.context();

        context.entry("smith");
        this.lang().add(context.entryName(), "Smithing");
        this.lang().add(context.entryDescription(), "Equipment Modifier!");
        this.lang("zh_cn").add(context.entryName(), "重铸");
        this.lang("zh_cn").add(context.entryDescription(), "装备加成！");

        context.page("modifier");
        this.lang().add(context.pageTitle(), "Modifier");
        this.lang().add(context.pageText(),
                """
                        \\
                        Every tool and armor piece you obtain will have special modifiers, which provide additional stat bonuses and abilities.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "装备词条");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你获得的每件工具和盔甲都有特殊的词条，可以提供额外的属性加成。
                        """
        );

        Item table = BookGenUtil.getItem(Identifier.of("equipment_standard", "reforge_table"));

        BookSpotlightPageModel modifier = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(table))
                .build();

        context.page("reforge");
        this.lang().add(context.pageTitle(), "Reforge");
        this.lang().add(context.pageText(),
                """
                        \\
                        You can use reforge scrolls to reforge your equipment on the reforge table, and the higher the level of reforge scrolls the higher the probability of getting better modifiers.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "重铸");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你可以使用重铸卷轴在重铸台上重铸你的装备，越高级的重铸卷轴能获得较好词条的概率越高。
                        """
        );

        BookSpotlightPageModel reforge = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(
                        Identifier.of("equipment_standard", "reforge_scroll_lv1"),
                        Identifier.of("equipment_standard", "reforge_scroll_lv2"),
                        Identifier.of("equipment_standard", "reforge_scroll_lv3")
                ))
                .build();

        return this.entry('s')
                .withIcon(table)
                .withPages(modifier, reforge);
    }

    private BookEntryModel entryStorage()
    {
        BookContextHelper context = this.context();

        context.entry("storage");
        this.lang().add(context.entryName(), "Storage");
        this.lang().add(context.entryDescription(), "Well organized.");
        this.lang("zh_cn").add(context.entryName(), "存储");
        this.lang("zh_cn").add(context.entryDescription(), "井然有序。");

        context.page("storage");
        this.lang().add(context.pageTitle(), "Tom's Simple Storage");
        this.lang().add(context.pageText(),
                """
                        \\
                        Place a storage connector with a terminal next to your chests, after which you can manage the items of all your chests in a unified way through the terminal.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "汤姆的简易存储");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        在你的箱子旁放置存储整合器与终端，之后你便可以通过终端统一管理所有箱子里的物品。
                        """
        );

        BookSpotlightPageModel storage = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Content.connector.get(), Content.terminal.get(), Content.craftingTerminal.get()))
                .build();

        return this.entry('g')
                .withIcon(Content.connector.get())
                .withPage(storage);
    }

    private BookEntryModel entryHostility()
    {
        BookContextHelper context = this.context();

        context.entry("hostility");
        this.lang().add(context.entryName(), "Lightland Hostility");
        this.lang().add(context.entryDescription(), "This hostile world.");
        this.lang("zh_cn").add(context.entryName(), "莱特兰·恶意");
        this.lang("zh_cn").add(context.entryDescription(), "这充斥着恶意的世界。");

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

        return this.entry('f')
                .withIcon(LHTraits.ENDER.asItem())
                .withPages(prev, next);
    }

    private BookEntryModel entryEnchant()
    {
        BookContextHelper context = this.context();

        context.entry("enchant");
        this.lang().add(context.entryName(), "Enchant");
        this.lang().add(context.entryDescription(), "Enchanting & Disenchanting.");
        this.lang("zh_cn").add(context.entryName(), "附魔");
        this.lang("zh_cn").add(context.entryDescription(), "附魔与祛魔。");

        context.page("infuse");
        this.lang().add(context.pageTitle(), "Enchantment Infusion");
        this.lang().add(context.pageText(),
                """
                        \\
                        Enchantment Infusion is a directional enchanting method. The key blocks of Infusion are the Enchantment Infusion Table and the Pedestal. Use item to right-click them to put it in, and empty-handed right-click to remove item from them.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "魔咒灌注");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        魔咒灌注是一种定向附魔的方式。魔咒灌注的核心方块是附魔灌注台和附魔灌注基座。使用物品右键它们将其放入，空手右键取出其中物品。
                        """
        );

        BookSpotlightPageModel infuse = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(EIItems.INFUSION_TABLE_ITEM, EIItems.INFUSION_PEDESTAL_ITEM))
                .build();

        context.page("image");
        this.lang().add(context.pageTitle(), "Positioning of table and pedestals");
        this.lang("zh_cn").add(context.pageTitle(), "附魔灌注台与基座的位置");

        BookImagePageModel image = BookImagePageModel
                .builder()
                .withTitle(context.pageTitle())
                .withImages(BookGenUtil.id("textures/infusion.png"))
                .build();

        context.page("pos");
        this.lang().add(context.pageText(),
                """
                        \\
                        You need to place a enchantment infusion table (centre position) and pedestals (surrounding position) like the picture, then place the items on the pedestals according to the recipe (the order doesn't matter), and finally place the item you want to enchant on the infusion table in the centre, and wait for seconds for it to finish enchanting.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你需要像图中这样摆放魔咒灌注台（中心位置）与魔咒灌注基座（周围的位置），然后按照配方在基座上摆放对应的物品（次序不重要），最后在中心的灌注台上摆放你想要附魔的物品，等待若干秒即可完成附魔。
                        """
        );

        BookTextPageModel pos = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("disenchant");
        this.lang().add(context.pageTitle(), "Disenchant");
        this.lang().add(context.pageText(),
                """
                        \\
                        You can transfer enchantments to books by placing equipment with enchantments and books on the grindstone.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "祛魔");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你可以把带有附魔的装备和书放在砂轮上，从而将魔咒转移至书上。
                        """
        );

        BookSpotlightPageModel disenchant = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.GRINDSTONE))
                .build();

        return this.entry('e')
                .withIcon(Items.ENCHANTING_TABLE)
                .withPages(infuse, image, pos, disenchant);
    }

    private BookEntryModel entryDummy()
    {
        BookContextHelper context = this.context();

        context.entry("dummy");
        this.lang().add(context.entryName(), "Dummy");
        this.lang().add(context.entryDescription(), "MmmMmmMmmMmm");
        this.lang("zh_cn").add(context.entryName(), "假人");
        this.lang("zh_cn").add(context.entryDescription(), "MmmMmmMmmMmm");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Target Dummy");
        this.lang().add(context.pageText(),
                """
                        \\
                        The dummy can be placed in the world, where it'll stand silently, judging your every action. It'll wiggle funny if you hit it, screaming large numbers of damage at you. It can be dressed up to look even more awesome. ~~This will please the dummy, making the numbers smaller.~~
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "训练假人");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        训练假人可以放置在世界中。它静静伫立在那里，判断你的一举一动。如果你击中它，它会滑稽地扭动起来，向你发出蛤人的尖叫。它甚至可以打扮得更加漂亮。~~这会让假人高兴，把你的数据变小。~~
                        """
        );

        BookSpotlightPageModel prev = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Dummmmmmy.DUMMY_ITEM.get()))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId(Dummmmmmy.res(Dummmmmmy.TARGET_DUMMY_NAME).toString())
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        TL; DR: target dummies show damage dealt to them and can be equipped with armor.
                        \\
                        \\
                        Usage: Right click on a block with a target dummy to place it.He can be rotated 16 different directions depending on the way you face when you place it, similar to an armor stand.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        太长不看: 训练假人记录并显示你对其造成的伤害，并且可以装备盔甲。
                        \\
                        \\
                        用法: 右键单击方块放置它。它可以根据放置时面对的方向旋转 16个不同的方向，类似于盔甲架。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        context.page("last");
        this.lang().add(context.pageText(),
                """
                        \\
                        You can start dressing the little dude with all kind of armors and equipment.Just right click him with the desired item.To unequip a certain armor piece just click on his corresponding body part.
                        \\
                        \\
                        Got tired of testing your dps? You can remove the dummy just by shift left clicking him with an empty hand!
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        你可以给这家伙穿上各种盔甲和装备。只需用物品右键单击它即可。要取消装备某个盔甲，只需单击其相应的身体部位即可。
                        \\
                        \\
                        开始对测试DPS感到厌倦？只需空手左键即可移除假人！
                        """
        );

        BookTextPageModel last = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return this.entry('m')
                .withIcon(Dummmmmmy.DUMMY_ITEM.get())
                .withPages(prev, entity, next, last);
    }

    private BookEntryModel entryTrader()
    {
        BookContextHelper context = this.context();

        context.entry("trader");
        this.lang().add(context.entryName(), "Trader");
        this.lang().add(context.entryDescription(), "He really loves apples.");
        this.lang("zh_cn").add(context.entryName(), "商人");
        this.lang("zh_cn").add(context.entryDescription(), "他真的很喜欢吃苹果。");

        context.page("prev");
        this.lang().add(context.pageTitle(), "Goblin Trader");
        this.lang().add(context.pageText(),
                """
                        \\
                        Perhaps you have a chance to meet goblin traders, a cute race of people who like apples, and unlike wandering traders with llamas, the items they sell tend to be very helpful to you.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "哥布林商人");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        也许你有机会遇到哥布林商人，他们是喜欢吃苹果的可爱种族，与牵着羊驼的流浪商人不同，他们出售的物品往往对你很有帮助。
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
                        \\
                        Goblin traders are often found in the caves of overworld, while vein goblin traders are often found in the nether, with the latter selling much rarer items.
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        哥布林商人常在主世界的洞穴出没，熔岩哥布林商人则常在下界出没，后者出售的物品更加稀有。
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        BookEntityPageModel goblin = BookEntityPageModel
                .builder()
                .withEntityId("goblintraders:goblin_trader")
                .build();

        BookEntityPageModel vein = BookEntityPageModel
                .builder()
                .withEntityId("goblintraders:vein_goblin_trader")
                .build();

        return this.entry('t')
                .withIcon(Items.APPLE)
                .withPages(prev, next, goblin, vein);
    }

    private BookEntryModel entryAlloy()
    {
        BookContextHelper context = this.context();

        context.entry("alloy");
        this.lang().add(context.entryName(), "Alloy Forging");
        this.lang().add(context.entryDescription(), "An easy way to increase ore yield.");
        this.lang("zh_cn").add(context.entryName(), "合金冶炼");
        this.lang("zh_cn").add(context.entryDescription(), "提高矿石产量的简单方法。");

        context.page("alloy");
        this.lang().add(context.pageTitle(), "Alloy Forging");
        this.lang().add(context.pageText(),
                """
                        \\
                        Alloy Forging serves as an easy way to increase ore yield.
                        \\
                        \\
                        To construct a forge, simply follow the convenient guide shown in the picture using any combination of blocks that forge type supports.
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "合金冶炼");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        合金冶炼是提高矿石产量的简单方法。
                        \\
                        \\
                        要建造一个锻造炉，只需按照图中所示的指南，使用冶炼炉类型所支持的任何方块组合即可。
                        """
        );

        BookSpotlightPageModel alloy = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(ForgeRegistry.controllerBlocksView().stream().map(block -> block.asItem().getDefaultStack())))
                .build();

        Identifier id = BookGenUtil.id("textures/alloy_forge/");

        BookImagePageModel image = BookImagePageModel
                .builder()
                .withImages(
                        id.withSuffixedPath("1.png"),
                        id.withSuffixedPath("2.png"),
                        id.withSuffixedPath("3.png")
                )
                .build();

        return this.entry('l')
                .withIcon(ForgeRegistry.getControllerBlocks().get(0))
                .withPages(alloy, image);
    }
}
