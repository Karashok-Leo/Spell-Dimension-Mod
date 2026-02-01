package karashokleo.spell_dimension.client.screen;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.util.Identifier;

public interface SpiritTomePage
{
    Identifier getBackground();

    void render(Rect2i viewport, DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player);

    boolean mouseClicked(Rect2i viewport, double mouseX, double mouseY);
}
