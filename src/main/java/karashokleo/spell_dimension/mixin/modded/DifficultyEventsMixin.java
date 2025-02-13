package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.l2hostility.content.event.DifficultyEvents;
import karashokleo.leobrary.damage.api.modify.DamageAccess;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = DifficultyEvents.class, remap = false)
public abstract class DifficultyEventsMixin
{
    @ModifyVariable(
            method = "onDamageArmor",
            at = @At(
                    value = "INVOKE",
                    target = "Lkarashokleo/leobrary/damage/api/modify/DamageAccess;addModifier(Lkarashokleo/leobrary/damage/api/modify/DamageModifier;)V"
            )
    )
    private static double inject_onDamageArmor(double factor, @Local(argsOnly = true) DamageAccess access)
    {
        if (!(access.getEntity() instanceof PlayerEntity player))
            return factor;
        if (GameStageComponent.isNormalMode(player))
            return factor;
        return factor * 2;
    }
}
