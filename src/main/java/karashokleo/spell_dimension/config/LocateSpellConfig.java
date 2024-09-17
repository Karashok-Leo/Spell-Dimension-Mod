package karashokleo.spell_dimension.config;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;
import net.runes.api.RuneItems;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class LocateSpellConfig
{
    private static final Map<Item, RegistryKey<Structure>> STRUCTURE_CONFIG = new HashMap<>();
    private static final Map<Item, RegistryKey<Biome>> BIOME_CONFIG = new HashMap<>();

    static
    {
        STRUCTURE_CONFIG.put(Items.SCULK, StructureKeys.ANCIENT_CITY);
        STRUCTURE_CONFIG.put(RuneItems.get(RuneItems.RuneType.FIRE), StructureKeys.NETHER_FOSSIL);
        STRUCTURE_CONFIG.put(RuneItems.get(RuneItems.RuneType.HEALING), StructureKeys.BASTION_REMNANT);
        STRUCTURE_CONFIG.put(Items.DRAGON_BREATH, StructureKeys.END_CITY);
        STRUCTURE_CONFIG.put(Items.EMERALD_BLOCK, StructureKeys.MANSION);
        BIOME_CONFIG.put(RuneItems.get(RuneItems.RuneType.FROST), BiomeKeys.SNOWY_PLAINS);
        BIOME_CONFIG.put(Items.SAND, BiomeKeys.DESERT);
        BIOME_CONFIG.put(Items.GRASS, BiomeKeys.PLAINS);
        BIOME_CONFIG.put(Items.CANDLE, BiomeKeys.DEEP_DARK);
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
}
