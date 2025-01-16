package karashokleo.spell_dimension.mixin.modded;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.spell_dimension.init.AllEvents;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdaptingTrait.class)
public abstract class AdaptingTraitMixin
{
    @Inject(
            method = "onHurt",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_onHurt(int level, LivingEntity entity, LivingHurtEvent event, CallbackInfo ci)
    {
        if (AllEvents.adaptSpell(level, entity, event))
            ci.cancel();
    }
}
