package karashokleo.spell_dimension.data.generic.recipe;

import artifacts.registry.ModItems;
import com.kyanite.deeperdarker.content.DDItems;
import com.lion.graveyard.init.TGItems;
import com.spellbladenext.Spellblades;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.locate.LocateBiomeRecipeJsonProvider;
import karashokleo.spell_dimension.content.recipe.locate.LocateStructureRecipeJsonProvider;
import karashokleo.spell_dimension.init.AllTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.runes.api.RuneItems;
import nourl.mythicmetals.item.MythicItems;

import java.util.function.Consumer;

public class SDLocateRecipes
{
    // Structure ID
    public static final Identifier DARK_DUNGEON = new Identifier("dungeonz:dark_dungeon_structure");
    public static final Identifier DESERT_DUNGEON = new Identifier("dungeonz:desert_dungeon_structure");
    public static final Identifier SHIP = new Identifier("aquamirae:ship");
    public static final Identifier SHELTER = new Identifier("aquamirae:shelter");
    public static final Identifier CHAMPIONS_GRAVES = new Identifier("soulsweapons:champions_graves");
    public static final Identifier CATHEDRAL_OF_RESURRECTION = new Identifier("soulsweapons:cathedral_of_resurrection");
    public static final Identifier DECAYING_KINGDOM = new Identifier("soulsweapons:decaying_kingdom");
    public static final Identifier MONUMENT = new Identifier("betteroceanmonuments:ocean_monument");
    public static final Identifier FORTRESS = new Identifier("betterfortresses:fortress");
    public static final Identifier ANCIENT_TEMPLE = new Identifier("deeperdarker:ancient_temple");
    public static final Identifier GRAVE_RUINS = new Identifier("graveyard:ruins");
    public static final Identifier LICH_PRISON = new Identifier("graveyard:lich_prison");
    public static final Identifier GAUNTLET_ARENA = new Identifier("bosses_of_mass_destruction:gauntlet_arena");
    public static final Identifier OBSIDILITH_ARENA = new Identifier("bosses_of_mass_destruction:obsidilith_arena");
    public static final Identifier FIRECALLER_HUT = new Identifier("illagerinvasion:firecaller_hut");
    public static final Identifier SORCERER_HUT = new Identifier("illagerinvasion:sorcerer_hut");
    public static final Identifier ILLUSIONER_TOWER = new Identifier("illagerinvasion:illusioner_tower");
    public static final Identifier ILLAGER_FORT = new Identifier("illagerinvasion:illager_fort");
    public static final Identifier LABYRINTH = new Identifier("illagerinvasion:labyrinth");
    public static final Identifier DESERT_TEMPLE = new Identifier("betterdeserttemples:desert_temple");
    public static final Identifier STRONGHOLD = new Identifier("betterstrongholds:stronghold");
    public static final Identifier ABANDONED_TEMPLE = new Identifier("dungeons_arise:abandoned_temple");
    public static final Identifier AVIARY = new Identifier("dungeons_arise:aviary");
    public static final Identifier BANDIT_TOWERS = new Identifier("dungeons_arise:bandit_towers");
    public static final Identifier BANDIT_VILLAGE = new Identifier("dungeons_arise:bandit_village");
    public static final Identifier BATHHOUSE = new Identifier("dungeons_arise:bathhouse");
    public static final Identifier CERYNEIAN_HIND = new Identifier("dungeons_arise:ceryneian_hind");
    public static final Identifier COLISEUM = new Identifier("dungeons_arise:coliseum");
    public static final Identifier FISHING_HUT = new Identifier("dungeons_arise:fishing_hut");
    public static final Identifier FOUNDRY = new Identifier("dungeons_arise:foundry");
    public static final Identifier GREENWOOD_PUB = new Identifier("dungeons_arise:greenwood_pub");
    public static final Identifier HEAVENLY_CHALLENGER = new Identifier("dungeons_arise:heavenly_challenger");
    public static final Identifier HEAVENLY_CONQUEROR = new Identifier("dungeons_arise:heavenly_conqueror");
    public static final Identifier HEAVENLY_RIDER = new Identifier("dungeons_arise:heavenly_rider");
    public static final Identifier ILLAGER_CAMPSITE = new Identifier("dungeons_arise:illager_campsite");
    public static final Identifier ILLAGER_CORSAIR = new Identifier("dungeons_arise:illager_corsair");
    public static final Identifier ILLAGER_FORT_1 = new Identifier("dungeons_arise:illager_fort");
    public static final Identifier ILLAGER_GALLEY = new Identifier("dungeons_arise:illager_galley");
    public static final Identifier ILLAGER_WINDMILL = new Identifier("dungeons_arise:illager_windmill");
    public static final Identifier INFESTED_TEMPLE = new Identifier("dungeons_arise:infested_temple");
    public static final Identifier JUNGLE_TREE_HOUSE = new Identifier("dungeons_arise:jungle_tree_house");
    public static final Identifier KEEP_KAYRA = new Identifier("dungeons_arise:keep_kayra");
    public static final Identifier LIGHTHOUSE = new Identifier("dungeons_arise:lighthouse");
    public static final Identifier MERCHANT_CAMPSITE = new Identifier("dungeons_arise:merchant_campsite");
    public static final Identifier MINING_SYSTEM = new Identifier("dungeons_arise:mining_system");
    public static final Identifier MONASTERY = new Identifier("dungeons_arise:monastery");
    public static final Identifier MUSHROOM_HOUSE = new Identifier("dungeons_arise:mushroom_house");
    public static final Identifier MUSHROOM_MINES = new Identifier("dungeons_arise:mushroom_mines");
    public static final Identifier MUSHROOM_VILLAGE = new Identifier("dungeons_arise:mushroom_village");
    public static final Identifier PLAGUE_ASYLUM = new Identifier("dungeons_arise:plague_asylum");
    public static final Identifier SCORCHED_MINES = new Identifier("dungeons_arise:scorched_mines");
    public static final Identifier SHIRAZ_PALACE = new Identifier("dungeons_arise:shiraz_palace");
    public static final Identifier SMALL_BLIMP = new Identifier("dungeons_arise:small_blimp");
    public static final Identifier THORNBORN_TOWERS = new Identifier("dungeons_arise:thornborn_towers");
    public static final Identifier TYPHON = new Identifier("dungeons_arise:typhon");
    public static final Identifier UNDEAD_PIRATE_SHIP = new Identifier("dungeons_arise:undead_pirate_ship");
    public static final Identifier CORSAIR_CORVETTE = new Identifier("dungeons_arise_seven_seas:corsair_corvette");
    public static final Identifier PIRATE_JUNK = new Identifier("dungeons_arise_seven_seas:pirate_junk");
    public static final Identifier SMALL_YACHT = new Identifier("dungeons_arise_seven_seas:small_yacht");
    public static final Identifier UNICORN_GALLEON = new Identifier("dungeons_arise_seven_seas:unicorn_galleon");
    public static final Identifier VICTORY_FRIGATE = new Identifier("dungeons_arise_seven_seas:victory_frigate");
    public static final Identifier CASTLE_ILLAGER_DESERT = new Identifier("castle_dungeons:castle_illager_desert");
    public static final Identifier CASTLE_ILLAGER_PLAINS = new Identifier("castle_dungeons:castle_illager_plains");

