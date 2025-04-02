package karashokleo.spell_dimension.init;

import com.google.common.collect.ArrayListMultimap;
import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.item.*;
import karashokleo.spell_dimension.content.item.essence.*;
import karashokleo.spell_dimension.content.item.essence.base.ColorProvider;
import karashokleo.spell_dimension.content.item.trinket.*;
import karashokleo.spell_dimension.content.object.Tier;
import karashokleo.spell_dimension.util.SchoolUtil;
import karashokleo.spell_dimension.util.TagUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.NetherStarItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.runes.RunesMod;
import net.spell_engine.api.item.trinket.SpellBooks;
import net.spell_engine.internals.SpellRegistry;
import net.spell_power.api.SpellSchool;

import java.util.ArrayList;
import java.util.List;

public class AllItems
{
    public static final List<Item> COLOR_PROVIDERS = new ArrayList<>();

    public static final ArrayListMultimap<SpellSchool, BaseEssenceItem> BASE_ESSENCES = ArrayListMultimap.create();
    public static final ArrayListMultimap<SpellSchool, DynamicSpellBookItem> SPELL_BOOKS = ArrayListMultimap.create();

    public static List<SpecifiedLootBagItem> RANDOM_MATERIAL = new ArrayList<>();
    public static List<SpecifiedLootBagItem> RANDOM_GEAR = new ArrayList<>();
    public static List<SpecifiedLootBagItem> RANDOM_BOOK = new ArrayList<>();

    public static SpellScrollItem SPELL_SCROLL;
    public static Item DEBUG_STAFF;
    public static EnlighteningEssenceItem ENLIGHTENING_ESSENCE;
    public static EnchantedEssenceItem ENCHANTED_ESSENCE;
    public static DisenchantedEssenceItem DISENCHANTED_ESSENCE;
    public static MendingEssenceItem MENDING_ESSENCE;
    public static QuestScrollItem QUEST_SCROLL;
    public static SpawnerSoulItem SPAWNER_SOUL;
    public static BrokenItem BROKEN_ITEM;

    // Stage Items
    public static Item ABYSS_GUARD;
    public static Item ACCURSED_BLACKSTONE;
    public static Item CELESTIAL_DEBRIS;
    public static EndStageItem CELESTIAL_LUMINARY;

    // Endgame Trinkets
    public static SingleEpicTrinketItem ARMOR_OF_CONVERGENCE;
    public static ArcaneThroneItem ARCANE_THRONE;
    public static NirvanaStarfallItem NIRVANA_STARFALL;
    public static SingleEpicTrinketItem GLACIAL_NUCLEAR_ERA;
    public static FrostbiteDomeItem FROSTBITE_DOME;
    public static HeartSpellSteelItem HEART_STEEL;
    public static RejuvenatingBlossomItem REJUVENATING_BLOSSOM;

    // Breastplates
    public static AtomicBreastplateItem ATOMIC_BREASTPLATE;
    public static EnchantedBreastplateItem ENCHANTED_BREASTPLATE;
    public static FlexBreastplateItem FLEX_BREASTPLATE;
    public static FlickerBreastplateItem FLICKER_BREASTPLATE;
    public static OblivionBreastplateItem OBLIVION_BREASTPLATE;

    // Misc Items
    public static SpellContainerItem SPELL_CONTAINER;
    public static MagicMirrorItem MAGIC_MIRROR;
    public static MagicMirrorItem BROKEN_MAGIC_MIRROR;
    public static BottleOfNightmare BOTTLE_NIGHTMARE;
    public static BottleOfSoulBinding BOTTLE_SOUL_BINDING;
    public static SpellPrismItem SPELL_PRISM;
    public static CursedAppleItem CURED_APPLE;
    public static MedalItem MEDAL;

    public static void register()
    {
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            for (int grade = 0; grade < 3; grade++)
            {
                BASE_ESSENCES.put(school, registerBaseEssence(school, grade));
                SPELL_BOOKS.put(school, registerSpellBook(school, grade));
            }
        }
        for (Tier tier : Tier.values())
        {
            RANDOM_MATERIAL.add(registerRandomLoot(tier, "material"));
            RANDOM_GEAR.add(registerRandomLoot(tier, "gear"));
            RANDOM_BOOK.add(registerRandomLoot(tier, "book"));
        }

