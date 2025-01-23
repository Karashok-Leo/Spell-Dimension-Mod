package karashokleo.spell_dimension.content.block;

import karashokleo.spell_dimension.content.block.tile.ProtectiveCoverBlockTile;
import karashokleo.spell_dimension.init.AllBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ProtectiveCoverBlock extends AbstractGlassBlock implements BlockEntityProvider
{
    public ProtectiveCoverBlock()
    {
        super(
                FabricBlockSettings.create()
                        .mapColor(DyeColor.PURPLE)
                        .instrument(Instrument.HAT)
                        .strength(0.3F)
                        .sounds(BlockSoundGroup.GLASS)
                        .nonOpaque()
                        .allowsSpawning(Blocks::never)
                        .solidBlock(Blocks::never)
                        .suffocates(Blocks::never)
                        .blockVision(Blocks::never)
        );
        this.setDefaultState(
                this.stateManager.getDefaultState()
                        .with(Properties.PERSISTENT, false)
        );
    }

    public static void place(World world, BlockPos pos, int life)
    {
        world.setBlockState(pos, AllBlocks.PROTECTIVE_COVER.block().getDefaultState());
        if (world.getBlockEntity(pos) instanceof ProtectiveCoverBlockTile tile)
            tile.setLife(life);
    }

    public static void placePersistent(World world, BlockPos pos)
    {
        world.setBlockState(pos, AllBlocks.PROTECTIVE_COVER.block().getDefaultState().with(Properties.PERSISTENT, true));
    }

    public static void placeAsBarrier(World world, BlockPos pos, int radius, int life)
    {
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    if (x == -radius || x == radius ||
                        y == -radius || y == radius ||
                        z == -radius || z == radius)
                    {
                        BlockPos placePos = pos.add(x, y, z);
                        if (world.getBlockState(placePos).isAir())
                        {
                            world.setBlockState(placePos, AllBlocks.PROTECTIVE_COVER.block().getDefaultState());
                            if (world.getBlockEntity(placePos) instanceof ProtectiveCoverBlockTile tile)
                                tile.setLife(life);
                        }
                    }
    }

    public static void breakBarrier(World world, BlockPos pos, int radius)
    {
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    if (x == -radius || x == radius ||
                        y == -radius || y == radius ||
                        z == -radius || z == radius)
                    {
                        BlockPos breakPos = pos.add(x, y, z);
                        BlockState blockState = world.getBlockState(breakPos);
                        if (blockState.isOf(AllBlocks.PROTECTIVE_COVER.block()))
                        {
                            breakNonPersistent(world, breakPos);
//                            world.breakBlock(breakPos, false);
//                            world.removeBlockEntity(breakPos);
                        }
                    }
    }

    public static void breakNonPersistent(World world, BlockPos pos)
    {
        world.setBlockState(pos, world.getFluidState(pos).getBlockState(), 3);
        world.removeBlockEntity(pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(Properties.PERSISTENT);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new ProtectiveCoverBlockTile(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return (!state.get(Properties.PERSISTENT) &&
                type == AllBlocks.PROTECTIVE_COVER_TILE) ?
                (w, pos, s, tile) -> ProtectiveCoverBlockTile.tick(w, pos, s, (ProtectiveCoverBlockTile) tile) : null;
    }
}
