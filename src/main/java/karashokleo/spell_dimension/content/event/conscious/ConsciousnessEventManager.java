package karashokleo.spell_dimension.content.event.conscious;

import karashokleo.spell_dimension.content.block.tile.ProtectiveCoverBlockTile;
import karashokleo.spell_dimension.content.entity.ConsciousnessEventEntity;
import karashokleo.spell_dimension.init.AllBlocks;
import karashokleo.spell_dimension.init.AllEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ConsciousnessEventManager
{
    public static final int TIME_LIMIT = 12000;
    public static final int RADIUS = 32;
    public static final int SPAWN_LIMIT = 100;

    public static int randomEventLevel(Random random, double playerLevel, double levelFactor)
    {
        return (int) (playerLevel * (1 + levelFactor * (1 + random.nextGaussian() * 0.4)));
    }

    @Nullable
    public static ConsciousnessEventEntity startEvent(ServerWorld world, BlockPos pos, int level)
    {
        placeAsBarrier(world, pos, RADIUS, TIME_LIMIT);
        ConsciousnessEventEntity event = AllEntities.CONSCIOUSNESS_EVENT.spawn(world, pos, SpawnReason.EVENT);
        if (event == null) return null;
        event.setPos(pos.getX(), pos.getY(), pos.getZ());
        event.setBoundingBox(RADIUS);
        event.initLevel(level);
        return event;
    }

    public static void placeAsBarrier(World world, BlockPos pos, int radius, int life)
    {
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    if (x == -radius || x == radius ||
                        y == -radius || y == radius ||
                        z == -radius || z == radius)
                    {
                        BlockPos placePos = pos.add(x, y, z);
                        if (world.getBlockState(placePos).isReplaceable())
                        {
                            world.setBlockState(placePos, AllBlocks.PROTECTIVE_COVER.block().getDefaultState());
                            if (world.getBlockEntity(placePos) instanceof ProtectiveCoverBlockTile tile)
                                tile.setLife(life);
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

    public static BlockPos findTeleportPos(ServerWorld source, ServerWorld destination, BlockPos pos)
    {
        WorldBorder worldBorder = destination.getWorldBorder();
        double factor = DimensionType.getCoordinateScaleFactor(source.getDimension(), destination.getDimension());
        int resultX = (int) (pos.getX() * factor);
        int resultZ = (int) (pos.getZ() * factor);
        int resultY = getTopY(destination, resultX, resultZ) + 1;
        return worldBorder.clamp(resultX, resultY, resultZ);
    }

    private static int getTopY(ServerWorld world, int x, int z)
    {
        WorldChunk worldChunk = world.getChunk(ChunkSectionPos.getSectionCoord(x), ChunkSectionPos.getSectionCoord(z));
        return worldChunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
    }
}
