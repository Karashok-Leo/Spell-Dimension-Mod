package karashokleo.spell_dimension.client.screen;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public interface SpiritTomePage
{
    Identifier getBackground();

    void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player);

    boolean mouseClicked(double mouseX, double mouseY);
}
