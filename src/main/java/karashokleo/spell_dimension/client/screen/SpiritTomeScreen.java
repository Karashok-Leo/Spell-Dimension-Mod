package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.config.SpiritUpgradeConfig;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.content.network.C2SSpiritAttributeUpgrade;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.stat.Stats;
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
    public static final int LINE_HEIGHT = 15;
    public static final int ATTRIBUTE_COUNT = 12;

    private int marginX;
    private int marginY;
    private int viewportWidth;
    private int viewportHeight;
    private List<AttributeLine> attributeLines;

    public SpiritTomeScreen()
    {
        super(Text.empty());
    }

    @Override
    protected void init()
    {
        super.init();
        this.viewportWidth = 384;
        this.viewportHeight = 226;
        this.marginX = (this.width - this.viewportWidth) / 2;
        this.marginY = (this.height - this.viewportHeight) / 2;
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

        ClientPlayerEntity player = this.client.player;
        if (player == null)
        {
            return;
        }

        // background
        renderBackground(context, mouseX, mouseY, marginX, marginY);
        // player model
        int playerX = this.marginX + this.viewportWidth / 4;
        int playerY = this.height / 2 - 15;
        InventoryScreen.drawEntity(context, playerX, playerY, 30, playerX - mouseX, playerY - 50 - mouseY, player);

        // basic info
        renderBasicInfo(context, player);

        // attribute lines
        context.fill(
            this.width / 2 + 15,
            marginY + 15,
            this.width - marginX - 15,
            this.height - marginY - 15,
            0x33ffffff
        );
        int spirit = SpiritTomeComponent.getSpirit(player);
        for (AttributeLine line : this.attributeLines)
        {
            line.update(spirit);
        }

        SpellPower.Result result = SpellPower.getSpellPower(SpellSchools.SOUL, player);
        for (AttributeLine line : this.attributeLines)
        {
            line.render(context, mouseX, mouseY, this.textRenderer, player, result);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void renderBasicInfo(DrawContext context, ClientPlayerEntity player)
    {
        int x1 = marginX + 30;
        int x2 = x1 + 80;
        int y = this.height / 2 + 15;
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$NAME.get(player.getName()), x1, y, 0xFFFFFF, true);
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$GENDER.get(getGender(player)), x1, y += LINE_HEIGHT, 0xFFFFFF, true);
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$AGE.get("%.1f".formatted(getAge(player))), x2, y, 0xFFFFFF, true);
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$HEIGHT.get("%.2f".formatted(player.getHeight())), x1, y += LINE_HEIGHT, 0xFFFFFF, true);
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$WEIGHT.get("%.1f".formatted(SpiritTomeComponent.getWeight(player))), x2, y, 0xFFFFFF, true);
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$HEALTH.get("%.1f".formatted(player.getHealth())), x1, y += LINE_HEIGHT, 0xFFFFFF, true);
        int spirit = SpiritTomeComponent.getSpirit(player);
        context.drawText(this.textRenderer, SDTexts.TEXT$SPIRIT_TOME$SPIRIT.get(spirit), x2, y, 0xFFFFFF, true);
    }

    private void renderBackground(DrawContext context, int mouseX, int mouseY, int marginX, int marginY)
    {
        if (this.viewportWidth <= 0 ||
            this.viewportHeight <= 0)
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

        drawBorder(
            context,
            marginX - 12, marginY - 12,
            this.viewportWidth + 24, this.viewportHeight + 24,
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

    private static Text getGender(ClientPlayerEntity player)
    {
        return switch (player.getModel())
        {
            case "default" -> SDTexts.TEXT$SPIRIT_TOME$MALE.get();
            case "slim" -> SDTexts.TEXT$SPIRIT_TOME$FEMALE.get();
            default -> SDTexts.TEXT$SPIRIT_TOME$UNKNOWN.get();
        };
    }

    private static float getAge(ClientPlayerEntity player)
    {
        int playTimeTicks = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME));
        // define 1 year as 30 days
        float yearsFromPlayTime = playTimeTicks / (24_000f * 30f);
        return 16 + Math.max(0, yearsFromPlayTime);
    }

    private List<AttributeLine> buildAttributeLines()
    {
        int x = this.width / 2 + 25;
        int y = (this.height - ATTRIBUTE_COUNT * LINE_HEIGHT) / 2 + 4;
        return List.of(
            new AttributeLine(EntityAttributes.GENERIC_MAX_HEALTH, SpiritTomeScreen::formatAttribute, x, y),
            new AttributeLine(EntityAttributes.GENERIC_ARMOR, SpiritTomeScreen::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, SpiritTomeScreen::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, SpiritTomeScreen::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_MOVEMENT_SPEED, SpiritTomeScreen::formatAttributeDot2, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_ATTACK_DAMAGE, SpiritTomeScreen::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_ATTACK_SPEED, SpiritTomeScreen::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_LUCK, SpiritTomeScreen::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(SpellSchools.SOUL.attribute, (player, attribute, result) -> "%.1f".formatted(result.nonCriticalValue()), x, y += LINE_HEIGHT),
            new AttributeLine(SpellPowerMechanics.CRITICAL_CHANCE.attribute, (player, attribute, result) -> "%.1f%%".formatted(result.criticalChance() * 100), x, y += LINE_HEIGHT),
            new AttributeLine(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, (player, attribute, result) -> "× %.1f%%".formatted(result.criticalDamage() * 100), x, y += LINE_HEIGHT),
            new AttributeLine(SpellPowerMechanics.HASTE.attribute, (player, attribute, result) -> "+ %.1f%%".formatted((SpellPower.getHaste(player, SpellSchools.SOUL) - 1) * 100), x, y + LINE_HEIGHT)
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
            Text cost = SDTexts.TEXT$SPIRIT_TOME$COST.get(upgrade.cost());
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
