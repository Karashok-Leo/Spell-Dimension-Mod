package karashokleo.spell_dimension.client.compat.rei;

import karashokleo.l2hostility.compat.shared.LivingEntityWrapper;
import karashokleo.spell_dimension.client.compat.SummonedEntityWrapperFactory;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record REISummonDisplay(EntryIngredient input, LivingEntityWrapper wrapper) implements Display
{
    public REISummonDisplay(SummonRecipe recipe)
    {
        this(EntryIngredients.ofIngredient(recipe.ingredient()), SummonedEntityWrapperFactory.of(recipe.entityType(), recipe.count()));
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
        return REICompat.SUMMON;
    }
}
