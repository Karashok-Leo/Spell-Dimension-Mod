package karashokleo.spell_dimension.content.worldgen;

import com.mojang.serialization.Codec;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.block.*;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;

import java.util.function.BiConsumer;

/**
 * Copy of <code>twilightforest.world.components.feature.trees.HollowTreeFeature</code> with some modifications.
 */
public class ConsciousnessTreeFeature extends TFTreeFeature<TFTreeFeatureConfig>
{
    private static final int LEAF_DUNGEON_CHANCE = 8;

    public ConsciousnessTreeFeature(Codec<TFTreeFeatureConfig> config)
    {
        super(config);
    }

    @Override
    public boolean generate(
        StructureWorldAccess world,
        Random random, BlockPos pos,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        BiConsumer<BlockPos, BlockState> decorationPlacer,
        TFTreeFeatureConfig config
    )
    {
        float v = random.nextFloat() - 0.5f;
        // in the original code, it was named "diameter" but it was actually the radius
        int radiusRange = config.maxRadius() - config.minRadius();
        int heightRange = config.maxHeight() - config.minHeight();
        int radius = (config.minRadius() + config.maxRadius()) / 2 + (int) (v * radiusRange);
        int height = (config.minHeight() + config.maxHeight()) / 2 + (int) (v * heightRange);

        // do we have enough height?
        if (world.isOutOfHeightLimit(pos.getY()) ||
            world.isOutOfHeightLimit(pos.getY() + height + radius))
        {
            return false;
        }

        // check the top too
        int crownRadius = radius * 4 + 8;
        for (int dx = -crownRadius; dx <= crownRadius; dx++)
        {
            for (int dz = -crownRadius; dz <= crownRadius; dz++)
            {
                for (int dy = height - crownRadius; dy <= height + crownRadius; dy++)
                {
                    Block whatsThere = world.getBlockState(pos.add(dx, dy, dz)).getBlock();
                    if (whatsThere == Blocks.AIR ||
                        whatsThere instanceof LeavesBlock)
                    {
                        continue;
                    }
                    return false;
                }
            }
        }

        // Start with roots first, so they don't fail placement because they intersect the trunk shell first
        // 3-5 roots at the bottom
        buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, 3, 2, 6, 0.75D, 3, 5, 3, false, config);

