package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.worldgen.ConsciousnessPivotFeature;
import karashokleo.spell_dimension.content.worldgen.ConsciousnessTreeFeature;
import karashokleo.spell_dimension.content.worldgen.TFTreeFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

public class AllWorldGen
{
    public static final Identifier OC_ID = SpellDimension.modLoc("ocean_of_consciousness");
    public static final RegistryKey<DimensionType> OC_DIMENSION_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, OC_ID);
    public static final RegistryKey<World> OC_WORLD = RegistryKey.of(RegistryKeys.WORLD, OC_ID);
    public static final RegistryKey<Biome> OC_BIOME = RegistryKey.of(RegistryKeys.BIOME, OC_ID);

    public static final ConsciousnessTreeFeature CONSCIOUSNESS_TREE = new ConsciousnessTreeFeature(TFTreeFeatureConfig.CODEC);
    public static final ConsciousnessPivotFeature CONSCIOUSNESS_PIVOT = new ConsciousnessPivotFeature();

    public static void register()
    {
        Registry.register(Registries.FEATURE, SpellDimension.modLoc("tree_of_consciousness"), CONSCIOUSNESS_TREE);
        Registry.register(Registries.FEATURE, SpellDimension.modLoc("consciousness_pivot"), CONSCIOUSNESS_PIVOT);

        String biome_key = OC_BIOME.getValue().toTranslationKey("biome");
        SpellDimension.EN_TEXTS.addText(biome_key, "Ocean of Consciousness");
        SpellDimension.ZH_TEXTS.addText(biome_key, "识之海");
        biome_key += ".color";
        SpellDimension.EN_TEXTS.addText(biome_key, "ff00ff");
        SpellDimension.ZH_TEXTS.addText(biome_key, "ff00ff");
    }
}
