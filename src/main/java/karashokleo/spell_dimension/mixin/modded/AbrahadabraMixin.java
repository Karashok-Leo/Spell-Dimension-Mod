package karashokleo.spell_dimension.mixin.modded;

import karashokleo.l2hostility.content.item.trinket.misc.Abrahadabra;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.spell_dimension.content.trait.SpellTrait;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = Abrahadabra.class, remap = false)
public abstract class AbrahadabraMixin
{
    @Inject(
            method = "canReflect",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_canReflect(MobTrait trait, CallbackInfoReturnable<Boolean> cir)
    {
        if (trait instanceof SpellTrait)
        {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "appendTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lkarashokleo/l2hostility/content/item/trinket/core/CurseTrinketItem;appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V"
            )
    )
    private void inject_appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci)
    {
        tooltip.add(SDTexts.TOOLTIP$ABRAHADABRA.get().formatted(Formatting.RED));
    }
}
