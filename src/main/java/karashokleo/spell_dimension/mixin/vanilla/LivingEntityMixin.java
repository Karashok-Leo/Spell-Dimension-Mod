package karashokleo.spell_dimension.mixin.vanilla;

import karashokleo.spell_dimension.api.ApplyFoodEffectsCallback;
import karashokleo.spell_dimension.content.block.fluid.ConsciousnessFluid;
import karashokleo.spell_dimension.content.misc.LivingEntityExtensions;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityExtensions
{
    @Shadow
    @Nullable
    protected PlayerEntity attackingPlayer;

    @Shadow
    protected abstract void dropLoot(DamageSource damageSource, boolean causedByPlayer);

    @Override
    public void dropSacrificeLoot(PlayerEntity player)
    {
        PlayerEntity tempAttackingPlayer = this.attackingPlayer;
        this.attackingPlayer = player;
        DamageSource damageSource = SpellDamageSource.create(SpellSchools.SOUL, player);
        this.dropLoot(damageSource, true);
        this.attackingPlayer = tempAttackingPlayer;
    }

    @Inject(
        method = "applyFoodEffects",
        at = @At("HEAD")
    )
    private void inject_applyFoodEffects(ItemStack stack, World world, LivingEntity targetEntity, CallbackInfo ci)
    {
        ApplyFoodEffectsCallback.EVENT.invoker().onApplyFoodEffects(targetEntity, world, stack);
    }

    @Inject(
        method = "canWalkOnFluid",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_canWalkOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> info)
    {
        if (fluidState.getFluid() instanceof ConsciousnessFluid)
        {
            info.setReturnValue(true);
        }
    }

    @Inject(
        method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_canTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir)
    {
        if (!SoulControl.isSoulMinion(target, (LivingEntity) (Object) this))
        {
            return;
        }

        cir.setReturnValue(false);
    }
}
