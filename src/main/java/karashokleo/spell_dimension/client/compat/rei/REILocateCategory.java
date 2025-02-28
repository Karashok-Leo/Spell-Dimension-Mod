package karashokleo.spell_dimension.client.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.data.SpellTexts;
import karashokleo.spell_dimension.init.AllItems;
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
    public static final EntryIngredient WORKSTATION = EntryIngredients.ofItemTag(AllTags.LOCATE_TARGET);
    public static final EntryIngredient SPELL_SCROLL = EntryIngredients.of(AllItems.SPELL_SCROLL.getStack(SpellDimension.modLoc("locate")));

    @Override
    public List<Widget> setupDisplay(REILocateDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        Slot scroll = Widgets.createSlot(new Point(startPoint.x - 8 - 20, startPoint.y - 18)).entries(SPELL_SCROLL).noInteractable().disableBackground();
        Slot input = Widgets.createSlot(new Point(startPoint.x - 8, startPoint.y - 18)).entries(display.input()).markInput();
        Slot station = Widgets.createSlot(new Point(startPoint.x - 8 + 20, startPoint.y - 18)).entries(WORKSTATION).noInteractable().disableBackground();
        Label destination = Widgets.createLabel(new Point(startPoint.x, startPoint.y + 6), display.spot()).shadow();
        widgets.add(scroll);
        widgets.add(input);
        widgets.add(station);
        widgets.add(destination);
        widgets.add(Widgets.createTooltip(point ->
                bounds.contains(point) &&
                !(input.getBounds().contains(point) ||
                  scroll.getBounds().contains(point) ||
                  station.getBounds().contains(point) ||
                  destination.getBounds().contains(point)) ?
                        Tooltip.create(point, display.tooltip()) : null));
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
        if (WORKSTATION.isEmpty()) return EntryStack.empty();
        return WORKSTATION.get(0);
    }
}
