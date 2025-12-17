package karashokleo.spell_dimension.mixin.modded;

import karashokleo.spell_dimension.api.SpellProjectileHitCallback;
import karashokleo.spell_dimension.content.object.EmptyHitResult;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
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
    public abstract SpellInfo getSpellInfo();

    private SpellProjectileMixin(EntityType<? extends ProjectileEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/spell_engine/entity/SpellProjectile;setFollowedTarget(Lnet/minecraft/entity/Entity;)V"))
    private void inject_onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci)
    {
        SpellProjectileHitCallback.EVENT.invoker().onHit((SpellProjectile) (Object) this, getSpellInfo(), getOwner(), entityHitResult);
    }

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    private void inject_onBlockHit(BlockHitResult blockHitResult, CallbackInfo ci)
    {
        SpellProjectileHitCallback.EVENT.invoker().onHit((SpellProjectile) (Object) this, getSpellInfo(), getOwner(), blockHitResult);
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
        SpellProjectileHitCallback.EVENT.invoker().onHit((SpellProjectile) (Object) this, getSpellInfo(), getOwner(), new EmptyHitResult(getPos()));
    }

    @Inject(
        method = "tick",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_tick_Killed(CallbackInfo ci)
    {
        if (this.getWorld().isClient())
        {
            return;
        }
        if (this.getVelocity().lengthSquared() > 0.001)
        {
            return;
        }
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
        SpellProjectileHitCallback.EVENT.invoker().onHit((SpellProjectile) (Object) this, getSpellInfo(), getOwner(), new EmptyHitResult(getPos()));
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
        SpellProjectileHitCallback.EVENT.invoker().onHit((SpellProjectile) (Object) this, getSpellInfo(), getOwner(), new EmptyHitResult(getPos()));
    }
}
