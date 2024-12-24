package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.spell_dimension.init.AllBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMIInfusionRecipe(
        EmiStack base,
        EmiStack addition,
        EmiStack output
) implements EmiRecipe
{
    public static final EmiStack CATALYSTS = EmiStack.of(AllBlocks.SPELL_INFUSION_PEDESTAL.item());

    public EMIInfusionRecipe(Item base, Item addition, ItemStack output)
    {
        this(
                EmiStack.of(base),
                EmiStack.of(addition),
                EmiStack.of(output)
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
        return List.of(CATALYSTS);
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
        widgets.addSlot(addition, 20, 0).catalyst(true);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 42, 0);
        widgets.addSlot(output, 70, 0);
    }
}
