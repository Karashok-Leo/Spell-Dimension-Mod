package karashokleo.spell_dimension.content.misc;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

public interface SpawnerExtension
{
    String KEY_REMAIN = "Remain";

    int DEFAULT_REMAIN = 20;

    NbtCompound getEntityNbt();

    int getRemain();

    void setRemain(int remain);

    default boolean noRemain()
    {
        return this.getRemain() <= 0;
    }

    NoRemainAction getNoRemainAction();

    void setNoRemainAction(NoRemainAction action);

    enum NoRemainAction
    {
        BREAK((world, pos) -> world.breakBlock(pos, false));

        final BiConsumer<ServerWorld, BlockPos> action;

        NoRemainAction(BiConsumer<ServerWorld, BlockPos> action)
        {
            this.action = action;
        }

        public void action(ServerWorld world, BlockPos pos)
        {
            action.accept(world, pos);
        }
    }
}
