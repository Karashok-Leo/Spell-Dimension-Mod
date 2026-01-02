package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import karashokleo.spell_dimension.content.misc.SpawnerExtension;
import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin implements SpawnerExtension
{
    @Shadow
    @Nullable
    private MobSpawnerEntry spawnEntry;

    @Shadow
    protected abstract void setSpawnEntry(@Nullable World world, BlockPos pos, MobSpawnerEntry spawnEntry);

    @Unique
    private int remain = DEFAULT_REMAIN;

    @Unique
    private NoRemainAction noRemainAction = NoRemainAction.BREAK;

    @ModifyExpressionValue(
        method = "serverTick",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/Optional;isEmpty()Z",
            ordinal = 0
        )
    )
    private boolean cancelSpawn_inject_serverTick(boolean original)
    {
        return this.noRemain() || original;
    }

    @Inject(
        method = "serverTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;syncWorldEvent(ILnet/minecraft/util/math/BlockPos;I)V"
        )
    )
    private void updateRemain_inject_serverTick(ServerWorld world, BlockPos pos, CallbackInfo ci)
    {
        this.remain--;
        if (this.noRemain())
        {
            this.setSpawnEntry(world, pos, new MobSpawnerEntry());
            this.getNoRemainAction().action.accept(world, pos, (MobSpawnerLogic) (Object) this);
        }

        if (!(world.getBlockEntity(pos) instanceof MobSpawnerBlockEntity spawner))
        {
            return;
        }

        spawner.markDirty();
        world.updateListeners(pos, spawner.getCachedState(), spawner.getCachedState(), Block.NOTIFY_ALL);
    }

    @Inject(
        method = "readNbt",
        at = @At("HEAD")
    )
    private void inject_readNbt(World world, BlockPos pos, NbtCompound nbt, CallbackInfo ci)
    {
        this.remain = nbt.contains(KEY_REMAIN) ? nbt.getInt(KEY_REMAIN) : DEFAULT_REMAIN;
        this.noRemainAction = nbt.contains(KEY_NO_REMAIN_ACTION) ? NoRemainAction.values()[nbt.getInt(KEY_NO_REMAIN_ACTION)] : NoRemainAction.BREAK;
    }

    @Inject(
        method = "writeNbt",
        at = @At("HEAD")
    )
    private void inject_writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir)
    {
        nbt.putInt(KEY_REMAIN, this.remain);
        nbt.putInt(KEY_NO_REMAIN_ACTION, this.noRemainAction.ordinal());
    }

    @Override
    public NbtCompound getEntityNbt()
    {
        if (this.spawnEntry == null)
        {
            return new NbtCompound();
        }
        return this.spawnEntry.getNbt();
    }

    @Override
    public int getRemain()
    {
        return remain;
    }

    @Override
    public void setRemain(int remain)
    {
        this.remain = remain;
    }

    @Override
    public NoRemainAction getNoRemainAction()
    {
        return noRemainAction;
    }

    @Override
    public void setNoRemainAction(NoRemainAction noRemainAction)
    {
        this.noRemainAction = noRemainAction;
    }
}
