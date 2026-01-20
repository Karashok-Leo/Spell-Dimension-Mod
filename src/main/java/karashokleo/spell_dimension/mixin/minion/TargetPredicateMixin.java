package karashokleo.spell_dimension.mixin.minion;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TargetPredicate.class)
public abstract class TargetPredicateMixin
{
    @WrapOperation(
        method = "test",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;isTeammate(Lnet/minecraft/entity/Entity;)Z"
        )
    )
    private boolean inject_test(LivingEntity instance, Entity entity, Operation<Boolean> original)
    {
        if (instance instanceof MobEntity mob)
        {
            SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
            PlayerEntity owner = minionComponent.getOwner();
            if (owner != null && owner != entity)
            {
                return false;
            }
        }
        return original.call(instance, entity);
    }
}
