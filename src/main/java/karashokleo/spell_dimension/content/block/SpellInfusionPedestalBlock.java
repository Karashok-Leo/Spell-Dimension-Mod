package karashokleo.spell_dimension.content.block;

import karashokleo.enchantment_infusion.api.block.AbstractInfusionBlock;
import karashokleo.spell_dimension.content.block.tile.SpellInfusionPedestalTile;
import karashokleo.spell_dimension.init.AllBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SpellInfusionPedestalBlock extends AbstractInfusionBlock
{
    protected static final VoxelShape TOP = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 10.0, 16.0);
    protected static final VoxelShape BODY = Block.createCuboidShape(3.0, 2.0, 3.0, 13.0, 8.0, 13.0);
    protected static final VoxelShape BOTTOM = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 2.0, 14.0);
    protected static final VoxelShape SHAPE = VoxelShapes.union(TOP, BODY, BOTTOM);

    public SpellInfusionPedestalBlock()
    {
        super(
                FabricBlockSettings.create()
                        .mapColor(MapColor.BLACK)
                        .instrument(Instrument.BASEDRUM)
                        .strength(5.0f, 1200.0f)
                        .requiresTool()
                        .nonOpaque()
        );
    }

    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return SHAPE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return world.isClient() ?
                checkType(
                        type,
                        AllBlocks.SPELL_INFUSION_PEDESTAL_TILE,
                        SpellInfusionPedestalTile::clientTick
                ) :
                checkType(
                        type,
                        AllBlocks.SPELL_INFUSION_PEDESTAL_TILE,
                        SpellInfusionPedestalTile::serverTick
                );
//        return checkType(
//                type,
//                AllBlocks.SPELL_INFUSION_PEDESTAL_TILE,
//                world.isClient() ?
//                        SpellInfusionPedestalTile::clientTick : SpellInfusionPedestalTile::serverTick
//        );
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new SpellInfusionPedestalTile(pos, state);
    }
}
