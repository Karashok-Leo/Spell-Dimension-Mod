package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import karashokleo.spell_dimension.init.AllCriterions;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin
{
    @ModifyExpressionValue(
        method = "copyFrom",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"
        )
    )
    private boolean inject_copyFrom(boolean original, @Local(ordinal = 1, argsOnly = true) ServerPlayerEntity oldPlayer)
    {
        boolean keepInventory = GameStageComponent.keepInventory(oldPlayer);
        return original || keepInventory;
    }

    @Inject(
        method = "dropItem",
        at = @At("RETURN")
    )
    private void inject_dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir)
    {
        AllCriterions.DROPPED_ITEMS.trigger((ServerPlayerEntity) (Object) this, stack);
    }
}
