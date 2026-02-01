package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class SpiritTomeRulePage implements SpiritTomePage
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/2.png");
    private static final int LINE_HEIGHT = 15;
    private static final int HORIZONTAL_PADDING = 20;

    private static final List<Text> RULES = List.of(
        SDTexts.TEXT$SPIRIT_TOME$RULE$0.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$1.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$2.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$3.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$4.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$5.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$6.get(),
        SDTexts.TEXT$SPIRIT_TOME$RULE$7.get()
    );

    private final Rect2i viewport;

    public SpiritTomeRulePage(Rect2i viewport)
    {
        this.viewport = viewport;
    }

    @Override
    public Identifier getBackground()
    {
        return BACKGROUND_TEXTURE;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player)
    {
        int RULE_GAP = 6;
        int left = this.viewport.getX() + HORIZONTAL_PADDING;
        int right = this.viewport.getX() + this.viewport.getWidth() - HORIZONTAL_PADDING;
        int totalHeight = RULES.size() * LINE_HEIGHT + (RULES.size() - 1) * RULE_GAP;
        int y = this.viewport.getY() + (this.viewport.getHeight() - totalHeight) / 2;

        for (int i = 0; i < RULES.size(); i++)
        {
            Text rule = RULES.get(i);
            int top = y;
            int bottom = top + LINE_HEIGHT;
            boolean hovered = mouseX >= left &&
                mouseX < right &&
                mouseY >= top &&
                mouseY < bottom;
            drawText(context, textRenderer, rule, left, top, right, bottom, hovered);
            y += LINE_HEIGHT;
            if (i < RULES.size() - 1)
            {
                y += RULE_GAP;
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY)
    {
        return false;
    }

    protected static void drawText(DrawContext context, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, boolean hovered)
    {
        int maxWidth = Math.max(0, right - left);
        int width = textRenderer.getWidth(text);
        int y = top + (LINE_HEIGHT - textRenderer.fontHeight) / 2;
        if (width < maxWidth)
        {
            context.drawTextWithShadow(textRenderer, text, left, y, 0xffffff);
        } else
        {
            if (hovered)
            {
                // hovered, scroll
                int overflow = width - maxWidth;
                double time = Util.getMeasuringTimeMs() / 1000.0;
                double period = Math.max(overflow * 0.5, 3.0);
                double factor = Math.sin((Math.PI / 2) * Math.cos((Math.PI * 2) * time / period)) / 2.0 + 0.5;
                double offset = MathHelper.lerp(factor, 0.0, overflow);
                context.enableScissor(left, top, right, bottom);
                context.drawTextWithShadow(textRenderer, text, left - (int) offset, y, 0xffffff);
                context.disableScissor();
            } else
            {
                // not hovered, ends with ellipsis
                int ellipsisWidth = textRenderer.getWidth(ScreenTexts.ELLIPSIS);
                int available = Math.max(0, maxWidth - ellipsisWidth);
                StringVisitable trimmed = textRenderer.trimToWidth(text, available);
                if (text != trimmed)
                {
                    trimmed = StringVisitable.concat(trimmed, ScreenTexts.ELLIPSIS);
                }
                context.drawTextWithShadow(textRenderer, Language.getInstance().reorder(trimmed), left, y, 0xffffff);
            }
        }
    }
}
