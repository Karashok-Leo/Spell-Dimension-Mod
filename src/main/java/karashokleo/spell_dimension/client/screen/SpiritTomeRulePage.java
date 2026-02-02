package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.Util;

public class SpiritTomeRulePage implements SpiritTomePage
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/2.png");
    private static final int LINE_HEIGHT = 15;
    private static final int LINE_GAP = 10;
    private static final int HORIZONTAL_PADDING = 20;
    private static final String REPEAT = "?".repeat(100);

    private final Rect2i viewport;
    private long hoverTime;
    private final Text[] rules;
    private final Text[] rulesObfuscated;
    private final OrderedText[] rulesPreview;

    public SpiritTomeRulePage(Rect2i viewport, TextRenderer textRenderer)
    {
        this.viewport = viewport;
        this.hoverTime = -1L;
        this.rules = new Text[]{
            SDTexts.TEXT$SPIRIT_TOME$RULE$0.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$1.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$2.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$3.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$4.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$5.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$6.get(),
            SDTexts.TEXT$SPIRIT_TOME$RULE$7.get()
        };
        int maxWidth = Math.max(0, this.viewport.getWidth() - 2 * HORIZONTAL_PADDING);
        this.rulesObfuscated = new Text[this.rules.length];
        this.rulesPreview = new OrderedText[this.rules.length];
        for (int i = 0; i < this.rules.length; i++)
        {
            Text rule = this.rules[i];
            this.rulesObfuscated[i] = getTextObfuscated(textRenderer, rule, maxWidth);
            this.rulesPreview[i] = getTextWithEllipsis(textRenderer, rule, maxWidth);
        }
    }

    @Override
    public Identifier getBackground()
    {
        return BACKGROUND_TEXTURE;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player)
    {
        int left = this.viewport.getX() + HORIZONTAL_PADDING;
        int right = this.viewport.getX() + this.viewport.getWidth() - HORIZONTAL_PADDING;
        int numRules = this.rules.length;
        int totalHeight = numRules * LINE_HEIGHT + (numRules - 1) * LINE_GAP;
        int y = this.viewport.getY() + (this.viewport.getHeight() - totalHeight) / 2;

        boolean flag = false;
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        for (int i = 0; i < numRules; i++)
        {
            int top = y;
            int bottom = top + LINE_HEIGHT;
            boolean hovered = mouseX >= left &&
                mouseX < right &&
                mouseY >= top &&
                mouseY < bottom;
            flag |= hovered;
            drawText(context, textRenderer, i, left, top, right, bottom, hovered, !component.isRuleRevealed(i));
            y += LINE_HEIGHT;
            if (i < numRules - 1)
            {
                y += LINE_GAP;
            }
        }
        if (flag)
        {
            return;
        }
        // not hovering any line, reset hover time
        this.hoverTime = -1L;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY)
    {
        return false;
    }

    protected void drawText(DrawContext context, TextRenderer textRenderer, int index, int left, int top, int right, int bottom, boolean hovered, boolean obfuscated)
    {
        int y = top + (LINE_HEIGHT - textRenderer.fontHeight) / 2;
        if (obfuscated)
        {
            context.drawTextWithShadow(textRenderer, this.rulesObfuscated[index], left, y, 0xffffff);
            return;
        }

        Text text = this.rules[index];
        int maxWidth = Math.max(0, right - left);
        int width = textRenderer.getWidth(text);
        if (width < maxWidth)
        {
            context.drawTextWithShadow(textRenderer, text, left, y, 0xffffff);
            return;
        }
        if (hovered)
        {
            // hovered, scroll
            int overflow = width - maxWidth;
            long now = Util.getMeasuringTimeMs();
            if (this.hoverTime < 0L)
            {
                this.hoverTime = now;
            }
            double time = (now - this.hoverTime) / 20.0;
            int offset = (int) Math.min(overflow, Math.round(time));
            context.enableScissor(left, top, right, bottom);
            context.drawTextWithShadow(textRenderer, text, left - offset, y, 0xffffff);
            context.disableScissor();
            return;
        }

        // not hovered, ends with ellipsis
        context.drawTextWithShadow(textRenderer, this.rulesPreview[index], left, y, 0xffffff);
    }

    private static Text getTextObfuscated(TextRenderer textRenderer, Text text, int maxWidth)
    {
        int width = textRenderer.getWidth(text);
        width = Math.min(width, maxWidth);
        String string = textRenderer.trimToWidth(REPEAT, width);
        return Text.literal(string).formatted(Formatting.OBFUSCATED);
    }

    private static OrderedText getTextWithEllipsis(TextRenderer textRenderer, Text text, int maxWidth)
    {
        int ellipsisWidth = textRenderer.getWidth(ScreenTexts.ELLIPSIS);
        int available = Math.max(0, maxWidth - ellipsisWidth);
        StringVisitable trimmed = textRenderer.trimToWidth(text, available);
        if (text != trimmed)
        {
            trimmed = StringVisitable.concat(trimmed, ScreenTexts.ELLIPSIS);
        }
        return Language.getInstance().reorder(trimmed);
    }
}
