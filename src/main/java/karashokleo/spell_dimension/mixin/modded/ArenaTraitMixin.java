package karashokleo.spell_dimension.mixin.modded;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.highlevel.ArenaTrait;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArenaTrait.class)
public abstract class ArenaTraitMixin
{
    @Inject(
        method = "allowDamage",
        at = @At("RETURN"),
        cancellable = true
    )
    private void inject_allowDamage(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource source, CallbackInfoReturnable<Boolean> cir)
    {
        if (!(entity instanceof MobEntity mob))
        {
            // impossible case
            return;
        }
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        PlayerEntity owner = minionComponent.getOwner();
        if (owner != null && source.getAttacker() == owner)
        {
            cir.setReturnValue(true);
        }
    }
}
