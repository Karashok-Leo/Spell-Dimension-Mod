package karashokleo.spell_dimension.content.misc;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ThreadedAnvilChunkStorageExtension
{
    void updatePositionOfControlledEntity(ServerPlayerEntity player, Entity entity);
}
