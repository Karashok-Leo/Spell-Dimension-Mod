package karashokleo.spell_dimension.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

public class SpiritTomeScreen extends Screen
{
    private static final Identifier BACKGROUND_TEXTURE = new Identifier("spell-dimension-book", "textures/background/1.png");
    private static final int BACKGROUND_SIZE = 512;

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
        renderTexts(context, this.client.player);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderTexts(DrawContext context, PlayerEntity player)
    {
        int x = 300;
        int y = 100;
        int lineHeight = 15;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_MAX_HEALTH,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_ARMOR,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_MOVEMENT_SPEED,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_ATTACK_DAMAGE,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_ATTACK_SPEED,
            player
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            EntityAttributes.GENERIC_LUCK,
            player
        );
        y += lineHeight;

        SpellSchool school = SpellSchools.SOUL;
        SpellPower.Result result = SpellPower.getSpellPower(school, player);

        renderAttributeText(
            context, x, y,
            school.attribute,
            "%.1f".formatted(result.baseValue())
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            SpellPowerMechanics.CRITICAL_DAMAGE.attribute,
            "%.1f%%".formatted(result.criticalChance() * 100)
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            SpellPowerMechanics.CRITICAL_DAMAGE.attribute,
            "× %.1f%%".formatted(result.criticalDamage() * 100)
        );
        y += lineHeight;

        renderAttributeText(
            context, x, y,
            SpellPowerMechanics.HASTE.attribute,
            "+ %.1f%%".formatted((SpellPower.getHaste(player, school) - 1) * 100)
        );
    }

    private void renderAttributeText(DrawContext context, int x, int y, EntityAttribute attribute, String value)
    {
        context.drawText(this.textRenderer, Text.translatable(attribute.getTranslationKey()), x, y, 0xFFFFFF, true);
        int width = this.textRenderer.getWidth(value);
        context.drawText(this.textRenderer, value, x + 120 - width, y, 0xFFFFFF, true);
    }

    private void renderAttributeText(DrawContext context, int x, int y, EntityAttribute attribute, PlayerEntity player)
    {
        renderAttributeText(context, x, y, attribute, "%.1f".formatted(player.getAttributeValue(attribute)));
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
    }
}
