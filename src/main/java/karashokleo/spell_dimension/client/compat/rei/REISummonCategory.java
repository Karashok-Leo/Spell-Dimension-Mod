package karashokleo.spell_dimension.client.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.spell_dimension.data.SpellTexts;
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
    public static final EntryStack<ItemStack> SPAWNER = EntryStacks.of(Items.SPAWNER);

    private static final int DISPLAY_OFFSET_X = 12;
    private static final int DISPLAY_OFFSET_Y = -44;
    private static final int SLOT_OFFSET = 36;
    private static final int ARROW_OFFSET = -1;

    @Override
    public List<Widget> setupDisplay(REISummonDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + DISPLAY_OFFSET_X + ARROW_OFFSET, startPoint.y + DISPLAY_OFFSET_Y)));
        Slot input = Widgets.createSlot(new Point(startPoint.x + DISPLAY_OFFSET_X - SLOT_OFFSET - 8, startPoint.y + DISPLAY_OFFSET_Y)).entries(display.input()).markInput();
        Slot spawner = Widgets.createSlot(new Point(startPoint.x + DISPLAY_OFFSET_X - SLOT_OFFSET + 8 + 6, startPoint.y + DISPLAY_OFFSET_Y)).entries(List.of(SPAWNER)).noInteractable().disableBackground();
        Widget entity = Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> display.wrapper().render(graphics, startPoint.x, bounds.y + 84, mouseX, mouseY));
        widgets.add(input);
        widgets.add(spawner);
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
        return SPAWNER;
    }
}