    // World ID
    public static final Identifier OTHERSIDE = new Identifier("deeperdarker:otherside");

    public static void add(Consumer<RecipeJsonProvider> exporter)
    {
        // Required Items
        Item bossCompass = Registries.ITEM.get(new Identifier("soulsweapons:boss_compass"));
        Item lostSoul = Registries.ITEM.get(new Identifier("soulsweapons:lost_soul"));

        // Structures
        addStructure(exporter, StructureKeys.BASTION_REMNANT, World.NETHER, RuneItems.get(RuneItems.RuneType.HEALING));
        addStructure(exporter, StructureKeys.END_CITY, World.END, Items.DRAGON_BREATH);
        addStructure(exporter, StructureKeys.ANCIENT_CITY, World.OVERWORLD, RuneItems.get(RuneItems.RuneType.ARCANE));
        addStructure(exporter, StructureKeys.MANSION, World.OVERWORLD, Items.EMERALD_BLOCK);
        addStructure(exporter, MONUMENT, World.OVERWORLD, Items.SEA_LANTERN);
        addStructure(exporter, DARK_DUNGEON, World.OVERWORLD, ComplementItems.CURSED_DROPLET);
        addStructure(exporter, DESERT_DUNGEON, World.OVERWORLD, ComplementItems.SUN_MEMBRANE);
        addStructure(exporter, SHIP, World.OVERWORLD, RuneItems.get(RuneItems.RuneType.FROST));
        addStructure(exporter, SHELTER, World.OVERWORLD, Spellblades.RUNEFROST);
        addStructure(exporter, CATHEDRAL_OF_RESURRECTION, World.OVERWORLD, lostSoul);
        addStructure(exporter, CHAMPIONS_GRAVES, World.OVERWORLD, bossCompass);
        addStructure(exporter, DECAYING_KINGDOM, World.NETHER, bossCompass);
        addStructure(exporter, FORTRESS, World.NETHER, RuneItems.get(RuneItems.RuneType.FIRE));
        addStructure(exporter, GAUNTLET_ARENA, World.NETHER, ModItems.FIRE_GAUNTLET.get());
        addStructure(exporter, OBSIDILITH_ARENA, World.END, Items.CRYING_OBSIDIAN);
        addStructure(exporter, FIRECALLER_HUT, World.OVERWORLD, Items.RED_SANDSTONE);
        addStructure(exporter, SORCERER_HUT, World.OVERWORLD, Items.PURPLE_WOOL);
        addStructure(exporter, ILLUSIONER_TOWER, World.OVERWORLD, Items.COBBLESTONE_WALL);
        addStructure(exporter, ILLAGER_FORT, World.OVERWORLD, Items.SPRUCE_FENCE);
        addStructure(exporter, LABYRINTH, World.OVERWORLD, Items.POLISHED_ANDESITE);
        addStructure(exporter, ANCIENT_TEMPLE, OTHERSIDE, DDItems.HEART_OF_THE_DEEP);
        addStructure(exporter, GRAVE_RUINS, World.OVERWORLD, Items.BONE_BLOCK);
        addStructure(exporter, LICH_PRISON, World.OVERWORLD, TGItems.CORRUPTION.get());
        addStructure(exporter, DESERT_TEMPLE, World.OVERWORLD, Items.SANDSTONE);
        addStructure(exporter, STRONGHOLD, World.OVERWORLD, Items.ENDER_EYE);
        addStructure(exporter, ABANDONED_TEMPLE, World.OVERWORLD, Items.JUNGLE_PLANKS);
        addStructure(exporter, AVIARY, World.END, Items.CHORUS_FRUIT);
        addStructure(exporter, BANDIT_TOWERS, World.OVERWORLD, Items.CYAN_WOOL);
        addStructure(exporter, BANDIT_VILLAGE, World.OVERWORLD, Items.ORANGE_WOOL);
        addStructure(exporter, BATHHOUSE, World.OVERWORLD, Items.MANGROVE_PLANKS);
        addStructure(exporter, CERYNEIAN_HIND, World.OVERWORLD, Items.SMOOTH_QUARTZ);
        addStructure(exporter, COLISEUM, World.OVERWORLD, Items.IRON_BARS);
        addStructure(exporter, FISHING_HUT, World.OVERWORLD, Items.BARREL);
        addStructure(exporter, FOUNDRY, World.OVERWORLD, Items.RED_TERRACOTTA);
        addStructure(exporter, GREENWOOD_PUB, World.OVERWORLD, Items.BIRCH_LEAVES);
        addStructureWithSuffix(exporter, HEAVENLY_CHALLENGER, World.OVERWORLD, Items.SHROOMLIGHT, "_overworld");
        addStructureWithSuffix(exporter, HEAVENLY_CONQUEROR, World.OVERWORLD, Items.END_STONE_BRICKS, "_overworld");
        addStructureWithSuffix(exporter, HEAVENLY_RIDER, World.OVERWORLD, Items.ACACIA_WOOD, "_overworld");
        addStructureWithSuffix(exporter, HEAVENLY_CHALLENGER, World.END, Items.SHROOMLIGHT, "_end");
        addStructureWithSuffix(exporter, HEAVENLY_CONQUEROR, World.END, Items.END_STONE_BRICKS, "_end");
        addStructureWithSuffix(exporter, HEAVENLY_RIDER, World.END, Items.ACACIA_WOOD, "_end");
        addStructure(exporter, ILLAGER_CAMPSITE, World.OVERWORLD, Items.BLUE_TERRACOTTA);
        addStructure(exporter, ILLAGER_CORSAIR, World.OVERWORLD, Items.WHITE_WOOL);
        addStructure(exporter, ILLAGER_FORT_1, World.OVERWORLD, Items.MAGENTA_TERRACOTTA);
        addStructure(exporter, ILLAGER_GALLEY, World.OVERWORLD, Items.MELON);
        addStructure(exporter, ILLAGER_WINDMILL, World.OVERWORLD, Items.WHEAT_SEEDS);
        addStructure(exporter, INFESTED_TEMPLE, World.OVERWORLD, Items.YELLOW_TERRACOTTA);
        addStructure(exporter, JUNGLE_TREE_HOUSE, World.OVERWORLD, Items.JUNGLE_LEAVES);
        addStructure(exporter, KEEP_KAYRA, World.OVERWORLD, Items.MUD_BRICKS);
        addStructure(exporter, LIGHTHOUSE, World.OVERWORLD, Items.REDSTONE_LAMP);
        addStructure(exporter, MERCHANT_CAMPSITE, World.OVERWORLD, Items.BROWN_WOOL);
        addStructure(exporter, MINING_SYSTEM, World.OVERWORLD, Items.RAW_IRON_BLOCK);
        addStructure(exporter, MONASTERY, World.OVERWORLD, Items.LANTERN);
        addStructure(exporter, MUSHROOM_HOUSE, World.OVERWORLD, Items.PURPLE_TERRACOTTA);
        addStructure(exporter, MUSHROOM_MINES, World.OVERWORLD, Items.GOLD_ORE);
        addStructure(exporter, MUSHROOM_VILLAGE, World.OVERWORLD, Items.BEETROOT);
        addStructure(exporter, PLAGUE_ASYLUM, World.OVERWORLD, Items.COARSE_DIRT);
        addStructure(exporter, SCORCHED_MINES, World.OVERWORLD, Items.POLISHED_BLACKSTONE_BRICKS);
        addStructure(exporter, SHIRAZ_PALACE, World.OVERWORLD, Items.ROSE_BUSH);
        addStructure(exporter, SMALL_BLIMP, World.OVERWORLD, Items.LIGHT_GRAY_TERRACOTTA);
        addStructure(exporter, THORNBORN_TOWERS, World.OVERWORLD, Items.POLISHED_GRANITE);
        addStructure(exporter, TYPHON, World.OVERWORLD, Items.SEAGRASS);
        addStructure(exporter, UNDEAD_PIRATE_SHIP, World.OVERWORLD, Items.BLACK_WOOL);
        addStructure(exporter, CORSAIR_CORVETTE, World.OVERWORLD, Items.GRAY_WOOL);
        addStructure(exporter, PIRATE_JUNK, World.OVERWORLD, Items.IRON_TRAPDOOR);
        addStructure(exporter, SMALL_YACHT, World.OVERWORLD, Items.CHISELED_QUARTZ_BLOCK);
        addStructure(exporter, UNICORN_GALLEON, World.OVERWORLD, Items.STONE_BUTTON);
        addStructure(exporter, VICTORY_FRIGATE, World.OVERWORLD, Items.ACACIA_PLANKS);
        addStructure(exporter, CASTLE_ILLAGER_DESERT, World.OVERWORLD, Items.CHISELED_SANDSTONE);
        addStructure(exporter, CASTLE_ILLAGER_PLAINS, World.OVERWORLD, Items.CRACKED_STONE_BRICKS);

        // Biomes
        addBiome(exporter, ConventionalBiomeTags.TAIGA, World.OVERWORLD, Items.SWEET_BERRIES);
        addBiome(exporter, ConventionalBiomeTags.JUNGLE, World.OVERWORLD, Items.COCOA_BEANS);
        addBiome(exporter, ConventionalBiomeTags.SAVANNA, World.OVERWORLD, Items.ACACIA_LOG);
        addBiome(exporter, ConventionalBiomeTags.SWAMP, World.OVERWORLD, Items.VINE);
        addBiome(exporter, ConventionalBiomeTags.MUSHROOM, World.OVERWORLD, Items.BROWN_MUSHROOM);
        addBiome(exporter, ConventionalBiomeTags.BADLANDS, World.OVERWORLD, Items.CLAY_BALL);
        addBiome(exporter, ConventionalBiomeTags.SNOWY, World.OVERWORLD, Items.SNOWBALL);
        addBiome(exporter, ConventionalBiomeTags.ICY, World.OVERWORLD, Items.ICE);
        addBiome(exporter, ConventionalBiomeTags.OCEAN, World.OVERWORLD, Items.KELP);
        addBiome(exporter, ConventionalBiomeTags.DEEP_OCEAN, World.OVERWORLD, Items.PRISMARINE_SHARD);
        addBiome(exporter, ConventionalBiomeTags.DESERT, World.OVERWORLD, Items.SAND);
        addBiome(exporter, ConventionalBiomeTags.PLAINS, World.OVERWORLD, Items.GRASS_BLOCK);
        addBiome(exporter, BiomeTags.ANCIENT_CITY_HAS_STRUCTURE, World.OVERWORLD, Items.CANDLE);
        addBiome(exporter, ConventionalBiomeTags.NETHER_FORESTS, World.NETHER, Items.RED_MUSHROOM);
        addBiome(exporter, AllTags.PRISMACHASM, World.OVERWORLD, MythicItems.Mats.UNOBTAINIUM);
    }

