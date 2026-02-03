package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.content.network.C2SSpiritTomeShopBuy;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class SpiritTomeShopPage implements SpiritTomePage
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/3.png");
    public static final Text TITLE = SDTexts.TEXT$SPIRIT_TOME$SHOP$TITLE.get();
    public static final Text SUBTITLE = SDTexts.TEXT$SPIRIT_TOME$SHOP$SUBTITLE.get();

    private final Rect2i viewport;
    private final List<ShopCard> cards;

    public SpiritTomeShopPage(Rect2i viewport)
    {
        this.viewport = viewport;
        this.cards = createCards(viewport, 16, 8);
    }

    @Override
    public Identifier getBackground()
    {
        return BACKGROUND_TEXTURE;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player)
    {
        // title
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(
            viewport.getX() + viewport.getWidth() / 2f,
            viewport.getY() + viewport.getHeight() / 2f,
            0
        );
        matrices.scale(2, 2, 2);
        context.drawCenteredTextWithShadow(
            textRenderer,
            TITLE,
            0,
            -textRenderer.fontHeight - 2,
            0xFFFFFF
        );
        context.drawCenteredTextWithShadow(
            textRenderer,
            SUBTITLE,
            0,
            2,
            0xDDDDDD
        );
        matrices.pop();

        // cards
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        for (ShopCard card : this.cards)
        {
            card.update(component);
        }
        for (ShopCard card : this.cards)
        {
            card.render(context, textRenderer, mouseX, mouseY, player);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY)
    {
        for (ShopCard card : this.cards)
        {
            if (card.mouseClicked(mouseX, mouseY))
            {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("SameParameterValue")
    private static List<ShopCard> createCards(Rect2i viewport, int gapX, int gapY)
    {
        List<ShopCard> cards = new ArrayList<>(9);
        int gridX = viewport.getX() + gapX;
        int gridY = viewport.getY() + gapY;
        int gridWidth = (viewport.getWidth() - 4 * gapX) / 3;
        int gridHeight = (viewport.getHeight() - 4 * gapY) / 3;
        cards.add(new ItemCard(gridX, gridY, gridWidth, gridHeight, 0));
        gridX += (gridWidth + gapX);
        cards.add(new ItemCard(gridX, gridY, gridWidth, gridHeight, 1));
        gridX += (gridWidth + gapX);
        cards.add(new ItemCard(gridX, gridY, gridWidth, gridHeight, 2));
        gridY += (gridHeight + gapY);
        gridX = viewport.getX() + gapX;
        cards.add(new LotteryCard(gridX, gridY, gridWidth, gridHeight));
        gridX += (gridWidth + gapX) * 2;
        cards.add(new RefreshCard(gridX, gridY, gridWidth, gridHeight));
        gridY += (gridHeight + gapY);
        gridX = viewport.getX() + gapX;
        cards.add(new ItemCard(gridX, gridY, gridWidth, gridHeight, 3));
        gridX += (gridWidth + gapX);
        cards.add(new ItemCard(gridX, gridY, gridWidth, gridHeight, 4));
        gridX += (gridWidth + gapX);
        cards.add(new ItemCard(gridX, gridY, gridWidth, gridHeight, 5));
        return cards;
    }

    private static abstract class ShopCard
    {
        protected static final Text TEXT_BUY = SDTexts.TEXT$SPIRIT_TOME$SHOP$BUY.get();
        protected static final int TEXT_MARGIN = 4;
        protected final Rect2i cardRect;
        protected final Rect2i iconRect;
        protected final Rect2i buttonRect;
        protected final int operationFlag;
        protected ItemStack icon = ItemStack.EMPTY;
        protected int cost;
        protected boolean affordable;
        protected boolean active;

        protected ShopCard(int x, int y, int width, int height, int operationFlag)
        {
            this.cardRect = new Rect2i(x, y, width, height);
            int iconSize = 16;
            this.iconRect = new Rect2i(
                x + (width - iconSize) / 2,
                y + 4,
                iconSize,
                iconSize
            );
            int buttonWidth = 54;
            int buttonHeight = 12;
            this.buttonRect = new Rect2i(
                x + (width - buttonWidth) / 2,
                y + height - 4 - buttonHeight,
                buttonWidth,
                buttonHeight
            );
            this.operationFlag = operationFlag;
        }

        protected void update(SpiritTomeComponent component)
        {
            this.cost = SpiritTomeComponent.getShopCost(this.operationFlag);
            this.affordable = component.getSpirit() >= cost;
            this.active = this.affordable;
        }

        protected void render(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY, ClientPlayerEntity player)
        {
            // background
//            context.fill(
//                this.cardRect.getX(),
//                this.cardRect.getY(),
//                this.cardRect.getX() + this.cardRect.getWidth(),
//                this.cardRect.getY() + this.cardRect.getHeight(),
//                0x33000000
//            );
            // slot
            int padding = 1;
            context.fill(
                this.iconRect.getX() - padding,
                this.iconRect.getY() - padding,
                this.iconRect.getX() + this.iconRect.getWidth() + padding,
                this.iconRect.getY() + this.iconRect.getHeight() + padding,
                0xAA666666
            );
            // icon
            context.drawItem(this.icon, this.iconRect.getX(), this.iconRect.getY());
            // title
            int textY = this.iconRect.getY() + this.iconRect.getHeight() + 4;
            drawScrollableText(
                context,
                textRenderer,
                this.getTitleText(),
                this.cardRect.getX() + TEXT_MARGIN,
                textY,
                this.cardRect.getX() + this.cardRect.getWidth() - TEXT_MARGIN,
                textY + textRenderer.fontHeight,
                0xffffff
            );
            textY += 12;
            // cost
            drawScrollableText(
                context,
                textRenderer,
                SDTexts.TEXT$SPIRIT_TOME$SHOP$COST.get(this.cost),
                this.cardRect.getX() + TEXT_MARGIN,
                textY,
                this.cardRect.getX() + this.cardRect.getWidth() - TEXT_MARGIN,
                textY + textRenderer.fontHeight,
                0xdddddd
            );
            // button
            boolean buttonHovered = this.buttonRect.contains(mouseX, mouseY);
            renderButton(context, textRenderer, buttonHovered);
            // tooltip
            List<Text> tooltip = null;
            if (supportsItemTooltip() &&
                this.iconRect.contains(mouseX, mouseY))
            {
                tooltip = icon.getTooltip(player, TooltipContext.BASIC);
            } else if (this.buttonRect.contains(mouseX, mouseY))
            {
                tooltip = getButtonTooltip();
            }
            if (tooltip == null || tooltip.isEmpty())
            {
                return;
            }
            context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
        }

        private void renderButton(DrawContext context, TextRenderer textRenderer, boolean hovered)
        {
            int background = active ? (hovered ? 0xAA888888 : 0xAA666666) : 0xAA222222;
            context.fill(
                this.buttonRect.getX(),
                this.buttonRect.getY(),
                this.buttonRect.getX() + this.buttonRect.getWidth(),
                this.buttonRect.getY() + this.buttonRect.getHeight(),
                background
            );
            Text text = this.getButtonText();
            int color = active ? 0xFFFFFF : 0x888888;
            drawScrollableText(
                context, textRenderer, text,
                this.buttonRect.getX() + TEXT_MARGIN,
                this.buttonRect.getY(),
                this.buttonRect.getX() + this.buttonRect.getWidth() - TEXT_MARGIN,
                this.buttonRect.getY() + this.buttonRect.getHeight(),
                color
            );
        }

        /**
         * Vanilla copy
         */
        protected static void drawScrollableText(DrawContext context, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, int color)
        {
            int width = textRenderer.getWidth(text);
            int y = (top + bottom - 9) / 2 + 1;
            int maxWidth = right - left;
            if (width > maxWidth)
            {
                int overflow = width - maxWidth;
                double time = Util.getMeasuringTimeMs() / 1000.0;
                double period = Math.max(overflow * 0.5, 3.0);
                double factor = Math.sin((Math.PI / 2) * Math.cos((Math.PI * 2) * time / period)) / 2.0 + 0.5;
                double offset = MathHelper.lerp(factor, 0.0, overflow);
                context.enableScissor(left, top, right, bottom);
                context.drawTextWithShadow(textRenderer, text, left - (int) offset, y, color);
                context.disableScissor();
            } else
            {
                context.drawCenteredTextWithShadow(textRenderer, text, (left + right) / 2, y, color);
            }
        }

        protected Text getTitleText()
        {
            return icon.getName();
        }

        protected Text getButtonText()
        {
            return TEXT_BUY;
        }

        protected boolean supportsItemTooltip()
        {
            return true;
        }

        protected List<Text> getButtonTooltip()
        {
            return List.of();
        }

        protected boolean mouseClicked(double mouseX, double mouseY)
        {
            if (!this.buttonRect.contains((int) mouseX, (int) mouseY))
            {
                return false;
            }
            AllPackets.toServer(new C2SSpiritTomeShopBuy(this.operationFlag));
            return true;
        }
    }

    private static final class ItemCard extends ShopCard
    {
        private static final Text TEXT_PURCHASED = SDTexts.TEXT$SPIRIT_TOME$SHOP$PURCHASED.get();
        private boolean purchased;

        private ItemCard(int x, int y, int width, int height, int operationFlag)
        {
            super(x, y, width, height, operationFlag);
        }

        @Override
        protected void update(SpiritTomeComponent component)
        {
            List<Item> items = component.getShopItems();
            if (this.operationFlag >= items.size())
            {
                this.icon = Items.BARRIER.getDefaultStack();
                this.cost = 0;
                this.affordable = false;
                this.purchased = false;
                this.active = false;
                return;
            }
            this.icon = items.get(this.operationFlag).getDefaultStack();
            this.cost = SpiritTomeComponent.getShopCost(this.icon);
            this.affordable = component.getSpirit() >= cost;
            this.purchased = component.isShopItemPurchased(this.operationFlag);
            this.active = affordable && !purchased;
        }

        @Override
        protected Text getButtonText()
        {
            return purchased ? TEXT_PURCHASED : TEXT_BUY;
        }

        @Override
        protected List<Text> getButtonTooltip()
        {
            if (purchased)
            {
                return List.of();
            }
            List<Text> tooltip = new ArrayList<>();
            if (!affordable)
            {
                tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
            }
            tooltip.add(SDTexts.TEXT$SPIRIT_TOME$COST.get(cost));
            tooltip.add(TEXT_BUY.copy().append(ScreenTexts.SPACE).append(icon.getName()));
            return tooltip;
        }
    }

    private static final class LotteryCard extends ShopCard
    {
        private static final Text TITLE = SDTexts.TEXT$SPIRIT_TOME$SHOP$LOTTERY.get();

        private LotteryCard(int x, int y, int width, int height)
        {
            super(x, y, width, height, SpiritTomeComponent.LOTTERY_FLAG);
            this.icon = Items.COMMAND_BLOCK.getDefaultStack();
        }

        @Override
        protected boolean supportsItemTooltip()
        {
            return false;
        }

        @Override
        protected Text getTitleText()
        {
            return TITLE;
        }

        @Override
        protected List<Text> getButtonTooltip()
        {
            List<Text> tooltip = new ArrayList<>();
            if (!affordable)
            {
                tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
            }
            tooltip.add(SDTexts.TEXT$SPIRIT_TOME$COST.get(cost));
            tooltip.add(TEXT_BUY.copy().append(ScreenTexts.SPACE).append(TITLE));
            return tooltip;
        }
    }

    private static final class RefreshCard extends ShopCard
    {
        private static final Text TITLE = SDTexts.TEXT$SPIRIT_TOME$SHOP$REFRESH.get();

        private RefreshCard(int x, int y, int width, int height)
        {
            super(x, y, width, height, SpiritTomeComponent.REFRESH_FLAG);
            this.icon = Items.CLOCK.getDefaultStack();
        }

        @Override
        protected boolean supportsItemTooltip()
        {
            return false;
        }

        @Override
        protected Text getTitleText()
        {
            return TITLE;
        }

        @Override
        protected List<Text> getButtonTooltip()
        {
            List<Text> tooltip = new ArrayList<>();
            if (!affordable)
            {
                tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
            }
            tooltip.add(SDTexts.TEXT$SPIRIT_TOME$COST.get(cost));
            tooltip.add(TITLE);
            return tooltip;
        }
    }
}
