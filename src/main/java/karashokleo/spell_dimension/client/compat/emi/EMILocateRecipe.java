package karashokleo.spell_dimension.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.spell_dimension.config.recipe.LocateSpellConfig;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMILocateRecipe(EmiStack input, Text spot, Text tooltip) implements EmiRecipe
{
    public static final EmiIngredient CATALYSTS = EmiIngredient.of(AllTags.LOCATE_TARGET);

    public EMILocateRecipe(Item item, RegistryKey<World> worldKey, RegistryKey<?> spotKey)
    {
        this(
                EmiStack.of(item),
                LocateSpellConfig.getSpotName(spotKey),
                Text.translatable(
                        "travelerstitles.%s.%s"
                                .formatted(
                                        worldKey.getValue().getNamespace(),
                                        worldKey.getValue().getPath()
                                )
                ).append(" - ").append(Text.of(spotKey.getValue().toString()))
        );
    }

    public EMILocateRecipe(Item item, RegistryKey<World> worldKey, TagKey<?> spotKey)
    {
        this(
                EmiStack.of(item),
                LocateSpellConfig.getSpotName(spotKey),
                Text.translatable(
                        "travelerstitles.%s.%s"
                                .formatted(
                                        worldKey.getValue().getNamespace(),
                                        worldKey.getValue().getPath()
                                )
                ).append(" - ").append(Text.of(spotKey.id().toString()))
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
        int width = MinecraftClient.getInstance().textRenderer.getWidth(spot);
        return 70 + width + 5;
    }

    @Override
    public int getDisplayHeight()
    {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        widgets.addSlot(input, 0, 0);
        widgets.addSlot(CATALYSTS, 20, 0).catalyst(true).drawBack(false);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 42, 0);
        widgets.addText(spot, 70, 4, -1, true);
        widgets.addTooltipText(List.of(tooltip), 0, 0, getDisplayWidth(), getDisplayHeight());
    }
}
