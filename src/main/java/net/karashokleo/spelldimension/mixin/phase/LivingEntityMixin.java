package net.karashokleo.spelldimension.mixin.phase;

import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.karashokleo.spelldimension.misc.INoClip;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements INoClip
{
    private LivingEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Override
    public boolean isNoClip()
    {
        return ((LivingEntity) (Object) this).hasStatusEffect(AllStatusEffects.PHASE_EFFECT);
    }

    @Override
    public boolean doesRenderOnFire()
    {
        return super.doesRenderOnFire() && !this.isNoClip();
    }

    @Override
    public boolean isInSneakingPose()
    {
        return super.isInSneakingPose() && !this.isNoClip();
    }

    @Override
    public boolean hasNoGravity()
    {
        return super.hasNoGravity() || this.isNoClip();
    }

    @Override
    public PistonBehavior getPistonBehavior()
    {
        if (this.isNoClip()) return PistonBehavior.IGNORE;
        else return super.getPistonBehavior();
    }

    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    private void onCanHit(CallbackInfoReturnable<Boolean> cir)
    {
        if (this.isNoClip())
            cir.setReturnValue(false);
    }

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    private void onIsPushable(CallbackInfoReturnable<Boolean> cir)
    {
        if (this.isNoClip())
            cir.setReturnValue(false);
    }
}
