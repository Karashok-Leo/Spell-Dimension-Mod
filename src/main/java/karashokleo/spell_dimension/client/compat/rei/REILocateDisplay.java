package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.text.Text;

import java.util.List;

public record REILocateDisplay(EntryIngredient input, Text spot, Text tooltip) implements Display
{
    public REILocateDisplay(LocateRecipe recipe)
    {
        this(
                EntryIngredients.ofIngredient(recipe.getIngredient()),
                recipe.getTargetName(),
                recipe.getWorldName().append(" - ").append(Text.of(recipe.getTargetId().toString()))
        );
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.LOCATE;
    }
}
