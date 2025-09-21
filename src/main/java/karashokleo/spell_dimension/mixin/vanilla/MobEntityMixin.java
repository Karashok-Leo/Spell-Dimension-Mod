package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.object.SoulInput;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 对受控实体应用控制玩家的输入
 */
@Mixin(MobEntity.class)
public abstract class MobEntityMixin
{
    @Inject(
        method = "tickNewAi",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_tickNewAi(CallbackInfo ci)
    {
        MobEntity mob = (MobEntity) (Object) this;
        SoulControllerComponent component = SoulControl.getSoulController(mob);
        if (component == null)
        {
            return;
        }
        SoulInput input = component.getInput();
        if (!input.controlling)
        {
            return;
        }
        input.applyRotation(mob, 2);
        input.applyMovement(mob);
        ci.cancel();
    }
}
