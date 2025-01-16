package karashokleo.spell_dimension.mixin.modded;

import net.adventurez.entity.VoidShadowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VoidShadowEntity.class)
public abstract class VoidShadowEntityMixin extends FlyingEntity
{
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
        if (this.age % 20 == 0)
            this.heal(1.0F);
    }
}
