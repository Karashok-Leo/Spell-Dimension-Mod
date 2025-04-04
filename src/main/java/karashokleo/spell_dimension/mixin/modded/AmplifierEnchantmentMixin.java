package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.enchantment.Enchantment;
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
            method = "canAccept",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_canAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(true);
    }

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
                stack.isIn(AllTags.MELEE_WEAPONS) ||
                stack.isIn(AllTags.MAGIC) ||
                stack.isIn(AllTags.MAGIC_WEAPON)
        );
    }
}
