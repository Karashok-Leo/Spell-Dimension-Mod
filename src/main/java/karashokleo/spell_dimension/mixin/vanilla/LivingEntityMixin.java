package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.api.ApplyFoodEffectsCallback;
import karashokleo.spell_dimension.content.block.fluid.ConsciousnessFluid;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @Inject(
            method = "applyFoodEffects",
            at = @At("HEAD")
    )
    private void inject_applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci)
    {
        ApplyFoodEffectsCallback.EVENT.invoker().onApplyFoodEffects(targetEntity, world, stack);
    }

    @Inject(
            method = "canWalkOnFluid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_canWalkOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> info)
    {
        if (fluidState.getFluid() instanceof ConsciousnessFluid)
            info.setReturnValue(true);
    }
}
