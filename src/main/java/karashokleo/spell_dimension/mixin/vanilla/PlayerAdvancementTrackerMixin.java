package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.event.PlayerHealthEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin
{
    @Shadow
    private ServerPlayerEntity owner;

    @Inject(
            method = "grantCriterion",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/advancement/AdvancementRewards;apply(Lnet/minecraft/server/network/ServerPlayerEntity;)V"
            )
    )
    private void inject_grantCriterion(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir)
    {
        PlayerHealthEvents.onAdvancementGrant(advancement, this.owner);
    }
}
