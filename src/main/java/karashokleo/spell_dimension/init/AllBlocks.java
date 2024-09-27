package karashokleo.spell_dimension.init;

import karashokleo.leobrary.datagen.builder.BlockBuilder;
import karashokleo.leobrary.datagen.builder.BlockSet;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.block.SpellInfusionPedestalBlock;
import karashokleo.spell_dimension.content.block.SpellLightBlock;
import karashokleo.spell_dimension.content.block.tile.SpellInfusionPedestalTile;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllBlocks
{
    public static BlockSet SPELL_INFUSION_PEDESTAL;
    public static BlockEntityType<SpellInfusionPedestalTile> SPELL_INFUSION_PEDESTAL_TILE;

    public static SpellLightBlock SPELL_LIGHT;

    public static void register()
    {
        SPELL_INFUSION_PEDESTAL = Entry.of("spell_infusion_pedestal", new SpellInfusionPedestalBlock())
                .addEN()
                .addZH("魔力灌注基座")
                .addLoot()
                .addSimpleItem()
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

        SpellDimension.MODELS.addBlock(generator -> generator.registerSimpleState(SPELL_INFUSION_PEDESTAL.block()));
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
