package karashokleo.spell_dimension.compat.rei;

import karashokleo.spell_dimension.compat.EntityWrapper;
import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;

import java.util.List;

public record REISummonDisplay(EntryIngredient input, EntityWrapper wrapper) implements Display
{
    public REISummonDisplay(Item item, SummonSpellConfig.Entry entry)
    {
        this(EntryIngredients.of(item), EntityWrapper.of(entry));
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
