package karashokleo.spell_dimension.mixin.phase;

import karashokleo.spell_dimension.content.misc.NoClip;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

/**
 * 实体谓词修改
 * Learned from <a href="https://github.com/andantet/noclip/blob/1.20/src/main/java/me/andante/noclip/mixin/EntityPredicatesMixin.java">...</a>
 */
@Mixin(EntityPredicates.class)
public abstract class EntityPredicatesMixin
{
    /**
     * Adds an extra check to {@link EntityPredicates#VALID_LIVING_ENTITY} for clipping.
     * <p>This predicate is used when checking for players near spawners and dripstone landing.</p>
     */
    @Inject(method = "method_32878", at = @At("TAIL"), cancellable = true, remap = false)
    private static void onValidLivingEntity(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValueZ())
        {
            if (NoClip.noClip(entity))
            {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "method_24517", at = @At("TAIL"), cancellable = true, remap = false)
    private static void onExceptSpectator(Entity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValueZ())
        {
            if (NoClip.noClip(entity))
            {
                cir.setReturnValue(false);
            }
        }
    }

    /**
     * Removes collision entirely from clipping players.
     */
    @Inject(method = "canBePushedBy", at = @At("HEAD"), cancellable = true)
    private static void onCanBePushedBy(Entity entity, CallbackInfoReturnable<Predicate<Entity>> cir)
    {
        if (NoClip.noClip(entity))
        {
            cir.setReturnValue(e -> false);
        }
    }
}
