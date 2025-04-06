package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.api.SpellProjectileHitBlockCallback;
import karashokleo.spell_dimension.api.SpellProjectileHitEntityCallback;
import karashokleo.spell_dimension.api.SpellProjectileOutOfRangeCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.spell_engine.entity.SpellProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpellProjectile.class)
public abstract class SpellProjectileMixin extends ProjectileEntity
{
    @Shadow
    private Identifier spellId;

    private SpellProjectileMixin(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/spell_engine/entity/SpellProjectile;setFollowedTarget(Lnet/minecraft/entity/Entity;)V"))
    private void inject_onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci)
    {
        SpellProjectileHitEntityCallback.EVENT.invoker().onHitEntity((SpellProjectile) (Object) this, spellId, entityHitResult);
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    private void inject_onBlockHit(BlockHitResult blockHitResult, CallbackInfo ci)
    {
        SpellProjectileHitBlockCallback.EVENT.invoker().onHitBlock((SpellProjectile) (Object) this, spellId, blockHitResult);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/spell_engine/entity/SpellProjectile;kill()V",
                    ordinal = 1
            )
    )
    private void inject_tick_flyKilled(CallbackInfo ci)
    {
        SpellProjectileOutOfRangeCallback.EVENT.invoker().onOutOfRange((SpellProjectile) (Object) this, spellId);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_tick_Killed(CallbackInfo ci)
    {
        if (this.getWorld().isClient()) return;
        if (this.getVelocity().lengthSquared() > 0.001) return;
        this.kill();
        ci.cancel();
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/spell_engine/entity/SpellProjectile;kill()V",
                    ordinal = 2
            )
    )
    private void inject_tick_fallKilled(CallbackInfo ci)
    {
        SpellProjectileOutOfRangeCallback.EVENT.invoker().onOutOfRange((SpellProjectile) (Object) this, spellId);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/spell_engine/entity/SpellProjectile;kill()V",
                    ordinal = 4
            )
    )
    private void inject_tick_defaultKilled(CallbackInfo ci)
    {
        SpellProjectileOutOfRangeCallback.EVENT.invoker().onOutOfRange((SpellProjectile) (Object) this, spellId);
    }
}
