package karashokleo.spell_dimension.init;

import artifacts.registry.ModItems;
import com.kyanite.deeperdarker.content.DDEntities;
import com.spellbladenext.Spellblades;
import com.teamremastered.endrem.registry.ERItems;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.api.quest.Quest;
import karashokleo.spell_dimension.content.enchantment.TraitEffectImmunityEnchantment;
import karashokleo.spell_dimension.util.TagUtil;
import net.adventurez.init.EntityInit;
import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
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
import net.minecraft.world.biome.Biome;
import net.runes.RunesMod;
import net.runes.api.RuneItems;
import net.spell_engine.internals.SpellInfinityEnchantment;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.trique.mythicupgrades.MythicUpgradesDamageTypes;
import net.wizards.WizardsMod;
import nourl.mythicmetals.item.MythicItems;
import nourl.mythicmetals.item.tools.MythicTools;
import tocraft.walkers.Walkers;

import java.util.List;
import java.util.stream.Stream;

public class AllTags
{
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

    public static final List<TagKey<Item>> DIFFICULTY_ALLOW_USE_ITEM = List.of(
        TagUtil.itemTag("difficulty_allow/use_item/0"),
        TagUtil.itemTag("difficulty_allow/use_item/1"),
        TagUtil.itemTag("difficulty_allow/use_item/2")
    );
    // TODO: items with this tag cannot be passed to onUse method of blocks, how to fix?
    public static final List<TagKey<Item>> DIFFICULTY_ALLOW_USE_BLOCK = List.of(
        TagUtil.itemTag("difficulty_allow/use_block/0"),
        TagUtil.itemTag("difficulty_allow/use_block/1"),
        TagUtil.itemTag("difficulty_allow/use_block/2")
    );
    public static final List<TagKey<Item>> DIFFICULTY_ALLOW_USE_TRINKET = List.of(
        TagUtil.itemTag("difficulty_allow/use_trinket/0"),
        TagUtil.itemTag("difficulty_allow/use_trinket/1"),
        TagUtil.itemTag("difficulty_allow/use_trinket/2")
    );

    public static final List<TagKey<Item>> GEARS = List.of(
        TagUtil.itemTag("common/gear"),
        TagUtil.itemTag("uncommon/gear"),
        TagUtil.itemTag("rare/gear"),
        TagUtil.itemTag("epic/gear"),
        TagUtil.itemTag("legendary/gear")
    );

    public static final TagKey<Item> RUNE = TagUtil.itemTag("rune");
    public static final TagKey<Item> HEART_FOOD = TagUtil.itemTag("heart_food");
    public static final TagKey<Item> ENDGAME_TRINKETS = TagUtil.itemTag("endgame_trinkets");

    public static final TagKey<Item> SPELL_TRAIT_ITEM = TagUtil.itemTag("spell_trait_item");
    public static final TagKey<Item> SPIRIT_TOME_SHOP_BLACKLIST = TagUtil.itemTag("spirit_tome_shop_blacklist");

    // Equipment Standard
    public static final TagKey<Item> ARMOR = TagUtil.itemTag(new Identifier("equipment_standard:armor"));
    public static final TagKey<Item> MELEE_WEAPONS = TagUtil.itemTag(new Identifier("equipment_standard:melee_weapons"));
    public static final TagKey<Item> MAGIC = TagUtil.itemTag(new Identifier("equipment_standard:magic"));
    public static final TagKey<Item> MAGIC_WEAPON = TagUtil.itemTag(new Identifier("equipment_standard:magic/weapons"));
    public static final TagKey<Item> MAGIC_ARMOR = TagUtil.itemTag(new Identifier("equipment_standard:magic/armor"));

    public static final TagKey<Item> BANNED = TagUtil.itemTag("banned");
    public static final TagKey<Item> DUNGEON_BANNED = TagUtil.itemTag("dungeon_banned");
    public static final TagKey<Item> REFILL_BANNED = TagUtil.itemTag("refill_banned");

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

