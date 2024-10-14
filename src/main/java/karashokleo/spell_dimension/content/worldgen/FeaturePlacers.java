package karashokleo.spell_dimension.content.worldgen;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Feature Utility methods that invoke placement. For non-placement see FeatureLogic
 */
public final class FeaturePlacers
{
    public static final BiFunction<TestableWorld, BlockPos, Boolean> VALID_TREE_POS = TreeFeature::canReplace;

    /**
     * Draws a line from {x1, y1, z1} to {x2, y2, z2}
     * This takes all variables for setting Branch
     */
    public static void drawBresenhamBranch(WorldAccess world, BiConsumer<BlockPos, BlockState> trunkPlacer, Random random, BlockPos start, BlockPos end, BlockStateProvider config)
    {
        for (BlockPos pixel : new VoxelBresenhamIterator(start, end))
        {
            placeIfValidTreePos(world, trunkPlacer, random, pixel, config);
        }
    }


    public static void placeProvidedBlock(TestableWorld world, BiConsumer<BlockPos, BlockState> worldPlacer, BiFunction<TestableWorld, BlockPos, Boolean> predicate, BlockPos pos, BlockStateProvider config, Random random)
    {
        if (predicate.apply(world, pos)) worldPlacer.accept(pos, config.get(random, pos));
    }

    // Version without the `verticalBias` unlike above
    public static void placeSpheroid(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, BiFunction<TestableWorld, BlockPos, Boolean> predicate, Random random, BlockPos centerPos, float xzRadius, float yRadius, BlockStateProvider config)
    {
        float xzRadiusSquared = xzRadius * xzRadius;
        float yRadiusSquared = yRadius * yRadius;
        float superRadiusSquared = xzRadiusSquared * yRadiusSquared;
        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos, config, random);

        for (int y = 0; y <= yRadius; y++)
        {
            if (y > yRadius) continue;

            FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(0, y, 0), config, random);
            FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(0, -y, 0), config, random);
        }

        for (int x = 0; x <= xzRadius; x++)
        {
            for (int z = 1; z <= xzRadius; z++)
            {
                if (x * x + z * z > xzRadiusSquared) continue;

                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(x, 0, z), config, random);
                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(-x, 0, -z), config, random);
                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(-z, 0, x), config, random);
                FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(z, 0, -x), config, random);

                for (int y = 1; y <= yRadius; y++)
                {
                    float xzSquare = ((x * x + z * z) * yRadiusSquared);

                    if (xzSquare + (y * y) * xzRadiusSquared <= superRadiusSquared)
                    {
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(x, y, z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(-x, y, -z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(-z, y, x), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(z, y, -x), config, random);

                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(x, -y, z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(-x, -y, -z), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(-z, -y, x), config, random);
                        FeaturePlacers.placeProvidedBlock(world, placer, predicate, centerPos.add(z, -y, -x), config, random);
                    }
                }
            }
        }
    }

    // [VanillaCopy] TrunkPlacer.placeLog - Swapped TreeConfiguration for BlockStateProvider
    // If possible, use TrunkPlacer.placeLog instead
    public static boolean placeIfValidTreePos(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos pos, BlockStateProvider config)
    {
        if (TreeFeature.canReplace(world, pos))
        {
            placer.accept(pos, config.get(random, pos));
            return true;
        } else return false;
    }

    public static boolean placeIfValidRootPos(TestableWorld world, BiConsumer<BlockPos, BlockState> placer, Random random, BlockPos pos, BlockStateProvider config)
    {
        if (FeatureLogic.canRootGrowIn(world, pos))
        {
            placer.accept(pos, config.get(random, pos));
            return true;
        } else return false;
    }

    public static void traceRoot(
            TestableWorld worldReader,
            BiConsumer<BlockPos, BlockState> worldPlacer,
            Random random,
            BlockStateProvider dirtRoot,
            Iterable<BlockPos> posTracer
    )
    {
        // Trace block positions and stop tracing too far into open air
        for (BlockPos rootPos : posTracer)
            // If the block/position cannot be replaced or is detached from ground-mass, stop
            if (!FeaturePlacers.placeIfValidRootPos(worldReader, worldPlacer, random, rootPos, dirtRoot))
                return;
    }

    public static void traceExposedRoot(
            TestableWorld worldReader,
            BiConsumer<BlockPos, BlockState> worldPlacer,
            Random random,
            BlockStateProvider exposedRoot,
            BlockStateProvider dirtRoot,
            Iterable<BlockPos> posTracer
    )
    {
        // Trace block positions and alternate the root tracing once "underground"
        for (BlockPos exposedPos : posTracer)
        {
            // Is the position considered underground?
            if (!FeatureLogic.hasEmptyHorizontalNeighbor(worldReader, exposedPos))
            {
                // Retry placement at position as underground root. If successful, continue the tracing as regular root
                if (FeaturePlacers.placeIfValidRootPos(worldReader, worldPlacer, random, exposedPos, dirtRoot))
                    traceRoot(worldReader, worldPlacer, random, dirtRoot, posTracer);
                // Now the outer loop can end. Goodbye!
                return;
            } else
            {
                // Not underground
                // Check if the position is not replaceable
                if (!worldReader.testBlockState(exposedPos, FeatureLogic::worldGenReplaceable))
                    return; // Root must stop

                // Good to go!
                worldPlacer.accept(exposedPos, exposedRoot.get(random, exposedPos));
            }
        }
    }
}
