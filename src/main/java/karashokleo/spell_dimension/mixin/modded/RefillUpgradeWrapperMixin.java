package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllTags;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.p3pp3rf1y.sophisticatedbackpacks.upgrades.refill.RefillUpgradeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RefillUpgradeWrapper.class)
public abstract class RefillUpgradeWrapperMixin
{
    @SuppressWarnings("UnstableApiUsage")
    @Inject(
            method = "tryRefillFilter",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_tryRefillFilter(Entity entity, PlayerInventoryStorage playerInvHandler, ItemStack filter, RefillUpgradeWrapper.TargetSlot targetSlot, CallbackInfo ci)
    {
        if (!filter.isIn(AllTags.REFILL_BANNED))
        {
            return;
        }
        entity.sendMessage(SDTexts.TEXT$REFILL_BANNED.get().formatted(Formatting.RED));
        ci.cancel();
    }
}
