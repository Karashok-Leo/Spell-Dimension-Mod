package karashokleo.spell_dimension.content.misc;

import net.minecraft.entity.player.PlayerEntity;

public interface LivingEntityExtensions
{
    default void dropSacrificeLoot(PlayerEntity player)
    {
        throw new UnsupportedOperationException();
    }
}
