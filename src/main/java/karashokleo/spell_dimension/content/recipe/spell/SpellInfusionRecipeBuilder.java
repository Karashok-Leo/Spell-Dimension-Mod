package karashokleo.spell_dimension.content.recipe.spell;

import com.google.gson.JsonObject;
import karashokleo.enchantment_infusion.api.util.SerialUtil;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpellInfusionRecipeBuilder
{
    private Ingredient input = null;
    private final List<Ingredient> ingredients = new ArrayList<>();

    public SpellInfusionRecipeBuilder withTableIngredient(Ingredient input)
    {
        this.input = input;
        return this;
    }

    public SpellInfusionRecipeBuilder withPedestalItem(int count, ItemConvertible item)
    {
        return withPedestalItem(count, Ingredient.ofItems(item));
    }

    public SpellInfusionRecipeBuilder withPedestalItem(int count, Ingredient ingredient)
    {
        for (int i = 0; i < count; i++)
        {
            ingredients.add(ingredient);
            if (ingredients.size() > 8)
                throw new UnsupportedOperationException();
        }
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, Identifier spellId)
    {
        if (ingredients.isEmpty())
            throw new IllegalArgumentException("No ingredients for spell infusion recipe");
        if (ingredients.size() > 8)
            throw new IllegalArgumentException("Too many ingredients for spell infusion recipe");
        exporter.accept(new SpellInfusionRecipeJsonProvider(recipeId, input, ingredients, spellId));
    }

    public record SpellInfusionRecipeJsonProvider(
            Identifier recipeId,
            Ingredient input,
            List<Ingredient> ingredients,
            Identifier spellId
    ) implements RecipeJsonProvider
    {
        @Override
        public void serialize(JsonObject json)
        {
            json.add("input", input.toJson());
            json.add("ingredients", SerialUtil.ingredientsToJsonArray(ingredients));
            json.addProperty("spell_id", spellId.toString());
        }

        @Override
        public Identifier getRecipeId()
        {
            return recipeId;
        }

        @Override
        public RecipeSerializer<?> getSerializer()
        {
            return SpellInfusionRecipeSerializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson()
        {
            return null;
        }

        @Nullable
        @Override
        public Identifier getAdvancementId()
        {
            return null;
        }
    }
}
