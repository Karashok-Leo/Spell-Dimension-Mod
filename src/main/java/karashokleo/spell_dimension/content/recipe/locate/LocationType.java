package karashokleo.spell_dimension.content.recipe.locate;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum LocationType
{
    STRUCTURE,
    BIOME,
    ;

    public MutableText getName(Identifier id)
    {
        return switch (this)
        {
            case STRUCTURE -> Text.translatable(
                    id.toTranslationKey("structure").replace('/', '.')
            );
            case BIOME -> Text.translatable(
                    id.toTranslationKey("biome").replace('/', '.')
            );
        };
    }

    public String toString(Identifier id)
    {
        return switch (this)
        {
            case STRUCTURE -> id.toString();
            case BIOME -> "#" + id.toString();
        };
    }
}
