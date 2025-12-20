package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.l2hostility.compat.shared.LivingEntityWrapper;
import karashokleo.spell_dimension.client.compat.SummonedEntityWrapperFactory;
import karashokleo.spell_dimension.content.recipe.summon.SummonRecipe;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMISummonRecipe(EmiIngredient input, LivingEntityWrapper wrapper) implements EmiRecipe
{
    public static final EmiStack WORKSTATION = EmiStack.of(Items.SPAWNER);
    public static final EmiStack SPELL_SCROLL = EmiStack.of(AllItems.SPELL_SCROLL.getStack(AllSpells.SUMMON));

    public EMISummonRecipe(SummonRecipe recipe)
    {
        this(EmiIngredient.of(recipe.ingredient()), SummonedEntityWrapperFactory.of(recipe.entityType(), recipe.count()));
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.SUMMON_CATEGORY;
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
        return 144;
    }

    @Override
    public int getDisplayHeight()
    {
        return 110;
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

        widgets.addDrawable(
            0, 0, 40, 40,
            (draw, mouseX, mouseY, delta) -> wrapper.render(draw, centerX, 84, mouseX, mouseY)
        );
    }
}
