package karashokleo.spell_dimension.content.event.conscious;

import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Optional;

public class ConsciousnessEventManager
{
    public static final int TIME_LIMIT = 20 * 60 * 10;
    public static final int RADIUS = 32;
    public static final int SPAWN_LIMIT = 100;

    public static int randomEventLevel(Random random, double playerLevel, double levelFactor)
    {
        return (int) playerLevel + random.nextInt((int) (100 * (levelFactor + 0.2)));
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
                            world.breakBlock(breakPos, false);
                            world.removeBlockEntity(breakPos);
                        }
                    }
    }

    public static Optional<BlockPos> tryFindSummonPos(ServerWorld world, BlockPos pos, EntityType<?> entityType)
    {
        Random random = world.getRandom();
        int range = RADIUS / 2;
        int x = random.nextBetweenExclusive(-range, range);
        int z = random.nextBetweenExclusive(-range, range);
        int y = -range;
        while (y < pos.getY() + range)
        {
            BlockPos checkPos = pos.add(x, y, z);
            if (world.getBlockState(checkPos).isAir() &&
                world.isSpaceEmpty(entityType.createSimpleBoundingBox(checkPos.getX(), checkPos.getY(), checkPos.getZ())))
                return Optional.of(checkPos);
            y++;
        }
        return Optional.empty();
    }
}
