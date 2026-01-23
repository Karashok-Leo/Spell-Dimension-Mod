package karashokleo.spell_dimension.mixin.minion;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.content.entity.brain.SoulMinionBrain;
import karashokleo.spell_dimension.content.entity.goal.AttackWithSoulOwnerGoal;
import karashokleo.spell_dimension.content.entity.goal.FollowSoulOwnerGoal;
import karashokleo.spell_dimension.content.entity.goal.StandbySoulMinionGoal;
import karashokleo.spell_dimension.content.entity.goal.TrackSoulOwnerAttackerGoal;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin
{
    @Shadow
    @Final
    public GoalSelector targetSelector;

    @Shadow
    @Final
    public GoalSelector goalSelector;

    @WrapOperation(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/mob/MobEntity;initGoals()V"
        )
    )
    private void wrap_initGoals(MobEntity instance, Operation<Void> original)
    {
        original.call(instance);
        var mob = (MobEntity) (Object) this;
        this.targetSelector.add(1, new TrackSoulOwnerAttackerGoal(mob));
        this.targetSelector.add(2, new AttackWithSoulOwnerGoal(mob));
        this.goalSelector.add(0, new StandbySoulMinionGoal(mob));
        this.goalSelector.add(6, new FollowSoulOwnerGoal(mob));
    }

    @WrapMethod(method = "setTarget")
    private void wrap_setTarget(LivingEntity target, Operation<Void> original)
    {
        if (SoulControl.isSoulMinion(target, (MobEntity) (Object) this))
        {
            return;
        }
        original.call(target);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void inject_tick(CallbackInfo ci)
    {
        MobEntity mob = (MobEntity) (Object) this;
        if (mob.getWorld().isClient())
        {
            return;
        }

        SoulMinionBrain.tickSoulMinionStandbyMode(mob);
    }
}
