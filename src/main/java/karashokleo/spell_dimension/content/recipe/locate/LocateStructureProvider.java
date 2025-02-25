package karashokleo.spell_dimension.content.recipe.locate;

import com.google.gson.JsonObject;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class LocateStructureProvider extends LocateRecipeProvider
{
    protected final Identifier structure;

    public LocateStructureProvider(Identifier recipeId, Identifier world, Ingredient ingredient, Identifier structure)
    {
        super(recipeId, world, ingredient);
        this.structure = structure;
    }

    @Override
    public void serialize(JsonObject json)
    {
        super.serialize(json);
        json.addProperty("structure", structure.toString());
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return LocateStructureRecipeSerializer.INSTANCE;
    }
}
