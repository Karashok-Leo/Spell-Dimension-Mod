package karashokleo.spell_dimension.mixin.minion;

import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrackTargetGoal.class)
public abstract class TrackTargetGoalMixin
{
    @Shadow
    @Final
    protected MobEntity mob;

    @Shadow
    public abstract void stop();

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void inject_start(CallbackInfo ci)
    {
        if (!SoulControl.isSoulMinion(mob.getTarget(), mob))
        {
            return;
        }

        stop();
        ci.cancel();
    }
}
