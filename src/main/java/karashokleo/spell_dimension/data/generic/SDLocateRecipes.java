package karashokleo.spell_dimension.data.generic;

import artifacts.registry.ModItems;
import com.kyanite.deeperdarker.content.DDItems;
import com.lion.graveyard.init.TGItems;
import com.spellbladenext.Spellblades;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.locate.LocateBiomeRecipeProvider;
import karashokleo.spell_dimension.content.recipe.locate.LocateStructureProvider;
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

    // World ID
    public static final Identifier OTHERSIDE = new Identifier("deeperdarker:otherside");

    public static void add(Consumer<RecipeJsonProvider> exporter)
    {
        // Required Items
        Item bossCompass = Registries.ITEM.get(new Identifier("soulsweapons:boss_compass"));
        Item lostSoul = Registries.ITEM.get(new Identifier("soulsweapons:lost_soul"));

        // Structures
        addStructure(exporter, MONUMENT, World.OVERWORLD, Items.SEA_LANTERN);
        addStructure(exporter, StructureKeys.ANCIENT_CITY, World.OVERWORLD, RuneItems.get(RuneItems.RuneType.ARCANE));
        addStructure(exporter, StructureKeys.MANSION, World.OVERWORLD, Items.EMERALD_BLOCK);
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
        addStructure(exporter, StructureKeys.BASTION_REMNANT, World.NETHER, RuneItems.get(RuneItems.RuneType.HEALING));
        addStructure(exporter, StructureKeys.END_CITY, World.END, Items.DRAGON_BREATH);

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
    }

    public static void addTranslations()
    {
        addStructureTranslations(MONUMENT, "海底神殿");
        addStructureTranslations(StructureKeys.ANCIENT_CITY, "远古城市");
        addStructureTranslations(StructureKeys.MANSION, "林地府邸");
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
        addStructureTranslations(ANCIENT_TEMPLE, "远古神殿");
        addStructureTranslations(GRAVE_RUINS, "死者遗迹");
        addStructureTranslations(LICH_PRISON, "巫妖之狱");
        addStructureTranslations(StructureKeys.BASTION_REMNANT, "堡垒遗迹");
        addStructureTranslations(StructureKeys.END_CITY, "末地城");

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
    }

    public static void addStructure(Consumer<RecipeJsonProvider> exporter, RegistryKey<Structure> structure, RegistryKey<World> worldKey, Item item)
    {
        addStructure(exporter, structure.getValue(), worldKey, item);
    }

    public static void addStructure(Consumer<RecipeJsonProvider> exporter, Identifier structure, RegistryKey<World> worldKey, Item item)
    {
        addStructure(exporter, structure, worldKey.getValue(), item);
    }

    public static void addStructure(Consumer<RecipeJsonProvider> exporter, Identifier structure, Identifier world, Item item)
    {
        exporter.accept(
                new LocateStructureProvider(
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
                new LocateBiomeRecipeProvider(
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
