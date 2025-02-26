package karashokleo.spell_dimension.content.recipe.spell;

import com.google.gson.JsonObject;
import karashokleo.enchantment_infusion.api.util.SerialUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class SpellInfusionRecipeSerializer implements RecipeSerializer<SpellInfusionRecipe>
{
    public static final SpellInfusionRecipeSerializer INSTANCE = new SpellInfusionRecipeSerializer();

    @Override
    public SpellInfusionRecipe read(Identifier id, JsonObject json)
    {
        Ingredient input = Ingredient.fromJson(JsonHelper.getElement(json, "input"));
        DefaultedList<Ingredient> ingredients = SerialUtil.ingredientsFromJsonArray(JsonHelper.getArray(json, "ingredients"));
        Identifier spellId = new Identifier(JsonHelper.getString(json, "spell_id"));
        return new SpellInfusionRecipe(id, input, ingredients, spellId);
    }

    @Override
    public SpellInfusionRecipe read(Identifier id, PacketByteBuf buf)
    {
        Ingredient input = Ingredient.fromPacket(buf);
        DefaultedList<Ingredient> ingredients = SerialUtil.ingredientsFromPacket(buf);
        Identifier spellId = buf.readIdentifier();
        return new SpellInfusionRecipe(id, input, ingredients, spellId);
    }

    @Override
    public void write(PacketByteBuf buf, SpellInfusionRecipe recipe)
    {
        recipe.input().write(buf);
        SerialUtil.ingredientsToPacket(buf, recipe.ingredients());
        buf.writeIdentifier(recipe.spellId());
    }
}
