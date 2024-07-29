package karashokleo.spell_dimension.mixin;

import karashokleo.spell_dimension.api.ApplyFoodEffectsCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
}
