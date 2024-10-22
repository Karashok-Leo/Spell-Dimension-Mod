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
