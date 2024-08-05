package karashokleo.spell_dimension.data.book.category;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookContextHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookEntityPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.spell_dimension.util.BookGenUtil;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public class BossProvider extends CategoryProvider
{
    public BossProvider(BookProvider parent)
    {
        super(parent, "boss");
    }

    @Override
    protected String[] generateEntryMap()
    {
        return new String[]{
                "r_g_d_w_",
                "_______",
                "t_p_e_m_",
                "_______",
                "t_l____",
                "_______",
        };
    }

    @Override
    protected void generateEntries()
    {
        BookEntryModel wither = this.entryWither();
        BookEntryModel guardian = this.entryGuardian();
        BookEntryModel dragon = this.entryDragon();
        BookEntryModel warden = this.entryWarden();
        BookEntryModel stalker = this.entryStalker();
        BookEntryModel captain = this.entryCaptain();
        this.add(wither);
        this.add(guardian);
        this.add(dragon);
        this.add(warden);
        this.add(stalker);
        this.add(captain);
    }

    @Override
    protected BookCategoryModel generateCategory()
    {
        BookContextHelper context = this.context();
        this.lang().add(context.categoryName(), "Conquer All");
        this.lang("zh_cn").add(context.categoryName(), "尽数征服");
        return BookCategoryModel
                .create(this.modLoc(context.categoryId()), context.categoryName())
                .withIcon(Items.NETHER_STAR)
                .withBackground(new Identifier(ModonomiconAPI.ID, "textures/gui/parallax/flow/base.png"));
    }

    private BookEntryModel entryWither()
    {
        BookContextHelper context = this.context();

        context.entry("wither");
        this.lang().add(context.entryName(), "Wither");
        this.lang().add(context.entryDescription(), "");
        this.lang("zh_cn").add(context.entryName(), "凋灵");
        this.lang("zh_cn").add(context.entryDescription(), "");

        context.page("boss");
        this.lang().add(context.pageTitle(), "Wither");
        this.lang().add(context.pageText(),
                """
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "凋灵");
        this.lang("zh_cn").add(context.pageText(),
                """
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.WITHER_SKELETON_SKULL))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:wither")
                .build();

        return this.entry('r')
                .withIcon(Items.NETHER_STAR)
                .withPages(boss, entity);
    }

    private BookEntryModel entryGuardian()
    {
        BookContextHelper context = this.context();

        context.entry("guardian");
        this.lang().add(context.entryName(), "Elder Guardian");
        this.lang().add(context.entryDescription(), "");
        this.lang("zh_cn").add(context.entryName(), "远古守卫者");
        this.lang("zh_cn").add(context.entryDescription(), "");

        context.page("boss");
        this.lang().add(context.pageTitle(), "Elder Guardian");
        this.lang().add(context.pageText(),
                """
                        \\
                        In the **Ocean Monument**.
                        \\
                        \\
                        *Ocean Monument: Structure ID minecraft:monument*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "远古守卫者");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        出没于**海底神殿**。
                        \\
                        \\
                        *海底神殿: 结构ID为 minecraft:monument*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(ComplementItems.GUARDIAN_EYE))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:elder_guardian")
                .withScale(0.3F)
                .build();

        return this.entry('g')
                .withIcon(ComplementItems.GUARDIAN_EYE)
                .withPages(boss, entity);
    }

    private BookEntryModel entryDragon()
    {
        BookContextHelper context = this.context();

        context.entry("dragon");
        this.lang().add(context.entryName(), "Ender Dragon");
        this.lang().add(context.entryDescription(), "");
        this.lang("zh_cn").add(context.entryName(), "末影龙");
        this.lang("zh_cn").add(context.entryDescription(), "");

        context.page("boss");
        this.lang().add(context.pageTitle(), "Ender Dragon");
        this.lang().add(context.pageText(),
                """
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "末影龙");
        this.lang("zh_cn").add(context.pageText(),
                """
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.DRAGON_HEAD))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:ender_dragon")
                .build();

        return this.entry('d')
                .withIcon(Items.DRAGON_HEAD)
                .withPages(boss, entity);
    }

    private BookEntryModel entryWarden()
    {
        BookContextHelper context = this.context();

        context.entry("warden");
        this.lang().add(context.entryName(), "Warden");
        this.lang().add(context.entryDescription(), "");
        this.lang("zh_cn").add(context.entryName(), "监守者");
        this.lang("zh_cn").add(context.entryDescription(), "");

        context.page("boss");
        this.lang().add(context.pageTitle(), "Warden");
        this.lang().add(context.pageText(),
                """
                        \\
                        When **Sculk Sensor** is activated at least 4 times, Warden burrows out of the ground.
                        \\
                        \\
                        *Sculk Sensor: Generated in the Deep Dark.*
                        \\
                        \\
                        *Deep Dark: Biome ID minecraft:deep_dark*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "监守者");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        当**幽匿尖啸体**被激活至少4次时，监守者会从地面钻出。
                        \\
                        \\
                        *幽匿尖啸体: 生成于深暗之域。*
                        \\
                        \\
                        *深暗之域: 群系ID为 minecraft:deep_dark*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(Ingredient.ofItems(Items.SCULK_SENSOR))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("minecraft:warden")
                .build();

        return this.entry('w')
                .withIcon(BookGenUtil.getItem(new Identifier("deeperdarker", "heart_of_the_deep")))
                .withPages(boss, entity);
    }

    private BookEntryModel entryStalker()
    {
        BookContextHelper context = this.context();

        context.entry("stalker");
        this.lang().add(context.entryName(), "Stalker");
        this.lang().add(context.entryDescription(), "");
        this.lang("zh_cn").add(context.entryName(), "Stalker");
        this.lang("zh_cn").add(context.entryDescription(), "");

        context.page("boss");
        this.lang().add(context.pageTitle(), "Stalker");
        this.lang().add(context.pageText(),
                """
                        \\
                        Spawns rarely when an **Ancient Vase** is broken.
                        \\
                        \\
                        *Ancient Vase: Generated in the Ancient Temple in the Deeper Darker.*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "Stalker");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        在**远古花瓶**被打破时有小概率生成。
                        \\
                        \\
                        *远古花瓶: 生成于深暗维度的远古神庙。*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(new Identifier("deeperdarker", "ancient_vase")))
                .build();

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withEntityId("deeperdarker:stalker")
                .build();

        context.page("next");
        this.lang().add(context.pageText(),
                """
                        \\
                        *Ancient Temple: Structure ID deeperdarker:ancient_temple*
                        \\
                        \\
                        *Deeper Darker: Use the Heart of the Deep to open the portal within the ancient city to enter.*
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        *远古神庙: 结构ID为 deeperdarker:ancient_temple*
                        \\
                        \\
                        *深暗维度: 使用深渊之心开启古代城市内的传送门进入。*
                        """
        );

        BookTextPageModel next = BookTextPageModel
                .builder()
                .withText(context.pageText())
                .build();

        return this.entry('t')
                .withIcon(BookGenUtil.getItem(new Identifier("deeperdarker", "soul_crystal")))
                .withPages(boss, entity, next);
    }

    private BookEntryModel entryCaptain()
    {
        BookContextHelper context = this.context();

        context.entry("captain");
        this.lang().add(context.entryName(), "Ghost of Captain Cornelia");
        this.lang().add(context.entryDescription(), "The sparse trumpets of the survivors would have awakened her soul.");
        this.lang("zh_cn").add(context.entryName(), "船长科妮莉亚亡灵");
        this.lang("zh_cn").add(context.entryDescription(), "求生者吹响的稀疏号角自会惊醒她的灵魂。");

        context.page("boss");
        this.lang().add(context.pageTitle(), "Ghost of Captain Cornelia");
        this.lang().add(context.pageText(),
                """
                        \\
                        When you hold the **Shell Horn** to blow it on the seashore of the Frozen Deep, Captain Cornelia Undead will emerge from the water.
                        \\
                        \\
                        *Shell Horn: Can be obtained by killing the Marauders staying in the Captain's Room in the *Stranded Ship*.*
                        """
        );
        this.lang("zh_cn").add(context.pageTitle(), "船长科妮莉亚亡灵");
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        当你手持**贝壳号角**并在冰冻深海的海边吹响，船长科妮莉亚亡灵会从水中浮现。
                        \\
                        \\
                        *贝壳号角: 击杀*搁浅舰船*遗迹中待在船长室的掠夺者可获得。*
                        """
        );

        BookSpotlightPageModel boss = BookSpotlightPageModel
                .builder()
                .withTitle(context.pageTitle())
                .withText(context.pageText())
                .withItem(BookGenUtil.getIngredient(new Identifier("aquamirae", "shell_horn")))
                .build();

        context.page("entity");
        this.lang().add(context.pageText(),
                """
                        \\
                        *Stranded Ship: Structure ID aquamirae:ship*
                        """
        );
        this.lang("zh_cn").add(context.pageText(),
                """
                        \\
                        *搁浅舰船: 结构ID为aquamirae:ship*
                        """
        );

        BookEntityPageModel entity = BookEntityPageModel
                .builder()
                .withText(context.pageText())
                .withEntityId("aquamirae:captain_cornelia")
                .build();

        return this.entry('p')
                .withIcon(BookGenUtil.getItem(new Identifier("aquamirae", "three_bolt_helmet")))
                .withPages(boss, entity);
    }

