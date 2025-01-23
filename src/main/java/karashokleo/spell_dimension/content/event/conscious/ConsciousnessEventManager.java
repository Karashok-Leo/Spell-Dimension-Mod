package karashokleo.spell_dimension.content.event.conscious;

import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

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