    public static void addTranslations()
    {
        addStructureTranslations(StructureKeys.BASTION_REMNANT, "堡垒遗迹");
        addStructureTranslations(StructureKeys.END_CITY, "末地城");
        addStructureTranslations(StructureKeys.ANCIENT_CITY, "远古城市");
        addStructureTranslations(StructureKeys.MANSION, "林地府邸");
        addStructureTranslations(MONUMENT, "海底神殿");
        addStructureTranslations(DARK_DUNGEON, "暗黑地牢");
        addStructureTranslations(DESERT_DUNGEON, "沙漠地牢");
        addStructureTranslations(SHIP, "搁浅舰船");
        addStructureTranslations(SHELTER, "避难所");
        addStructureTranslations(CATHEDRAL_OF_RESURRECTION, "复苏教堂");
        addStructureTranslations(CHAMPIONS_GRAVES, "古英雄的陵墓");
        addStructureTranslations(DECAYING_KINGDOM, "腐朽王国");
        addStructureTranslations(FORTRESS, "下界要塞");
        addStructureTranslations(GAUNTLET_ARENA, "下界铁掌竞技场");
        addStructureTranslations(OBSIDILITH_ARENA, "黑曜巨石柱竞技场");
        addStructureTranslations(FIRECALLER_HUT, "唤火师小屋");
        addStructureTranslations(SORCERER_HUT, "不祥术士小屋");
        addStructureTranslations(ILLUSIONER_TOWER, "幻术师高塔");
        addStructureTranslations(ILLAGER_FORT, "灾厄堡垒");
        addStructureTranslations(LABYRINTH, "地下迷宫");
        addStructureTranslations(ANCIENT_TEMPLE, "远古神庙");
        addStructureTranslations(GRAVE_RUINS, "死者遗迹");
        addStructureTranslations(LICH_PRISON, "巫妖之狱");
        addStructureTranslations(DESERT_TEMPLE, "沙漠神殿");
        addStructureTranslations(STRONGHOLD, "末地要塞");
        addStructureTranslations(ABANDONED_TEMPLE, "废弃寺庙");
        addStructureTranslations(AVIARY, "幻梦塔楼");
        addStructureTranslations(BANDIT_TOWERS, "疆盗塔楼");
        addStructureTranslations(BANDIT_VILLAGE, "疆盗村落");
        addStructureTranslations(BATHHOUSE, "祸水澡堂");
        addStructureTranslations(CERYNEIAN_HIND, "刻律涅牝鹿的尸骨");
        addStructureTranslations(COLISEUM, "斗兽场");
        addStructureTranslations(FISHING_HUT, "渔屋");
        addStructureTranslations(FOUNDRY, "铸造厂");
        addStructureTranslations(GREENWOOD_PUB, "绿荫酒吧");
        addStructureTranslations(HEAVENLY_CHALLENGER, "天际挑战者");
        addStructureTranslations(HEAVENLY_CONQUEROR, "天堂征服者");
        addStructureTranslations(HEAVENLY_RIDER, "天际骑士团战舰");
        addStructureTranslations(ILLAGER_CAMPSITE, "灾厄营地");
        addStructureTranslations(ILLAGER_CORSAIR, "灾厄海盗船");
        addStructureTranslations(ILLAGER_FORT_1, "灾厄城堡");
        addStructureTranslations(ILLAGER_GALLEY, "灾厄桨帆船");
        addStructureTranslations(ILLAGER_WINDMILL, "灾厄风车");
        addStructureTranslations(INFESTED_TEMPLE, "蚀败寺庙");
        addStructureTranslations(JUNGLE_TREE_HOUSE, "丛林树屋");
        addStructureTranslations(KEEP_KAYRA, "创世神殿");
        addStructureTranslations(LIGHTHOUSE, "灯塔");
        addStructureTranslations(MERCHANT_CAMPSITE, "商人营地");
        addStructureTranslations(MINING_SYSTEM, "矿井系统");
        addStructureTranslations(MONASTERY, "修道院");
        addStructureTranslations(MUSHROOM_HOUSE, "蘑菇屋");
        addStructureTranslations(MUSHROOM_MINES, "蘑菇矿架");
        addStructureTranslations(MUSHROOM_VILLAGE, "蘑菇村落");
        addStructureTranslations(PLAGUE_ASYLUM, "瘟疫避难所");
        addStructureTranslations(SCORCHED_MINES, "焦黑矿井");
        addStructureTranslations(SHIRAZ_PALACE, "繁花之宫");
        addStructureTranslations(SMALL_BLIMP, "小气艇");
        addStructureTranslations(THORNBORN_TOWERS, "荆棘城堡");
        addStructureTranslations(TYPHON, "提丰遗骸");
        addStructureTranslations(UNDEAD_PIRATE_SHIP, "幽灵海盗船");
        addStructureTranslations(CORSAIR_CORVETTE, "海盗护卫舰");
        addStructureTranslations(PIRATE_JUNK, "海盗戎克船");
        addStructureTranslations(SMALL_YACHT, "小型游艇");
        addStructureTranslations(UNICORN_GALLEON, "独角扬帆号");
        addStructureTranslations(VICTORY_FRIGATE, "胜利护卫艇");
        addStructureTranslations(CASTLE_ILLAGER_DESERT, "灾厄城堡（沙漠）");
        addStructureTranslations(CASTLE_ILLAGER_PLAINS, "灾厄城堡（平原）");

        addBiomeTranslations(ConventionalBiomeTags.TAIGA, "针叶林");
        addBiomeTranslations(ConventionalBiomeTags.JUNGLE, "热带雨林");
        addBiomeTranslations(ConventionalBiomeTags.SAVANNA, "热带草原");
        addBiomeTranslations(ConventionalBiomeTags.SWAMP, "沼泽");
        addBiomeTranslations(ConventionalBiomeTags.MUSHROOM, "蘑菇岛");
        addBiomeTranslations(ConventionalBiomeTags.BADLANDS, "恶地");
        addBiomeTranslations(ConventionalBiomeTags.SNOWY, "覆雪地带");
        addBiomeTranslations(ConventionalBiomeTags.ICY, "冰冻地带");
        addBiomeTranslations(ConventionalBiomeTags.OCEAN, "海洋");
        addBiomeTranslations(ConventionalBiomeTags.DEEP_OCEAN, "深海");
        addBiomeTranslations(ConventionalBiomeTags.DESERT, "沙漠");
        addBiomeTranslations(ConventionalBiomeTags.PLAINS, "平原");
        addBiomeTranslations(BiomeTags.ANCIENT_CITY_HAS_STRUCTURE, "存在远古城市的群系");
        addBiomeTranslations(ConventionalBiomeTags.NETHER_FORESTS, "下界森林");
        addBiomeTranslations(AllTags.PRISMACHASM, "彩虹水晶洞穴");
    }

