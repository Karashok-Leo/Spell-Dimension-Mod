package karashokleo.spell_dimension.init;

import com.kyanite.deeperdarker.content.DDEntities;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.util.TagUtil;
import net.adventurez.init.EntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.runes.api.RuneItems;
import net.spell_engine.internals.SpellInfinityEnchantment;
import net.trique.mythicupgrades.MythicUpgradesDamageTypes;
import nourl.mythicmetals.item.tools.MythicTools;

import java.util.List;
import java.util.stream.Stream;

public class AllTags
{
    public static final TagKey<Item> BREASTPLATE_SLOT = TagUtil.itemTag(new Identifier("trinkets", "chest/breastplate"));

    public static final List<TagKey<Item>> ESSENCE = List.of(
            TagUtil.itemTag("essence/0"),
            TagUtil.itemTag("essence/1"),
            TagUtil.itemTag("essence/2")
    );
    public static final TagKey<Item> ESSENCE_ALL = TagUtil.itemTag("essence/all");
    public static final List<TagKey<Item>> BOOK = List.of(
            TagUtil.itemTag("book/0"),
            TagUtil.itemTag("book/1"),
            TagUtil.itemTag("book/2")
    );
    public static final TagKey<Item> BOOK_ALL = TagUtil.itemTag("book/all");

    public static final TagKey<Item> RUNE = TagUtil.itemTag("rune");
    public static final TagKey<Item> HEART_FOOD = TagUtil.itemTag("heart_food");

    public static final TagKey<Item> ARMOR = TagUtil.itemTag(new Identifier("equipment_standard:armor"));
    public static final TagKey<Item> MELEE_WEAPONS = TagUtil.itemTag(new Identifier("equipment_standard:melee_weapons"));
    public static final TagKey<Item> MAGIC = TagUtil.itemTag(new Identifier("equipment_standard:magic"));
    public static final TagKey<Item> MAGIC_WEAPON = TagUtil.itemTag(new Identifier("equipment_standard:magic/weapons"));

    public static final TagKey<Item> DUNGEON_BANNED = TagUtil.itemTag("dungeon_banned");

    public static final List<TagKey<Item>> SPELL_POWER_ENCHANTMENT_TAGS = Stream.of(
            "enchant_spell_power_generic",
            "enchant_spell_power_soulfrost",
            "enchant_spell_power_sunfire",
            "enchant_spell_power_energize",
            "enchant_critical_chance",
            "enchant_critical_damage",
            "enchant_haste"
    ).map(s -> TagUtil.itemTag(new Identifier("spell_power", s))).toList();

    public static final TagKey<Item> SPELL_INFINITY = TagUtil.itemTag(SpellInfinityEnchantment.tagId);

    public static final List<TagKey<Item>> REFORGE_CORE_TAGS = Stream.of(1, 2, 3).map(i -> TagUtil.itemTag(new Identifier("equipment_standard:reforge_core/lv" + i))).toList();

    public static final List<TagKey<Item>> FORGE_CONTROLLERS = Stream.of(1, 2, 3).map(i -> TagUtil.itemTag("forge_controller/" + i)).toList();

    public static final TagKey<Block> LOCATE_TARGET = TagUtil.blockTag("locate_target");

    public static final TagKey<Item> BACK = TagUtil.itemTag(new Identifier("trinkets", "chest/back"));
    public static final TagKey<Item> CAPE = TagUtil.itemTag(new Identifier("trinkets", "chest/cape"));

    public static final TagKey<Item> HOSTILITY_CURSE = TagUtil.itemTag("hostility_curse");
    public static final TagKey<Item> HOSTILITY_RING = TagUtil.itemTag("hostility_ring");