        // several more taproots
        buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, 1, 2, 8, 0.9D, 3, 5, 3, false, config);

        // make a tree!

        // build the trunk
        buildTrunk(world, trunkPlacer, decorationPlacer, random, pos, radius, height, config);

        // build the crown
        buildFullCrown(world, trunkPlacer, leavesPlacer, random, pos, radius, height, config);

        // 3-5 couple branches on the way up...
        int numBranches = random.nextInt(10) + 3;
        for (int i = 0; i <= numBranches; i++)
        {
            int branchHeight = (int) (height * random.nextDouble() * 0.9) + (height / 10);
            double branchRotation = random.nextDouble();
            makeSmallBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, branchHeight, 4, branchRotation, 0.35D, true, config);
        }

        return true;
    }

    /**
     * Build a ring of branches around the tree
     * size 0 = small, 1 = med, 2 = large, 3 = root
     */
    protected void buildBranchRing(
        StructureWorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos pos,
        int radius,
        int branchHeight,
        int heightVar,
        int length,
        double tilt,
        int minBranches,
        int maxBranches,
        int size,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        //let's do this!
        int numBranches = random.nextInt(maxBranches - minBranches) + minBranches;
        double branchRotation = 1.0 / (numBranches + 1);
        double branchOffset = random.nextDouble();

        for (int i = 0; i <= numBranches; i++)
        {
            int dHeight = branchHeight;
            if (heightVar > 0)
            {
                dHeight += random.nextInt(2 * heightVar) - heightVar;
            }

            if (size == 0)
            {
                makeSmallBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, dHeight, length, i * branchRotation + branchOffset, tilt, leafy, config);
            } else if (size == 1)
            {
                makeMedBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, dHeight, length - 1, i * branchRotation + branchOffset, tilt, leafy, config);
            } else if (size == 2)
            {
                makeLargeBranch(world, trunkPlacer, leavesPlacer, random, pos, radius, dHeight, length - 3, i * branchRotation + branchOffset, tilt, leafy, config);
            } else
            {
                makeRoot(world, random, pos, radius, dHeight, length, i * branchRotation + branchOffset, tilt, config);
            }
        }
    }

    /**
     * This function builds the hollow trunk of the tree
     */
    protected void buildTrunk(
        WorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> decoPlacer,
        Random random,
        BlockPos pos,
        int radius,
        int height,
        TFTreeFeatureConfig config
    )
    {
        final int hollow = radius >> 1;

        // go down 4 squares and fill in extra trunk as needed, in case we're on uneven terrain
        for (int dx = -radius; dx <= radius; dx++)
        {
            for (int dz = -radius; dz <= radius; dz++)
            {
                for (int dy = -4; dy < 0; dy++)
                {
                    // determine how far we are from the center.
                    int ax = Math.abs(dx);
                    int az = Math.abs(dz);
                    int dist = Math.max(ax, az) + (Math.min(ax, az) >> 1);

                    if (dist <= radius)
                    {
                        BlockPos dPos = pos.add(dx, dy, dz);
                        if (FeatureLogic.hasAirAround(world, dPos))
                        {
                            if (dist > hollow)
                            {
                                FeaturePlacers.placeIfValidTreePos(world, trunkPlacer, random, dPos, config.trunkProvider());
                            } else
                            {
                                FeaturePlacers.placeIfValidTreePos(world, trunkPlacer, random, dPos, config.branchProvider());
                            }
                        } else
                        {
                            FeaturePlacers.placeIfValidRootPos(world, decoPlacer, random, dPos, config.rootsProvider());
                        }
                    }
                }
            }
        }

        // build the tree hole

        // build the trunk upwards
        for (int dx = -radius; dx <= radius; dx++)
        {
            for (int dz = -radius; dz <= radius; dz++)
            {
                for (int dy = 0; dy <= height; dy++)
                {
                    BlockPos dPos = pos.add(dx, dy, dz);
                    // determine how far we are from the center.
                    int ax = Math.abs(dx);
                    int az = Math.abs(dz);
                    int dist = Math.max(ax, az) + (Math.min(ax, az) >> 1);

                    // make a trunk!
//                    if (dist <= radius)
//                    {
//                        if (!dPos.isWithinDistance(pos, radius))
//                            FeaturePlacers.placeIfValidTreePos(world, trunkPlacer, random, dPos, config.trunkProvider());
//                    }
                    if (dist <= radius && dist > hollow)
                    {
                        FeaturePlacers.placeIfValidTreePos(world, trunkPlacer, random, dPos, config.trunkProvider());
                    }

                    if (dist == hollow && dx == hollow)
                    {
                        world.setBlockState(dPos, Blocks.VINE.getDefaultState().with(VineBlock.EAST, true), 3);
                    }
                }
            }
        }
    }

    /**
     * Build the crown of the tree
     */
    protected void buildFullCrown(
        StructureWorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random, BlockPos pos,
        int radius,
        int height,
        TFTreeFeatureConfig config
    )
    {
        int crownRadius = radius * 4 + 2;
        int bvar = radius + 2;

        // okay, let's do 3-5 main branches starting at the bottom of the crown
        buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height - crownRadius, 0, crownRadius, 0.35D, bvar, bvar + 2, 2, true, config);

        // then, let's do 3-5 medium branches at the crown middle
        buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height - (crownRadius / 2), 0, crownRadius, 0.28D, bvar, bvar + 2, 1, true, config);

        // finally, let's do 2-4 main branches at the crown top
        buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height, 0, crownRadius, 0.15D, 2, 4, 2, true, config);

        // and extra finally, let's do 3-6 medium branches going straight up
        buildBranchRing(world, trunkPlacer, leavesPlacer, random, pos, radius, height, 0, (crownRadius / 2), 0.05D, bvar, bvar + 2, 1, true, config);

        // this glass sphere approximates where we want our crown
        //drawBlob(x, y + height, z, (byte)crownRadius, (byte)Blocks.GLASS, false);
    }

    /**
     * Make a small branch at a certain height
     */
    protected void makeSmallBranch(
        WorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos pos,
        int radius,
        int branchHeight,
        double length,
        double angle,
        double tilt,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        BlockPos src = FeatureLogic.translate(pos.up(branchHeight), radius, angle, 0.5);
        makeSmallBranch(world, trunkPlacer, leavesPlacer, random, src, length, angle, tilt, leafy, config);
    }

    /**
     * Make a small branch with a leaf blob at the end
     */
    protected void makeSmallBranch(
        WorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos src,
        double length,
        double angle,
        double tilt,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);

        FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src, dest, config.branchProvider());

        if (leafy)
        {
            float leafRad = random.nextInt(2) + 1.5f;
            FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, dest, leafRad, leafRad, config.leavesProvider());
        }
    }

    /**
     * Make a branch!
     */
    protected void makeMedBranch(
        WorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random, BlockPos pos,
        int radius,
        int branchHeight,
        double length,
        double angle,
        double tilt,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        BlockPos src = FeatureLogic.translate(pos.up(branchHeight), radius, angle, 0.5);
        makeMedBranch(world, trunkPlacer, leavesPlacer, random, src, length, angle, tilt, leafy, config);
    }

    /**
     * Make a branch!
     */
    protected void makeMedBranch(
        WorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos src,
        double length,
        double angle,
        double tilt,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);

        FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src, dest, config.branchProvider());

        // with leaves!

        if (leafy)
        // and a blob at the end
        {
            FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, dest, 2.5f, 2.5f, config.leavesProvider());
        }

        // and several small branches

        int numShoots = random.nextInt(2) + 1;
        double angleInc, angleVar, outVar, tiltVar;

        angleInc = 0.8 / numShoots;

        for (int i = 0; i <= numShoots; i++)
        {

            angleVar = (angleInc * i) - 0.4;
            outVar = (random.nextDouble() * 0.8) + 0.2;
            tiltVar = (random.nextDouble() * 0.75) + 0.15;

            BlockPos bsrc = FeatureLogic.translate(src, length * outVar, angle, tilt);
            double slength = length * 0.4;

            makeSmallBranch(world, trunkPlacer, leavesPlacer, random, bsrc, slength, angle + angleVar, tilt * tiltVar, leafy, config);
        }
    }

    /**
     * Make a large, branching "base" branch off of the tree
     */
    protected void makeLargeBranch(
        StructureWorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos pos,
        int radius,
        int branchHeight,
        double length,
        double angle,
        double tilt,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        BlockPos src = FeatureLogic.translate(pos.up(branchHeight), radius, angle, 0.5);
        makeLargeBranch(world, trunkPlacer, leavesPlacer, random, src, length, angle, tilt, leafy, config);
    }

    /**
     * Make a large, branching "base" branch in a specific location.
     * <p>
     * The large branch will have 1-4 medium branches and several small branches too
     */
    protected void makeLargeBranch(
        StructureWorldAccess world,
        BiConsumer<BlockPos, BlockState> trunkPlacer,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos src,
        double length,
        double angle,
        double tilt,
        boolean leafy,
        TFTreeFeatureConfig config
    )
    {
        BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);

        // draw the main branch
        FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src, dest, config.branchProvider());

        // reinforce it
        // drawBresenham(src[0], src[1] + 1, src[2], dest[0], dest[1], dest[2], treeBlock, true);
        int reinforcements = random.nextInt(3);
        for (int i = 0; i <= reinforcements; i++)
        {
            int vx = (i & 2) == 0 ? 1 : 0;
            int vy = (i & 1) == 0 ? 1 : -1;
            int vz = (i & 2) == 0 ? 0 : 1;
            FeaturePlacers.drawBresenhamBranch(world, trunkPlacer, random, src.add(vx, vy, vz), dest, config.branchProvider());
        }

        if (leafy)
        {
            // add a leaf blob at the end
            FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, dest.up(), 3.5f, 3.5f, config.leavesProvider());
        }

        // go about halfway out and make a few medium branches.
        // the number of medium branches we can support depends on the length of the big branch
        // every other branch switches sides
        int numMedBranches = random.nextInt((int) (length / 6)) + random.nextInt(2) + 1;

        for (int i = 0; i <= numMedBranches; i++)
        {
            double outVar = (random.nextDouble() * 0.3) + 0.3;
            double angleVar = random.nextDouble() * 0.225 * ((i & 1) == 0 ? 1.0 : -1.0);
            BlockPos bsrc = FeatureLogic.translate(src, length * outVar, angle, tilt);

            makeMedBranch(world, trunkPlacer, leavesPlacer, random, bsrc, length * 0.6, angle + angleVar, tilt, leafy, config);
        }

        // make 1-2 small ones near the base
        int numSmallBranches = random.nextInt(2) + 1;
        for (int i = 0; i <= numSmallBranches; i++)
        {

            double outVar = (random.nextDouble() * 0.25) + 0.25;
            double angleVar = random.nextDouble() * 0.25 * ((i & 1) == 0 ? 1.0 : -1.0);
            BlockPos bsrc = FeatureLogic.translate(src, length * outVar, angle, tilt);

            makeSmallBranch(world, trunkPlacer, leavesPlacer, random, bsrc, Math.max(length * 0.3, 2), angle + angleVar, tilt, leafy, config);
        }

        if (random.nextInt(LEAF_DUNGEON_CHANCE) == 0)
        {
            makeLeafDungeon(world, leavesPlacer, random, dest.up(), config);
        }
    }

    /**
     * Make a root
     */
    protected void makeRoot(
        WorldAccess worldReader,
        Random random,
        BlockPos pos,
        int radius,
        int branchHeight,
        double length,
        double angle,
        double tilt,
        TFTreeFeatureConfig config
    )
    {
        BlockPos src = FeatureLogic.translate(pos.up(branchHeight), radius, angle, 0.5);
        BlockPos dest = FeatureLogic.translate(src, length, angle, tilt);

        FeaturePlacers.traceExposedRoot(worldReader, (checkedPos, state) -> worldReader.setBlockState(checkedPos, state, 3), random, config.rootsProvider(), config.rootsProvider(), new VoxelBresenhamIterator(src, dest));
    }

    private void makeLeafDungeon(
        StructureWorldAccess world,
        BiConsumer<BlockPos, BlockState> leavesPlacer,
        Random random,
        BlockPos pos,
        TFTreeFeatureConfig config
    )
    {
        // make leaves
        FeaturePlacers.placeSpheroid(world, leavesPlacer, FeaturePlacers.VALID_TREE_POS, random, pos, 4.5f, 4.5f, config.leavesProvider());
        // wood support
        drawBlob(world, pos, 3, config.branchProvider().get(random, pos));
        // air
        drawBlob(world, pos, 2, Blocks.AIR.getDefaultState());

        // spawner
        world.setBlockState(pos.up(), Blocks.SPAWNER.getDefaultState(), 16 | 2);

        // treasure chests?
        Direction chestDir = Direction.Type.HORIZONTAL.random(random);
        pos = pos.offset(chestDir, 2);
        world.setBlockState(pos, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, chestDir.getOpposite()), 2);
        if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity lootChest)
        {
            lootChest.setLootTable(SpellDimension.modLoc("pool/start"), world.getSeed() * pos.getX() * pos.getY() * pos.getZ());
        }
    }

    /**
     * Draw a giant blob of whatevs.
     */
    public static void drawBlob(WorldAccess world, BlockPos pos, int rad, BlockState state)
    {
        // then trace out a quadrant
        for (byte dx = 0; dx <= rad; dx++)
        {
            for (byte dy = 0; dy <= rad; dy++)
            {
                for (byte dz = 0; dz <= rad; dz++)
                {
                    // determine how far we are from the center.
                    int dist;
                    if (dx >= dy && dx >= dz)
                    {
                        dist = dx + (Math.max(dy, dz) >> 1) + (Math.min(dy, dz) >> 2);
                    } else if (dy >= dx && dy >= dz)
                    {
                        dist = dy + (Math.max(dx, dz) >> 1) + (Math.min(dx, dz) >> 2);
                    } else
                    {
                        dist = dz + (Math.max(dx, dy) >> 1) + (Math.min(dx, dy) >> 2);
                    }


                    // if we're inside the blob, fill it
                    if (dist <= rad)
                    {
                        // do eight at a time for easiness!
                        world.setBlockState(pos.add(+dx, +dy, +dz), state, 3);
                        world.setBlockState(pos.add(+dx, +dy, -dz), state, 3);
                        world.setBlockState(pos.add(-dx, +dy, +dz), state, 3);
                        world.setBlockState(pos.add(-dx, +dy, -dz), state, 3);
                        world.setBlockState(pos.add(+dx, -dy, +dz), state, 3);
                        world.setBlockState(pos.add(+dx, -dy, -dz), state, 3);
                        world.setBlockState(pos.add(-dx, -dy, +dz), state, 3);
                        world.setBlockState(pos.add(-dx, -dy, -dz), state, 3);
                    }
                }
            }
        }
    }
}