        SPELL_SCROLL = Entry.of("spell_scroll", new SpellScrollItem())
                .addModel()
                .register();
        DEBUG_STAFF = Entry.of("debug_staff", new Item(new FabricItemSettings().maxCount(1)))
                .addEN()
                .addZH("调试法杖")
                .addModel()
                .register();
        ENLIGHTENING_ESSENCE = Entry.of("enlightening_essence", new EnlighteningEssenceItem())
                .addModel()
                .register();
        ENCHANTED_ESSENCE = Entry.of("enchanted_essence", new EnchantedEssenceItem())
                .addModel()
                .register();
        DISENCHANTED_ESSENCE = Entry.of("disenchanted_essence", new DisenchantedEssenceItem())
                .addEN()
                .addZH("祛魔精华")
                .addModel()
                .register();
        MENDING_ESSENCE = Entry.of("mending_essence", new MendingEssenceItem())
                .addEN()
                .addZH("修复精华")
                .addModel()
                .register();
        QUEST_SCROLL = Entry.of("quest_scroll", new QuestScrollItem())
                .addEN()
                .addZH("任务卷轴")
                .addModel()
                .register();
        SPAWNER_SOUL = Entry.of("spawner_soul", new SpawnerSoulItem())
                .addEN()
                .addZH("笼中魄")
                .addModel()
                .register();
        BROKEN_ITEM = Entry.of("spawner_soul", new BrokenItem())
                .addEN()
                .addZH("笼中魄")
                .addTag(LHTags.NO_SEAL)
                .addModel(Models.HANDHELD)
                .register();

