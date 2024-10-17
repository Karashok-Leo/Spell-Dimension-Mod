package karashokleo.spell_dimension.content.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ConsciousnessLogBlock extends Block
{
    public static final int MAX_LEVEL = 6;
    public static final BooleanProperty TURNING = BooleanProperty.of("turning");
    public static final IntProperty LEVEL = IntProperty.of("level", 1, MAX_LEVEL);

    public ConsciousnessLogBlock()
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
                        .with(LEVEL, 1)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(TURNING);
        builder.add(LEVEL);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        this.tryTurnSelf(state, world, pos);
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if (state.get(TURNING))
        {
            world.setBlockState(pos, state.with(TURNING, false));
            int level = state.get(LEVEL);
            if (level < MAX_LEVEL)
                world.setBlockState(pos, world.getBlockState(pos).with(LEVEL, level + 1));

            // TODO: play sound
            // TODO: spawn particles

            this.tryTurnSurround(world.getBlockState(pos), world, pos);
        }
    }

    private void tryTurnSelf(BlockState state, World world, BlockPos pos)
    {
        if (!state.get(TURNING) && state.get(LEVEL) < MAX_LEVEL)
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
                state.get(LEVEL) > otherState.get(LEVEL))
                this.tryTurnSelf(otherState, world, otherPos);
        }
    }
}
