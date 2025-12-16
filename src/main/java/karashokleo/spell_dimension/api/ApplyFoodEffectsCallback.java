package karashokleo.spell_dimension.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ApplyFoodEffectsCallback
{
    Event<ApplyFoodEffectsCallback> EVENT = EventFactory.createArrayBacked(ApplyFoodEffectsCallback.class, (listeners) -> ((entity, world, stack) ->
    {
        for (ApplyFoodEffectsCallback listener : listeners)
        {
            listener.onApplyFoodEffects(entity, world, stack);
        }
    }));

    void onApplyFoodEffects(LivingEntity entity, World world, ItemStack stack);
}
