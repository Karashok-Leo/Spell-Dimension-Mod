package karashokleo.spell_dimension.content.entity.goal;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class AttackWithSoulOwnerGoal extends TrackTargetGoal
{
    private LivingEntity attacking;
    private int lastAttackTime;

    public AttackWithSoulOwnerGoal(MobEntity mob)
    {
        super(mob, false);
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart()
    {
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(this.mob);
        PlayerEntity owner = minionComponent.getOwner();
        if (owner == null)
        {
            return false;
        }

        this.attacking = owner.getAttacking();
        int i = owner.getLastAttackTime();
        return i != this.lastAttackTime &&
            this.canTrack(this.attacking, TargetPredicate.DEFAULT) &&
            !RelationUtil.isAlly(this.mob, this.attacking);
    }

    @Override
    public void start()
    {
        this.mob.setTarget(this.attacking);
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(this.mob);
        PlayerEntity owner = minionComponent.getOwner();
        if (owner != null)
        {
            this.lastAttackTime = owner.getLastAttackTime();
        }
        super.start();
    }
}
