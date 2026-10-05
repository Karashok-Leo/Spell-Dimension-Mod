package karashokleo.spell_dimension.mixin.modded;

import com.extraspellattributes.ReabsorptionInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ReabsorptionInit.class, remap = false)
public abstract class ExtraSpellAttributesMixin
{
    // Temporary workaround for Extra Spell Attributes 1.4.0: some casters, such as
    // the Ender Dragon, have no attack damage attribute to convert into spell power.
    // Remove when fixed upstream: https://github.com/cleannrooster/extraspellattributes/issues/13
    @Inject(method = "physicalMeleeBase", at = @At("HEAD"), cancellable = true)
    private static void inject_physicalMeleeBase(LivingEntity entity, CallbackInfoReturnable<Double> cir)
    {
        if (entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE) == null)
        {
            cir.setReturnValue(0.0);
        }
    }
}
