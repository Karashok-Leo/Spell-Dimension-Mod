package karashokleo.spell_dimension.content.block.fluid;

import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public abstract class ConsciousnessFluid extends WaterFluid
{
    @Override
    public Fluid getFlowing()
    {
        return AllBlocks.FLOWING_CONSCIOUSNESS;
    }

    @Override
    public Fluid getStill()
    {
        return AllBlocks.STILL_CONSCIOUSNESS;
    }

    @Override
    public boolean matchesType(Fluid fluid)
    {
        return fluid == getFlowing() || fluid == getStill();
    }

    @Override
    public Item getBucketItem()
    {
        return Items.AIR;
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random)
    {
        if (!state.isStill() &&
            !(Boolean) state.get(FALLING) &&
            random.nextInt(64) == 0)
            world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
        else if (random.nextInt(10) == 0)
            world.addParticle(ParticleTypes.END_ROD, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
    }

    @Override
    public Vec3d getVelocity(BlockView world, BlockPos pos, FluidState state)
    {
        return super.getVelocity(world, pos, state).add(0, 0.01, 0);
    }

    @Nullable
    @Override
    public ParticleEffect getParticle()
    {
        return ParticleTypes.END_ROD;
    }

    @Override
    public int getTickRate(WorldView world)
    {
        return 2;
    }

    @Override
    public BlockState toBlockState(FluidState state)
    {
        return AllBlocks.CONSCIOUSNESS.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public int getLevelDecreasePerBlock(WorldView world)
    {
        return 1;
    }

    @Override
    protected float getBlastResistance()
    {
        return 0;
    }

    @Override
    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction)
    {
        return false;
    }

    public static class Flowing extends ConsciousnessFluid
    {
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder)
        {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        public int getLevel(FluidState state)
        {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state)
        {
            return false;
        }
    }

    public static class Still extends ConsciousnessFluid
    {
        public int getLevel(FluidState state)
        {
            return 8;
        }

        public boolean isStill(FluidState state)
        {
            return true;
        }
    }
}