    public static void addStructure(Consumer<RecipeJsonProvider> exporter, RegistryKey<Structure> structure, RegistryKey<World> worldKey, Item item)
    {
        addStructure(exporter, structure.getValue(), worldKey, item);
    }

    public static void addStructure(Consumer<RecipeJsonProvider> exporter, Identifier structure, RegistryKey<World> worldKey, Item item)
    {
        addStructure(exporter, structure, worldKey.getValue(), item);
    }

    public static void addStructureWithSuffix(Consumer<RecipeJsonProvider> exporter, Identifier structure, RegistryKey<World> world, Item item, String suffix)
    {
        exporter.accept(
            new LocateStructureRecipeJsonProvider(
                SpellDimension.modLoc("locate/structure/%s/%s%s".formatted(structure.getNamespace(), structure.getPath(), suffix)),
                world.getValue(),
                Ingredient.ofItems(item),
                structure
            )
        );
    }

    public static void addStructure(Consumer<RecipeJsonProvider> exporter, Identifier structure, Identifier world, Item item)
    {
        exporter.accept(
            new LocateStructureRecipeJsonProvider(
                SpellDimension.modLoc("locate/structure/%s/%s".formatted(structure.getNamespace(), structure.getPath())),
                world,
                Ingredient.ofItems(item),
                structure
            )
        );
    }

