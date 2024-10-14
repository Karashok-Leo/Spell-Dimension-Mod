package karashokleo.spell_dimension.content.worldgen;


import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.treedecorator.TreeDecorator;

import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class TFTreeFeature<T extends TFTreeFeatureConfig> extends Feature<T>
{
    public TFTreeFeature(Codec<T> configIn)
    {
        super(configIn);
    }

    // [VanillaCopy] TreeFeature.place, swapped TreeConfiguration for generic <T extends TFTreeFeatureConfig>. Omitted code are commented out instead of deleted
    @Override
    public final boolean generate(FeatureContext<T> context)
    {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();
        BlockPos blockpos = context.getOrigin();
        T treeFeatureConfig = context.getConfig();
        Set<BlockPos> set = Sets.newHashSet();
        Set<BlockPos> set1 = Sets.newHashSet();
        Set<BlockPos> set2 = Sets.newHashSet();
        Set<BlockPos> set3 = Sets.newHashSet();
        BiConsumer<BlockPos, BlockState> trunkPlacer = createPlacer(set, structureWorldAccess);
        BiConsumer<BlockPos, BlockState> leavesPlacer = createPlacer(set1, structureWorldAccess);
        BiConsumer<BlockPos, BlockState> decorationPlacer = createPlacer(set2, structureWorldAccess);
        BiConsumer<BlockPos, BlockState> biConsumer4 = createPlacer(set3, structureWorldAccess);
        boolean flag = this.generate(structureWorldAccess, random, blockpos, trunkPlacer, leavesPlacer, decorationPlacer, treeFeatureConfig);
        if (flag && (!set1.isEmpty() || !set2.isEmpty()))
        {
            if (!treeFeatureConfig.decorators().isEmpty())
            {
                TreeDecorator.Generator generator = new TreeDecorator.Generator(structureWorldAccess, biConsumer4, random, set1, set2, set);
                treeFeatureConfig.decorators().forEach((decorator) -> decorator.generate(generator));
            }

            return BlockBox.encompassPositions(Iterables.concat(set, set1, set2, set3)).map((boundingBox) ->
            {
                VoxelSet discreteVoxelShape = placeLogsAndLeaves(structureWorldAccess, boundingBox, set1, set3, set);
                StructureTemplate.updateCorner(structureWorldAccess, 3, discreteVoxelShape, boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ());
                return true;
            }).orElse(false);
        } else return false;
    }

    private static BiConsumer<BlockPos, BlockState> createPlacer(Set<BlockPos> set, StructureWorldAccess structureWorldAccess)
    {
        return (pos, state) ->
        {
            set.add(pos.toImmutable());
            structureWorldAccess.setBlockState(pos, state, 19);
        };
    }

    // Vanilla Copy
    private static VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> trunkPositions, Set<BlockPos> decorationPositions, Set<BlockPos> rootPositions)
    {
        VoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());
        List<Set<BlockPos>> list = Lists.newArrayList();

        for (int j = 0; j < 7; ++j)
        {
            list.add(Sets.newHashSet());
        }

        for (BlockPos blockPos : Lists.newArrayList(Sets.union(decorationPositions, rootPositions)))
            if (box.contains(blockPos))
                voxelSet.set(blockPos.getX() - box.getMinX(), blockPos.getY() - box.getMinY(), blockPos.getZ() - box.getMinZ());

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int k = 0;
        list.get(0).addAll(trunkPositions);

        while (true)
        {
            while (k >= 7 || !list.get(k).isEmpty())
            {
                if (k >= 7) return voxelSet;

                Iterator<BlockPos> iterator = list.get(k).iterator();
                BlockPos blockPos2 = iterator.next();
                iterator.remove();
                if (box.contains(blockPos2))
                {
                    if (k != 0)
                    {
                        BlockState blockState = world.getBlockState(blockPos2);

                        world.setBlockState(blockPos2, blockState.with(Properties.DISTANCE_1_7, k), 19);
                    }

                    voxelSet.set(blockPos2.getX() - box.getMinX(), blockPos2.getY() - box.getMinY(), blockPos2.getZ() - box.getMinZ());
                    Direction[] var25 = Direction.values();

                    for (Direction direction : var25)
                    {
                        mutable.set(blockPos2, direction);
                        if (box.contains(mutable))
                        {
                            int l = mutable.getX() - box.getMinX();
                            int m = mutable.getY() - box.getMinY();
                            int n = mutable.getZ() - box.getMinZ();
                            if (!voxelSet.contains(l, m, n))
                            {
                                BlockState blockState2 = world.getBlockState(mutable);
                                OptionalInt optionalInt = LeavesBlock.getOptionalDistanceFromLog(blockState2);
                                if (optionalInt.isPresent())
                                {
                                    int o = Math.min(optionalInt.getAsInt(), k + 1);
                                    if (o < 7)
                                    {
                                        list.get(o).add(mutable.toImmutable());
                                        k = Math.min(k, o);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ++k;
        }
    }

    /**
     * This works akin to the AbstractTreeFeature.generate, but put our branches and roots here
     */
    protected abstract boolean generate(
            StructureWorldAccess world,
            Random random,
            BlockPos pos,
            BiConsumer<BlockPos, BlockState> trunkPlacer,
            BiConsumer<BlockPos, BlockState> leavesPlacer,
            BiConsumer<BlockPos, BlockState> decorationPlacer,
            T config
    );
}
