package karashokleo.spell_dimension.content.recipe.locate;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class LocateStructureRecipeSerializer implements RecipeSerializer<LocateStructureRecipe>
{
    public static final LocateStructureRecipeSerializer INSTANCE = new LocateStructureRecipeSerializer();

    @Override
    public LocateStructureRecipe read(Identifier id, JsonObject json)
    {
        Identifier world = new Identifier(JsonHelper.getString(json, "world"));
        Ingredient ingredient = Ingredient.fromJson(JsonHelper.getElement(json, "ingredient"));
        Identifier structure = new Identifier(JsonHelper.getString(json, "structure"));
        return new LocateStructureRecipe(id, world, ingredient, structure);
    }

    @Override
    public LocateStructureRecipe read(Identifier id, PacketByteBuf buf)
    {
        Identifier world = buf.readIdentifier();
        Ingredient ingredient = Ingredient.fromPacket(buf);
        Identifier structure = buf.readIdentifier();
        return new LocateStructureRecipe(id, world, ingredient, structure);
    }

    @Override
    public void write(PacketByteBuf buf, LocateStructureRecipe recipe)
    {
        buf.writeIdentifier(recipe.world);
        recipe.ingredient.write(buf);
        buf.writeIdentifier(recipe.structure);
    }
}
