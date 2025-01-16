package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.spell_engine.entity.SpellProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpellProjectile.class)
public abstract class SpellProjectileMixin
{
    @Shadow
    private Identifier spellId;

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/spell_engine/entity/SpellProjectile;setFollowedTarget(Lnet/minecraft/entity/Entity;)V"))
    private void injectedOnEntityHit(EntityHitResult entityHitResult, CallbackInfo ci)
    {
        SpellProjectileHitEntityCallback.EVENT.invoker().onHitEntity((SpellProjectile) (Object) this, spellId, entityHitResult);
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    private void injectedOnBlockHit(BlockHitResult blockHitResult, CallbackInfo ci)
    {
        SpellProjectileHitBlockCallback.EVENT.invoker().onHitBlock((SpellProjectile) (Object) this, spellId, blockHitResult);
    }
}
