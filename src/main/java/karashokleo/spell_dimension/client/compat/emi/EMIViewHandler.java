package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.EmiStackInteraction;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.item.ItemStack;

public class EMIViewHandler
{
    public static boolean handleRecipeKeybind(int keyCode, int scanCode, ItemStack stack)
    {
        return EmiScreenManager.stackInteraction(
            new EmiStackInteraction(EmiStack.of(stack)),
            bind -> bind.matchesKey(keyCode, scanCode)
        );
    }
}
