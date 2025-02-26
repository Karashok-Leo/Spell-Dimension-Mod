package karashokleo.spell_dimension.content.recipe.locate;

import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class LocateRecipeJsonProvider implements RecipeJsonProvider
{
    protected final Identifier recipeId;
    protected final Identifier world;
    protected final Ingredient ingredient;

    protected LocateRecipeJsonProvider(Identifier recipeId, Identifier world, Ingredient ingredient)
    {
        this.recipeId = recipeId;
        this.world = world;
        this.ingredient = ingredient;
    }

    @Override
    public void serialize(JsonObject json)
    {
        json.addProperty("world", world.toString());
        json.add("ingredient", ingredient.toJson());
    }

    @Override
    public Identifier getRecipeId()
    {
        return recipeId;
    }

    @Override
    public @Nullable JsonObject toAdvancementJson()
    {
        return null;
    }

    @Override
    public @Nullable Identifier getAdvancementId()
    {
        return null;
    }
}
