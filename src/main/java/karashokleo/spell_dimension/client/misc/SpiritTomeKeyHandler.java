package karashokleo.spell_dimension.client.misc;

import karashokleo.spell_dimension.client.screen.SpiritTomeScreen;
import karashokleo.spell_dimension.data.SDTexts;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SpiritTomeKeyHandler
{
    public static KeyBinding OPEN_TOME_KEY;

    public static void register()
    {
        OPEN_TOME_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            SDTexts.KEY$OPEN_SPIRIT_TOME.getKey(),
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            SDTexts.KEY$CATEGORY.getKey()
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (!OPEN_TOME_KEY.isPressed())
            {
                return;
            }
            if (client.world == null)
            {
                return;
            }
            if (client.player == null)
            {
                return;
            }
            if (client.currentScreen != null)
            {
                return;
            }
            client.setScreen(new SpiritTomeScreen());
        });
    }
}
