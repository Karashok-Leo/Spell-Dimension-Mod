package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 1200)
public abstract class EntityMixin
{
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

        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        if (!minionComponent.hasOwner())
        {
            return;
        }

//        cir.setReturnValue(SpellSchools.SOUL.color);
        cir.setReturnValue(
            minionComponent.getOwner() == MinecraftClient.getInstance().player ?
                0x2dd4da :
                0x078b8f
        );
    }
}
