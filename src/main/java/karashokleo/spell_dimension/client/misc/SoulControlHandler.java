package karashokleo.spell_dimension.client.misc;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.C2SReleaseControl;
import karashokleo.spell_dimension.init.AllPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SoulControlHandler
{
    private static KeyBinding KEY_RELEASE_CONTROL;

    public static void register()
    {
        // TODO
        KEY_RELEASE_CONTROL = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.examplemod.spook",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.examplemod.test"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (!KEY_RELEASE_CONTROL.isPressed())
            {
                return;
            }

            SoulControllerComponent controllerComponent = SoulControl.getSoulController(MinecraftClient.getInstance().player);
            if (!controllerComponent.isControlling())
            {
                return;
            }

            AllPackets.toServer(new C2SReleaseControl());
        });
    }
}
