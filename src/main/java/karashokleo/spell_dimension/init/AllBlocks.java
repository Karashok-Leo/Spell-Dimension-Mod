package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.builder.BlockBuilder;
import karashokleo.leobrary.datagen.object.BlockSet;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.block.*;
import karashokleo.spell_dimension.content.block.fluid.ConsciousnessFluid;
import karashokleo.spell_dimension.content.block.tile.ConsciousnessCoreTile;
import karashokleo.spell_dimension.content.block.tile.ProtectiveCoverBlockTile;
import karashokleo.spell_dimension.content.block.tile.SpellInfusionPedestalTile;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class AllBlocks
{
    public static BlockSet SPELL_INFUSION_PEDESTAL;
    public static BlockEntityType<SpellInfusionPedestalTile> SPELL_INFUSION_PEDESTAL_TILE;

    public static SpellLightBlock SPELL_LIGHT;

    public static BlockSet CONSCIOUSNESS_BASE;
    public static BlockSet CONSCIOUSNESS_CORE;
    public static BlockEntityType<ConsciousnessCoreTile> CONSCIOUSNESS_CORE_TILE;

    public static BlockSet PROTECTIVE_COVER;
    public static BlockEntityType<ProtectiveCoverBlockTile> PROTECTIVE_COVER_TILE;

    public static FluidBlock CONSCIOUSNESS;
    public static ConsciousnessFluid STILL_CONSCIOUSNESS;
    public static ConsciousnessFluid FLOWING_CONSCIOUSNESS;

    public static void register()
    {
        SPELL_INFUSION_PEDESTAL = Entry.of("spell_infusion_pedestal", new SpellInfusionPedestalBlock())
                .addEN()
                .addZH("魔力灌注基座")
                .addLoot()
                .addSimpleItem()
                .addTag(BlockTags.PICKAXE_MINEABLE)
                .registerWithItem();
        SPELL_INFUSION_PEDESTAL_TILE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                SpellDimension.modLoc("spell_infusion_pedestal"),
                FabricBlockEntityTypeBuilder.create(SpellInfusionPedestalTile::new, SPELL_INFUSION_PEDESTAL.block()).build()
        );
        SPELL_LIGHT = Entry.of("spell_light", new SpellLightBlock())
                .addEN()
                .addZH("魔力之光")
                .register();
        CONSCIOUSNESS_BASE = Entry.of("consciousness_base", new ConsciousnessBaseBlock())
                .addEN()
                .addZH("识之基座")
                .addSimpleItem(new FabricItemSettings().rarity(Rarity.EPIC))
                .registerWithItem();
        CONSCIOUSNESS_CORE = Entry.of("consciousness_core", new ConsciousnessCoreBlock())
                .addEN()
                .addZH("识之核心")
                .addSimpleItem(new FabricItemSettings().rarity(Rarity.EPIC))
                .addModel()
                .registerWithItem();
        CONSCIOUSNESS_CORE_TILE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                SpellDimension.modLoc("consciousness_core"),
                FabricBlockEntityTypeBuilder.create(ConsciousnessCoreTile::new, CONSCIOUSNESS_CORE.block()).build()
        );

        PROTECTIVE_COVER = Entry.of("protective_cover", new ProtectiveCoverBlock())
                .addEN()
                .addZH("屏障")
                .addSimpleItem()
                .addModel()
                .registerWithItem();
        PROTECTIVE_COVER_TILE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                SpellDimension.modLoc("protective_cover"),
                FabricBlockEntityTypeBuilder.create(ProtectiveCoverBlockTile::new, PROTECTIVE_COVER.block()).build()
        );

        STILL_CONSCIOUSNESS = Registry.register(Registries.FLUID, SpellDimension.modLoc("still_consciousness"), new ConsciousnessFluid.Still());
        FLOWING_CONSCIOUSNESS = Registry.register(Registries.FLUID, SpellDimension.modLoc("flowing_consciousness"), new ConsciousnessFluid.Flowing());

        CONSCIOUSNESS = Entry.of("consciousness", new FluidBlock(STILL_CONSCIOUSNESS, Block.Settings.copy(Blocks.WATER)))
                .addEN()
                .addZH("识")
                .register();

        SpellDimension.MODELS.addBlock(generator ->
        {
            generator.registerSimpleState(SPELL_INFUSION_PEDESTAL.block());
            generator.registerSimpleState(SPELL_LIGHT);
            generator.registerSimpleState(CONSCIOUSNESS);
            generator.registerItemModel(CONSCIOUSNESS_CORE.item());
        });
        SpellDimension.MODELS.addBlock(generator ->
        {
            Identifier base = Models.CUBE_ALL.upload(CONSCIOUSNESS_BASE.block(), TextureMap.all(SpellDimension.modLoc("block/consciousness_base")), generator.modelCollector);
            Identifier base_turned = Models.CUBE_ALL.upload(CONSCIOUSNESS_BASE.block(), "_turned", TextureMap.all(SpellDimension.modLoc("block/consciousness_base_turned")), generator.modelCollector);
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(CONSCIOUSNESS_BASE.block()).coordinate(BlockStateModelGenerator.createBooleanModelMap(ConsciousnessBaseBlock.TURNED, base_turned, base)));
        });
    }

    static class Entry<T extends Block> extends BlockBuilder<T>
    {
        public static <T extends Block> Entry<T> of(String name, T content)
        {
            return new Entry<>(name, content);
        }

        public Entry(String name, T content)
        {
            super(name, content);
        }

        @Override
        public String getNameSpace()
        {
            return SpellDimension.MOD_ID;
        }
    }
}
