package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.config.SpiritUpgradeConfig;
import karashokleo.spell_dimension.content.component.SpiritHolderComponent;
import karashokleo.spell_dimension.content.network.C2SSpiritAttributeUpgrade;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class SpiritTomeScreen extends Screen
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/1.png");
    private static final Identifier BORDER_TEXTURE = new Identifier("spell-dimension", "textures/gui/spirit_tome.png");
    private static final int BACKGROUND_SIZE = 512;

    private final List<AttributeLine> attributeLines;

    public SpiritTomeScreen()
    {
        super(Text.empty());
        this.attributeLines = buildAttributeLines();
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

        if (this.client.player == null)
        {
            return;
        }

        int marginX = this.width / 5;
        int marginY = this.height / 8;

        // background
        renderBackground(context, mouseX, mouseY, marginX, marginY);
        // player model
        InventoryScreen.drawEntity(context, 200, 150, 30, 200 - mouseX, 150 - 50 - mouseY, this.client.player);
        // attribute panel
        context.fillGradient(280, 80, 480, 300, 0x33ffffff, 0x33ffffff);

        // attribute lines
        int spirit = SpiritHolderComponent.getSpirit(this.client.player);
        for (AttributeLine line : this.attributeLines)
        {
            line.update(spirit);
        }

        context.drawText(this.textRenderer, Text.literal("Spirit: " + SpiritHolderComponent.getSpirit(this.client.player)), 300, 120 - 15, 0xFFFFFF, true);
        SpellPower.Result result = SpellPower.getSpellPower(SpellSchools.SOUL, this.client.player);
        for (AttributeLine line : this.attributeLines)
        {
            line.render(context, mouseX, mouseY, this.textRenderer, this.client.player, result);
        }

        super.render(context, mouseX, mouseY, delta);
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
        if (button == 0)
        {
            for (AttributeLine line : this.attributeLines)
            {
                if (line.mouseClicked(mouseX, mouseY))
                {
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private static List<AttributeLine> buildAttributeLines()
    {
        int x = 300;
        int y = 100;
        int lineHeight = 15;
        return List.of(
            new AttributeLine(EntityAttributes.GENERIC_MAX_HEALTH, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_ARMOR, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_MOVEMENT_SPEED, SpiritTomeScreen::formatAttributeDot2, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_ATTACK_DAMAGE, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_ATTACK_SPEED, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(EntityAttributes.GENERIC_LUCK, SpiritTomeScreen::formatAttribute, x, y += lineHeight),
            new AttributeLine(SpellSchools.SOUL.attribute, (player, attribute, result) -> "%.1f".formatted(result.nonCriticalValue()), x, y += lineHeight),
            new AttributeLine(SpellPowerMechanics.CRITICAL_CHANCE.attribute, (player, attribute, result) -> "%.1f%%".formatted(result.criticalChance() * 100), x, y += lineHeight),
            new AttributeLine(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, (player, attribute, result) -> "× %.1f%%".formatted(result.criticalDamage() * 100), x, y += lineHeight),
            new AttributeLine(SpellPowerMechanics.HASTE.attribute, (player, attribute, result) -> "+ %.1f%%".formatted((SpellPower.getHaste(player, SpellSchools.SOUL) - 1) * 100), x, y + lineHeight)
        );
    }

    private static String formatAttribute(PlayerEntity player, EntityAttribute attribute, SpellPower.Result result)
    {
        return "%.1f".formatted(player.getAttributeValue(attribute));
    }

    private static String formatAttributeDot2(PlayerEntity player, EntityAttribute attribute, SpellPower.Result result)
    {
        return "%.2f".formatted(player.getAttributeValue(attribute));
    }

    public static class AttributeLine
    {
        public final EntityAttribute attribute;
        public final ValueProvider valueProvider;
        private final int x;
        private final int y;
        private final UpgradeButton upgradeButton;
        private final List<Text> tooltip;

        public AttributeLine(EntityAttribute attribute, ValueProvider valueProvider, int x, int y)
        {
            this.attribute = attribute;
            this.valueProvider = valueProvider;
            this.upgradeButton = new UpgradeButton(attribute, x + 130, y - 2, 11, 11);
            this.tooltip = buildUpgradeTooltip(attribute);
            this.x = x;
            this.y = y;
        }

        public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, PlayerEntity player, SpellPower.Result result)
        {
            String valueText = valueProvider.get(player, attribute, result);
            // attribute name
            context.drawText(textRenderer, Text.translatable(attribute.getTranslationKey()), x, y, 0xFFFFFF, true);
            // attribute value
            int gap = 120 - textRenderer.getWidth(valueText);
            context.drawText(textRenderer, valueText, x + gap, y, 0xFFFFFF, true);
            // upgrade button
            upgradeButton.render(context, mouseX, mouseY, textRenderer);
            // tooltip
            if (!tooltip.isEmpty() && upgradeButton.isHovered(mouseX, mouseY))
            {
                context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
            }
        }

        public void update(int spirit)
        {
            SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(attribute);
            upgradeButton.setActive(upgrade != null && spirit >= upgrade.cost());
        }

        public boolean mouseClicked(double mouseX, double mouseY)
        {
            return upgradeButton.mouseClicked(mouseX, mouseY);
        }

        private static List<Text> buildUpgradeTooltip(EntityAttribute attribute)
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

        @FunctionalInterface
        public interface ValueProvider
        {
            String get(PlayerEntity player, EntityAttribute attribute, SpellPower.Result result);
        }

        public static class UpgradeButton
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

            public boolean mouseClicked(double mouseX, double mouseY)
            {
                if (!isHovered(mouseX, mouseY))
                {
                    return false;
                }
                if (!active)
                {
                    return false;
                }
                // request upgrade
                Identifier id = Registries.ATTRIBUTE.getId(attribute);
                if (id == null)
                {
                    return false;
                }
                AllPackets.toServer(new C2SSpiritAttributeUpgrade(id));
                return true;
            }

            public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer)
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
}