    public static final TagKey<Item> HAT_SLOT = TagUtil.itemTag(new Identifier("trinkets", "head/hat"));
    public static final TagKey<Item> NECKLACE_SLOT = TagUtil.itemTag(new Identifier("trinkets", "chest/necklace"));
    public static final TagKey<Item> CAPE_SLOT = TagUtil.itemTag(new Identifier("trinkets", "chest/cape"));
    public static final TagKey<Item> BELT_SLOT = TagUtil.itemTag(new Identifier("trinkets", "legs/belt"));
    public static final TagKey<Item> BREASTPLATE_SLOT = TagUtil.itemTag(new Identifier("trinkets", "chest/breastplate"));
    public static final TagKey<Item> SECONDARY_SCHOOL_SLOT = TagUtil.itemTag(new Identifier("trinkets", "chest/secondary_school"));
    public static final TagKey<Item> SPELL_CONTAINER_SLOT = TagUtil.itemTag(new Identifier("trinkets", "legs/spell_container"));

    public static final TagKey<Item> HOSTILITY_CURSE = TagUtil.itemTag("hostility_curse");
    public static final TagKey<Item> HOSTILITY_RING = TagUtil.itemTag("hostility_ring");

    // Wizards
    public static final TagKey<Item> WANDS = TagUtil.itemTag(new Identifier(WizardsMod.ID, "wands"));
    public static final TagKey<Item> STAVES = TagUtil.itemTag(new Identifier(WizardsMod.ID, "staves"));
    public static final TagKey<Item> WIZARD_ROBES = TagUtil.itemTag(new Identifier(WizardsMod.ID, "wizard_robes"));
    public static final TagKey<Item> WIZARD_RUNES = TagUtil.itemTag(new Identifier(WizardsMod.ID, "wizard_runes"));
    // RPG Series
    public static final TagKey<Item> TIER_2_WEAPONS = TagUtil.itemTag(new Identifier("rpg_series:tier_2_weapons"));
    public static final TagKey<Item> TIER_2_ARMORS = TagUtil.itemTag(new Identifier("rpg_series:tier_2_armors"));
    public static final TagKey<Item> TIER_3_WEAPONS = TagUtil.itemTag(new Identifier("rpg_series:tier_3_weapons"));
    public static final TagKey<Item> TIER_3_ARMORS = TagUtil.itemTag(new Identifier("rpg_series:tier_3_armors"));
    // End Remastered
    public static final TagKey<Item> ENDREM_EYES = TagUtil.itemTag("endrem_eyes");

    public static final TagKey<EntityType<?>> ZOMBIES = TagUtil.entityTypeTag(new Identifier("zombies"));
    public static final TagKey<EntityType<?>> SKELETONS = EntityTypeTags.SKELETONS;
    public static final TagKey<EntityType<?>> RAIDERS_VANILLA = TagUtil.entityTypeTag("raiders_vanilla");
    public static final TagKey<EntityType<?>> RAIDERS_INVADE = TagUtil.entityTypeTag("raiders_invade");
    public static final TagKey<EntityType<?>> NETHER = TagUtil.entityTypeTag("nether");
    public static final TagKey<EntityType<?>> SCULK = TagUtil.entityTypeTag("sculk");
    public static final TagKey<EntityType<?>> ADVZ_MONSTER = TagUtil.entityTypeTag("advz_monster");
    public static final TagKey<EntityType<?>> SW_MONSTER = TagUtil.entityTypeTag("sw_monster");
    public static final TagKey<EntityType<?>> FLYING = TagUtil.entityTypeTag(Walkers.id("flying"));
    public static final TagKey<EntityType<?>> NO_CLIPS = TagUtil.entityTypeTag(Walkers.id("fall_through_blocks"));
    public static final TagKey<EntityType<?>> INVALID_MOBS = TagUtil.entityTypeTag("invalid_mobs");

    public static final TagKey<Enchantment> LOOTABLE = TagUtil.enchantmentTag("lootable");
    public static final TagKey<Enchantment> TRADABLE = TagUtil.enchantmentTag("tradable");
    public static final TagKey<Enchantment> ENCHANTABLE = TagUtil.enchantmentTag("enchantable");

    public static final TagKey<Fluid> CONSCIOUSNESS = TagUtil.fluidTag("consciousness");

    public static final TagKey<Quest> MAIN = TagUtil.questTag("main");
    public static final TagKey<Quest> BRANCH = TagUtil.questTag("branch");
    public static final TagKey<Quest> BEGINNING = TagUtil.questTag("beginning");
    public static final TagKey<Quest> END = TagUtil.questTag("end");
    public static final TagKey<Quest> CHALLENGE = TagUtil.questTag("challenge");
    public static final TagKey<Quest> SKIPPABLE = TagUtil.questTag("skippable");

