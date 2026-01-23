package karashokleo.spell_dimension.mixin.minion;

import karashokleo.spell_dimension.content.entity.FakePlayerEntity;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tocraft.walkers.api.PlayerShape;

import java.util.List;
import java.util.function.Predicate;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin<T extends LivingEntity> extends TrackTargetGoal
{
    @Shadow
    @Nullable
    protected LivingEntity targetEntity;

    @Shadow
    @Final
    protected Class<T> targetClass;

    @Unique
    private Predicate<LivingEntity> entityPredicate;

    @Shadow
    protected abstract Box getSearchBox(double distance);

    private ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility)
    {
        super(mob, checkVisibility);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/mob/MobEntity;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V", at = @At("TAIL"))
    private void inject_init(MobEntity mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, Predicate<LivingEntity> targetPredicate, CallbackInfo ci)
    {
        this.entityPredicate = targetPredicate;
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void inject_start(CallbackInfo ci)
    {
        if (SoulControl.mobCannotAttack(mob, targetEntity))
        {
            stop();
            ci.cancel();
        }
    }

    @Inject(method = "findClosestTarget", at = @At("TAIL"))
    private void inject_findClosestTarget(CallbackInfo ci)
    {
        if (this.targetEntity == null)
        {
            List<? extends LivingEntity> entityList;
            // FakePlayerEntity will be targeted
            if (this.targetClass == PlayerEntity.class ||
                this.targetClass == ServerPlayerEntity.class)
            {
                entityList = this.mob
                    .getWorld()
                    .getEntitiesByClass(
                        FakePlayerEntity.class,
                        this.getSearchBox(this.getFollowRange()),
                        target ->
                        {
                            PlayerEntity player = target.getPlayer();
                            return !RelationUtil.isAlly(this.mob, target) &&
                                (player == null || this.entityPredicate == null || this.entityPredicate.test(target));
                        });
            }
            // players shaped as targetClass will be targeted
            else
            {
                entityList = this.mob
                    .getWorld()
                    .getPlayers()
                    .stream()
                    .filter(player ->
                    {
                        LivingEntity shape = PlayerShape.getCurrentShape(player);
                        return this.targetClass.isInstance(shape) &&
                            (this.entityPredicate == null || this.entityPredicate.test(shape));
                    })
                    .toList();
            }
            this.targetEntity = this.mob
                .getWorld()
                .getClosestEntity(
                    entityList,
                    TargetPredicate.DEFAULT,
                    this.mob,
                    this.mob.getX(),
                    this.mob.getEyeY(),
                    this.mob.getZ()
                );
        }

        if (targetEntity != null)
        {
            if (SoulControl.mobCannotAttack(mob, targetEntity))
            {
                targetEntity = null;
            }
        }
    }
}