        // Stage Items
        ABYSS_GUARD = Entry.of("abyss_guard", new NetherStarItem(new FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.EPIC)))
                .addEN()
                .addZH("深渊守护")
                .addModel()
                .register();
        ACCURSED_BLACKSTONE = Entry.of("accursed_blackstone", new NetherStarItem(new FabricItemSettings().fireproof().maxCount(16).rarity(Rarity.EPIC)))
                .addEN()
                .addZH("朽咒黑石")
                .addModel()
                .register();
        CELESTIAL_DEBRIS = Entry.of("celestial_debris", new Item(new FabricItemSettings().fireproof().rarity(Rarity.EPIC)))
                .addEN()
                .addZH("日月星碎")
                .addModel()
                .register();
        CELESTIAL_LUMINARY = Entry.of("celestial_luminary", new EndStageItem())
                .addEN()
                .addZH("无尽星辉")
                .addModel()
                .register();

        // Endgame Trinkets
        ARMOR_OF_CONVERGENCE = Entry.of(
                        "armor_of_convergence",
                        new SingleEpicTrinketItem()
                )
                .addEN()
                .addZH("汇聚甲胄")
                .addTag(AllTags.BACK, AllTags.CAPE)
                .addModel()
                .register();
        ARCANE_THRONE = Entry.of("arcane_throne", new ArcaneThroneItem())
                .addEN()
                .addZH("秘术王座")
                .addTag(LHTags.CURSE_SLOT)
                .addModel()
                .register();
        NIRVANA_STARFALL = Entry.of("nirvana_starfall", new NirvanaStarfallItem())
                .addEN()
                .addZH("涅槃星陨")
                .addTag(AllTags.BACK)
                .addModel()
                .register();
        GLACIAL_NUCLEAR_ERA = Entry.of("glacial_nuclear_era", new SingleEpicTrinketItem())
                .addEN()
                .addZH("冰核世纪")
                .addTag(AllTags.BACK)
                .addModel()
                .register();
        FROSTBITE_DOME = Entry.of("frostbite_dome", new FrostbiteDomeItem())
                .addEN()
                .addZH("冰点穹狱")
                .addTag(AllTags.BACK)
                .addModel()
                .register();
        HEART_STEEL = Entry.of("heart_spell_steel", new HeartSpellSteelItem())
                .addEN()
                .addZH("心之魔钢")
                .addTag(LHTags.CHARM_SLOT)
                .addModel()
                .register();
        REJUVENATING_BLOSSOM = Entry.of("rejuvenating_blossom", new RejuvenatingBlossomItem())
                .addEN()
                .addZH("复苏绽放")
                .addTag(LHTags.CHARM_SLOT)
                .addModel()
                .register();

        // Breastplates
        ATOMIC_BREASTPLATE = Entry.of("atomic_breastplate", new AtomicBreastplateItem())
                .addEN()
                .addZH("原子护心镜")
                .addTag(AllTags.BREASTPLATE_SLOT)
                .addModel()
                .register();
        ENCHANTED_BREASTPLATE = Entry.of("enchanted_breastplate", new EnchantedBreastplateItem())
                .addEN()
                .addZH("束魔护心镜")
                .addTag(AllTags.BREASTPLATE_SLOT)
                .addModel()
                .register();
        FLEX_BREASTPLATE = Entry.of("flex_breastplate", new FlexBreastplateItem())
                .addEN()
                .addZH("曲御护心镜")
                .addTag(AllTags.BREASTPLATE_SLOT)
                .addModel()
                .register();
        FLICKER_BREASTPLATE = Entry.of("flicker_breastplate", new FlickerBreastplateItem())
                .addEN()
                .addZH("闪曳护心镜")
                .addTag(AllTags.BREASTPLATE_SLOT)
                .addModel()
                .register();
        OBLIVION_BREASTPLATE = Entry.of("oblivion_breastplate", new OblivionBreastplateItem())
                .addEN()
                .addZH("湮灭护心镜")
                .addTag(AllTags.BREASTPLATE_SLOT)
                .addModel()
                .register();

        // Misc Items
        SPELL_CONTAINER = Entry.of("spell_container", new SpellContainerItem())
                .addEN()
                .addZH("法术容器")
                .addModel()
                .register();
        MAGIC_MIRROR = Entry.of("magic_mirror", new MagicMirrorItem(false))
                .addEN()
                .addZH("魔镜")
                .addModel()
                .register();
        BROKEN_MAGIC_MIRROR = Entry.of("broken_magic_mirror", new MagicMirrorItem(true))
                .addEN()
                .addZH("破碎魔镜")
                .addModel()
                .register();
        BOTTLE_NIGHTMARE = Entry.of(
                        "bottle_of_nightmare",
                        new BottleOfNightmare(
                                new FabricItemSettings()
                                        .maxCount(64)
                                        .rarity(Rarity.EPIC)
                                        .recipeRemainder(Items.GLASS_BOTTLE)
                        )
                )
                .addModel()
                .addEN()
                .addZH("梦魇之瓶")
                .register();
        BOTTLE_SOUL_BINDING = Entry.of(
                        "bottle_of_soul_binding",
                        new BottleOfSoulBinding(
                                new FabricItemSettings()
                                        .maxCount(64)
                                        .rarity(Rarity.RARE)
                                        .recipeRemainder(Items.GLASS_BOTTLE)
                        )
                )
                .addModel()
                .addEN()
                .addZH("灵魂羁绊之瓶")
                .register();
        SPELL_PRISM = Entry.of("spell_prism", new SpellPrismItem())
                .addEN()
                .addZH("法术棱镜")
                .addModel()
                .register();
        CURED_APPLE = Entry.of("cursed_apple", new CursedAppleItem())
                .addEN()
                .addZH("诅咒禁果")
                .addModel()
                .register();
        MEDAL = Entry.of("medal", new MedalItem())
                .addEN()
                .addZH("勋章")
                .addModel()
                .register();
    }

    public static BaseEssenceItem registerBaseEssence(SpellSchool school, int grade)
    {
        return Entry.of(
                        "essence_" + school.id.getPath() + "_" + grade,
                        new BaseEssenceItem(school, grade)
                )
                .addModel("base_essence_" + grade)
                .addTag(
                        AllTags.ESSENCE.get(grade),
                        TagUtil.itemTag(new Identifier(RunesMod.ID, "rune_crafting/reagent/" + school.id.getPath() + "_small"))
                )
                .register();
    }

    public static DynamicSpellBookItem registerSpellBook(SpellSchool school, int grade)
    {
        String name = "spell_book_" + school.id.getPath() + "_" + grade;
        DynamicSpellBookItem item = Entry.of(
                        name,
                        new DynamicSpellBookItem(school, grade)
                )
                .addModel("spell_book/" + school.id.getPath() + "/" + grade)
                .addTag(AllTags.BOOK.get(grade))
                .register();
        SpellBooks.all.add(item);
        SpellRegistry.book_containers.put(SpellDimension.modLoc(name), item.getSpellContainer());
        return item;
    }

    public static SpecifiedLootBagItem registerRandomLoot(Tier tier, String suffix)
    {
        Identifier id = SpellDimension.modLoc(tier.name + "/" + suffix);
        return Entry.of(
                        "random_" + tier.name + "_" + suffix,
                        new SpecifiedLootBagItem(id, tier)
                )
                .addModel("random/" + suffix)
                .register();
    }

    public static class Entry<T extends Item> extends ItemBuilder<T>
    {
        public static <T extends Item> Entry<T> of(String name, T content)
        {
            return new Entry<>(name, content);
        }

        private Entry(String name, T content)
        {
            super(name, content, null);
            if (content instanceof ColorProvider) COLOR_PROVIDERS.add(content);
            if (content instanceof TraitSymbol)
                this.setTab(LHMiscs.TRAITS);
        }

        public ItemBuilder<T> addModel(String textureId)
        {
            this.getModelGenerator().addItemWithTexturePath(content, Models.GENERATED, textureId);
            return this;
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }
    }
}