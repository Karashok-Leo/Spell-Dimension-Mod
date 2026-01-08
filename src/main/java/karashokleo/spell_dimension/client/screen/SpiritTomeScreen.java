package karashokleo.spell_dimension.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpiritTomeScreen extends Screen
{
    public SpiritTomeScreen()
    {
        super(Text.empty());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (super.keyPressed(keyCode, scanCode, modifiers))
        {
            return true;
        } else if (this.client != null &&
            this.client.options.inventoryKey.matchesKey(keyCode, scanCode))
        {
            this.close();
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public boolean shouldPause()
    {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        context.drawTexture(
            new Identifier("spell-dimension-book", "textures/background/1.png"),
            (this.width - 512) / 2,
            (this.height - 512) / 2,
            0,
            0,
            512,
            512
        );

        super.render(context, mouseX, mouseY, delta);
    }
}
