package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity
{
    private SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Shadow
    public abstract int getSize();

    @WrapOperation(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"
        )
    )
    private void wrap_addParticle(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original)
    {
        int size = this.getSize();
        // If the size is more than 8, try to cancel the particle spawn
        if (size > 8 &&
            this.getRandom().nextInt(size * 8) > 64)
        {
            return;
        }
        original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);
    }
}
