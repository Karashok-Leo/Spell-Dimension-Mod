package karashokleo.spell_dimension.init;

import com.kyanite.deeperdarker.content.DDEntities;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.util.TagUtil;
import net.adventurez.init.EntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.runes.api.RuneItems;
import net.spell_engine.internals.SpellInfinityEnchantment;

import java.util.List;
import java.util.stream.Stream;

public class AllTags
{
    public static final List<TagKey<Item>> MATERIAL = List.of(
            TagUtil.itemTag("common/material"),
            TagUtil.itemTag("uncommon/material"),
            TagUtil.itemTag("rare/material"),
            TagUtil.itemTag("epic/material"),
            TagUtil.itemTag("legendary/material")
    );

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

    public static final TagKey<Item> MELEE_WEAPONS = TagUtil.itemTag(new Identifier("equipment_standard:melee_weapons"));
    public static final TagKey<Item> ARMOR = TagUtil.itemTag(new Identifier("equipment_standard:armor"));

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

    public static final TagKey<Item> SHELL_HORN_REQUIREMENT = TagUtil.itemTag("shell_horn_requirement");

    public static final TagKey<Item> VICTUS_ITEM = TagUtil.itemTag("victus_item");

    public static final TagKey<Block> LOCATE_TARGET = TagUtil.blockTag("locate_target");

    public static final TagKey<Item> BACK = TagUtil.itemTag(new Identifier("trinkets", "chest/back"));
    public static final TagKey<Item> CAPE = TagUtil.itemTag(new Identifier("trinkets", "chest/cape"));

    public static final TagKey<EntityType<?>> ZOMBIES = TagUtil.entityTypeTag(new Identifier("zombies"));
    public static final TagKey<EntityType<?>> SKELETONS = EntityTypeTags.SKELETONS;
    public static final TagKey<EntityType<?>> RAIDERS_VANILLA = TagUtil.entityTypeTag("raiders_vanilla");
    public static final TagKey<EntityType<?>> RAIDERS_INVADE = TagUtil.entityTypeTag("raiders_invade");
    public static final TagKey<EntityType<?>> NETHER = TagUtil.entityTypeTag("nether");
    public static final TagKey<EntityType<?>> SCULK = TagUtil.entityTypeTag("sculk");
    public static final TagKey<EntityType<?>> ADVZ_MONSTER = TagUtil.entityTypeTag("advz_monster");
    public static final TagKey<EntityType<?>> SW_MONSTER = TagUtil.entityTypeTag("sw_monster");

    public static void register()
    {
        TagGenerator<Item> generator = SpellDimension.ITEM_TAGS;

        RuneItems.entries.stream().map(RuneItems.Entry::id)
                .forEach(id -> generator.getOrCreateContainer(RUNE).add(id));

        generator.getOrCreateContainer(HEART_FOOD)
                .add(Items.ENCHANTED_GOLDEN_APPLE)
                .addOptional(
                        new Identifier("l2hostility:life_essence"),
                        new Identifier("midashunger:enchanted_golden_carrot")
                );

        generator.getOrCreateContainer(REFORGE_CORE_TAGS.get(0), true)
                .add(AllItems.SPAWNER_SOUL);
        generator.getOrCreateContainer(REFORGE_CORE_TAGS.get(1), true)
                .addOptional(new Identifier("l2hostility:chaos_ingot"));
        generator.getOrCreateContainer(REFORGE_CORE_TAGS.get(2), true)
                .addOptional(new Identifier("l2hostility:miracle_ingot"));

        TagGenerator.Container<Item> essenceAllContainer = generator.getOrCreateContainer(ESSENCE_ALL);
        for (TagKey<Item> key : ESSENCE)
            essenceAllContainer.addTag(key);

        TagGenerator.Container<Item> bookAllContainer = generator.getOrCreateContainer(BOOK_ALL);
        for (TagKey<Item> key : BOOK)
            bookAllContainer.addTag(key);

        generator.getOrCreateContainer(VICTUS_ITEM)
                .addOptional(
                        new Identifier("victus:grilled_heart_aspect"),
                        new Identifier("victus:bundle_heart_aspect"),
                        new Identifier("victus:creeper_heart_aspect"),
                        new Identifier("victus:diamond_heart_aspect"),
                        new Identifier("victus:light_heart_aspect"),
                        new Identifier("victus:ocean_heart_aspect"),
                        new Identifier("victus:totem_heart_aspect"),
                        new Identifier("victus:potion_heart_aspect"),
                        new Identifier("victus:archery_heart_aspect"),
                        new Identifier("victus:blazing_heart_aspect"),
                        new Identifier("victus:draconic_heart_aspect"),
                        new Identifier("victus:emerald_heart_aspect"),
                        new Identifier("victus:evoking_heart_aspect"),
                        new Identifier("victus:golden_heart_aspect"),
                        new Identifier("victus:icy_heart_aspect"),
                        new Identifier("victus:iron_heart_aspect"),
                        new Identifier("victus:lapis_heart_aspect"),
                        new Identifier("victus:sweet_heart_aspect"),
                        new Identifier("victus:cheese_heart_aspect")
                );

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
                        EntityInit.ENDERWARTHOG,
                        EntityInit.VOID_FRAGMENT
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
    }
}
