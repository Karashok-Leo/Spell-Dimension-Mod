package karashokleo.spell_dimension.mixin.client;

import karashokleo.spell_dimension.client.compat.emi.EMIViewHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.fabric.xplat.FabricXplatImpl;

@Mixin(FabricXplatImpl.class)
public abstract class PatchouliXplatMixin
{
    @Inject(
        method = "handleRecipeKeybind",
        at = @At(
            value = "INVOKE",
            target = "Lnet/fabricmc/loader/api/FabricLoader;getInstance()Lnet/fabricmc/loader/api/FabricLoader;"
        ),
        cancellable = true
    )
    private void inject_handleRecipeKeybind(int keyCode, int scanCode, ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (FabricLoader.getInstance().isModLoaded("emi"))
        {
            cir.setReturnValue(EMIViewHandler.handleRecipeKeybind(keyCode, scanCode, stack));
        }
    }
}
