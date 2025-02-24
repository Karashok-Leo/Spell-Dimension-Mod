package karashokleo.spell_dimension.content.object;

import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum EventAward
{
    BAG("pool/event/bag"),
    ESSENCE("pool/event/essence"),
    SPELL_SCROLL("pool/event/spell_scroll"),
    BOOK("pool/event/book"),
    GEAR("pool/event/gear"),
    MATERIAL("pool/event/material"),
    TRINKET("pool/event/trinket"),
    ;

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

    public void addText(String en, String zh)
    {
        SpellDimension.EN_TEXTS.addText(this.getTranslationKey(), en);
        SpellDimension.ZH_TEXTS.addText(this.getTranslationKey(), zh);
    }
}
