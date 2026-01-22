package karashokleo.spell_dimension.content.entity.goal;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.object.SoulMinionMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class FollowSoulOwnerGoal extends Goal
{
    private static final float MIN_DISTANCE_SQR = 10 * 10;
    private static final float MAX_DISTANCE_SQR = 2 * 2;
    private static final float TELEPORT_DISTANCE_SQR = 12 * 12;

    private final MobEntity mob;
    private final EntityNavigation navigation;
    private PlayerEntity owner;
    private int updateCountdownTicks;
    private float oldWaterPathfindingPenalty;

    public FollowSoulOwnerGoal(MobEntity mob)
    {
        this.mob = mob;
        this.navigation = mob.getNavigation();
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart()
    {
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(this.mob);
        if (minionComponent.mode == SoulMinionMode.STANDBY)
        {
            return false;
        }
        var soulOwner = minionComponent.getOwner();
        if (soulOwner == null)
        {
            return false;
        }

        if (cannotFollow())
        {
            return false;
        }

        if (this.mob.squaredDistanceTo(soulOwner) < MIN_DISTANCE_SQR)
        {
            return false;
        }

        this.owner = soulOwner;
        return true;
    }

    @Override
    public boolean shouldContinue()
    {
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(this.mob);
        if (minionComponent.mode == SoulMinionMode.STANDBY)
        {
            return false;
        }
        return !this.navigation.isIdle() &&
            !this.cannotFollow() &&
            !(this.mob.squaredDistanceTo(this.owner) <= MAX_DISTANCE_SQR);
    }

    private boolean cannotFollow()
    {
        // if MeleeAttackGoal.pauseWhenMobIdle = false
        // MeleeAttackGoal will repeatedly start and stop
        // when it stops, FollowSoulOwnerGoal will replace it
        // so we need to check target here to prevent that
        LivingEntity target = this.mob.getTarget();
        return target != null && target.isAlive() ||
            this.mob.hasVehicle() ||
            this.mob.isLeashed();
    }

    @Override
    public void start()
    {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
        this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop()
    {
        this.owner = null;
        this.navigation.stop();
        this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick()
    {
        this.mob.getLookControl().lookAt(this.owner, 10.0F, this.mob.getMaxLookPitchChange());
        --this.updateCountdownTicks;
        if (this.updateCountdownTicks <= 0)
        {
            this.updateCountdownTicks = this.getTickCount(10);
            if (this.mob.squaredDistanceTo(this.owner) >= TELEPORT_DISTANCE_SQR)
            {
                SoulMinionMode mode = SoulControl.getSoulMinion(this.mob).mode;
                SoulControl.teleportNearSomeone(this.mob, this.owner, mode == SoulMinionMode.FORCED_FOLLOW);
                this.navigation.stop();
            } else
            {
                // default speed is 1.0
                this.navigation.startMovingTo(this.owner, 1);
            }
        }
    }
}
