package karashokleo.spell_dimension.compat.rei;

import karashokleo.spell_dimension.config.recipe.InfusionRecipes;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record REIInfusionDisplay(
        EntryIngredient base,
        EntryIngredient addition,
        EntryIngredient output,
        boolean consume,
        int time
) implements Display
{
    public REIInfusionDisplay(InfusionRecipes.RecipeEntry entry)
    {
        this(
                EntryIngredients.of(entry.base()),
                EntryIngredients.of(entry.addition()),
                EntryIngredients.of(entry.output()),
                entry.consume(),
                entry.time()
        );
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(base, addition);
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of(output);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.SPELL_INFUSION;
    }
}
