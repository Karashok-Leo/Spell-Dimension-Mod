package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin
{
    @WrapOperation(
        method = "updateResult",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z"
        )
    )
    private boolean injected(Enchantment instance, ItemStack stack, Operation<Boolean> original)
    {
        return original.call(instance, stack) &&
            !stack.isOf(AllItems.SPELL_PRISM);
    }
}
