package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal
{
    @Shadow
    @Nullable
    protected LivingEntity targetEntity;

    private ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility)
    {
        super(mob, checkVisibility);
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void inject_start(CallbackInfo ci)
    {
        if (!SoulControl.isSoulMinion(targetEntity, mob))
        {
            return;
        }

        stop();
        ci.cancel();
    }

    @Inject(method = "findClosestTarget", at = @At("TAIL"))
    private void inject_findClosestTarget(CallbackInfo ci)
    {
        if (!SoulControl.isSoulMinion(targetEntity, mob))
        {
            return;
        }

        targetEntity = null;
    }
}
