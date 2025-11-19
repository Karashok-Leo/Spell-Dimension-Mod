package karashokleo.spell_dimension.mixin.vanilla;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import karashokleo.spell_dimension.content.misc.ThreadedAnvilChunkStorageExtension;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkRenderDistanceCenterS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.PlayerChunkWatchingManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin implements ThreadedAnvilChunkStorageExtension
{
    @Shadow
    int watchDistance;

    @Shadow
    @Final
    ServerWorld world;

    @Shadow
    @Final
    private Int2ObjectMap<ThreadedAnvilChunkStorage.EntityTracker> entityTrackers;

    @Shadow
    @Final
    private PlayerChunkWatchingManager playerChunkWatchingManager;

    @Shadow
    @Final
    private ThreadedAnvilChunkStorage.TicketManager ticketManager;

    @Shadow
    protected abstract void sendWatchPackets(ServerPlayerEntity player, ChunkPos pos, MutableObject<ChunkDataS2CPacket> packet, boolean oldWithinViewDistance, boolean newWithinViewDistance);

    @Shadow
    protected abstract boolean doesNotGenerateChunks(ServerPlayerEntity player);

    @Override
    public void updatePositionOfControlledEntity(ServerPlayerEntity player, Entity entity)
    {
        for (ThreadedAnvilChunkStorage.EntityTracker tracker : this.entityTrackers.values())
        {
            if (tracker.entity == player)
            {
                tracker.updateTrackedStatus(this.world.getPlayers());
            } else
            {
                tracker.updateTrackedStatus(player);
            }
        }

        ChunkSectionPos previousSection = player.getWatchedSection();
        ChunkSectionPos currentSection = ChunkSectionPos.from(entity);
        long previousChunk = previousSection.toChunkPos().toLong();
        long currentChunk = currentSection.toChunkPos().toLong();

        boolean previouslyDisabled = this.playerChunkWatchingManager.isWatchDisabled(player);
        boolean currentDisabled = this.doesNotGenerateChunks(player);

        if (previousSection.asLong() != currentSection.asLong() ||
            previouslyDisabled != currentDisabled)
        {
            // 相当于 updateWatchedSection，但是用实体的位置
            player.setWatchedSection(currentSection);
            player.networkHandler.sendPacket(
                new ChunkRenderDistanceCenterS2CPacket(currentSection.getSectionX(), currentSection.getSectionZ())
            );

            if (!previouslyDisabled)
            {
                this.ticketManager.handleChunkLeave(previousSection, player);
            }
            if (!currentDisabled)
            {
                this.ticketManager.handleChunkEnter(currentSection, player);
            }
            if (!previouslyDisabled && currentDisabled)
            {
                this.playerChunkWatchingManager.disableWatch(player);
            }
            if (previouslyDisabled && !currentDisabled)
            {
                this.playerChunkWatchingManager.enableWatch(player);
            }
            if (previousChunk != currentChunk)
            {
                this.playerChunkWatchingManager.movePlayer(previousChunk, currentChunk, player);
            }
        }

        int prevX = previousSection.getSectionX();
        int prevZ = previousSection.getSectionZ();
        int curX = currentSection.getSectionX();
        int curZ = currentSection.getSectionZ();
        int padding = this.watchDistance + 1;

        if (Math.abs(prevX - curX) <= padding * 2 && Math.abs(prevZ - curZ) <= padding * 2)
        {
            int minX = Math.min(curX, prevX) - padding;
            int minZ = Math.min(curZ, prevZ) - padding;
            int maxX = Math.max(curX, prevX) + padding;
            int maxZ = Math.max(curZ, prevZ) + padding;
            for (int x = minX; x <= maxX; ++x)
            {
                for (int z = minZ; z <= maxZ; ++z)
                {
                    boolean oldIn = ThreadedAnvilChunkStorage.isWithinDistance(x, z, prevX, prevZ, this.watchDistance);
                    boolean newIn = ThreadedAnvilChunkStorage.isWithinDistance(x, z, curX, curZ, this.watchDistance);
                    this.sendWatchPackets(player, new ChunkPos(x, z), new MutableObject<>(), oldIn, newIn);
                }
            }
        } else
        {
            for (int x = prevX - padding; x <= prevX + padding; ++x)
            {
                for (int z = prevZ - padding; z <= prevZ + padding; ++z)
                {
                    if (!ThreadedAnvilChunkStorage.isWithinDistance(x, z, prevX, prevZ, this.watchDistance))
                    {
                        continue;
                    }
                    this.sendWatchPackets(player, new ChunkPos(x, z), new MutableObject<>(), true, false);
                }
            }
            for (int x = curX - padding; x <= curX + padding; ++x)
            {
                for (int z = curZ - padding; z <= curZ + padding; ++z)
                {
                    if (!ThreadedAnvilChunkStorage.isWithinDistance(x, z, curX, curZ, this.watchDistance))
                    {
                        continue;
                    }
                    this.sendWatchPackets(player, new ChunkPos(x, z), new MutableObject<>(), false, true);
                }
            }
        }
    }
}
