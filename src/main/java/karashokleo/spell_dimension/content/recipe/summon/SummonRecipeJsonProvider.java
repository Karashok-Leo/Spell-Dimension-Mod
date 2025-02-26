package karashokleo.spell_dimension.content.recipe.summon;

import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record SummonRecipeJsonProvider(
        Identifier recipeId,
        Ingredient ingredient,
        EntityType<?> entityType,
        int count
) implements RecipeJsonProvider
{
    @Override
    public void serialize(JsonObject json)
    {
        json.add("ingredient", ingredient.toJson());
        json.addProperty("entity_type", Registries.ENTITY_TYPE.getId(entityType).toString());
        json.addProperty("count", count);
    }

    @Override
    public Identifier getRecipeId()
    {
        return recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SummonRecipeSerializer.INSTANCE;
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
