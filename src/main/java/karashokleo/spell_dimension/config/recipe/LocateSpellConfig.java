package karashokleo.spell_dimension.config.recipe;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.leobrary.datagen.util.StringUtil;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.runes.api.RuneItems;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class LocateSpellConfig
{
    private static final Map<Item, RegistryKey<Structure>> STRUCTURE_CONFIG = new HashMap<>();
    private static final Map<Item, RegistryKey<Biome>> BIOME_CONFIG = new HashMap<>();

    public static final RegistryKey<Structure> DARK_DUNGEON = RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier("dungeonz:dark_dungeon_structure"));

    static
    {
        STRUCTURE_CONFIG.put(Items.SCULK, StructureKeys.ANCIENT_CITY);
        STRUCTURE_CONFIG.put(RuneItems.get(RuneItems.RuneType.FIRE), StructureKeys.FORTRESS);
        STRUCTURE_CONFIG.put(RuneItems.get(RuneItems.RuneType.HEALING), StructureKeys.BASTION_REMNANT);
        STRUCTURE_CONFIG.put(Items.DRAGON_BREATH, StructureKeys.END_CITY);
        STRUCTURE_CONFIG.put(Items.EMERALD_BLOCK, StructureKeys.MANSION);
        STRUCTURE_CONFIG.put(ComplementItems.CURSED_DROPLET, DARK_DUNGEON);

        BIOME_CONFIG.put(RuneItems.get(RuneItems.RuneType.FROST), BiomeKeys.SNOWY_PLAINS);
        BIOME_CONFIG.put(Items.SAND, BiomeKeys.DESERT);
        BIOME_CONFIG.put(Items.GRASS_BLOCK, BiomeKeys.PLAINS);
        BIOME_CONFIG.put(Items.CANDLE, BiomeKeys.DEEP_DARK);
    }

    public static void addTranslation()
    {
        addTranslation(StructureKeys.ANCIENT_CITY, "远古城市");
        addTranslation(StructureKeys.FORTRESS, "下界要塞");
        addTranslation(StructureKeys.BASTION_REMNANT, "堡垒遗迹");
        addTranslation(StructureKeys.END_CITY, "末地城");
        addTranslation(StructureKeys.MANSION, "林地府邸");
        addTranslation(DARK_DUNGEON, "暗黑地牢");
    }

    @Nullable
    public static RegistryKey<Structure> getStructure(Item item)
    {
        return STRUCTURE_CONFIG.get(item);
    }

    @Nullable
    public static RegistryKey<Biome> getBiome(Item item)
    {
        return BIOME_CONFIG.get(item);
    }

    public static Text getSpotName(RegistryKey<?> registryKey)
    {
        return Text.translatable(
                getSpotKey(registryKey)
        ).formatted(Formatting.BOLD);
    }

    public static String getSpotKey(RegistryKey<?> registryKey)
    {
        return registryKey.getValue().toTranslationKey(
                registryKey.getRegistry().getPath().split("/")[1]
        );
    }

    public static void addTranslation(RegistryKey<?> registryKey, String zh)
    {
        String spotKey = getSpotKey(registryKey);
        SpellDimension.EN_TEXTS.addText(spotKey, StringUtil.defaultName(spotKey));
        SpellDimension.ZH_TEXTS.addText(spotKey, zh);
    }

    public static void forEach(BiConsumer<Item, RegistryKey<?>> action)
    {
        STRUCTURE_CONFIG.forEach(action);
        BIOME_CONFIG.forEach(action);
    }
}
