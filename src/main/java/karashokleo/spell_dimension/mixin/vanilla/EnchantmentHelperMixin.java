package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Implement of {@link AllTags#ENCHANTABLE}.
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin
{
    @WrapOperation(
        method = "getPossibleEntries",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;isAvailableForRandomSelection()Z"
        )
    )
    private static boolean inject_getPossibleEntries(Enchantment instance, Operation<Boolean> original)
    {
        return original.call(instance) ||
            Registries.ENCHANTMENT.getEntry(instance).isIn(AllTags.ENCHANTABLE);
    }
}
