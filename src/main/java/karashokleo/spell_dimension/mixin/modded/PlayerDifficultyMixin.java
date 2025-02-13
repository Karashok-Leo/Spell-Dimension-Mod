package karashokleo.spell_dimension.mixin.modded;

import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.spell_dimension.content.component.GameStageComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = PlayerDifficulty.class, remap = false)
public abstract class PlayerDifficultyMixin
{
    @Shadow
    public PlayerEntity owner;

    @ModifyVariable(
            method = "addKillCredit",
            at = @At(
                    value = "INVOKE",
                    target = "Lkarashokleo/l2hostility/content/logic/DifficultyLevel;grow(DLkarashokleo/l2hostility/content/component/mob/MobDifficulty;)V"
            )
    )
    private double inject_addKillCredit(double factor)
    {
        if (GameStageComponent.isNormalMode(owner)) return factor;
        return factor * 2;
    }
}
