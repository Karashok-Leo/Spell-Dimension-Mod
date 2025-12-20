package karashokleo.spell_dimension.mixin.vanilla;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin<T extends LivingEntity> extends TrackTargetGoal
{
    @Shadow
    @Nullable
    protected LivingEntity targetEntity;

    @Shadow
    @Final
    protected Class<T> targetClass;

    @Shadow
    protected TargetPredicate targetPredicate;

    @Shadow
    protected abstract Box getSearchBox(double distance);

    private ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility)
    {
        super(mob, checkVisibility);
    }

    @Inject(method = "start", at = @At("HEAD"), cancellable = true)
    private void inject_start(CallbackInfo ci)
    {
        if (!SoulControl.isSoulMinion(targetEntity, mob))
        {
            return;
        }

        stop();
        ci.cancel();
    }

    @Inject(method = "findClosestTarget", at = @At("TAIL"))
    private void inject_findClosestTarget(CallbackInfo ci)
    {
        if (this.targetEntity == null)
        {
            // FakePlayerEntity will be targeted
            if (this.targetClass == PlayerEntity.class ||
                this.targetClass == ServerPlayerEntity.class)
            {
                this.targetEntity = this.mob
                    .getWorld()
                    .getClosestEntity(
                        this.mob.getWorld().getEntitiesByClass(FakePlayerEntity.class, this.getSearchBox(this.getFollowRange()), target -> !RelationUtil.isAlly(this.mob, target)),
                        this.targetPredicate,
                        this.mob,
                        this.mob.getX(),
                        this.mob.getEyeY(),
                        this.mob.getZ()
                    );
            }
        }

        if (targetEntity != null)
        {
            if (SoulControl.isSoulMinion(targetEntity, mob))
            {
                targetEntity = null;
            }
        }
    }
}
