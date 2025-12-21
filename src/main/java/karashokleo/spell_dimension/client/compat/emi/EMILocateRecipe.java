package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.spell_dimension.client.compat.LocationStack;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.util.Identifier;

import java.util.List;

public record EMILocateRecipe(
    Identifier id,
    EmiIngredient input,
    LocationEmiStack location
) implements EmiRecipe
{
    public static final EmiIngredient WORKSTATION = EmiIngredient.of(AllTags.LOCATE_TARGET);
    public static final EmiStack SPELL_SCROLL = EmiStack.of(AllItems.SPELL_SCROLL.getStack(AllSpells.LOCATE));

    public EMILocateRecipe(LocateRecipe recipe)
    {
        this(
            recipe.getId(),
            EmiIngredient.of(recipe.getIngredient()),
            new LocationEmiStack(LocationStack.fromRecipe(recipe))
        );
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.LOCATE_CATEGORY;
    }

    @Override
    public List<EmiIngredient> getCatalysts()
    {
        return List.of(WORKSTATION, SPELL_SCROLL);
    }

    @Override
    public Identifier getId()
    {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs()
    {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs()
    {
        return List.of(location);
    }

    @Override
    public int getDisplayWidth()
    {
        return 84;
    }

    @Override
    public int getDisplayHeight()
    {
        return 26;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        int centerX = widgets.getWidth() / 2 + 1;
        int centerY = 4;

        widgets.addSlot(input, centerX - 20 * 2, centerY);

        widgets.addSlot(SPELL_SCROLL, centerX - 20, centerY)
            .catalyst(true)
            .drawBack(false);

        widgets.addSlot(WORKSTATION, centerX, centerY)
            .catalyst(true)
            .drawBack(false);

        widgets.addSlot(location, centerX + 20, centerY)
            .recipeContext(this);
    }
}
