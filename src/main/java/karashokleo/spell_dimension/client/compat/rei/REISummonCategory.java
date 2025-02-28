package karashokleo.spell_dimension.client.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.data.SpellTexts;
import karashokleo.spell_dimension.init.AllItems;
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
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class REISummonCategory implements DisplayCategory<REISummonDisplay>
{
    public static final EntryStack<ItemStack> WORKSTATION = EntryStacks.of(Items.SPAWNER);
    public static final EntryStack<ItemStack> SPELL_SCROLL = EntryStacks.of(AllItems.SPELL_SCROLL.getStack(SpellDimension.modLoc("summon")));

    @Override
    public List<Widget> setupDisplay(REISummonDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        Slot scroll = Widgets.createSlot(new Point(startPoint.x - 8 - 20, startPoint.y - 44)).entry(SPELL_SCROLL).noInteractable().disableBackground();
        Slot input = Widgets.createSlot(new Point(startPoint.x - 8, startPoint.y - 44)).entries(display.input()).markInput();
        Slot station = Widgets.createSlot(new Point(startPoint.x - 8 + 20, startPoint.y - 44)).entries(List.of(WORKSTATION)).noInteractable().disableBackground();
        Widget entity = Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> display.wrapper().render(graphics, startPoint.x, bounds.y + 84, mouseX, mouseY));
        widgets.add(scroll);
        widgets.add(input);
        widgets.add(station);
        widgets.add(entity);
        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 110;
    }

    @Override
    public CategoryIdentifier<? extends REISummonDisplay> getCategoryIdentifier()
    {
        return REICompat.SUMMON;
    }

    @Override
    public Text getTitle()
    {
        return SpellTexts.SUMMON.getNameText();
    }

    @Override
    public Renderer getIcon()
    {
        return WORKSTATION;
    }
}
