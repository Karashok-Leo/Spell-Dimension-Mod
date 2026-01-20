package karashokleo.spell_dimension.mixin.minion;

import karashokleo.spell_dimension.content.misc.LivingEntityExtensions;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
