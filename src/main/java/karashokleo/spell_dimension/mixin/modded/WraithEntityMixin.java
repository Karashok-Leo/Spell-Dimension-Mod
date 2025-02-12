package karashokleo.spell_dimension.mixin.modded;

import com.lion.graveyard.entities.HostileGraveyardEntity;
import com.lion.graveyard.entities.WraithEntity;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WraithEntity.class)
public abstract class WraithEntityMixin extends HostileGraveyardEntity
{
    private WraithEntityMixin(EntityType<? extends HostileEntity> entityType, World world, String name)
    {
        super(entityType, world, name);
    }

    @Inject(
            method = "isInvulnerableTo",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir)
    {
        if (damageSource.isIn(LHTags.MAGIC))
            cir.setReturnValue(super.isInvulnerableTo(damageSource));
    }
}
