package karashokleo.spell_dimension.api;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface PatchouliLookupCallback
{
    Event<PatchouliLookupCallback> EVENT = EventFactory.createArrayBacked(PatchouliLookupCallback.class, (listeners) -> ((stack) ->
    {
        for (PatchouliLookupCallback listener : listeners)
        {
            Pair<Identifier, Integer> entry = listener.lookupEntry(stack);
            if (entry != null)
            {
                return entry;
            }
        }
        return null;
    }));

    @Nullable
    Pair<Identifier, Integer> lookupEntry(ItemStack stack);
}
