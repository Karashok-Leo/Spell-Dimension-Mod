package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AdaptingTrait;
import karashokleo.spell_dimension.content.event.AdaptiveCompat;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AdaptingTrait.class, remap = false)
public abstract class AdaptingTraitMixin
{
    @Inject(
            method = "onHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lkarashokleo/l2hostility/content/trait/common/AdaptingTrait$Data;adapt(Ljava/lang/String;I)Ljava/util/Optional;"
            ),
            cancellable = true
    )
    private void inject_onHurt(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event, CallbackInfo ci, @Local AdaptingTrait.Data data)
    {
        if (AdaptiveCompat.adaptSpell(level, entity, event, data))
            ci.cancel();
    }
}
