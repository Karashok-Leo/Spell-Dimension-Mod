package karashokleo.spell_dimension.mixin;

import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.item.ItemStack;
import net.spell_engine.internals.SpellInfinityEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpellInfinityEnchantment.class)
public abstract class SpellInfinityEnchantmentMixin
{
    @Inject(
            method = "isEligible",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void inject_isEligible(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(
                cir.getReturnValue() ||
                        stack.isIn(AllTags.MELEE_WEAPONS)
        );
    }
}
