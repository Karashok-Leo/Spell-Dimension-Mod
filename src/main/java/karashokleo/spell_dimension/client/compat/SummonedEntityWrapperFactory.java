package karashokleo.spell_dimension.client.compat;

import karashokleo.l2hostility.compat.shared.LivingEntityWrapper;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class SummonedEntityWrapperFactory
{
    public static LivingEntityWrapper of(EntityType<?> entityType, int count)
    {
        LivingEntityWrapper wrapper = LivingEntityWrapper.of(entityType, 3, SDTexts.TOOLTIP$QUEST$MUL.get(entityType.getName(), count).formatted(Formatting.BOLD));
        Objects.requireNonNull(wrapper, entityType + " is not a living entity. Check your summon recipes.");
        return wrapper;
    }
}
