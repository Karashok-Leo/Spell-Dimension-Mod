package karashokleo.spell_dimension.mixin.modded;

import com.obscuria.aquamirae.common.effects.HealthDecreaseEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HealthDecreaseEffect.class)
public abstract class HealthDecreaseEffectMixin
{
    @ModifyArg(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Lcom/obscuria/aquamirae/common/effects/HealthDecreaseEffect;addAttributeModifier(Lnet/minecraft/entity/attribute/EntityAttribute;Ljava/lang/String;DLnet/minecraft/entity/attribute/EntityAttributeModifier$Operation;)Lnet/minecraft/entity/effect/StatusEffect;"
        ),
        index = 2
    )
    private double modify(double amount)
    {
        return -0.02;
    }
}
