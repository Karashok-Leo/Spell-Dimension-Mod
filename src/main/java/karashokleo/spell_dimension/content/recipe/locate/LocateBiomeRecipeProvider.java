package karashokleo.spell_dimension.content.recipe.locate;

import com.google.gson.JsonObject;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class LocateBiomeRecipeProvider extends LocateRecipeProvider
{
    protected final Identifier biomeTag;

    public LocateBiomeRecipeProvider(Identifier recipeId, Identifier world, Ingredient ingredient, Identifier biomeTag)
    {
        super(recipeId, world, ingredient);
        this.biomeTag = biomeTag;
    }

    @Override
    public void serialize(JsonObject json)
    {
        super.serialize(json);
        json.addProperty("biome", biomeTag.toString());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return LocateBiomeRecipeSerializer.INSTANCE;
    }
}
