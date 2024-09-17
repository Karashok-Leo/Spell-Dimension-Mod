package karashokleo.spell_dimension.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import karashokleo.spell_dimension.content.misc.ISpawnerExtension;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin implements ISpawnerExtension
{
    @Unique
    private int remain = DEFAULT_REMAIN;

    @Unique
    private NoRemainAction noRemainAction = NoRemainAction.BREAK;

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
    }

    @ModifyExpressionValue(
            method = "serverTick",
            at = @At(value = "INVOKE", target = "Ljava/util/Optional;isEmpty()Z")
    )
    private boolean cancelSpawn_inject_serverTick(boolean original)
    {
        return this.noRemain() || original;
    }

    @Inject(
            method = "serverTick",
            at = @At("HEAD")
    )
    private void noRemainAction_inject_serverTick(ServerWorld world, BlockPos pos, CallbackInfo ci)
    {
        if (this.noRemain())
            this.getNoRemainAction().action(world, pos);
    }

    @Inject(
            method = "readNbt",
            at = @At("HEAD")
    )
    private void inject_readNbt(World world, BlockPos pos, NbtCompound nbt, CallbackInfo ci)
    {
        this.remain = nbt.contains(KEY_REMAIN) ? nbt.getShort(KEY_REMAIN) : DEFAULT_REMAIN;
    }

    @Inject(
            method = "writeNbt",
            at = @At("HEAD")
    )
    private void inject_writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir)
    {
        nbt.putShort(KEY_REMAIN, (short) this.remain);
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
