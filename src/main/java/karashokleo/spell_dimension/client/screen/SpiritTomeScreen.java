package karashokleo.spell_dimension.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SpiritTomeScreen extends Screen
{
    private static final Identifier BORDER_TEXTURE = new Identifier("spell-dimension", "textures/gui/spirit_tome.png");
    private static final int BACKGROUND_SIZE = 512;

    private Rect2i viewport;
    private SpiritTomePage[] pages;
    private int currentPage;

    public SpiritTomeScreen()
    {
        super(Text.empty());
    }

    @Override
    protected void init()
    {
        super.init();
        int marginX = Math.max(0, (this.width - 384) / 2);
        int marginY = Math.max(0, (this.height - 226) / 2);
        int width = this.width - 2 * marginX;
        int height = this.height - 2 * marginY;
        this.viewport = new Rect2i(marginX, marginY, width, height);
        this.pages = new SpiritTomePage[]{
            new SpiritTomeInfoPage(this.viewport)
        };
        this.currentPage = 0;
    }

    protected SpiritTomePage getCurrentPage()
    {
        return this.pages[this.currentPage];
    }

    @Override
    public boolean shouldPause()
    {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        if (this.client == null)
        {
            return;
        }

        ClientPlayerEntity player = this.client.player;
        if (player == null)
        {
            return;
        }

        // background
        renderBackground(context, mouseX, mouseY);

        // page
        SpiritTomePage page = this.getCurrentPage();
        page.render(this.viewport, context, mouseX, mouseY, this.textRenderer, player);

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderBackground(DrawContext context, int mouseX, int mouseY)
    {
        context.enableScissor(
            this.viewport.getX(),
            this.viewport.getY(),
            this.width - this.viewport.getX(),
            this.height - this.viewport.getY()
        );

        // -1 ~ 1
        int length = Math.max(this.width, this.height) / 2;
        float normalizedX = MathHelper.clamp((mouseX - this.width / 2f) / length, -1f, 1f);
        float normalizedY = MathHelper.clamp((mouseY - this.height / 2f) / length, -1f, 1f);

        int maxOffsetX = Math.max(0, (BACKGROUND_SIZE - this.viewport.getWidth()) / 2);
        int maxOffsetY = Math.max(0, (BACKGROUND_SIZE - this.viewport.getHeight()) / 2);
        int maxOffset = Math.min(maxOffsetX, maxOffsetY);

        int offsetX = Math.round(-normalizedX * maxOffset);
        int offsetY = Math.round(-normalizedY * maxOffset);

        int baseX = (this.width - BACKGROUND_SIZE) / 2;
        int baseY = (this.height - BACKGROUND_SIZE) / 2;

        Identifier background = this.getCurrentPage().getBackground();
        context.drawTexture(
            background,
            baseX + offsetX,
            baseY + offsetY,
            0,
            0,
            BACKGROUND_SIZE,
            BACKGROUND_SIZE,
            BACKGROUND_SIZE,
            BACKGROUND_SIZE
        );

        context.disableScissor();

        drawBorder(
            context,
            this.viewport.getX() - 12, this.viewport.getY() - 12,
            this.viewport.getWidth() + 24, this.viewport.getHeight() + 24,
            32, 27,
            76, 64,
            0, 0
        );
    }

    @SuppressWarnings("SameParameterValue")
    private static void drawBorder(
        DrawContext context,
        int x, int y,
        int width, int height,
        int cornerWidth, int cornerHeight,
        int borderWidth, int borderHeight,
        int u, int v
    )
    {
        // left top
        context.drawTexture(
            BORDER_TEXTURE,
            x, y,
            u, v,
            cornerWidth, cornerHeight
        );
        // right top
        context.drawTexture(
            BORDER_TEXTURE,
            x + width - cornerWidth, y,
            u + borderWidth - cornerWidth, v,
            cornerWidth, cornerHeight
        );
        // left bottom
        context.drawTexture(
            BORDER_TEXTURE,
            x, y + height - cornerHeight,
            u, v + borderHeight - cornerHeight,
            cornerWidth, cornerHeight
        );
        // right bottom
        context.drawTexture(
            BORDER_TEXTURE,
            x + width - cornerWidth, y + height - cornerHeight,
            u + borderWidth - cornerWidth, v + borderHeight - cornerHeight,
            cornerWidth, cornerHeight
        );
        // repeating edges
        int halfEdgeWidth = (borderWidth - cornerWidth * 2) / 2;
        int halfEdgeHeight = (borderHeight - cornerHeight * 2) / 2;
        int halfRenderWidth = (width - cornerWidth * 2) / 2;
        int halfRenderHeight = (height - cornerHeight * 2) / 2;
        // top center
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x + cornerWidth, y,
            halfRenderWidth, cornerHeight,
            u + cornerWidth, v,
            halfEdgeWidth, cornerHeight
        );
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x + cornerWidth + halfRenderWidth, y,
            halfRenderWidth, cornerHeight,
            u + cornerWidth + halfEdgeWidth, v,
            halfEdgeWidth, cornerHeight
        );
        // bottom center
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x + cornerWidth, y + height - cornerHeight,
            halfRenderWidth, cornerHeight,
            u + cornerWidth, v + borderHeight - cornerHeight,
            halfEdgeWidth, cornerHeight
        );
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x + cornerWidth + halfRenderWidth, y + height - cornerHeight,
            halfRenderWidth, cornerHeight,
            u + cornerWidth + halfEdgeWidth, v + borderHeight - cornerHeight,
            halfEdgeWidth, cornerHeight
        );
        // left center
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x, y + cornerHeight,
            cornerWidth, halfRenderHeight,
            u, v + cornerHeight,
            cornerWidth, halfEdgeHeight
        );
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x, y + cornerHeight + halfRenderHeight,
            cornerWidth, halfRenderHeight,
            u, v + cornerHeight + halfEdgeHeight,
            cornerWidth, halfEdgeHeight
        );
        // right center
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x + width - cornerWidth, y + cornerHeight,
            cornerWidth, halfRenderHeight,
            u + borderWidth - cornerWidth, v + cornerHeight,
            cornerWidth, halfEdgeHeight
        );
        context.drawRepeatingTexture(
            BORDER_TEXTURE,
            x + width - cornerWidth, y + cornerHeight + halfRenderHeight,
            cornerWidth, halfRenderHeight,
            u + borderWidth - cornerWidth, v + cornerHeight + halfEdgeHeight,
            cornerWidth, halfEdgeHeight
        );
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
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (button == 0 &&
            this.getCurrentPage()
                .mouseClicked(this.viewport, mouseX, mouseY))
        {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
