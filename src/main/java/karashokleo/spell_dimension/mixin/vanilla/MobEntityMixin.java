package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.content.entity.goal.AttackWithSoulControllerGoal;
import karashokleo.spell_dimension.content.entity.goal.TrackSoulControllerAttackerGoal;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
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
    public GoalSelector targetSelector;

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
        this.targetSelector.add(1, new TrackSoulControllerAttackerGoal(mob));
        this.targetSelector.add(2, new AttackWithSoulControllerGoal(mob));
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
}
