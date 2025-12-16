package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.spell_dimension.client.compat.LocationStack;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record REILocateDisplay(EntryIngredient input, EntryStack<LocationStack> location) implements Display
{
    public REILocateDisplay(LocateRecipe recipe)
    {
        this(
            EntryIngredients.ofIngredient(recipe.getIngredient()),
            EntryStack.of(
                REICompat.LOCATION,
                LocationStack.fromRecipe(recipe)
            )
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
        return List.of(EntryIngredient.of(location));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.LOCATE;
    }
}
