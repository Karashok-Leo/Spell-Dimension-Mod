package karashokleo.spell_dimension.mixin.minion;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 1200)
public abstract class EntityMixin
{
    @WrapMethod(
        method = "isTeammate"
    )
    private boolean is(Entity other, Operation<Boolean> original)
    {
        if (SoulControl.isSoulMinion(other, (Entity) (Object) this))
        {
            return true;
        }
        return original.call(other);
    }

    @Inject(
        method = "getTeamColorValue",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_getTeamColorValue(CallbackInfoReturnable<Integer> cir)
    {
        if (!(((Entity) (Object) this) instanceof MobEntity mob))
        {
            return;
        }

        if (!mob.getWorld().isClient())
        {
            return;
        }

        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        if (!minionComponent.hasOwner())
        {
            return;
        }

        cir.setReturnValue(SoulControl.getOutlineColor(minionComponent));
    }
}
