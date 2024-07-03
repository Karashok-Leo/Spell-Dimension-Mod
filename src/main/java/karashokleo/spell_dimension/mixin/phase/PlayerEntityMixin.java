package karashokleo.spell_dimension.mixin.phase;

import karashokleo.spell_dimension.content.component.MageComponent;
import karashokleo.spell_dimension.content.misc.INoClip;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings("InvalidInjectorMethodSignature")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Override
    public boolean isFireImmune()
    {
        return super.isFireImmune() || MageComponent.get((PlayerEntity) (Object) this).school() == SpellSchools.FIRE;
    }

    /*
     * 更新noClip等字段
     * 取消姿势更新
     * 取消挖掘惩罚
     * 取消实体碰撞
     * 取消液体交互
     * Learned from
     * https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/PlayerEntityMixin.java
     * */
    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world)
    {
        super(entityType, world);
    }

    /**
     * Updates the player's clipping value based on our custom parameters.
     */
    @Inject(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;noClip:Z",
                    shift = At.Shift.AFTER
            )
    )
    private void onTickAfterNoClip(CallbackInfo ci)
    {
        if (INoClip.noClip(this))
        {
            this.noClip = true;
            this.fallDistance = 0;
            this.setOnGround(false);
        }
    }

    /**
     * Prevents the player's pose from updating when clipping.
     */
    @Inject(method = "updatePose", at = @At("HEAD"), cancellable = true)
    private void onUpdatePose(CallbackInfo ci)
    {
        if (INoClip.noClip(this))
        {
            this.setPose(EntityPose.STANDING);
            ci.cancel();
        }
    }

    /**
     * Ignores ground checks for block breaking speed when clipping.
     */
    @Inject(
            method = "getBlockBreakingSpeed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z",
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onGetBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir, float speed)
    {
        if (INoClip.noClip(this)) cir.setReturnValue(speed);
    }

    /**
     * Cancels any player collision code when clipping.
     */
    @Inject(method = "collideWithEntity", at = @At("HEAD"), cancellable = true)
    private void onCollideWithEntity(Entity entity, CallbackInfo ci)
    {
        if (INoClip.noClip(this)) ci.cancel();
    }

    /**
     * Cancels water interaction when clipping.
     */
    @Inject(method = "onSwimmingStart", at = @At("HEAD"), cancellable = true)
    private void onOnSwimmingStart(CallbackInfo ci)
    {
        if (INoClip.noClip(this)) ci.cancel();
    }
}
