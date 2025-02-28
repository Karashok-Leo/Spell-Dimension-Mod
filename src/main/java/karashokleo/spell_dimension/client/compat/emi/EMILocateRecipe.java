package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.recipe.locate.LocateRecipe;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMILocateRecipe(EmiIngredient input, Text spot, Text tooltip) implements EmiRecipe
{
    public static final EmiIngredient WORKSTATION = EmiIngredient.of(AllTags.LOCATE_TARGET);
    public static final EmiStack SPELL_SCROLL = EmiStack.of(AllItems.SPELL_SCROLL.getStack(SpellDimension.modLoc("locate")));

    public EMILocateRecipe(LocateRecipe recipe)
    {
        this(
                EmiIngredient.of(recipe.getIngredient()),
                recipe.getTargetName(),
                recipe.getWorldName().append(" - ").append(Text.of(recipe.getTargetId().toString()))
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
    public @Nullable Identifier getId()
    {
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs()
    {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs()
    {
        return List.of();
    }

    @Override
    public int getDisplayWidth()
    {
        return 200;
    }

    @Override
    public int getDisplayHeight()
    {
        return 38;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        int centerX = widgets.getWidth() / 2;

        widgets.addSlot(SPELL_SCROLL, centerX - 9 - 20, 4)
                .catalyst(true)
                .drawBack(false);

        widgets.addSlot(input, centerX - 9, 4);

        widgets.addSlot(WORKSTATION, centerX - 9 + 20, 4)
                .catalyst(true)
                .drawBack(false);

        int width = MinecraftClient.getInstance().textRenderer.getWidth(spot);
        widgets.addText(spot, centerX - width / 2, 26, -1, true);

        widgets.addTooltipText(List.of(tooltip), 0, 0, getDisplayWidth(), getDisplayHeight());
    }
}
