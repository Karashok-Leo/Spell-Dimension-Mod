package karashokleo.spell_dimension.content.object;

import karashokleo.spell_dimension.data.SDTexts;
import net.minecraft.text.MutableText;

import java.util.function.Supplier;

public enum ScrollType
{
    PRIMARY(SDTexts.SCROLL$PRIMARY::get),
    BINDING(SDTexts.SCROLL$BINDING::get),
    CRAFTING(SDTexts.SCROLL$CRAFTING::get),
    KILLING(SDTexts.SCROLL$KILLING::get),
    EXPLORING(SDTexts.SCROLL$EXPLORING::get),
    EVENT_AWARD(SDTexts.SCROLL$EVENT_AWARD::get),
    ;

    private final Supplier<MutableText> textSupplier;

    ScrollType(Supplier<MutableText> textSupplier)
    {
        this.textSupplier = textSupplier;
    }

    public MutableText getTooltip()
    {
        return this.textSupplier.get();
    }
}
