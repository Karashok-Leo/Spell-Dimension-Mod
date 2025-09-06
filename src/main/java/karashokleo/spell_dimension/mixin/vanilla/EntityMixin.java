package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @WrapOperation(
            method = "hasControllingPassenger",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getControllingPassenger()Lnet/minecraft/entity/LivingEntity;"
            )
    )
    private LivingEntity wrap_getControllingPassenger_0(Entity instance, Operation<LivingEntity> original)
    {
        LivingEntity owner = AllComponents.SOUL_CONTROLLER.get(instance).getOwner(instance.getWorld());
        if (owner != null)
        {
            return owner;
        }
        return original.call(instance);
    }

    @WrapOperation(
            method = "isLogicalSideForUpdatingMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getControllingPassenger()Lnet/minecraft/entity/LivingEntity;"
            )
    )
    private LivingEntity wrap_getControllingPassenger_1(Entity instance, Operation<LivingEntity> original)
    {
        LivingEntity owner = AllComponents.SOUL_CONTROLLER.get(instance).getOwner(instance.getWorld());
        if (owner != null)
        {
            return owner;
        }
        return original.call(instance);
    }
}
