package karashokleo.spell_dimension.client.screen;

import karashokleo.spell_dimension.config.SpiritUpgradeConfig;
import karashokleo.spell_dimension.content.component.SpiritTomeComponent;
import karashokleo.spell_dimension.content.network.C2SSpiritAttributeUpgrade;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllPackets;
import karashokleo.spell_dimension.util.AttributeUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

public class SpiritTomeInfoPage implements SpiritTomePage
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/1.png");
    private static final int LINE_HEIGHT = 15;
    private final Rect2i viewport;
    private final List<AttributeLine> attributeLines;

    public SpiritTomeInfoPage(Rect2i viewport)
    {
        this.viewport = viewport;
        this.attributeLines = createAttributeLines(viewport);
    }

    @Override
    public Identifier getBackground()
    {
        return BACKGROUND_TEXTURE;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, ClientPlayerEntity player)
    {
        // player model
        int playerX = this.viewport.getX() + this.viewport.getWidth() / 4;
        int playerY = this.viewport.getY() + this.viewport.getHeight() / 2 - 15;
        InventoryScreen.drawEntity(context, playerX, playerY, 30, playerX - mouseX, playerY - 50 - mouseY, player);

        // basic info
        SpiritTomeComponent component = SpiritTomeComponent.get(player);
        renderBasicInfo(context, player, textRenderer, component, mouseX, mouseY);

        // attributes
        context.fill(
            this.viewport.getX() + this.viewport.getWidth() / 2 + 15,
            this.viewport.getY() + 15,
            this.viewport.getX() + this.viewport.getWidth() - 15,
            this.viewport.getY() + this.viewport.getHeight() - 15,
            0x33ffffff
        );

        int spirit = component.getSpirit();
        for (AttributeLine line : this.attributeLines)
        {
            line.update(player, spirit);
        }

        SpellPower.Result result = SpellPower.getSpellPower(SpellSchools.SOUL, player);
        for (AttributeLine line : this.attributeLines)
        {
            line.render(context, mouseX, mouseY, textRenderer, player, result);
        }

        for (AttributeLine line : this.attributeLines)
        {
            if (!line.upgradeButton.isHovered(mouseX, mouseY))
            {
                continue;
            }
            List<Text> tooltip = line.upgradeButton.getTooltip(player);
            if (tooltip.isEmpty())
            {
                continue;
            }
            context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
            break;
        }
    }

    private void renderBasicInfo(DrawContext context, ClientPlayerEntity player, TextRenderer textRenderer, SpiritTomeComponent component, int mouseX, int mouseY)
    {
        int x1 = this.viewport.getX() + 30;
        int x2 = x1 + 80;
        int infoRight = this.viewport.getX() + this.viewport.getWidth() / 2 - 10;
        int infoWidth = infoRight - x1;
        int y = this.viewport.getY() + this.viewport.getHeight() / 2 + 15;

        // name
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$NAME.get(player.getName()), x1, y, 0xFFFFFF, true);

        // gender
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$GENDER.get(getGender(player)), x1, y += LINE_HEIGHT, 0xFFFFFF, true);

        // age
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$AGE.get("%.1f".formatted(getAge(player))), x2, y, 0xFFFFFF, true);

        // height
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$HEIGHT.get("%.2f".formatted(player.getHeight())), x1, y += LINE_HEIGHT, 0xFFFFFF, true);

        // weight
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$WEIGHT.get("%.1f".formatted(component.getWeight())), x2, y, 0xFFFFFF, true);

        // health / max health
        Text healthText = SDTexts.TEXT$SPIRIT_TOME$HEALTH.get("%.1f / %.1f".formatted(player.getHealth(), player.getMaxHealth()));
        if (textRenderer.getWidth(healthText) > infoWidth)
        {
            healthText = SDTexts.TEXT$SPIRIT_TOME$HEALTH.get("%s / %s".formatted(formatScientific(player.getHealth()), formatScientific(player.getMaxHealth())));
        }
        context.drawText(textRenderer, healthText, x1, y += LINE_HEIGHT, 0xFFFFFF, true);

        // spirit
        int spirit = component.getSpirit();
        Text spiritText = SDTexts.TEXT$SPIRIT_TOME$SPIRIT.get(spirit);
        if (textRenderer.getWidth(spiritText) > infoWidth)
        {
            spiritText = SDTexts.TEXT$SPIRIT_TOME$SPIRIT.get(formatScientific(spirit));
        }
        int spiritY = y + LINE_HEIGHT;
        context.drawText(textRenderer, spiritText, x1, spiritY, 0xFFFFFF, true);

        // tooltip for spirit
        if (mouseX >= x1 &&
            mouseY >= spiritY &&
            mouseX < x1 + textRenderer.getWidth(spiritText) &&
            mouseY < spiritY + textRenderer.fontHeight)
        {
            List<Text> tooltip = List.of(
                SDTexts.TEXT$SPIRIT_TOME$SPIRIT.get(spirit),
                SDTexts.TEXT$SPIRIT_TOME$POSITIVE_SPIRIT.get(component.getSpirit(SpiritTomeComponent.SpiritType.POSITIVE)),
                SDTexts.TEXT$SPIRIT_TOME$NEGATIVE_SPIRIT.get(component.getSpirit(SpiritTomeComponent.SpiritType.NEGATIVE))
            );
            context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
        }
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

    private static String formatScientific(double value)
    {
        return "%.2E".formatted(value);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY)
    {
        for (AttributeLine line : this.attributeLines)
        {
            if (line.mouseClicked(mouseX, mouseY))
            {
                return true;
            }
        }
        return false;
    }

    private static List<AttributeLine> createAttributeLines(Rect2i viewport)
    {
        int x = viewport.getX() + viewport.getWidth() / 2 + 25;
        int y = viewport.getY() + 27;
        return List.of(
            new AttributeLine(EntityAttributes.GENERIC_MAX_HEALTH, SpiritTomeInfoPage::formatAttribute, x, y),
            new AttributeLine(EntityAttributes.GENERIC_ARMOR, SpiritTomeInfoPage::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, SpiritTomeInfoPage::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, SpiritTomeInfoPage::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_MOVEMENT_SPEED, SpiritTomeInfoPage::formatAttributeDot2, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_ATTACK_DAMAGE, SpiritTomeInfoPage::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_ATTACK_SPEED, SpiritTomeInfoPage::formatAttribute, x, y += LINE_HEIGHT),
            new AttributeLine(EntityAttributes.GENERIC_LUCK, SpiritTomeInfoPage::formatAttribute, x, y += LINE_HEIGHT),
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

    private static class AttributeLine
    {
        private final EntityAttribute attribute;
        private final ValueProvider valueProvider;
        private final int x;
        private final int y;
        private final UpgradeButton upgradeButton;
        private long hoverTime = -1L;

        private AttributeLine(EntityAttribute attribute, ValueProvider valueProvider, int x, int y)
        {
            this.attribute = attribute;
            this.valueProvider = valueProvider;
            this.upgradeButton = new UpgradeButton(attribute, x + 130, y - 2, 11, 11);
            this.x = x;
            this.y = y;
        }

        private void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer, PlayerEntity player, SpellPower.Result result)
        {
            String valueText = valueProvider.get(player, attribute, result);
            int valueLeft = x + 120 - textRenderer.getWidth(valueText);
            // attribute name
            int right = valueLeft - 6;
            int bottom = y + textRenderer.fontHeight;
            boolean hovered = mouseX >= x &&
                mouseY >= y &&
                mouseX < right &&
                mouseY < bottom;
            drawScrollableText(context, textRenderer, Text.translatable(attribute.getTranslationKey()), x, y, right, bottom, hovered);
            // attribute value
            context.drawText(textRenderer, valueText, valueLeft, y, 0xFFFFFF, true);
            // upgrade button
            upgradeButton.render(context, mouseX, mouseY, textRenderer);
        }

        private void drawScrollableText(DrawContext context, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, boolean hovered)
        {
            int width = textRenderer.getWidth(text);
            int maxWidth = right - left;
            if (width > maxWidth)
            {
                if (hovered)
                {
                    int overflow = width - maxWidth;
                    long now = Util.getMeasuringTimeMs();
                    if (this.hoverTime < 0L)
                    {
                        this.hoverTime = now;
                    }
                    double time = (now - this.hoverTime) / 30.0;
                    int offset = (int) Math.min(overflow, Math.round(time));
                    context.enableScissor(left, top, right, bottom);
                    context.drawTextWithShadow(textRenderer, text, left - offset, top, 0xFFFFFF);
                    context.disableScissor();
                    return;
                }

                this.hoverTime = -1L;
                context.enableScissor(left, top, right, bottom);
                context.drawTextWithShadow(textRenderer, text, left, top, 0xFFFFFF);
                context.disableScissor();
                return;
            }
            this.hoverTime = -1L;
            context.drawTextWithShadow(textRenderer, text, left, top, 0xFFFFFF);
        }

        private void update(PlayerEntity player, int spirit)
        {
            SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(attribute);
            if (upgrade == null || !upgrade.canUpgrade(player))
            {
                upgradeButton.setActive(false);
                return;
            }
            int cost = upgrade.getCost(player);
            upgradeButton.setActive(spirit >= cost);
        }

        private boolean mouseClicked(double mouseX, double mouseY)
        {
            return upgradeButton.mouseClicked(mouseX, mouseY);
        }

        @FunctionalInterface
        private interface ValueProvider
        {
            String get(PlayerEntity player, EntityAttribute attribute, SpellPower.Result result);
        }

        private static class UpgradeButton
        {
            private final EntityAttribute attribute;
            private final int x;
            private final int y;
            private final int width;
            private final int height;
            private final Text bonus;
            private boolean active = true;

            private UpgradeButton(EntityAttribute attribute, int x, int y, int width, int height)
            {
                this.attribute = attribute;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.bonus = buildUpgradeBonus(attribute);
            }

            private void setActive(boolean active)
            {
                this.active = active;
            }

            private boolean isHovered(double mouseX, double mouseY)
            {
                return mouseX >= x &&
                    mouseY >= y &&
                    mouseX < x + width &&
                    mouseY < y + height;
            }

            private boolean mouseClicked(double mouseX, double mouseY)
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

            private void render(DrawContext context, int mouseX, int mouseY, TextRenderer textRenderer)
            {
                // button
                boolean hovered = isHovered(mouseX, mouseY);
                int background = active ? (hovered ? 0xaaaaaaaa : 0xaa666666) : 0xaa111111;
                context.fill(x, y, x + width, y + height, background);
                // text
                int textX = x + 1 + (width - textRenderer.getWidth("+")) / 2;
                int textY = y + 1 + (height - textRenderer.fontHeight) / 2;
                context.drawText(textRenderer, "+", textX, textY, 0xFFFFFF, false);
            }

            private List<Text> getTooltip(PlayerEntity player)
            {
                SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(attribute);
                if (upgrade == null)
                {
                    return List.of();
                }
                if (!upgrade.canUpgrade(player))
                {
                    return List.of(SDTexts.TEXT$SPIRIT_TOME$MAXED.get().formatted(Formatting.RED));
                }
                Text cost = SDTexts.TEXT$SPIRIT_TOME$COST.get(upgrade.getCost(player));
                if (active)
                {
                    return bonus == null ? List.of(cost) : List.of(cost, bonus);
                }
                List<Text> tooltip = new ArrayList<>();
                tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
                tooltip.add(cost);
                if (bonus != null)
                {
                    tooltip.add(bonus);
                }
                return tooltip;
            }

            private static Text buildUpgradeBonus(EntityAttribute attribute)
            {
                SpiritUpgradeConfig.SpiritUpgrade upgrade = SpiritUpgradeConfig.get(attribute);
                if (upgrade == null)
                {
                    return null;
                }
                return AttributeUtil.getTooltip(attribute, upgrade.amount(), upgrade.operation());
            }
        }
    }
}
