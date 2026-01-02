package karashokleo.spell_dimension.content.misc;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;

public interface SpawnerExtension
{
    String KEY_REMAIN = "Remain";
    String KEY_NO_REMAIN_ACTION = "NoRemainAction";

    int DEFAULT_REMAIN = 20;

    default NbtCompound getEntityNbt()
    {
        throw new UnsupportedOperationException();
    }

    default int getRemain()
    {
        throw new UnsupportedOperationException();
    }

    default void setRemain(int remain)
    {
        throw new UnsupportedOperationException();
    }

    default boolean noRemain()
    {
        return this.getRemain() <= 0;
    }

    default NoRemainAction getNoRemainAction()
    {
        throw new UnsupportedOperationException();
    }

    default void setNoRemainAction(NoRemainAction action)
    {
        throw new UnsupportedOperationException();
    }

    enum NoRemainAction
    {
        NONE((world, pos, spawnerLogic) ->
        {
        }),
        BREAK((world, pos, spawnerLogic) -> world.breakBlock(pos, false));

        public final SpawnerLogicConsumer action;

        NoRemainAction(SpawnerLogicConsumer action)
        {
            this.action = action;
        }
    }

    @FunctionalInterface
    interface SpawnerLogicConsumer
    {
        void accept(ServerWorld world, BlockPos pos, MobSpawnerLogic spawnerLogic);
    }
}
