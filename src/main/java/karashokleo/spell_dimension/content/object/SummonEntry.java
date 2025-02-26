package karashokleo.spell_dimension.content.object;

import net.minecraft.entity.EntityType;

public record SummonEntry(EntityType<?> entityType, int count)
{
}
