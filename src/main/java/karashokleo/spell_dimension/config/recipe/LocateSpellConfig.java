package karashokleo.spell_dimension.config.recipe;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.SpellDimension;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.runes.api.RuneItems;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class LocateSpellConfig
{
    public static final Map<Item, RegistryKey<Structure>> STRUCTURE_CONFIG = new HashMap<>();
    public static final Map<Item, TagKey<Biome>> BIOME_CONFIG = new HashMap<>();

    public static final RegistryKey<Structure> DARK_DUNGEON = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("dungeonz:dark_dungeon_structure"));
    public static final RegistryKey<Structure> DESERT_DUNGEON = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("dungeonz:desert_dungeon_structure"));

    static
    {
        register(StructureKeys.ANCIENT_CITY, Items.SCULK, "远古城市");
        register(StructureKeys.FORTRESS, RuneItems.get(RuneItems.RuneType.FIRE), "下界要塞");
        register(StructureKeys.BASTION_REMNANT, RuneItems.get(RuneItems.RuneType.HEALING), "堡垒遗迹");
        register(StructureKeys.END_CITY, Items.DRAGON_BREATH, "末地城");
        register(StructureKeys.MANSION, Items.EMERALD_BLOCK, "林地府邸");
        register(DARK_DUNGEON, ComplementItems.CURSED_DROPLET, "暗黑地牢");
        register(DESERT_DUNGEON, ComplementItems.SUN_MEMBRANE, "沙漠地牢");

        register(ConventionalBiomeTags.TAIGA, Items.SWEET_BERRIES, "针叶林");
        register(ConventionalBiomeTags.JUNGLE, Items.COCOA_BEANS, "热带雨林");
        register(ConventionalBiomeTags.SAVANNA, Items.ACACIA_LOG, "热带草原");
        register(ConventionalBiomeTags.SWAMP, Items.VINE, "沼泽");
        register(ConventionalBiomeTags.MUSHROOM, Items.BROWN_MUSHROOM, "蘑菇岛");
        register(ConventionalBiomeTags.BADLANDS, Items.CLAY_BALL, "恶地");
        register(ConventionalBiomeTags.SNOWY, Items.SNOWBALL, "覆雪地带");
        register(ConventionalBiomeTags.ICY, RuneItems.get(RuneItems.RuneType.FROST), "冰冻地带");
        register(ConventionalBiomeTags.OCEAN, Items.KELP, "海洋");
        register(ConventionalBiomeTags.DEEP_OCEAN, Items.PRISMARINE_SHARD, "深海");
        register(ConventionalBiomeTags.DESERT, Items.SAND, "沙漠");
        register(ConventionalBiomeTags.PLAINS, Items.GRASS_BLOCK, "平原");
        register(BiomeTags.ANCIENT_CITY_HAS_STRUCTURE, Items.CANDLE, "存在远古城市的群系");
        register(ConventionalBiomeTags.NETHER_FORESTS, Items.NETHER_WART, "下界森林");
    }

    @Nullable
    public static RegistryKey<Structure> getStructure(Item item)
    {
        return STRUCTURE_CONFIG.get(item);
    }

    @Nullable
    public static TagKey<Biome> getBiome(Item item)
    {
        return BIOME_CONFIG.get(item);
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

    public static void register(RegistryKey<Structure> registryKey, Item item, String zh)
    {
        STRUCTURE_CONFIG.put(item, registryKey);
        addTranslation(getSpotKey(registryKey.getRegistry(), registryKey.getValue()), zh);
    }

    public static void register(TagKey<Biome> tagKey, Item item, String zh)
    {
        BIOME_CONFIG.put(item, tagKey);
        addTranslation(getSpotKey(tagKey.registry().getValue(), tagKey.id()), zh);
    }

    public static void register()
    {
    }
}
