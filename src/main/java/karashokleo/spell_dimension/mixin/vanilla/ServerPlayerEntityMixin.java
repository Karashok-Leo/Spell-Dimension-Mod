package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
    private boolean inject_copyFrom(boolean original, @Local(name = "oldPlayer") ServerPlayerEntity oldPlayer)
    {
        boolean keepInventory = GameStageComponent.keepInventory((oldPlayer));
        return original || keepInventory;
    }
}
