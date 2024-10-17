package karashokleo.spell_dimension.content.item.logic;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

public class ConsciousnessEventManager
{
    public static BlockPos findTeleportTarget(ServerWorld source, ServerWorld destination, BlockPos pos)
    {
        WorldBorder worldBorder = destination.getWorldBorder();
        double factor = DimensionType.getCoordinateScaleFactor(source.getDimension(), destination.getDimension());
        int resultX = (int) (pos.getX() * factor);
        int resultZ = (int) (pos.getZ() * factor);
        WorldChunk worldChunk = destination.getChunk(ChunkSectionPos.getSectionCoord(resultX), ChunkSectionPos.getSectionCoord(resultZ));
        int resultY = worldChunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, resultX & 15, resultZ & 15);
        return worldBorder.clamp(resultX, resultY, resultZ);
    }
}
