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
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
                Vec3d.ZERO,
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

    public static CompletableFuture<Optional<BlockPos>> getTeleportPosFuture(ServerWorld source, ServerWorld destination, BlockPos pos)
    {
        return getTopYFuture(destination, pos.getX(), pos.getZ())
                .thenApply(
                        optionalY -> optionalY.map(
                                resultY ->
                                {
                                    WorldBorder worldBorder = destination.getWorldBorder();
                                    double factor = DimensionType.getCoordinateScaleFactor(source.getDimension(), destination.getDimension());
                                    int resultX = (int) (pos.getX() * factor);
                                    int resultZ = (int) (pos.getZ() * factor);
                                    return worldBorder.clamp(resultX, resultY, resultZ);
                                }
                        )
                );
    }

    public static CompletableFuture<Optional<BlockPos>> getTopPosFuture(ServerWorld world, BlockPos pos)
    {
        return getTopYFuture(world, pos.getX(), pos.getZ())
                .thenApply(optionalY -> optionalY.map(topY -> new BlockPos(pos.getX(), topY, pos.getZ())));
    }

    public static CompletableFuture<Optional<Integer>> getTopYFuture(ServerWorld world, int x, int z)
    {
        return world.getChunkManager()
                .getChunkFutureSyncOnMainThread(
                        ChunkSectionPos.getSectionCoord(x),
                        ChunkSectionPos.getSectionCoord(z),
                        ChunkStatus.FULL,
                        true
                )
                .thenApply(either -> either.left()
                        .map(chunk -> chunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x & 15, z & 15)));
    }
}
