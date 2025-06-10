package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import karashokleo.spell_dimension.client.compat.LocationStack;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class LocationEmiStack extends EmiStack
{
    protected LocationStack stack;

    public LocationEmiStack(LocationStack stack)
    {
        this.stack = stack;
    }

    @Override
    public EmiStack copy()
    {
        return new LocationEmiStack(stack)
                .setChance(chance)
                .setRemainder(getRemainder().copy())
                .comparison(comparison);
    }

    @Override
    public void render(DrawContext draw, int x, int y, float delta, int flags)
    {
        if ((flags & RENDER_ICON) == 0)
        {
            return;
        }
        stack.render(draw, x, y);
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public NbtCompound getNbt()
    {
        return null;
    }

    @Override
    public Object getKey()
    {
        return stack;
    }

    @Override
    public Identifier getId()
    {
        return stack.getId();
    }

    @Override
    public Text getName()
    {
        return stack.getName();
    }

    @Override
    public List<Text> getTooltipText()
    {
        return stack.getTooltip();
    }

    @Override
    public List<TooltipComponent> getTooltip()
    {
        List<TooltipComponent> list = getTooltipText().stream().map(EmiTooltipComponents::of).collect(Collectors.toList());
        EmiTooltipComponents.appendModName(list, getId().getNamespace());
        list.addAll(super.getTooltip());
        return list;
    }
}
