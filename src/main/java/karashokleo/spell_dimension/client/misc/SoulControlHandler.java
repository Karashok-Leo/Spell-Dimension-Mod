package karashokleo.spell_dimension.client.misc;

import karashokleo.spell_dimension.content.component.SoulControllerComponent;
import karashokleo.spell_dimension.content.misc.SoulControl;
import karashokleo.spell_dimension.content.network.C2SSoulControl;
import karashokleo.spell_dimension.content.object.SoulInput;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SoulControlHandler
{
    public static void consumePlayerMoveInput(ClientPlayerEntity player, Input input, float cursorDeltaX, float cursorDeltaY)
    {
        SoulControllerComponent minionComponent = SoulControl.getSoulMinion(player);
        if (!minionComponent.isControlling())
        {
            return;
        }
        var cancel = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_V);
        // Send input to server to control the minion.
        input.tick(false, 1);
        var packet = createPacketFromInput(input, cursorDeltaX, cursorDeltaY, !cancel);
        AllPackets.toServer(packet);
        // Clear input to prevent moving the player.
        clearMoveInput(input);
    }

    public static C2SSoulControl createPacketFromInput(Input input, float cursorDeltaX, float cursorDeltaY, boolean controlling)
    {
        SoulInput soulInput = new SoulInput();
        soulInput.cursorDeltaX = cursorDeltaX;
        soulInput.cursorDeltaY = cursorDeltaY;
        soulInput.forward = input.movementForward;
        soulInput.sideways = input.movementSideways;
        soulInput.jumping = input.jumping;
        soulInput.sneaking = input.sneaking;
        soulInput.controlling = controlling;
        return new C2SSoulControl(soulInput);
    }

    public static void clearMoveInput(Input input)
    {
        input.movementSideways = 0;
        input.movementForward = 0;
        input.pressingForward = false;
        input.pressingBack = false;
        input.pressingLeft = false;
        input.pressingRight = false;
        input.jumping = false;
        input.sneaking = false;
    }
}
