package karashokleo.spell_dimension.mixin.vanilla;

import com.llamalad7.mixinextras.sugar.Local;
import karashokleo.spell_dimension.init.AllTraits;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.StrafePlayerPhase;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrafePlayerPhase.class)
public abstract class StrafePlayerPhaseMixin extends AbstractPhase
{
    private StrafePlayerPhaseMixin(EnderDragonEntity dragon)
    {
        super(dragon);
    }

    @Inject(
        method = "serverTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
        )
    )
    private void injected_serverTick(
        CallbackInfo ci,
        @Local(ordinal = 3) double x,
        @Local(ordinal = 4) double y,
        @Local(ordinal = 5) double z,
        @Local(ordinal = 6) double directionX,
        @Local(ordinal = 7) double directionY,
        @Local(ordinal = 8) double directionZ
    )
    {
        AllTraits.BLACK_HOLE.tryNotifyTarget(this.dragon);
        World world = this.dragon.getWorld();
        for (int i = 0; i < 8; i++)
        {
            Vec3d direction = RandomUtil.perturbDirection(
                new Vec3d(directionX, directionY, directionZ),
                0.4,
                this.dragon.getRandom()
            );
            DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(world, this.dragon, direction.x, direction.y, direction.z);
            dragonFireballEntity.refreshPositionAndAngles(x, y, z, 0.0F, 0.0F);
            world.spawnEntity(dragonFireballEntity);
        }
    }
}
