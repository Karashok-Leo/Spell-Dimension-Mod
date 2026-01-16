package karashokleo.spell_dimension.mixin.modded;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.spell_dimension.api.SpellProjectileHitBox;
import karashokleo.spell_dimension.api.SpellProjectileHitCallback;
import karashokleo.spell_dimension.content.object.EmptyHitResult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_engine.entity.SpellProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

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

    @WrapOperation(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/ProjectileUtil;getCollision(Lnet/minecraft/entity/Entity;Ljava/util/function/Predicate;)Lnet/minecraft/util/hit/HitResult;"
        )
    )
    private HitResult wrap_getCollision(Entity entity, Predicate<Entity> predicate, Operation<HitResult> original)
    {
        HitResult hitResult = original.call(entity, predicate);
        if (hitResult.getType() != HitResult.Type.MISS)
        {
            return hitResult;
        }

        Vec3d extend = SpellProjectileHitBox.getExtension(this.getSpellInfo().id());
        if (extend == null)
        {
            return hitResult;
        }

        double d = Double.MAX_VALUE;
        Entity target = null;
        Vec3d pos = getPos();
        Box box = getBoundingBox()
            .expand(extend.x, extend.y, extend.z)
            .stretch(getVelocity());
        for (Entity e : getWorld().getOtherEntities(entity, box, predicate))
        {
            double sqr = pos.squaredDistanceTo(e.getPos());
            if (sqr < d)
            {
                target = e;
                d = sqr;
            }
        }
        if (target != null)
        {
            hitResult = new EntityHitResult(target);
        }

        return hitResult;
    }
}
