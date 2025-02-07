package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.spell_dimension.content.spell.BlackHoleSpell;
import karashokleo.spell_dimension.init.AllTraits;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonFireballEntity.class)
public abstract class DragonFireballEntityMixin extends ExplosiveProjectileEntity
{
    private DragonFireballEntityMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(
            method = "onCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/DragonFireballEntity;discard()V"
            )
    )
    private void inject_onCollision(HitResult hitResult, CallbackInfo ci)
    {
        Entity owner = this.getOwner();
        if (!(owner instanceof LivingEntity living)) return;
        if (!living.isAlive()) return;
        var diff = MobDifficulty.get(living);
        if (diff.isEmpty()) return;
        int traitLevel = diff.get().getTraitLevel(AllTraits.BLACK_HOLE);
        if (traitLevel <= 0) return;
        BlackHoleSpell.spawn(this.getWorld(), living, this.getPos());
    }
}
