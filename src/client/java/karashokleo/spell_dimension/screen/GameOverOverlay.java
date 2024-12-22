package karashokleo.spell_dimension.screen;

import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.leobrary.gui.api.IGuiOverlay;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import karashokleo.leobrary.gui.api.overlay.TextBox;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class GameOverOverlay extends SideBar<SideBar.IntSignature> implements IGuiOverlay
{
    public static final List<Text> TEXTS = List.of(
            SDTexts.TEXT$GAME_OVER$HEAD.get().formatted(Formatting.AQUA),
            SDTexts.TEXT$GAME_OVER$THANKS.get().formatted(Formatting.YELLOW),
            SDTexts.TEXT$GAME_OVER$MAKING.get().formatted(Formatting.LIGHT_PURPLE),
            SDTexts.TEXT$GAME_OVER$FEEDBACK.get().formatted(Formatting.GREEN),
            SDTexts.TEXT$GAME_OVER$WISH.get().formatted(Formatting.GOLD)
    );

    public GameOverOverlay()
    {
        super(80, 4);
    }

    @Override
    public IntSignature getSignature()
    {
        return new IntSignature(0);
    }

    @Override
    protected boolean isOnHold()
    {
        return true;
    }

    @Override
    public boolean isScreenOn()
    {
        if (L2HostilityClient.getClient().currentScreen != null) return false;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return false;
        return player.getMainHandStack().isOf(AllItems.MEDAL);
    }

    @Override
    protected int getYOffset(int height)
    {
        float progress = (max_ease - ease_time) / max_ease;
        return Math.round(-progress * height);
    }

    @Override
    public void render(InGameHud gui, DrawContext context, float tickDelta, int scaledWidth, int scaledHeight)
    {
        if (!ease(gui.getTicks() + tickDelta)) return;
        new TextBox(context, 1, 1, scaledWidth / 2 + getXOffset(scaledWidth), scaledHeight / 2 + getYOffset(scaledHeight), scaledWidth / 2)
                .renderLongText(MinecraftClient.getInstance().textRenderer, TEXTS);
    }
}
