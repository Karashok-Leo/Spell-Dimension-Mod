package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.component.GameStageComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PlayerKeepInventoryMixin
{
    @Inject(
        method = "drop",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_drop(DamageSource source, CallbackInfo ci)
    {
        if ((LivingEntity) (Object) (this) instanceof ServerPlayerEntity player)
        {
            if (GameStageComponent.keepInventory(player))
            {
                ci.cancel();
            }
        }
    }
}
