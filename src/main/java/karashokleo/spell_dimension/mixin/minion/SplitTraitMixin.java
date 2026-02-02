package karashokleo.spell_dimension.mixin.minion;

import karashokleo.l2hostility.content.trait.highlevel.SplitTrait;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplitTrait.class)
public abstract class SplitTraitMixin
{
    @Inject(
        method = "copyComponents",
        at = @At("HEAD")
    )
    private static void inject_copyComponents(LivingEntity entity, LivingEntity added, CallbackInfo ci)
    {
        // inject here and no need to inject SlimeEntity, because inside SlimeEntity remove method will call this
        if (entity instanceof MobEntity mob &&
            added instanceof MobEntity addedMob)
        {
            SoulMinionComponent parent = SoulControl.getSoulMinion(mob);
            SoulMinionComponent son = SoulControl.getSoulMinion(addedMob);
            son.copyFrom(parent);
        }
    }
}
