package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.client.compat.LocationStack;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;

public class LocationEntryRenderer implements EntryRenderer<LocationStack>
{
    @Override
    public void render(EntryStack<LocationStack> entry, DrawContext graphics, Rectangle bounds, int mouseX, int mouseY, float delta)
    {
        entry.getValue().render(graphics, bounds.getX(), bounds.getY());
    }

    @Override
    public @Nullable Tooltip getTooltip(EntryStack<LocationStack> entry, TooltipContext context)
    {
        return Tooltip.create(entry.getValue().getTooltip());
    }
}
