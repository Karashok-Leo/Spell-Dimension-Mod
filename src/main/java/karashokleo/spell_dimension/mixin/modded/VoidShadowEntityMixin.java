package karashokleo.spell_dimension.mixin.modded;

import net.adventurez.entity.VoidShadowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voidz.init.DimensionInit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VoidShadowEntity.class, remap = false)
public abstract class VoidShadowEntityMixin extends FlyingEntity
{
    @Shadow
    protected abstract boolean hasVoidMiddleCoordinates();

    @Shadow
    public abstract void setVoidMiddle(int x, int y, int z);

    @Shadow
    public abstract BlockPos getVoidMiddle();

    private VoidShadowEntityMixin(EntityType<? extends FlyingEntity> entityType, World world)
    {
        super(entityType, world);
    }

    @Inject(
            method = "mobTick",
            at = @At("TAIL")
    )
    private void inject_mobTick(CallbackInfo ci)
    {
        if (this.age % 20 != 0) return;
        if (!this.getWorld().getRegistryKey().equals(DimensionInit.VOID_WORLD)) return;
        if (!this.hasVoidMiddleCoordinates())
            this.setVoidMiddle(0, 100, 0);
        if (this.getBlockPos().getManhattanDistance(this.getVoidMiddle()) > 200)
            this.teleport(this.getVoidMiddle().getX(), this.getVoidMiddle().getY(), this.getVoidMiddle().getZ());
    }
}
