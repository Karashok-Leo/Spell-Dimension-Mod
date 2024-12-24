package karashokleo.spell_dimension.client.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class REIInfusionCategory implements DisplayCategory<REIInfusionDisplay>
{
    public static final EntryStack<ItemStack> PEDESTAL = EntryStacks.of(AllBlocks.SPELL_INFUSION_PEDESTAL.item());

    private static final int SLOT_OFFSET = 36;
    private static final int ARROW_OFFSET = -1;

    @Override
    public List<Widget> setupDisplay(REIInfusionDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + ARROW_OFFSET, startPoint.y - 9)));
        Slot base = Widgets.createSlot(new Point(startPoint.x - SLOT_OFFSET - 8, startPoint.y - 8)).entries(display.base()).markInput();
        Slot addition = Widgets.createSlot(new Point(startPoint.x - SLOT_OFFSET + 8 + 6, startPoint.y - 8)).entries(display.addition()).markInput();
        Slot output = Widgets.createSlot(new Point(startPoint.x + SLOT_OFFSET - 8, startPoint.y - 8)).entries(display.output()).markOutput();
        widgets.add(base);
        widgets.add(addition);
        widgets.add(output);
        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 32;
    }

    @Override
    public CategoryIdentifier<? extends REIInfusionDisplay> getCategoryIdentifier()
    {
        return REICompat.SPELL_INFUSION;
    }

    @Override
    public Text getTitle()
    {
        return SDTexts.TEXT$SPELL_INFUSION.get();
    }

    @Override
    public Renderer getIcon()
    {
        return PEDESTAL;
    }
}