    public static void addBiome(Consumer<RecipeJsonProvider> exporter, TagKey<Biome> biomeTag, RegistryKey<World> worldKey, Item item)
    {
        addBiome(exporter, biomeTag.id(), worldKey, item);
    }

    public static void addBiome(Consumer<RecipeJsonProvider> exporter, Identifier biomeTag, RegistryKey<World> worldKey, Item item)
    {
        addBiome(exporter, biomeTag, worldKey.getValue(), item);
    }

    public static void addBiome(Consumer<RecipeJsonProvider> exporter, Identifier biomeTag, Identifier world, Item item)
    {
        exporter.accept(
            new LocateBiomeRecipeJsonProvider(
                SpellDimension.modLoc("locate/biome/%s/%s".formatted(biomeTag.getNamespace(), biomeTag.getPath())),
                world,
                Ingredient.ofItems(item),
                biomeTag
            )
        );
    }

    public static void addStructureTranslations(RegistryKey<Structure> structure, String zh)
    {
        addStructureTranslations(structure.getValue(), zh);
    }

    public static void addStructureTranslations(Identifier structure, String zh)
    {
        addTranslation(structure, "structure", zh);
    }

    public static void addBiomeTranslations(TagKey<Biome> biomeTag, String zh)
    {
        addBiomeTranslations(biomeTag.id(), zh);
    }

    public static void addBiomeTranslations(Identifier biomeTag, String zh)
    {
        addTranslation(biomeTag, "biome", zh);
    }

    public static void addTranslation(Identifier id, String prefix, String zh)
    {
        String translationKey = id.toTranslationKey(prefix).replace('/', '.');
        SpellDimension.EN_TEXTS.addText(translationKey, StringUtil.defaultName(id.getPath().replace('/', '_')));
        SpellDimension.ZH_TEXTS.addText(translationKey, zh);
    }
}
