package karashokleo.spell_dimension.mixin;

import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.item.ItemStack;
import net.spell_power.internals.AmplifierEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AmplifierEnchantment.class)
public abstract class AmplifierEnchantmentMixin
{
    @Inject(
            method = "matchesRequiredTag",
            at = @At("RETURN"),
            cancellable = true
    )
    private void inject_matchesRequiredTag(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(
                cir.getReturnValue() ||
                        stack.isIn(AllTags.ARMOR) ||
                        stack.isIn(AllTags.MELEE_WEAPONS)
        );
    }
}
