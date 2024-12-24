package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.spell_dimension.client.compat.EntityWrapper;
import karashokleo.spell_dimension.config.recipe.SummonSpellConfig;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMISummonRecipe(EmiStack input, EntityWrapper wrapper) implements EmiRecipe
{
    public EMISummonRecipe(Item item, SummonSpellConfig.Entry entry)
    {
        this(EmiStack.of(item), EntityWrapper.of(entry));
    }

    public static final EmiStack CATALYSTS = EmiStack.of(Items.SPAWNER);

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.SUMMON_CATEGORY;
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
        return 100;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        int space = 18 + 2;

        widgets.addSlot(
                input,
                this.getDisplayWidth() / 2 - 24 - space * 2,
                (this.getDisplayHeight() - 18) / 2
        );

        widgets.addSlot(
                CATALYSTS,
                this.getDisplayWidth() / 2 - 24 - space,
                (this.getDisplayHeight() - 18) / 2
        ).catalyst(true).drawBack(false);

        widgets.addTexture(
                EmiTexture.EMPTY_ARROW,
                this.getDisplayWidth() / 2 - 24,
                (this.getDisplayHeight() - 18) / 2
        );

        widgets.addDrawable(
                0, 0, 40, 40,
                (draw, mouseX, mouseY, delta) -> wrapper.render(draw, this.getDisplayWidth() / 2 + 32, 75, mouseX, mouseY)
        );
    }
}
