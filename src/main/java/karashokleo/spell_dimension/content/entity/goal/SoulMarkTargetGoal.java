package karashokleo.spell_dimension.content.entity.goal;

import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

@Deprecated
public class SoulMarkTargetGoal extends TrackTargetGoal
{
    protected static final int RECIPROCAL_CHANCE = 5;
    @Nullable
    protected LivingEntity targetEntity;
    protected final TargetPredicate targetPredicate;

    public SoulMarkTargetGoal(MobEntity mob)
    {
        super(mob, false, false);
        this.setControls(EnumSet.of(Goal.Control.TARGET));
        this.targetPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange());
    }

    @Override
    public boolean canStart()
    {
        if (this.mob.getRandom().nextInt(RECIPROCAL_CHANCE) != 0)
        {
            return false;
        }
        if (!SoulControl.getSoulMinion(this.mob).hasOwner())
        {
            return false;
        }
        // we cannot suppose the soul mark of the target we found is not applied by the other players
        this.findClosestTarget();
        return this.targetEntity != null;
    }

    protected Box getSearchBox(double distance)
    {
        return this.mob.getBoundingBox().expand(distance, 4.0, distance);
    }

    protected void findClosestTarget()
    {
        this.targetEntity = this.mob.getWorld().getClosestEntity(
            this.mob.getWorld().getEntitiesByClass(
                LivingEntity.class,
                this.getSearchBox(this.getFollowRange()),
                living -> living.hasStatusEffect(AllStatusEffects.SOUL_MARK)
            ),
            this.targetPredicate,
            this.mob,
            this.mob.getX(),
            this.mob.getEyeY(),
            this.mob.getZ()
        );
    }

    @Override
    public void start()
    {
        this.mob.setTarget(this.targetEntity);
        super.start();
    }
}
