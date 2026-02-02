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
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;

public class SpiritTomeInfoPage implements SpiritTomePage
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/1.png");
    private static final int LINE_HEIGHT = 15;
    private static final int ATTRIBUTE_COUNT = 12;
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
        renderBasicInfo(context, player, textRenderer, component);

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
            line.update(spirit);
        }

        SpellPower.Result result = SpellPower.getSpellPower(SpellSchools.SOUL, player);
        for (AttributeLine line : this.attributeLines)
        {
            line.render(context, mouseX, mouseY, textRenderer, player, result);
        }
    }

    private void renderBasicInfo(DrawContext context, ClientPlayerEntity player, TextRenderer textRenderer, SpiritTomeComponent component)
    {
        int x1 = this.viewport.getX() + 30;
        int x2 = x1 + 80;
        int y = this.viewport.getY() + this.viewport.getHeight() / 2 + 15;
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$NAME.get(player.getName()), x1, y, 0xFFFFFF, true);
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$GENDER.get(getGender(player)), x1, y += LINE_HEIGHT, 0xFFFFFF, true);
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$AGE.get("%.1f".formatted(getAge(player))), x2, y, 0xFFFFFF, true);
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$HEIGHT.get("%.2f".formatted(player.getHeight())), x1, y += LINE_HEIGHT, 0xFFFFFF, true);
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$WEIGHT.get("%.1f".formatted(component.getWeight())), x2, y, 0xFFFFFF, true);
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$HEALTH.get("%.1f".formatted(player.getHealth())), x1, y += LINE_HEIGHT, 0xFFFFFF, true);
        int spirit = component.getSpirit();
        context.drawText(textRenderer, SDTexts.TEXT$SPIRIT_TOME$SPIRIT.get(spirit), x2, y, 0xFFFFFF, true);
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

        public AttributeLine(EntityAttribute attribute, ValueProvider valueProvider, int x, int y)
        {
            this.attribute = attribute;
            this.valueProvider = valueProvider;
            this.upgradeButton = new UpgradeButton(attribute, x + 130, y - 2, 11, 11);
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
            private final List<Text> tooltip;
            private boolean active = true;

            private UpgradeButton(EntityAttribute attribute, int x, int y, int width, int height)
            {
                this.attribute = attribute;
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.tooltip = buildUpgradeTooltip(attribute);
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
                boolean hovered = isHovered(mouseX, mouseY);
                int background = active ? (hovered ? 0xaaaaaaaa : 0xaa666666) : 0xaa111111;
                context.fill(x, y, x + width, y + height, background);
                // text
                int textX = x + 1 + (width - textRenderer.getWidth("+")) / 2;
                int textY = y + 1 + (height - textRenderer.fontHeight) / 2;
                context.drawText(textRenderer, "+", textX, textY, 0xFFFFFF, false);

                // tooltip
                if (!hovered)
                {
                    return;
                }
                List<Text> tooltip;
                if (active)
                {
                    tooltip = this.tooltip;
                } else
                {
                    tooltip = new ArrayList<>();
                    tooltip.add(SDTexts.TEXT$SPIRIT_TOME$INSUFFICIENT.get().formatted(Formatting.RED));
                    tooltip.addAll(this.tooltip);
                }
                context.drawTooltip(textRenderer, tooltip, mouseX, mouseY);
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
        }
    }
}