//    private BookEntryModel entryBoss()
//    {
//        BookContextHelper context = this.context();
//
//        context.entry("captain");
//        this.lang().add(context.entryName(), "");
//        this.lang().add(context.entryDescription(), "");
//        this.lang("zh_cn").add(context.entryName(), "");
//        this.lang("zh_cn").add(context.entryDescription(), "");
//
//        context.page("boss");
//        this.lang().add(context.pageTitle(), "");
//        this.lang().add(context.pageText(),
//                """
//                        \\
//
//                        \\
//                        \\
//                        *Stranded Ship: Structure ID aquamirae:ship*
//                        """
//        );
//        this.lang("zh_cn").add(context.pageTitle(), "");
//        this.lang("zh_cn").add(context.pageText(),
//                """
//                        \\
//
//                        \\
//                        \\
//                        *搁浅舰船: 结构ID为 aquamirae:ship*
//                        """
//        );
//
//        BookSpotlightPageModel boss = BookSpotlightPageModel
//                .builder()
//                .withTitle(context.pageTitle())
//                .withText(context.pageText())
//                .withItem()
//                .build();
//
//        BookEntityPageModel entity = BookEntityPageModel
//                .builder()
//                .withEntityId("aquamirae:captain_cornelia")
//                .build();
//
//        return this.entry('p')
//                .withIcon()
//                .withPages(boss, entity);
//    }
}
