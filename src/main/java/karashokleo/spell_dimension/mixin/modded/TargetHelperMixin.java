package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.spell_engine.utils.TargetHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetHelper.class)
public abstract class TargetHelperMixin
{
    @Inject(
        method = "getRelation",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void inject_getRelation(LivingEntity attacker, Entity target, CallbackInfoReturnable<TargetHelper.Relation> cir)
    {
        if (RelationUtil.isAlly(attacker, target))
        {
            cir.setReturnValue(TargetHelper.Relation.FRIENDLY);
        }
        if (attacker instanceof MobEntity mob)
        {
            LivingEntity actualTarget = mob.getTarget();
            cir.setReturnValue(
                actualTarget == target ?
                    TargetHelper.Relation.HOSTILE :
                    TargetHelper.Relation.SEMI_FRIENDLY
            );
        }
    }
}
