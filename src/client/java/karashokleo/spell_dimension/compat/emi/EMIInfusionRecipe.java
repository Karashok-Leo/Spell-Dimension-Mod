package karashokleo.spell_dimension.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.spell_dimension.config.recipe.InfusionRecipes;
import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMIInfusionRecipe(
        EmiStack base,
        EmiStack addition,
        EmiStack output,
        boolean consume,
        int time
) implements EmiRecipe
{
    public EMIInfusionRecipe(InfusionRecipes.RecipeEntry entry)
    {
        this(
                EmiStack.of(entry.base()),
                EmiStack.of(entry.addition()),
                EmiStack.of(entry.output()),
                entry.consume(),
                entry.time()
        );
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.SPELL_INFUSION_CATEGORY;
    }

    @Override
    public List<EmiIngredient> getCatalysts()
    {
        return List.of(EmiStack.of(AllBlocks.SPELL_INFUSION_PEDESTAL.item()));
    }

    @Override
    public @Nullable Identifier getId()
    {
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs()
    {
        return List.of(base, addition);
    }

    @Override
    public List<EmiStack> getOutputs()
    {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth()
    {
        return 88;
    }

    @Override
    public int getDisplayHeight()
    {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        widgets.addSlot(base, 0, 0);
        SlotWidget additionSlot = widgets.addSlot(addition, 20, 0).catalyst(true);
        if (!consume) additionSlot.appendTooltip(SDTexts.TOOLTIP$NOT_CONSUMED.get());
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 42, 0);
        widgets.addSlot(output, 70, 0);
        widgets.addTooltipText(List.of(SDTexts.TOOLTIP$TOOK_SECONDS.get(time / 20)), 0, 0, getDisplayWidth(), getDisplayHeight());
    }
}
