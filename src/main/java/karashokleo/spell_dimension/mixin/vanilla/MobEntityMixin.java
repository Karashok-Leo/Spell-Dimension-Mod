package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin
{
    @Shadow
    @Final
    public GoalSelector goalSelector;

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/MobEntity;updateGoalControls()V"
            )
    )
    private void wrap_updateGoalControls(MobEntity instance, Operation<Void> original)
    {
        if (AllComponents.SOUL_CONTROLLER.get(instance).getOwner(instance.getWorld()) == null)
        {
            original.call(instance);
        } else
        {
            this.goalSelector.setControlEnabled(Goal.Control.MOVE, false);
            this.goalSelector.setControlEnabled(Goal.Control.JUMP, false);
            this.goalSelector.setControlEnabled(Goal.Control.LOOK, false);
        }
    }
}
