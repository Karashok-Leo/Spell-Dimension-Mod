package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.component.GameStageComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
    @Inject(
            method = "dropInventory",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_dropInventory(CallbackInfo ci)
    {
        if (GameStageComponent.keepInventory((PlayerEntity) (Object) this))
            ci.cancel();
    }
}
