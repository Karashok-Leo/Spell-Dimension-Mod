package karashokleo.spell_dimension.content.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class ConsciousnessBaseBlock extends Block
{
    public static final BooleanProperty TURNING = BooleanProperty.of("turning");
    public static final BooleanProperty TURNED = BooleanProperty.of("turned");

    public ConsciousnessBaseBlock()
    {
        super(
                FabricBlockSettings.create()
                        .mapColor(MapColor.BLUE)
                        .instrument(Instrument.BASS)
                        .sounds(BlockSoundGroup.WOOD)
                        .strength(-1.0F, 3600000.0F)
                        .dropsNothing()
                        .allowsSpawning(Blocks::never)
        );
        this.setDefaultState(
                this.stateManager.getDefaultState()
                        .with(TURNING, false)
                        .with(TURNED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(TURNING);
        builder.add(TURNED);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (state.get(TURNING))
        {
            world.setBlockState(pos, state.with(TURNING, false));
            if (!state.get(TURNED))
                world.setBlockState(pos, world.getBlockState(pos).with(TURNED, true));

            // TODO: play sound
            // TODO: spawn particles

            this.tryTurnSurround(world.getBlockState(pos), world, pos);
        }
    }

    public void tryTurnSelf(BlockState state, World world, BlockPos pos)
    {
        if (!state.get(TURNING) && !state.get(TURNED))
            world.setBlockState(pos, state.with(TURNING, true));
        world.scheduleBlockTick(pos, state.getBlock(), 5);
    }

    private void tryTurnSurround(BlockState state, World world, BlockPos pos)
    {
        for (Direction direction : Direction.values())
        {
            BlockPos otherPos = pos.offset(direction);
            BlockState otherState = world.getBlockState(otherPos);
            if (otherState.isOf(this) &&
                state.get(TURNED))
                this.tryTurnSelf(otherState, world, otherPos);
        }
//        for (BlockPos otherPos : getSurroundingPoses(pos))
//        {
//            BlockState otherState = world.getBlockState(otherPos);
//            if (otherState.isOf(this) &&
//                state.get(TURNED))
//                this.tryTurnSelf(otherState, world, otherPos);
//        }
    }

    private static Set<BlockPos> getSurroundingPoses(BlockPos pos)
    {
        Set<BlockPos> poses = new HashSet<>();
        int radius = 1;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                {
                    if (x == 0 && y == 0 && z == 0)
                        continue;
                    poses.add(pos.add(x, y, z));
                }
        return poses;
    }
}
