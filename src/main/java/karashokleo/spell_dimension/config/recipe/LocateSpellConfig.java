package karashokleo.spell_dimension.config.recipe;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.spellbladenext.Spellblades;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.runes.api.RuneItems;

public class LocateSpellConfig
{
    public static final Table<Item, RegistryKey<World>, RegistryKey<Structure>> STRUCTURE_CONFIG = HashBasedTable.create();
    public static final Table<Item, RegistryKey<World>, TagKey<Biome>> BIOME_CONFIG = HashBasedTable.create();

    public static final RegistryKey<Structure> DARK_DUNGEON = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("dungeonz:dark_dungeon_structure"));
    public static final RegistryKey<Structure> DESERT_DUNGEON = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("dungeonz:desert_dungeon_structure"));
    public static final RegistryKey<Structure> SHIP = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("aquamirae:ship"));
    public static final RegistryKey<Structure> SHELTER = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("aquamirae:shelter"));
    public static final RegistryKey<Structure> CHAMPIONS_GRAVES = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("soulsweapons:champions_graves"));
    public static final RegistryKey<Structure> CATHEDRAL_OF_RESURRECTION = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("soulsweapons:cathedral_of_resurrection"));
    public static final RegistryKey<Structure> DECAYING_KINGDOM = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("soulsweapons:decaying_kingdom"));

    public static final Item BOSS_COMPASS = Registries.ITEM.get(new Identifier("soulsweapons:boss_compass"));
    public static final Item LOST_SOUL = Registries.ITEM.get(new Identifier("soulsweapons:lost_soul"));

    static
    {
        register(StructureKeys.ANCIENT_CITY, World.OVERWORLD, Items.SCULK, "远古城市");
        register(StructureKeys.MANSION, World.OVERWORLD, Items.EMERALD_BLOCK, "林地府邸");
        register(DARK_DUNGEON, World.OVERWORLD, ComplementItems.CURSED_DROPLET, "暗黑地牢");
        register(DESERT_DUNGEON, World.OVERWORLD, ComplementItems.SUN_MEMBRANE, "沙漠地牢");
        register(SHIP, World.OVERWORLD, RuneItems.get(RuneItems.RuneType.FROST), "搁浅舰船");
        register(SHELTER, World.OVERWORLD, Spellblades.RUNEFROST, "避难所");
        register(CATHEDRAL_OF_RESURRECTION, World.OVERWORLD, LOST_SOUL, "复苏教堂");
        register(CHAMPIONS_GRAVES, World.OVERWORLD, BOSS_COMPASS, "古英雄的陵墓");
        register(DECAYING_KINGDOM, World.NETHER, BOSS_COMPASS, "腐朽王国");
        register(StructureKeys.FORTRESS, World.NETHER, RuneItems.get(RuneItems.RuneType.FIRE), "下界要塞");
        register(StructureKeys.BASTION_REMNANT, World.NETHER, RuneItems.get(RuneItems.RuneType.HEALING), "堡垒遗迹");
        register(StructureKeys.END_CITY, World.END, Items.DRAGON_BREATH, "末地城");

        register(ConventionalBiomeTags.TAIGA, World.OVERWORLD, Items.SWEET_BERRIES, "针叶林");
        register(ConventionalBiomeTags.JUNGLE, World.OVERWORLD, Items.COCOA_BEANS, "热带雨林");
        register(ConventionalBiomeTags.SAVANNA, World.OVERWORLD, Items.ACACIA_LOG, "热带草原");
        register(ConventionalBiomeTags.SWAMP, World.OVERWORLD, Items.VINE, "沼泽");
        register(ConventionalBiomeTags.MUSHROOM, World.OVERWORLD, Items.BROWN_MUSHROOM, "蘑菇岛");
        register(ConventionalBiomeTags.BADLANDS, World.OVERWORLD, Items.CLAY_BALL, "恶地");
        register(ConventionalBiomeTags.SNOWY, World.OVERWORLD, Items.SNOWBALL, "覆雪地带");
        register(ConventionalBiomeTags.ICY, World.OVERWORLD, Items.ICE, "冰冻地带");
        register(ConventionalBiomeTags.OCEAN, World.OVERWORLD, Items.KELP, "海洋");
        register(ConventionalBiomeTags.DEEP_OCEAN, World.OVERWORLD, Items.PRISMARINE_SHARD, "深海");
        register(ConventionalBiomeTags.DESERT, World.OVERWORLD, Items.SAND, "沙漠");
        register(ConventionalBiomeTags.PLAINS, World.OVERWORLD, Items.GRASS_BLOCK, "平原");
        register(BiomeTags.ANCIENT_CITY_HAS_STRUCTURE, World.OVERWORLD, Items.CANDLE, "存在远古城市的群系");
        register(ConventionalBiomeTags.NETHER_FORESTS, World.NETHER, Items.RED_MUSHROOM, "下界森林");
    }

    public static Text getSpotName(RegistryKey<?> registryKey)
    {
        return Text.translatable(
                getSpotKey(registryKey.getRegistry(), registryKey.getValue())
        ).formatted(Formatting.BOLD);
    }

    public static Text getSpotName(TagKey<?> tagKey)
    {
        return Text.translatable(
                getSpotKey(tagKey.registry().getValue(), tagKey.id())
        ).formatted(Formatting.BOLD);
    }

    public static String getSpotKey(Identifier registry, Identifier id)
    {
        String[] split = registry.getPath().split("/");
        return id.toTranslationKey(split[split.length - 1]).replace("/", "_");
    }

    public static void addTranslation(String spotKey, String zh)
    {
        String[] split = spotKey.split("\\.");
        SpellDimension.EN_TEXTS.addText(spotKey, StringUtil.defaultName(split[split.length - 1]));
        SpellDimension.ZH_TEXTS.addText(spotKey, zh);
    }

    public static void register(RegistryKey<Structure> registryKey, RegistryKey<World> worldKey, Item item, String zh)
    {
        STRUCTURE_CONFIG.put(item, worldKey, registryKey);
        addTranslation(getSpotKey(registryKey.getRegistry(), registryKey.getValue()), zh);
    }

    public static void register(TagKey<Biome> tagKey, RegistryKey<World> worldKey, Item item, String zh)
    {
        BIOME_CONFIG.put(item, worldKey, tagKey);
        addTranslation(getSpotKey(tagKey.registry().getValue(), tagKey.id()), zh);
    }

    public static void register()
    {
    }
}
