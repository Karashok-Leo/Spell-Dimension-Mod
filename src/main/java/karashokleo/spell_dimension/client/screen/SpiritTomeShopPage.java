package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.content.network.C2SSpiritTomeShopBuy;
import karashokleo.spell_dimension.content.network.C2SSpiritTomeShopSync;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;

import java.util.ArrayList;
import java.util.List;

public class SpiritTomeShopPage implements SpiritTomePage
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/3.png");
    private static final ItemStack LOTTERY_ICON = new ItemStack(Items.COMMAND_BLOCK);
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 54;
    private static final int CARD_GAP_X = 10;
    private static final int CARD_GAP_Y = 8;
    private static final int BUTTON_WIDTH = 44;
    private static final int BUTTON_HEIGHT = 12;
    private static final int ICON_SIZE = 16;

    private final Rect2i viewport;
    private final List<ItemStack> shopItems;

    public SpiritTomeShopPage(Rect2i viewport, ClientPlayerEntity player)
    {
        this.viewport = viewport;
        this.shopItems = SpiritTomeComponent.get(player)
            .getShopItems()
            .stream()
            .map(Item::getDefaultStack)
            .toList();
        // request refresh
        AllPackets.toServer(new C2SSpiritTomeShopSync());
    }

    @Override
    public Identifier getBackground()
    {
        return BACKGROUND_TEXTURE;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player)
    {
        SpiritTomeComponent component = SpiritTomeComponent.get(player);

        // title
        int titleX = this.viewport.getX() + 16;
        int titleY = this.viewport.getY() + 10;
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$SHOP$TITLE.get(), titleX, titleY, 0xFFFFFF, true);
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$SHOP$REFRESH.get(), titleX, titleY + 14, 0xDDDDDD, true);

        int gridX = this.viewport.getX() + (this.viewport.getWidth() - getGridWidth()) / 2;
        int gridY = this.viewport.getY() + 32;

        boolean hovering = false;
        List<Text> tooltip = null;

        for (int i = 0; i < SpiritTomeComponent.SHOP_SLOT_COUNT; i++)
        {
            int cardX = gridX + (i % 3) * (CARD_WIDTH + CARD_GAP_X);
            int cardY = gridY + (i / 3) * (CARD_HEIGHT + CARD_GAP_Y);
            ItemStack stack = shopItems.get(i);
            boolean purchased = component.isShopItemPurchased(i);
            int cost = component.getShopCost(i);
            boolean affordable = component.getSpirit() >= cost;
            boolean active = affordable && !purchased && !stack.isEmpty();
            renderShopCard(context, textRenderer, mouseX, mouseY, cardX, cardY, stack, cost, purchased, active);
            if (isHoveringCard(mouseX, mouseY, cardX, cardY, CARD_WIDTH, CARD_HEIGHT))
            {
                hovering = true;
                tooltip = buildShopTooltip(stack, cost, purchased, affordable);
            }
        }

        int lotteryX = this.viewport.getX() + (this.viewport.getWidth() - getLotteryWidth()) / 2;
        int lotteryY = gridY + getGridHeight() + 10;
        int lotteryCost = component.getShopCost(-1);
        boolean lotteryAffordable = component.getSpirit() >= lotteryCost;
        renderLotteryCard(context, textRenderer, mouseX, mouseY, lotteryX, lotteryY, lotteryCost, lotteryAffordable);
        if (isHoveringCard(mouseX, mouseY, lotteryX, lotteryY, getLotteryWidth(), CARD_HEIGHT))
        {
            hovering = true;
            tooltip = buildLotteryTooltip(lotteryCost, lotteryAffordable);
        }

        if (!hovering || tooltip == null || tooltip.isEmpty())
        {
            return;
        }
        context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY)
    {
        int gridX = this.viewport.getX() + (this.viewport.getWidth() - getGridWidth()) / 2;
        int gridY = this.viewport.getY() + 32;

        for (int i = 0; i < SpiritTomeComponent.SHOP_SLOT_COUNT; i++)
        {
            int cardX = gridX + (i % 3) * (CARD_WIDTH + CARD_GAP_X);
            int cardY = gridY + (i / 3) * (CARD_HEIGHT + CARD_GAP_Y);
            Rect2i button = getButtonRect(cardX, cardY, CARD_WIDTH);
            if (isWithin(mouseX, mouseY, button))
            {
                AllPackets.toServer(new C2SSpiritTomeShopBuy(i));
                return true;
            }
        }

        int lotteryX = this.viewport.getX() + (this.viewport.getWidth() - getLotteryWidth()) / 2;
        int lotteryY = gridY + getGridHeight() + 10;
        Rect2i lotteryButton = getButtonRect(lotteryX, lotteryY, getLotteryWidth());
        if (isWithin(mouseX, mouseY, lotteryButton))
        {
            AllPackets.toServer(new C2SSpiritTomeShopBuy(-1));
            return true;
        }

        return false;
    }

    private void renderShopCard(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY, int x, int y, ItemStack stack, int cost, boolean purchased, boolean active)
    {
        context.fill(x, y, x + CARD_WIDTH, y + CARD_HEIGHT, 0x33000000);

        int iconX = x + (CARD_WIDTH - ICON_SIZE) / 2;
        int iconY = y + 4;
        if (!stack.isEmpty())
        {
            context.drawItem(stack, iconX, iconY);
        }

        Text name = stack.isEmpty() ? Text.literal("???") : stack.getName();
        OrderedText nameText = trim(textRenderer, name, CARD_WIDTH - 6);
        int nameX = x + (CARD_WIDTH - textRenderer.getWidth(nameText)) / 2;
        context.drawText(textRenderer, nameText, nameX, y + 24, 0xFFFFFF, true);

        OrderedText costText = trim(textRenderer, SDTexts.TEXT$SPIRIT_TOME$COST.get(cost), CARD_WIDTH - 6);
        int costX = x + (CARD_WIDTH - textRenderer.getWidth(costText)) / 2;
        context.drawText(textRenderer, costText, costX, y + 34, 0xDDDDDD, true);

        Rect2i button = getButtonRect(x, y, CARD_WIDTH);
        boolean hovered = isWithin(mouseX, mouseY, button);
        boolean enabled = active;
        renderButton(context, textRenderer, button, hovered, enabled, purchased ? SDTexts.TEXT$SPIRIT_TOME$SHOP$BOUGHT.get() : SDTexts.TEXT$SPIRIT_TOME$SHOP$BUY.get());
    }

    private void renderLotteryCard(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY, int x, int y, int cost, boolean affordable)
    {
        context.fill(x, y, x + getLotteryWidth(), y + CARD_HEIGHT, 0x33000000);

        int iconX = x + (getLotteryWidth() - ICON_SIZE) / 2;
        int iconY = y + 4;
        context.drawItem(LOTTERY_ICON, iconX, iconY);

        OrderedText title = trim(textRenderer, SDTexts.TEXT$SPIRIT_TOME$SHOP$LOTTERY.get(), getLotteryWidth() - 6);
        int titleX = x + (getLotteryWidth() - textRenderer.getWidth(title)) / 2;
        context.drawText(textRenderer, title, titleX, y + 24, 0xFFFFFF, true);

        OrderedText costText = trim(textRenderer, SDTexts.TEXT$SPIRIT_TOME$COST.get(cost), getLotteryWidth() - 6);
        int costX = x + (getLotteryWidth() - textRenderer.getWidth(costText)) / 2;
        context.drawText(textRenderer, costText, costX, y + 34, 0xDDDDDD, true);

        Rect2i button = getButtonRect(x, y, getLotteryWidth());
        boolean hovered = isWithin(mouseX, mouseY, button);
        renderButton(context, textRenderer, button, hovered, affordable, SDTexts.TEXT$SPIRIT_TOME$SHOP$LOTTERY.get());
    }

    private static void renderButton(DrawContext context, TextRenderer textRenderer, Rect2i button, boolean hovered, boolean enabled, Text text)
    {
        int background = enabled ? (hovered ? 0xAA888888 : 0xAA666666) : 0xAA222222;
        context.fill(button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight(), background);
        int textX = button.getX() + (button.getWidth() - textRenderer.getWidth(text)) / 2;
        int textY = button.getY() + (button.getHeight() - textRenderer.fontHeight) / 2;
        int color = enabled ? 0xFFFFFF : 0x888888;
        context.drawText(textRenderer, text, textX, textY, color, false);
    }

    private static OrderedText trim(TextRenderer textRenderer, Text text, int maxWidth)
    {
        return Language.getInstance().reorder(textRenderer.trimToWidth(text, maxWidth));
    }

    private static List<Text> buildShopTooltip(ItemStack stack, int cost, boolean purchased, boolean affordable)
    {
        List<Text> tooltip = new ArrayList<>();
        if (!stack.isEmpty())
        {
            tooltip.add(stack.getName());
        }
        tooltip.add(SDTexts.TEXT$SPIRIT_TOME$COST.get(cost));
        if (purchased)
        {
            tooltip.add(SDTexts.TEXT$SPIRIT_TOME$SHOP$BOUGHT.get().formatted(Formatting.GRAY));
        } else if (!affordable)
        {
            tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
        }
        return tooltip;
    }

    private static List<Text> buildLotteryTooltip(int cost, boolean affordable)
    {
        List<Text> tooltip = new ArrayList<>();
        tooltip.add(SDTexts.TEXT$SPIRIT_TOME$SHOP$LOTTERY_DESC.get());
        tooltip.add(SDTexts.TEXT$SPIRIT_TOME$COST.get(cost));
        if (!affordable)
        {
            tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
        }
        return tooltip;
    }

    private static boolean isHoveringCard(int mouseX, int mouseY, int x, int y, int width, int height)
    {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    private static boolean isWithin(double mouseX, double mouseY, Rect2i rect)
    {
        return mouseX >= rect.getX() && mouseY >= rect.getY() && mouseX < rect.getX() + rect.getWidth() && mouseY < rect.getY() + rect.getHeight();
    }

    private static Rect2i getButtonRect(int x, int y, int width)
    {
        int buttonX = x + (width - BUTTON_WIDTH) / 2;
        int buttonY = y + CARD_HEIGHT - BUTTON_HEIGHT - 4;
        return new Rect2i(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    private static int getGridWidth()
    {
        return 3 * CARD_WIDTH + 2 * CARD_GAP_X;
    }

    private static int getGridHeight()
    {
        return 2 * CARD_HEIGHT + CARD_GAP_Y;
    }

    private static int getLotteryWidth()
    {
        return CARD_WIDTH + 40;
    }
}
