package karashokleo.spell_dimension.client.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.data.SpellTexts;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
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
        Point startPoint = new Point(bounds.getCenterX() + 2, bounds.getCenterY() - 9);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        Slot input = Widgets.createSlot(
            new Point(startPoint.x - 20 * 2, startPoint.y)
        ).entries(display.input()).markInput();
        Slot scroll = Widgets.createSlot(
            new Point(startPoint.x - 20, startPoint.y)
        ).entries(SPELL_SCROLL).noInteractable().disableBackground();
        Slot station = Widgets.createSlot(
            new Point(startPoint.x, startPoint.y)
        ).entries(WORKSTATION).noInteractable().disableBackground();
        Slot location = Widgets.createSlot(
            new Point(startPoint.x + 20, startPoint.y)
        ).entry(display.location()).markOutput();
        widgets.add(input);
        widgets.add(scroll);
        widgets.add(station);
        widgets.add(location);
        return widgets;
    }

    @Override
    public int getDisplayWidth(REILocateDisplay display)
    {
        return 100;
    }

    @Override
    public int getDisplayHeight()
    {
        return 36;
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
        if (WORKSTATION.isEmpty())
        {
            return EntryStack.empty();
        }
        return WORKSTATION.get(0);
    }
}