    public static final TagKey<EntityType<?>> ZOMBIES = TagUtil.entityTypeTag(new Identifier("zombies"));
    public static final TagKey<EntityType<?>> SKELETONS = EntityTypeTags.SKELETONS;
    public static final TagKey<EntityType<?>> RAIDERS_VANILLA = TagUtil.entityTypeTag("raiders_vanilla");
    public static final TagKey<EntityType<?>> RAIDERS_INVADE = TagUtil.entityTypeTag("raiders_invade");
    public static final TagKey<EntityType<?>> NETHER = TagUtil.entityTypeTag("nether");
    public static final TagKey<EntityType<?>> SCULK = TagUtil.entityTypeTag("sculk");
    public static final TagKey<EntityType<?>> ADVZ_MONSTER = TagUtil.entityTypeTag("advz_monster");
    public static final TagKey<EntityType<?>> SW_MONSTER = TagUtil.entityTypeTag("sw_monster");

    public static final TagKey<Enchantment> LOOTABLE = TagUtil.enchantmentTag("lootable");
    public static final TagKey<Enchantment> TRADABLE = TagUtil.enchantmentTag("tradable");
    public static final TagKey<Enchantment> ENCHANTABLE = TagUtil.enchantmentTag("enchantable");

    public static final TagKey<Fluid> CONSCIOUSNESS = TagUtil.fluidTag("consciousness");

    public static final TagKey<Quest> MAIN = TagUtil.questTag("main");
    public static final TagKey<Quest> BRANCH = TagUtil.questTag("branch");
    public static final TagKey<Quest> BEGINNING = TagUtil.questTag("beginning");
    public static final TagKey<Quest> END = TagUtil.questTag("end");
    public static final TagKey<Quest> CHALLENGE = TagUtil.questTag("challenge");

