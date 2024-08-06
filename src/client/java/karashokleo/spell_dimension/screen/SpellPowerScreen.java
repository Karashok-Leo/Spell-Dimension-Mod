package karashokleo.spell_dimension.screen;

import dev.xkmc.l2tabs.tabs.content.BaseTextScreen;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import karashokleo.spell_dimension.SpellDimensionClient;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchool;

import java.util.List;

public class SpellPowerScreen extends BaseTextScreen
{
    protected SpellPowerScreen(Text title)
    {
        super(title, new Identifier("l2tabs:textures/gui/empty.png"));
    }

    @Override
    public void init()
    {
        super.init();
        new TabManager<>(this, new InvTabData()).init(this::addDrawableChild, SpellDimensionClient.TAB_SPELL_POWER);
    }

    @Override
    public void render(DrawContext g, int mx, int my, float ptick)
    {
        super.render(g, mx, my, ptick);
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        int x = this.leftPos + 8;
        int y = this.topPos + 6;
        SpellSchool focus = null;
        for (SpellSchool school : SchoolUtil.SCHOOLS)
        {
            Text text = getSpellPowerInfo(school, player);
            g.drawText(textRenderer, text, x, y, 0, false);
            if (mx > x && mx < x + textRenderer.getWidth(text) && my > y && my < y + 10)
                focus = school;
            y += 10;
        }
        if (focus != null)
            g.drawTooltip(textRenderer, getMechanicInfo(focus, player), mx, my);
    }

    private static Text getSpellPowerInfo(SpellSchool school, PlayerEntity player)
    {
        SpellPower.Result result = SpellPower.getSpellPower(school, player);
        return new SpellPowerEntry(
                school.attribute,
                String.format("%.1f", result.baseValue())
        ).getText().setStyle(Style.EMPTY.withColor(school.color));
    }

    private static List<Text> getMechanicInfo(SpellSchool school, PlayerEntity player)
    {
        SpellPower.Result result = SpellPower.getSpellPower(school, player);
        return List.of(
                new SpellPowerEntry(
                        SpellPowerMechanics.CRITICAL_CHANCE.attribute,
                        String.format("%.1f%%", result.criticalChance() * 100)
                ).getText(),
                new SpellPowerEntry(
                        SpellPowerMechanics.CRITICAL_DAMAGE.attribute,
                        String.format("× %.1f%%", result.criticalDamage() * 100)
                ).getText(),
                new SpellPowerEntry(
                        SpellPowerMechanics.HASTE.attribute,
                        String.format("+ %.1f%%", (SpellPower.getHaste(player, school) - 1) * 100)
                ).getText()
        );
    }

    record SpellPowerEntry(EntityAttribute attribute, String value)
    {
        MutableText getText()
        {
            return Text.translatable(attribute.getTranslationKey())
                    .append(" : ")
                    .append(value);
        }
    }
}
