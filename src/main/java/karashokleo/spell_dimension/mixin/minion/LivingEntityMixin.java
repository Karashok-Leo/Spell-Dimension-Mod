package karashokleo.spell_dimension.mixin.minion;

import karashokleo.spell_dimension.content.misc.LivingEntityExtensions;
import karashokleo.spell_dimension.content.misc.SoulControl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.spell_power.api.SpellDamageSource;
import net.spell_power.api.SpellSchools;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tocraft.walkers.impl.ShapeDataProvider;

@Mixin(value = LivingEntity.class, priority = 1500)
public abstract class LivingEntityMixin extends Entity implements LivingEntityExtensions
{
    @Shadow
    @Nullable
    protected PlayerEntity attackingPlayer;

    private LivingEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

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
        if (!((LivingEntity) (Object) this instanceof MobEntity mob))
        {
            return;
        }
        if (SoulControl.mobCannotAttack(mob, target))
        {
            cir.setReturnValue(false);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @ModifyVariable(
        method = "damage",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private DamageSource setPlayerDamageSource(DamageSource source)
    {
        Entity attacker = source.getAttacker();
        if (!(attacker instanceof ShapeDataProvider pvd))
        {
            return source;
        }
        int playerId = pvd.walkers$shapedPlayer();
        if (playerId == -1)
        {
            return source;
        }
        Entity player = getWorld().getEntityById(playerId);
        if (!(player instanceof PlayerEntity))
        {
            return source;
        }
        return new DamageSource(source.getTypeRegistryEntry(), source.getSource(), player);
    }
}
