package karashokleo.spell_dimension.api.buff;

import karashokleo.spell_dimension.content.component.BuffComponent;
import karashokleo.spell_dimension.init.AllComponents;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface Buff
{
    static <B extends Buff> Optional<B> get(LivingEntity entity, BuffType<B> type)
    {
        BuffComponent component = entity.getComponent(AllComponents.BUFF);
        return component.get(type);
    }

    static <B extends Buff> void apply(LivingEntity entity, BuffType<B> type, B buff, @Nullable LivingEntity source)
    {
        entity.getComponent(AllComponents.BUFF).apply(type, buff, source);
    }

    static <B extends Buff> void remove(LivingEntity entity, BuffType<B> type)
    {
        entity.getComponent(AllComponents.BUFF).remove(type);
    }

    BuffType<?> getType();

    default void serverTick(LivingEntity entity, @Nullable LivingEntity source)
    {
    }

    default void onApplied(LivingEntity entity, @Nullable LivingEntity source)
    {
    }

    default void onRemoved(LivingEntity entity, @Nullable LivingEntity source)
    {
    }
}
