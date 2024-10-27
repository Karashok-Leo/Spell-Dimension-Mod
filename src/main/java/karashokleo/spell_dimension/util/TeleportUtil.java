package karashokleo.spell_dimension.util;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;

public class TeleportUtil
{
    public static void teleportPlayer(Entity entity, BlockPos targetPos)
    {
        if (!(entity.getWorld() instanceof ServerWorld serverWorld))
            return;

        serverWorld.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, new ChunkPos(targetPos), 1, entity.getId());

        playTeleportSound(entity, serverWorld);

        entity.detach();
        entity.teleport(targetPos.getX(), targetPos.getY(), targetPos.getZ());

        playTeleportSound(entity, serverWorld);
    }

    public static void teleportPlayerChangeDimension(ServerPlayerEntity player, ServerWorld world, BlockPos targetPos)
    {
        if (player.getWorld().getRegistryKey().equals(world.getRegistryKey()))
            throw new IllegalArgumentException("Should not use this method to teleport player to the same dimension");

        world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, new ChunkPos(targetPos), 1, player.getId());

        playTeleportSound(player, world);

        player.detach();
        TeleportTarget target = new TeleportTarget(
                targetPos.toCenterPos(),
                new Vec3d(0, 0, 0),
                player.getYaw(),
                player.getPitch()
        );
        FabricDimensions.teleport(player, world, target);

        playTeleportSound(player, world);
    }

    public static void playTeleportSound(Entity entity, ServerWorld serverWorld)
    {
        serverWorld.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1F, 1F);
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

    public static int getTopY(ServerWorld world, int x, int z)
    {
        WorldChunk worldChunk = world.getChunk(ChunkSectionPos.getSectionCoord(x), ChunkSectionPos.getSectionCoord(z));
        return worldChunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
    }
}