    public static void register()
    {
        RuneItems.entries.stream().map(RuneItems.Entry::id)
                .forEach(id -> SpellDimension.ITEM_TAGS.getOrCreateContainer(RUNE).add(id));

        SpellDimension.ITEM_TAGS.getOrCreateContainer(HEART_FOOD)
                .add(Items.ENCHANTED_GOLDEN_APPLE)
                .addOptional(
                        new Identifier("midashunger:enchanted_golden_carrot")
                );

        SpellDimension.ITEM_TAGS.getOrCreateContainer(REFORGE_CORE_TAGS.get(0), true)
                .add(AllItems.SPAWNER_SOUL);
        SpellDimension.ITEM_TAGS.getOrCreateContainer(REFORGE_CORE_TAGS.get(1), true)
                .addOptional(new Identifier("l2hostility:chaos_ingot"));
        SpellDimension.ITEM_TAGS.getOrCreateContainer(REFORGE_CORE_TAGS.get(2), true)
                .addOptional(new Identifier("l2hostility:miracle_ingot"));

        SpellDimension.ITEM_TAGS.getOrCreateContainer(FORGE_CONTROLLERS.get(0), true)
                .addOptional(
                        new Identifier("alloy_forgery:deepslate_bricks_forge_controller"),
                        new Identifier("alloy_forgery:bricks_forge_controller"),
                        new Identifier("alloy_forgery:stone_bricks_forge_controller")
                );
        SpellDimension.ITEM_TAGS.getOrCreateContainer(FORGE_CONTROLLERS.get(1), true)
                .addOptional(
                        new Identifier("alloy_forgery:prismarine_bricks_forge_controller"),
                        new Identifier("alloy_forgery:polished_blackstone_forge_controller")
                );
        SpellDimension.ITEM_TAGS.getOrCreateContainer(FORGE_CONTROLLERS.get(2), true)
                .addOptional(
                        new Identifier("alloy_forgery:adamantite_block_forge_controller"),
                        new Identifier("alloy_forgery:end_stone_bricks_forge_controller")
                );

        SpellDimension.ITEM_TAGS.getOrCreateContainer(HOSTILITY_CURSE)
                .add(
                        TrinketItems.CURSE_ENVY,
                        TrinketItems.CURSE_GLUTTONY,
                        TrinketItems.CURSE_GREED,
                        TrinketItems.CURSE_LUST,
                        TrinketItems.CURSE_PRIDE,
                        TrinketItems.CURSE_SLOTH,
                        TrinketItems.CURSE_WRATH
                );
        SpellDimension.ITEM_TAGS.getOrCreateContainer(HOSTILITY_RING)
                .add(
                        TrinketItems.RING_OCEAN,
                        TrinketItems.RING_LIFE,
                        TrinketItems.RING_DIVINITY,
                        TrinketItems.RING_REFLECTION,
                        TrinketItems.RING_INCARCERATION,
                        TrinketItems.RING_CORROSION,
                        TrinketItems.RING_HEALING
                );

        TagGenerator.Container<Item> essenceAllContainer = SpellDimension.ITEM_TAGS.getOrCreateContainer(ESSENCE_ALL);
        for (TagKey<Item> key : ESSENCE)
            essenceAllContainer.addTag(key);

        TagGenerator.Container<Item> bookAllContainer = SpellDimension.ITEM_TAGS.getOrCreateContainer(BOOK_ALL);
        for (TagKey<Item> key : BOOK)
            bookAllContainer.addTag(key);

        SpellDimension.ITEM_TAGS.getOrCreateContainer(DUNGEON_BANNED)
                .add(MythicTools.LEGENDARY_BANGLUM.getPickaxe())
                .addOptionalTag(new Identifier("constructionwand:wands"));

        SpellDimension.BLOCK_TAGS.getOrCreateContainer(LOCATE_TARGET)
                .add(
                        Blocks.LODESTONE
                );

        SpellDimension.BLOCK_TAGS.getOrCreateContainer(BlockTags.REPLACEABLE_BY_TREES)
                .add(
                        AllBlocks.CONSCIOUSNESS
                );

        SpellDimension.FLUID_TAGS.getOrCreateContainer(FluidTags.WATER)
                .add(
                        AllBlocks.STILL_CONSCIOUSNESS,
                        AllBlocks.FLOWING_CONSCIOUSNESS
                );

        SpellDimension.FLUID_TAGS.getOrCreateContainer(CONSCIOUSNESS)
                .add(
                        AllBlocks.STILL_CONSCIOUSNESS,
                        AllBlocks.FLOWING_CONSCIOUSNESS
                );

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(LHTags.WHITELIST)
                .add(EntityInit.THE_EYE);

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(ZOMBIES)
                .add(
                        EntityType.ZOMBIE,
                        EntityType.ZOMBIE_VILLAGER,
                        EntityType.DROWNED,
                        EntityType.HUSK
                ).addOptional(
                        new Identifier("soulsweapons:soul_reaper_ghost"),
                        new Identifier("soulsweapons:frost_giant")
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(SKELETONS)
                .add(
                        EntityInit.SKELETON_VANGUARD,
                        EntityInit.SOUL_REAPER,
                        EntityInit.WITHER_PUPPET,
                        EntityInit.NECROMANCER
                ).addOptional(
                        new Identifier("skeletalremains:overgrownskeleton"),
                        new Identifier("skeletalremains:sharpshooterskeleton"),
                        new Identifier("skeletalremains:sunkenskeleton"),
                        new Identifier("skeletalremains:charredskeleton"),
                        new Identifier("skeletalremains:fallenskeleton"),
                        new Identifier("skeletalremains:swampedskeleton")
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(RAIDERS_VANILLA)
                .add(
                        EntityType.PILLAGER,
                        EntityType.VINDICATOR,
                        EntityType.RAVAGER,
                        EntityType.WITCH,
                        EntityType.EVOKER,
                        EntityType.ILLUSIONER
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(RAIDERS_INVADE)
                .add(
                        fuzs.illagerinvasion.init.ModRegistry.BASHER_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.PROVOKER_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.NECROMANCER_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.SORCERER_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.ARCHIVIST_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.MARAUDER_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.INQUISITOR_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.ALCHEMIST_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.FIRECALLER_ENTITY_TYPE.get(),
                        fuzs.illagerinvasion.init.ModRegistry.SURRENDERED_ENTITY_TYPE.get()
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(NETHER)
                .add(
                        EntityType.PIGLIN,
                        EntityType.PIGLIN_BRUTE,
                        EntityType.ZOGLIN,
                        EntityType.HOGLIN,
                        EntityType.WITHER_SKELETON,
                        EntityInit.BLAZE_GUARDIAN,
                        EntityInit.PIGLIN_BEAST,
                        EntityInit.SOUL_REAPER,
                        EntityInit.WITHER_PUPPET,
                        EntityInit.NECROMANCER

                ).addOptional(
                        new Identifier("soulsweapons:forlorn"),
                        new Identifier("soulsweapons:evil_forlorn"),
                        new Identifier("soulsweapons:warmth_entity"),
                        new Identifier("soulsweapons:dark_sorcerer"),
                        new Identifier("soulsweapons:withered_demon")
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(SCULK)
                .add(
                        DDEntities.SCULK_CENTIPEDE,
                        DDEntities.SHATTERED,
                        DDEntities.SCULK_SNAPPER,
                        DDEntities.SLUDGE,
                        DDEntities.SHRIEK_WORM
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(ADVZ_MONSTER)
                .add(
                        EntityInit.AMETHYST_GOLEM,
                        EntityInit.SKELETON_VANGUARD,
                        EntityInit.SUMMONER,
                        EntityInit.BLAZE_GUARDIAN,
                        EntityInit.ORC,
                        EntityInit.PIGLIN_BEAST,
                        EntityInit.SOUL_REAPER,
                        EntityInit.WITHER_PUPPET,
                        EntityInit.NECROMANCER,
                        EntityInit.DESERT_RHINO,
                        EntityInit.SHAMAN,
                        EntityInit.ENDERWARTHOG
                );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(SW_MONSTER)
                .addOptional(
                        new Identifier("soulsweapons:warmth_entity"),
                        new Identifier("soulsweapons:big_chungus"),
                        new Identifier("soulsweapons:dark_sorcerer"),
                        new Identifier("soulsweapons:soul_reaper_ghost"),
                        new Identifier("soulsweapons:forlorn"),
                        new Identifier("soulsweapons:evil_forlorn"),
                        new Identifier("soulsweapons:soulmass"),
                        new Identifier("soulsweapons:frost_giant"),
                        new Identifier("soulsweapons:rime_spectre")
                );

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(LHTags.WHITELIST)
                .addTag(
                        ADVZ_MONSTER,
                        SW_MONSTER
                );

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(LHTags.RANGED_ENEMY)
                .addTag(SKELETONS);

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(TagUtil.entityTypeTag(L2Hostility.id("shulker_blacklist")))
                .addOptional(
                        new Identifier("soulsweapons:forlorn"),
                        new Identifier("soulsweapons:evil_forlorn")
                );

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(TagUtil.entityTypeTag(L2Hostility.id("dispell_blacklist")))
                .add(EntityType.WARDEN)
                .add(DDEntities.STALKER)
                .addTag(SCULK);

        SpellDimension.ENCHANTMENT_TAGS.getOrCreateContainer(LOOTABLE)
                .add(
                        LHEnchantments.SHULKER_ARMOR,
                        LHEnchantments.STABLE_BODY,
                        LHEnchantments.DURABLE_ARMOR,
                        LHEnchantments.LIFE_SYNC,
                        LHEnchantments.LIFE_MENDING,
                        LHEnchantments.SAFEGUARD,
                        LHEnchantments.HARDENED,
                        LHEnchantments.DAMPENED,
                        LHEnchantments.WIND_SWEEP,
                        LHEnchantments.FLAME_BLADE,
                        LHEnchantments.ICE_BLADE,
                        LHEnchantments.SHARP_BLADE,
                        LHEnchantments.CURSE_BLADE,
                        LHEnchantments.ICE_THORN,
                        LHEnchantments.FLAME_THORN
                );

        SpellDimension.DAMAGE_TYPE_TAGS.getOrCreateContainer(LHTags.MAGIC)
                .addOptional(MythicUpgradesDamageTypes.DEFLECTING_DAMAGE_TYPE);
    }
}
