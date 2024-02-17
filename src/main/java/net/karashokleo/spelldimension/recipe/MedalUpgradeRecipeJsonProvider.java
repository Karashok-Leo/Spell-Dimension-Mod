package net.karashokleo.spelldimension.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.karashokleo.spelldimension.misc.Mage;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class MedalUpgradeRecipeJsonProvider implements RecipeJsonProvider
{
    final Identifier recipeId;
    final Ingredient ingredient;
    final Mage original;
    final Mage upgraded;

    public MedalUpgradeRecipeJsonProvider(Identifier recipeId, Ingredient ingredient, Mage original, Mage upgraded)
    {
        this.recipeId = recipeId;
        this.ingredient = ingredient;
        this.original = original;
        this.upgraded = upgraded;
    }

    @Override
    public void serialize(JsonObject json)
    {
        JsonArray ingredientsArray = new JsonArray();
        for (int i = 0; i < 8; ++i)
            ingredientsArray.add(ingredient.toJson());
        json.add(MedalUpgradeRecipe.INGREDIENTS_KEY, ingredientsArray);
        JsonObject originalJson = new JsonObject();
        original.writeToJson(originalJson);
        json.add(MedalUpgradeRecipe.ORIGINAL_KEY, originalJson);
        JsonObject upgradedJson = new JsonObject();
        upgraded.writeToJson(upgradedJson);
        json.add(MedalUpgradeRecipe.UPGRADED_KEY, upgradedJson);
    }

    @Override
    public Identifier getRecipeId()
    {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return MedalUpgradeRecipe.Serializer.INSTANCE;
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
