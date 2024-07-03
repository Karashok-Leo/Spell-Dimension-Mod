package karashokleo.spell_dimension.data;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum SDTexts
{
    ;
    String key;
    String en;
    String zh;

    public MutableText get(Object... objects)
    {
        return Text.translatable(this.key, objects);
    }
}
