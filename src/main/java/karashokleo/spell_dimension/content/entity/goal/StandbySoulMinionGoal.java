package karashokleo.spell_dimension.content.entity.goal;

import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.object.SoulMinionMode;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

public class StandbySoulMinionGoal extends Goal
{
    private final MobEntity mob;

    public StandbySoulMinionGoal(MobEntity mob)
    {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
    }

    @Override
    public boolean canStart()
    {
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(this.mob);
        return minionComponent.hasOwner() &&
            minionComponent.mode == SoulMinionMode.STANDBY;
    }

    @Override
    public boolean shouldContinue()
    {
        return this.canStart();
    }

    @Override
    public void start()
    {
        this.mob.getNavigation().stop();
        this.mob.setTarget(null);
    }

    @Override
    public void tick()
    {
        this.mob.getNavigation().stop();
        this.mob.setTarget(null);
    }
}
