package karashokleo.spell_dimension.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public record REIInfusionDisplay(
        EntryIngredient base,
        EntryIngredient addition,
        EntryIngredient output
) implements Display
{
    public REIInfusionDisplay(Item base, Item addition, ItemStack output)
    {
        this(
                EntryIngredients.of(base),
                EntryIngredients.of(addition),
                EntryIngredients.of(output)
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
