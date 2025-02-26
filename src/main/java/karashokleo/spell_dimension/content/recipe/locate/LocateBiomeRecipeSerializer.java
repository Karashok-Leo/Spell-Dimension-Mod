package karashokleo.spell_dimension.content.recipe.locate;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class LocateBiomeRecipeSerializer implements RecipeSerializer<LocateBiomeRecipe>
{
    public static final LocateBiomeRecipeSerializer INSTANCE = new LocateBiomeRecipeSerializer();

    @Override
    public LocateBiomeRecipe read(Identifier id, JsonObject json)
    {
        Identifier world = new Identifier(JsonHelper.getString(json, "world"));
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getElement(json, "ingredient"));
        Identifier biomeTag = new Identifier(JsonHelper.getString(json, "biomeTag"));
        return new LocateBiomeRecipe(id, world, ingredient, biomeTag);
    }

    @Override
    public LocateBiomeRecipe read(Identifier id, PacketByteBuf buf)
    {
        Identifier world = buf.readIdentifier();
        Ingredient ingredient = Ingredient.fromPacket(buf);
        Identifier biomeTag = buf.readIdentifier();
        return new LocateBiomeRecipe(id, world, ingredient, biomeTag);
    }

    @Override
    public void write(PacketByteBuf buf, LocateBiomeRecipe recipe)
    {
        buf.writeIdentifier(recipe.world);
        recipe.ingredient.write(buf);
        buf.writeIdentifier(recipe.biomeTag);
    }
}
