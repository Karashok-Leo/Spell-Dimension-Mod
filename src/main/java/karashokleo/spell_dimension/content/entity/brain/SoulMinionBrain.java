package karashokleo.spell_dimension.content.entity.brain;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.component.SoulMinionComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.object.SoulMinionMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

import java.util.Optional;

public final class SoulMinionBrain
{
    public static MemoryModuleType<Unit> SOUL_STANDBY_MEMORY;
    public static Activity SOUL_STANDBY_ACTIVITY;

    public static void register()
    {
        SOUL_STANDBY_MEMORY = Registry.register(
            Registries.MEMORY_MODULE_TYPE,
            SpellDimension.modLoc("soul_standby"),
            new MemoryModuleType<>(Optional.of(Codec.unit(Unit.INSTANCE)))
        );
        SOUL_STANDBY_ACTIVITY = Registry.register(
            Registries.ACTIVITY,
            SpellDimension.modLoc("soul_standby"),
            new Activity("soul_standby")
        );
    }

    public static void initBrain(Brain<? extends LivingEntity> brain)
    {
        brain.setTaskList(
            SOUL_STANDBY_ACTIVITY,
            9999,
            ImmutableList.of(SoulMinionBrain.createStandbyTask()),
            SOUL_STANDBY_MEMORY
        );
    }

    public static void tickSoulMinionStandbyMode(MobEntity mob)
    {
        SoulMinionComponent minionComponent = SoulControl.getSoulMinion(mob);
        if (!minionComponent.hasOwner())
        {
            mob.getBrain().forget(SOUL_STANDBY_MEMORY);
            return;
        }

        if (minionComponent.moveMode == SoulMinionMode.Move.STANDBY)
        {
            mob.getBrain().remember(SOUL_STANDBY_MEMORY, Unit.INSTANCE);
            mob.getBrain().doExclusively(SOUL_STANDBY_ACTIVITY);
        } else
        {
            mob.getBrain().forget(SOUL_STANDBY_MEMORY);
        }
    }

    private static SingleTickTask<LivingEntity> createStandbyTask()
    {
        return TaskTriggerer.task(
            context -> context
                .group(context.queryMemoryValue(SOUL_STANDBY_MEMORY))
                .apply(
                    context,
                    standby -> (world, entity, time) ->
                    {
                        Brain<?> brain = entity.getBrain();
                        brain.forget(MemoryModuleType.WALK_TARGET);
                        brain.forget(MemoryModuleType.PATH);
                        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
                        brain.forget(MemoryModuleType.ATTACK_TARGET);

                        if (entity instanceof MobEntity mob)
                        {
                            mob.getNavigation().stop();
                            mob.setTarget(null);
                        }

                        return true;
                    }
                )
        );
    }
}
