package karashokleo.spell_dimension.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.data.SpellTexts;
import karashokleo.spell_dimension.init.AllTags;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.text.Text;

import java.util.List;

public class REILocateCategory implements DisplayCategory<REILocateDisplay>
{
    public static final EntryIngredient LOCATE_TARGET = EntryIngredients.ofItemTag(AllTags.LOCATE_TARGET);

    private static final int DISPLAY_OFFSET_X = 12;
    private static final int DISPLAY_OFFSET_Y = -18;
    private static final int SLOT_OFFSET = 36;
    private static final int ARROW_OFFSET = -1;

    @Override
    public List<Widget> setupDisplay(REILocateDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + DISPLAY_OFFSET_X + ARROW_OFFSET, startPoint.y + DISPLAY_OFFSET_Y)));
        Slot input = Widgets.createSlot(new Point(startPoint.x + DISPLAY_OFFSET_X - SLOT_OFFSET - 8, startPoint.y + DISPLAY_OFFSET_Y)).entries(display.input()).markInput();
        Slot lodestone = Widgets.createSlot(new Point(startPoint.x + DISPLAY_OFFSET_X - SLOT_OFFSET + 8 + 6, startPoint.y + DISPLAY_OFFSET_Y)).entries(LOCATE_TARGET).noInteractable().disableBackground();
        Label destination = Widgets.createLabel(new Point(startPoint.x, startPoint.y + 6), display.text()).shadow();
        widgets.add(input);
        widgets.add(lodestone);
        widgets.add(destination);
        widgets.add(Widgets.createTooltip(point ->
                bounds.contains(point) &&
                !(input.getBounds().contains(point) ||
                  lodestone.getBounds().contains(point) ||
                  destination.getBounds().contains(point)) ?
                        Tooltip.create(point, display.id()) : null));
        return widgets;
    }

    @Override
    public int getDisplayWidth(REILocateDisplay display)
    {
        return 180;
    }

    @Override
    public int getDisplayHeight()
    {
        return 54;
    }

    @Override
    public CategoryIdentifier<? extends REILocateDisplay> getCategoryIdentifier()
    {
        return REICompat.LOCATE;
    }

    @Override
    public Text getTitle()
    {
        return SpellTexts.LOCATE.getNameText();
    }

    @Override
    public Renderer getIcon()
    {
        if (LOCATE_TARGET.isEmpty()) return EntryStack.empty();
        return LOCATE_TARGET.get(0);
    }
}
