package karashokleo.spell_dimension.mixin.phase;

import com.mojang.authlib.GameProfile;
import karashokleo.spell_dimension.content.misc.INoClip;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 取消浸水效果
 * 入水后不取消疾跑
 * 提升水下视野
 * Learned from <a href="https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/client/ClientPlayerEntityMixin.java">...</a>
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity
{
    @Shadow
    public Input input;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile)
    {
        super(world, profile);
    }

    /**
     * Cancels water submersion effects when clipping.
     */
    @Inject(
            method = "updateWaterSubmersionState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;updateWaterSubmersionState()Z",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void onUpdateWaterSubmersionState(CallbackInfoReturnable<Boolean> cir)
    {
        if (INoClip.noClip(this))
            cir.setReturnValue(this.isSubmergedInWater);
    }

    /**
     * Prevents the player from having their sprinting stopped when clipping through water.
     */
    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;setSprinting(Z)V",
                    ordinal = 3,
                    shift = At.Shift.AFTER
            )
    )
    private void preventStopSprinting(CallbackInfo ci)
    {
        if (INoClip.noClip(this) && this.input.hasForwardMovement())
            this.setSprinting(true);
    }

    /**
     * Fixes underwater vision when clipping to be that of spectator's.
     */
    @ModifyArg(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;clamp(III)I",
                    ordinal = 0
            ),
            index = 0
    )
    private int fixUnderwaterVision(int perTick)
    {
        return INoClip.noClip(this) ? perTick + (this.isSpectator() ? 0 : 10 - 1) : perTick;
    }
}