    public static final TagKey<Biome> PRISMACHASM = TagUtil.biomeTag("prismachasm");

    public static final TagKey<MobTrait> ESSENCE_LOOT_TRAIT_BLACKLIST = TagUtil.traitTag("essence_loot_blacklist");

    public static TagKey<Item> getRuneTag(SpellSchool school, String suffix)
    {
        return TagUtil.itemTag(new Identifier(RunesMod.ID, "rune_crafting/reagent/" + school.id.getPath() + suffix));
    }

    public static void register()
    {
        RuneItems.entries.stream().map(RuneItems.Entry::id)
            .forEach(id ->
            {
                SpellDimension.ITEM_TAGS.getOrCreateContainer(RUNE).add(id);
                SpellDimension.ITEM_TAGS.getOrCreateContainer(WIZARD_RUNES).add(id);
            });

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
        {
            essenceAllContainer.addTag(key);
        }

        TagGenerator.Container<Item> bookAllContainer = SpellDimension.ITEM_TAGS.getOrCreateContainer(BOOK_ALL);
        for (TagKey<Item> key : BOOK)
        {
            bookAllContainer.addTag(key);
        }

        SpellDimension.ITEM_TAGS.getOrCreateContainer(DIFFICULTY_ALLOW_USE_ITEM.get(0))
            .add(
                ConsumableItems.HOSTILITY_ORB,
                ConsumableItems.BOTTLE_SANITY
            );
        SpellDimension.ITEM_TAGS.getOrCreateContainer(DIFFICULTY_ALLOW_USE_BLOCK.get(2))
            .addOptional(
                new Identifier("kibe:cursed_seeds")
            );
        SpellDimension.ITEM_TAGS.getOrCreateContainer(DIFFICULTY_ALLOW_USE_TRINKET.get(2))
            .add(
                TrinketItems.CURSE_PRIDE,
                TrinketItems.CURSE_WRATH
            );

        SpellDimension.ITEM_TAGS.getOrCreateContainer(BANNED)
            .add(Spellblades.OFFERING)
            .add(Spellblades.HEXBLADEITEM)
            .add(Spellblades.PRISMATIC);

        SpellDimension.ITEM_TAGS.getOrCreateContainer(DUNGEON_BANNED)
            .add(MythicTools.LEGENDARY_BANGLUM.getPickaxe())
            .addOptionalTag(new Identifier("constructionwand:wands"));

        SpellDimension.ITEM_TAGS.getOrCreateContainer(REFILL_BANNED)
            .add(Items.TOTEM_OF_UNDYING)
            .add(ModItems.CHORUS_TOTEM.get())
            .addOptionalTag(new Identifier("fwaystones:void_totem"));

        SpellDimension.ITEM_TAGS.getOrCreateContainer(getRuneTag(SpellSchools.LIGHTNING, "_small"))
            .add(MythicItems.Copper.COPPER.getNugget());
        SpellDimension.ITEM_TAGS.getOrCreateContainer(getRuneTag(SpellSchools.LIGHTNING, "_medium"))
            .add(Items.COPPER_INGOT);

        SpellDimension.ITEM_TAGS.getOrCreateContainer(getRuneTag(SpellSchools.SOUL, "_small"))
            .add(MythicItems.QUADRILLUM.getNugget())
            .add(Items.SOUL_SOIL)
            .add(Items.SOUL_SAND);
        SpellDimension.ITEM_TAGS.getOrCreateContainer(getRuneTag(SpellSchools.SOUL, "_medium"))
            .add(MythicItems.QUADRILLUM.getIngot());

        SpellDimension.ITEM_TAGS.getOrCreateContainer(ENDREM_EYES)
            .add(ERItems.BLACK_EYE)
            .add(ERItems.COLD_EYE)
            .add(ERItems.CORRUPTED_EYE)
            .add(ERItems.LOST_EYE)
            .add(ERItems.NETHER_EYE)
            .add(ERItems.OLD_EYE)
            .add(ERItems.ROGUE_EYE)
            .add(ERItems.CURSED_EYE)
            .add(ERItems.EVIL_EYE)
            .add(ERItems.GUARDIAN_EYE)
            .add(ERItems.MAGICAL_EYE)
            .add(ERItems.WITHER_EYE)
            .add(ERItems.WITCH_EYE)
            .add(ERItems.UNDEAD_EYE)
            .add(ERItems.EXOTIC_EYE)
            .add(ERItems.CRYPTIC_EYE);

        SpellDimension.ITEM_TAGS.getOrCreateContainer(SPIRIT_TOME_SHOP_BLACKLIST)
            .add(
                Items.AIR,
                Items.BARRIER,
                Items.DEBUG_STICK,
                Items.STRUCTURE_BLOCK,
                Items.STRUCTURE_VOID,
                Items.JIGSAW,
                Items.LIGHT,
                Items.COMMAND_BLOCK,
                Items.REPEATING_COMMAND_BLOCK,
                Items.CHAIN_COMMAND_BLOCK,
                Items.COMMAND_BLOCK_MINECART,
                AllItems.DEBUG_STAFF,
                AllItems.CREATIVE_SOUL_CONTAINER,
//                MiscItems.AI_CONFIG_WAND,
//                MiscItems.EQUIPMENT_WAND,
//                MiscItems.TARGET_SELECT_WAND,
//                MiscItems.TRAIT_ADDER_WAND,
//                LHBlocks.SPAWNER.item(),
                ComplementItems.SPACE_SHARD
            )
            .addOptional(
                new Identifier("moonlight:placeable_item"),
                new Identifier("soulsweapons:test_item"),
                new Identifier("fwaystones:waystone_debugger"),
                new Identifier("spellbladenext:debug"),
                new Identifier("dungeonz:dungeon_compass"),
                new Identifier("sophisticatedbackpacks:infinity_upgrade"),
                new Identifier("sophisticatedbackpacks:survival_infinity_upgrade"),
                new Identifier("sophisticatedstorage:debug_tool")
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

        SpellDimension.FLUID_TAGS.getOrCreateContainer(CONSCIOUSNESS)
            .add(
                AllBlocks.STILL_CONSCIOUSNESS,
                AllBlocks.FLOWING_CONSCIOUSNESS
            );

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(LHTags.WHITELIST)
            .add(
                EntityInit.THE_EYE,
                Spellblades.ARCHMAGUS,
                Spellblades.REAVER
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

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(FLYING)
            .addOptional(
                new Identifier("soulsweapons:night_shade"),
                new Identifier("soulsweapons:day_stalker"),
                new Identifier("soulsweapons:night_prowler"),
                new Identifier("aquamirae:captain_cornelia"),
                new Identifier("bosses_of_mass_destruction:lich"),
                new Identifier("bosses_of_mass_destruction:gauntlet"),
                new Identifier("adventurez:the_eye"),
                new Identifier("adventurez:void_shadow"),
                new Identifier("adventurez:void_shade")
            );
        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(NO_CLIPS)
            .addOptional(
                new Identifier("soulsweapons:night_shade")
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

        TagGenerator.Container<Enchantment> noDispellContainer = SpellDimension.ENCHANTMENT_TAGS.getOrCreateContainer(LHTags.NO_DISPELL);
        noDispellContainer.add(
            LHEnchantments.ETERNAL,
            LHEnchantments.ENCH_PROJECTILE,
            LHEnchantments.ENCH_FIRE,
            LHEnchantments.ENCH_ENVIRONMENT,
            LHEnchantments.ENCH_EXPLOSION,
            LHEnchantments.ENCH_MAGIC,
            LHEnchantments.ENCH_INVINCIBLE,
            LHEnchantments.ENCH_MATES
        );
        for (TraitEffectImmunityEnchantment enchantment : AllEnchantments.EFFECT_IMMUNITY)
        {
            noDispellContainer.add(enchantment);
        }

        SpellDimension.ENTITY_TYPE_TAGS.getOrCreateContainer(INVALID_MOBS)
            .add(
                Dummmmmmy.TARGET_DUMMY.get(),
                EntityType.ARMOR_STAND
            );

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

        SpellDimension.BIOME_TAGS.getOrCreateContainer(PRISMACHASM)
            .addOptional(new Identifier("regions_unexplored:prismachasm"));

        SpellDimension.TRAIT_TAGS.getOrCreateContainer(ESSENCE_LOOT_TRAIT_BLACKLIST)
            .add(
                LHTraits.UNDYING,
                LHTraits.DISPELL,
                LHTraits.ADAPTIVE
            );
    }
}
