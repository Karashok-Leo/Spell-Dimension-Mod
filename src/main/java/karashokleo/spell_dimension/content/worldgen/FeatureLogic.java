package karashokleo.spell_dimension.content.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;

import java.util.function.Predicate;

/**
 * Feature Utility methods that don't invoke placement. For placement see FeaturePlacers
 */
public final class FeatureLogic
{
    public static final Predicate<BlockState> IS_REPLACEABLE_AIR = state -> state.isReplaceable() || state.isAir();

    public static boolean hasEmptyHorizontalNeighbor(TestableWorld worldReader, BlockPos pos)
    {
        return worldReader.testBlockState(pos.north(), IS_REPLACEABLE_AIR)
               || worldReader.testBlockState(pos.south(), IS_REPLACEABLE_AIR)
               || worldReader.testBlockState(pos.west(), IS_REPLACEABLE_AIR)
               || worldReader.testBlockState(pos.east(), IS_REPLACEABLE_AIR);
    }

    // Slight stretch of logic: We check if the block is completely surrounded by air.
    // If it's not completely surrounded by air, then there's a solid
    public static boolean hasSolidNeighbor(TestableWorld worldReader, BlockPos pos)
    {
        return !(worldReader.testBlockState(pos.down(), IS_REPLACEABLE_AIR)
                 && worldReader.testBlockState(pos.north(), IS_REPLACEABLE_AIR)
                 && worldReader.testBlockState(pos.south(), IS_REPLACEABLE_AIR)
                 && worldReader.testBlockState(pos.west(), IS_REPLACEABLE_AIR)
                 && worldReader.testBlockState(pos.east(), IS_REPLACEABLE_AIR)
                 && worldReader.testBlockState(pos.up(), IS_REPLACEABLE_AIR));
    }

    public static boolean canRootGrowIn(TestableWorld worldReader, BlockPos pos)
    {
        if (worldReader.testBlockState(pos, IS_REPLACEABLE_AIR))
        {
            // roots can grow through air if they are near a solid block
            return hasSolidNeighbor(worldReader, pos);
        } else
        {
            return worldReader.testBlockState(pos, FeatureLogic::worldGenReplaceable);
        }
    }

    public static boolean worldGenReplaceable(BlockState state)
    {
        return (state.isReplaceable() ||
                state.isIn(BlockTags.LUSH_GROUND_REPLACEABLE) ||
                state.isIn(BlockTags.REPLACEABLE_BY_TREES)) &&
               !state.isIn(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    /**
     * Does the block have at least 1 air block adjacent
     */
    private static final Direction[] directionsExceptDown = new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public static boolean hasAirAround(WorldAccess world, BlockPos pos)
    {
        for (Direction e : directionsExceptDown)
            if (world.isAir(pos.offset(e)))
                return true;
        return false;
    }

    /**
     * Moves distance along the vector.
     * <p>
     * This goofy function takes a float between 0 and 1 for the angle, where 0 is 0 degrees, .5 is 180 degrees and 1 is 360 degrees.
     * For the tilt, it takes a float between 0 and 1 where 0 is straight up, 0.5 is straight out and 1 is straight down.
     */
    public static BlockPos translate(BlockPos pos, double distance, double angle, double tilt)
    {
        double rAngle = angle * 2.0D * Math.PI;
        double rTilt = tilt * Math.PI;

        return pos.add(
                (int) Math.round(Math.sin(rAngle) * Math.sin(rTilt) * distance),
                (int) Math.round(Math.cos(rTilt) * distance),
                (int) Math.round(Math.cos(rAngle) * Math.sin(rTilt) * distance)
        );
    }
}
