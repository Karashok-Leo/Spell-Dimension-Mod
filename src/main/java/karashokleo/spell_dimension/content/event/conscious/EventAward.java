package karashokleo.spell_dimension.content.event.conscious;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum EventAward
{
    BAG("pool/event/bag"),
    ESSENCE("pool/event/essence"),
    SPELL_SCROLL("pool/event/spell_scroll");

    public final Identifier lootTable;

    EventAward(Identifier lootTable)
    {
        this.lootTable = lootTable;
    }

    EventAward(String path)
    {
        this(SpellDimension.modLoc(path));
    }

    public String getTranslationKey()
    {
        return "event_award." + SpellDimension.MOD_ID + "." + this.name().toLowerCase();
    }

    public Text getText()
    {
        return Text.translatable(this.getTranslationKey());
    }
}
