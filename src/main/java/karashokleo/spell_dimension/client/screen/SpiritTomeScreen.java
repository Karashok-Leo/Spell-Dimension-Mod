package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.config.SpiritUpgradeConfig;
import karashokleo.spell_dimension.content.component.SpiritHolderComponent;
import karashokleo.spell_dimension.content.network.C2SSpiritAttributeUpgrade;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpiritTomeScreen extends Screen
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/1.png");
    private static final Identifier BORDER_TEXTURE = new Identifier("spell-dimension", "textures/gui/spirit_tome.png");
    private static final int BACKGROUND_SIZE = 512;
    private static final int TEXT_START_X = 300;
    private static final int TEXT_START_Y = 100;
    private static final int LINE_HEIGHT = 15;
    private static final int VALUE_WIDTH = 120;
    private static final int BUTTON_SIZE = 12;
    private static final int BUTTON_OFFSET_X = 130;
    private static final int BUTTON_OFFSET_Y = -2;

    private final Map<EntityAttribute, UpgradeButton> upgradeButtons = new HashMap<>();

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
    protected void init()
    {
        super.init();
        this.upgradeButtons.clear();
        int x = TEXT_START_X + 130;
        int y = TEXT_START_Y - 2;
        for (SpiritUpgradeConfig.AttributeLine line : SpiritUpgradeConfig.ATTRIBUTE_LINES)
        {
            UpgradeButton button = new UpgradeButton(line.attribute(), x, y, 11, 11);
            this.upgradeButtons.put(line.attribute(), button);
            y += LINE_HEIGHT;
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        if (this.client == null)
        {
            return;
        }

        if (this.client.player == null)
        {
            return;
        }

        int marginX = this.width / 5;
        int marginY = this.height / 8;
        renderBackground(context, mouseX, mouseY, marginX, marginY);
        InventoryScreen.drawEntity(context, 200, 150, 30, 200 - mouseX, 150 - 50 - mouseY, this.client.player);
        context.fillGradient(280, 80, 480, 300, 0x33ffffff, 0x33ffffff);
        updateUpgradeButtons(this.client.player);
        renderTexts(context, this.client.player);
        renderUpgradeButtons(context, mouseX, mouseY);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (button == 0)
        {
            for (UpgradeButton upgradeButton : this.upgradeButtons.values())
            {
                if (upgradeButton.isHovered(mouseX, mouseY))
                {
                    if (upgradeButton.isActive())
                    {
                        requestUpgrade(upgradeButton.getAttribute());
                        return true;
                    }
                    return false;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderTexts(DrawContext context, PlayerEntity player)
    {
        int x = TEXT_START_X;
        int y = TEXT_START_Y;
        context.drawText(this.textRenderer, Text.literal("Spirit: " + SpiritHolderComponent.getSpirit(player)), x, y - LINE_HEIGHT, 0xFFFFFF, true);
        SpellPower.Result result = SpellPower.getSpellPower(SpellSchools.SOUL, player);
        for (SpiritUpgradeConfig.AttributeLine line : SpiritUpgradeConfig.ATTRIBUTE_LINES)
        {
            EntityAttribute attribute = line.attribute();
            String valueText = line.valueProvider().get(player, attribute, result);
            // attribute name
            context.drawText(this.textRenderer, Text.translatable(attribute.getTranslationKey()), x, y, 0xFFFFFF, true);
            // attribute value
            int width = this.textRenderer.getWidth(valueText);
            context.drawText(this.textRenderer, valueText, x + VALUE_WIDTH - width, y, 0xFFFFFF, true);
            y += LINE_HEIGHT;
        }
    }

    private void renderBackground(DrawContext context, int mouseX, int mouseY, int marginX, int marginY)
    {
        int viewportWidth = this.width - 2 * marginX;
        int viewportHeight = this.height - 2 * marginY;
        if (viewportWidth <= 0 || viewportHeight <= 0)
        {
            return;
        }

        context.enableScissor(marginX, marginY, this.width - marginX, this.height - marginY);

        // -1 ~ 1
        int length = Math.max(this.width, this.height) / 2;
        float normalizedX = MathHelper.clamp((mouseX - this.width / 2f) / length, -1f, 1f);
        float normalizedY = MathHelper.clamp((mouseY - this.height / 2f) / length, -1f, 1f);

        int maxOffsetX = Math.max(0, (BACKGROUND_SIZE - viewportWidth) / 2);
        int maxOffsetY = Math.max(0, (BACKGROUND_SIZE - viewportHeight) / 2);
        int maxOffset = Math.min(maxOffsetX, maxOffsetY);

        int offsetX = Math.round(-normalizedX * maxOffset);
        int offsetY = Math.round(-normalizedY * maxOffset);

        int baseX = (this.width - BACKGROUND_SIZE) / 2;
        int baseY = (this.height - BACKGROUND_SIZE) / 2;

        context.drawTexture(
            BACKGROUND_TEXTURE,
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

        context.drawNineSlicedTexture(
            BORDER_TEXTURE,
            marginX - 7, marginY - 7,
            viewportWidth + 14, viewportHeight + 14,
            7, 21, 21,
            0, 0
        );
    }

    private void renderUpgradeButtons(DrawContext context, int mouseX, int mouseY)
    {
        UpgradeButton hovered = null;
        for (UpgradeButton button : this.upgradeButtons.values())
        {
            button.render(context, mouseX, mouseY, this.textRenderer);
            if (hovered == null && button.isHovered(mouseX, mouseY))
            {
                hovered = button;
            }
        }

        if (hovered != null)
        {
            List<Text> tooltip = buildUpgradeTooltip(hovered.getAttribute());
            if (!tooltip.isEmpty())
            {
                context.drawTooltip(this.textRenderer, tooltip, mouseX, mouseY);
            }
        }
    }

    private List<Text> buildUpgradeTooltip(EntityAttribute attribute)
    {
        SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(attribute);
        if (upgrade == null)
        {
            return List.of();
        }
        Text cost = Text.literal("Spirit Cost: " + upgrade.cost());
        var bonus = AttributeUtil.getTooltip(attribute, upgrade.amount(), upgrade.operation());
        return bonus == null ? List.of(cost) : List.of(cost, bonus);
    }

    private void requestUpgrade(EntityAttribute attribute)
    {
        if (this.client == null || this.client.player == null)
        {
            return;
        }
        Identifier id = Registries.ATTRIBUTE.getId(attribute);
        if (id == null)
        {
            return;
        }
        AllPackets.toServer(new C2SSpiritAttributeUpgrade(id));
    }

    private void updateUpgradeButtons(PlayerEntity player)
    {
        int spirit = SpiritHolderComponent.getSpirit(player);
        for (Map.Entry<EntityAttribute, UpgradeButton> entry : this.upgradeButtons.entrySet())
        {
            SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(entry.getKey());
            entry.getValue().setActive(upgrade != null && spirit >= upgrade.cost());
        }
    }

    private static final class UpgradeButton
    {
        private final EntityAttribute attribute;
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private boolean active = true;

        private UpgradeButton(EntityAttribute attribute, int x, int y, int width, int height)
        {
            this.attribute = attribute;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public EntityAttribute getAttribute()
        {
            return attribute;
        }

        public boolean isActive()
        {
            return active;
        }

        public void setActive(boolean active)
        {
            this.active = active;
        }

        public boolean isHovered(double mouseX, double mouseY)
        {
            return mouseX >= x &&
                mouseY >= y &&
                mouseX < x + width &&
                mouseY < y + height;
        }

        public void render(DrawContext context, int mouseX, int mouseY, net.minecraft.client.font.TextRenderer textRenderer)
        {
            // button
            int background = active ? (isHovered(mouseX, mouseY) ? 0xaaaaaaaa : 0xaa666666) : 0xaa111111;
            context.fill(x, y, x + width, y + height, background);
            // text
            int textX = x + 1 + (width - textRenderer.getWidth("+")) / 2;
            int textY = y + 1 + (height - textRenderer.fontHeight) / 2;
            context.drawText(textRenderer, "+", textX, textY, 0xFFFFFF, false);
        }
    }
}
